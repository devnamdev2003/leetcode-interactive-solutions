package company.infosys.spdse;

import java.util.*;

class Third {
    // Memoization tables
    static long[][][] memo;
    static boolean[][][] visited;
    static int[] V;

    public static long solve(int N, int[] val) {
        if (N == 0)
            return 0;

        long initialScore = val[0];
        if (N == 1)
            return initialScore;

        V = val;
        // Re-initialize for every test case to prevent static data leakage
        memo = new long[N][N][2];
        visited = new boolean[N][N][2];

        // Start DP from Turn 0 (Robot A)
        return initialScore + dp(0, N - 1, 0);
    }

    private static long dp(int a, int b, int turn) {
        if (visited[a][b][turn]) {
            return memo[a][b][turn];
        }

        visited[a][b][turn] = true;
        long res;

        if (turn == 0) {
            // Robot A's Turn (Maximize Score)
            long opt1 = 0; // Default 0 handles ending the game intentionally
            if (a + 1 < b) {
                opt1 = V[a + 1] + dp(a + 1, b, 1);
            }

            long opt2 = 0;
            if (a + 2 < b) {
                // Bypasses a+1, only collects V[a+2]
                opt2 = V[a + 2] + dp(a + 2, b, 1);
            }

            res = Math.max(opt1, opt2);

        } else {
            // Robot B's Turn (Minimize Score)
            long opt1 = 0;
            if (b - 1 > a) {
                opt1 = dp(a, b - 1, 0);
            }

            long opt2 = 0;
            if (b - 2 > a) {
                opt2 = dp(a, b - 2, 0);
            }

            res = Math.min(opt1, opt2);
        }

        return memo[a][b][turn] = res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Process all test cases that the platform throws
        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int[] V = new int[N];
            for (int i = 0; i < N; i++) {
                V[i] = sc.nextInt();
            }

            long result = solve(N, V);
            System.out.println(result);
        }
        sc.close();
    }
}