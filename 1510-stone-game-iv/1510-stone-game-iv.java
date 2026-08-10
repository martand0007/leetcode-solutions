class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];

        // dp[0] = false:
        // no move is possible, so the player to move loses.

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                int remaining = i - j * j;

                // If the opponent loses from this state,
                // current player wins.
                if (!dp[remaining]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}