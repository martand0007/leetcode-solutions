
class Solution {
    public int minInsertions(String s) {
        int ans = 0;
        int open = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else {
                if (i + 1 < n && s.charAt(i + 1) == ')') {
                    i++; // Consume the pair ))
                } else {
                    ans++; // Insert one missing )
                }

                if (open == 0) {
                    ans++; // Insert a missing (
                } else {
                    open--;
                }
            }
        }

        ans += open * 2;
        return ans;
    }
}
