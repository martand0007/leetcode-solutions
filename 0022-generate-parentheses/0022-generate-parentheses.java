import java.util.*;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(result, new StringBuilder(), 0, 0, n);

        return result;
    }

    private void backtrack(List<String> result,
                            StringBuilder sb,
                            int open,
                            int close,
                            int n) {

        // Base case
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }

        // Add '(' if we still have opening brackets available
        if (open < n) {
            sb.append('(');

            backtrack(result, sb, open + 1, close, n);

            // Backtrack
            sb.deleteCharAt(sb.length() - 1);
        }

        // Add ')' only if it won't make the sequence invalid
        if (close < open) {
            sb.append(')');

            backtrack(result, sb, open, close + 1, n);

            // Backtrack
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}