class Solution {
    public boolean checkValidString(String s) {
        int min = 0; // minimum possible open brackets
        int max = 0; // maximum possible open brackets

        for (char c : s.toCharArray()) {

            if (c == '(') {
                min++;
                max++;
            } 
            else if (c == ')') {
                min--;
                max--;
            } 
            else { // '*'
                min--; // '*' acts as ')'
                max++; // '*' acts as '('
            }

            // Too many ')' even in the best case
            if (max < 0) {
                return false;
            }

            // Minimum cannot be negative
            min = Math.max(min, 0);
        }

        // If minimum open brackets can become 0, string is valid
        return min == 0;
    }
}