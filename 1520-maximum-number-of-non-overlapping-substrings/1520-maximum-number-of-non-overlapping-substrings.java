import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        // First and last occurrence of each character
        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        // Store all valid minimal intervals [start, end]
        List<int[]> intervals = new ArrayList<>();

        for (int c = 0; c < 26; c++) {
            if (first[c] == n) continue;

            int start = first[c];
            int end = last[c];

            boolean valid = true;

            // Expand interval whenever we find a character
            // whose complete range is outside the current interval.
            for (int i = start; i <= end; i++) {
                int current = s.charAt(i) - 'a';

                if (first[current] < start) {
                    valid = false;
                    break;
                }

                end = Math.max(end, last[current]);
            }

            if (valid) {
                intervals.add(new int[]{start, end});
            }
        }

        /*
         * Choose maximum number of non-overlapping intervals.
         * Since every interval is a minimal valid substring,
         * greedy selection by earliest ending position works.
         */
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        List<String> result = new ArrayList<>();
        int prevEnd = -1;

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            if (start > prevEnd) {
                result.add(s.substring(start, end + 1));
                prevEnd = end;
            }
        }

        return result;
    }
}