package company.infosys.spdse;

import java.util.*;

class Second {

    public static int solve(int N, String s, int[] w) {

        int[][] dp = new int[N][N];

        // Base case: single character
        for (int i = 0; i < N; i++) {
            dp[i][i] = w[i];
        }

        // Fill DP table
        for (int len = 2; len <= N; len++) {
            for (int i = 0; i <= N - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    if (i + 1 <= j - 1)
                        dp[i][j] = dp[i + 1][j - 1] + w[i] + w[j];
                    else
                        dp[i][j] = w[i] + w[j]; // adjacent case
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        sc.nextLine(); // IMPORTANT: consume newline

        String s = sc.nextLine().trim();

        int[] w = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = sc.nextInt();
        }

        int result = solve(N, s, w);
        System.out.println(result);
        sc.close();
    }
}