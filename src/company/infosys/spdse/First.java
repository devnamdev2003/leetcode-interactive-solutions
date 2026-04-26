package company.infosys.spdse;

import java.util.*;

class First {
    static long globalMax = Long.MIN_VALUE;
    static long[] bit;
    static int[] modified;
    static int modCount;
    static int offset;

    // Update Fenwick tree with minimum weight
    static void update(int i, long val) {
        for (; i < bit.length; i += i & -i) {
            if (bit[i] == Long.MAX_VALUE) {
                modified[modCount++] = i; // Track modified indices for quick reset
            }
            bit[i] = Math.min(bit[i], val);
        }
    }

    // Query Fenwick tree for minimum weight
    static long query(int i) {
        long min = Long.MAX_VALUE;
        for (; i > 0; i -= i & -i) {
            min = Math.min(min, bit[i]);
        }
        return min;
    }

    // Fast reset of the Fenwick tree
    static void clearBit() {
        for (int j = 0; j < modCount; j++) {
            bit[modified[j]] = Long.MAX_VALUE;
        }
        modCount = 0;
    }

    public static long solve(int N, int C, int[] A, int[] W) {
        globalMax = Long.MIN_VALUE;

        // Group indices by category
        Map<Integer, List<Integer>> occ = new HashMap<>();
        for (int i = 0; i < N; i++) {
            occ.computeIfAbsent(A[i], k -> new ArrayList<>()).add(i);
        }

        int T = 400; // Threshold for Sqrt Decomposition
        bit = new long[2 * N + 2];
        Arrays.fill(bit, Long.MAX_VALUE);
        modified = new int[2 * N + 2];
        offset = N + 1; // Offset for negative prefix sums

        for (Map.Entry<Integer, List<Integer>> entry : occ.entrySet()) {
            int c = entry.getKey();
            List<Integer> list = entry.getValue();
            int k = list.size();

            if (k <= T) {
                // STRATEGY 1: Small Categories (O(K^2) Sliding Window)
                // Any valid window must have length <= 2K - 1
                for (int i = 0; i < k; i++) {
                    // Partition valid windows by forcing list.get(i) to be the FIRST occurrence in
                    // the window
                    int min_l = (i == 0) ? 0 : (list.get(i - 1) + 1);
                    int start_l = Math.max(min_l, list.get(i) - 2 * k + 1);

                    for (int l = start_l; l <= list.get(i); l++) {
                        int count = 0;
                        long currentWeight = 0;
                        int max_r = Math.min(N - 1, l + 2 * k - 1);

                        for (int r = l; r <= max_r; r++) {
                            currentWeight += W[r];
                            if (A[r] == c)
                                count++;

                            // Check majority condition
                            if (count * 2 > (r - l + 1)) {
                                if (currentWeight > globalMax) {
                                    globalMax = currentWeight;
                                }
                            }
                        }
                    }
                }
            } else {
                // STRATEGY 2: Large Categories (O(N log N) Fenwick Tree)
                modCount = 0;
                update(offset, 0); // Base case for prefix sum at index 0

                long current_P = 0;
                long current_S = 0;

                for (int i = 0; i < N; i++) {
                    // +1 if it is the category, -1 otherwise
                    current_P += (A[i] == c) ? 1 : -1;
                    current_S += W[i]; // Accumulate weight

                    // We want previous prefix_P <= current_P - 1
                    int query_idx = (int) current_P - 1 + offset;
                    long min_prev_S = query(query_idx);

                    // If a valid previous state exists, update globalMax
                    if (min_prev_S != Long.MAX_VALUE) {
                        if (current_S - min_prev_S > globalMax) {
                            globalMax = current_S - min_prev_S;
                        }
                    }

                    // Register current state
                    update((int) current_P + offset, current_S);
                }
                clearBit(); // Clean up for the next large category
            }
        }
        return globalMax;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Supports sequential test case inputs commonly used in HackerRank/CodeChef
        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int C = sc.nextInt();

            int[] A = new int[N];
            for (int i = 0; i < N; i++)
                A[i] = sc.nextInt();

            int[] W = new int[N];
            for (int i = 0; i < N; i++)
                W[i] = sc.nextInt();

            System.out.println(solve(N, C, A, W));
        }
        sc.close();
    }
}