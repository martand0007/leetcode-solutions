class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }

        // Alice can initially take all n stones.
        long dp = prefix[n];

        // Consider taking the first i stones.
        // i must be >= 2.
        for (int i = n - 1; i >= 2; i--) {
            dp = Math.max(dp, prefix[i] - dp);
        }

        return (int) dp;
    }
}