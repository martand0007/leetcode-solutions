class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int left = 0;
        int ones = 0;
        int minLen = Integer.MAX_VALUE;
        String ans = "";

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Shrink while we still have at least k ones.
            while (ones >= k) {
                if (ones == k) {
                    int len = right - left + 1;
                    String cur = s.substring(left, right + 1);

                    if (len < minLen ||
                        (len == minLen && cur.compareTo(ans) < 0)) {
                        minLen = len;
                        ans = cur;
                    }
                }

                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }
        }

        return ans;
    }
}