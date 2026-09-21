class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] ans = new long[k];

        // dp[r] = number of subarrays ending at the
        // current index whose product % k == r
        long[] dp = new long[k];

        for (int num : nums) {

            int rem = num % k;

            // New DP for subarrays ending at current num
            long[] newDp = new long[k];

            // Subarray containing only the current element
            newDp[rem] = 1;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] > 0) {
                    int newRem = (int) ((long) r * rem % k);

                    newDp[newRem] += dp[r];
                }
            }

            // Add current subarrays to answer
            for (int r = 0; r < k; r++) {
                ans[r] += newDp[r];
            }

            dp = newDp;
        }

        return ans;
    }
}