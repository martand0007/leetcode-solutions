
class Solution {
    public int minOperations(String s) {
        int n = s.length();

        // Already sorted
        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }

        if (sorted) return 0;

        // A length-2 string cannot be sorted in one operation,
        // because the entire string cannot be selected.
        if (n == 2) return -1;

        char min = 'z';
        char max = 'a';

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            min = (char) Math.min(min, c);
            max = (char) Math.max(max, c);
        }

        // One operation is sufficient
        if (s.charAt(0) == min || s.charAt(n - 1) == max) {
            return 1;
        }

        // If min or max occurs more than once, two operations
        // are sufficient even when the endpoints are reversed.
        int minCount = 0;
        int maxCount = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == min) minCount++;
            if (s.charAt(i) == max) maxCount++;
        }

        // If the first character is max and the last is min,
        // check whether either extreme occurs more than once.
        if (s.charAt(0) == max && s.charAt(n - 1) == min) {
            return (minCount > 1 || maxCount > 1) ? 2 : 3;
        }

        return 2;
    }
}
