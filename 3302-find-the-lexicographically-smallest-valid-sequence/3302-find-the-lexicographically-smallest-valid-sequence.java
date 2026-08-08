class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        /*
         * dp[i] = maximum length of a suffix of word2
         * that can be matched exactly using word1[i...].
         */
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int pos = 0;
        j = 0;
        boolean usedMismatch = false;

        while (pos < n && j < m) {

            // Exact match.
            if (word1.charAt(pos) == word2.charAt(j)) {
                ans[j] = pos;
                pos++;
                j++;
            }

            // Use the one allowed mismatch.
            else if (!usedMismatch &&
                     dp[pos + 1] >= m - j - 1) {

                ans[j] = pos;
                pos++;
                j++;
                usedMismatch = true;
            }

            // Current index cannot be used.
            else {
                pos++;
            }
        }

        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}
