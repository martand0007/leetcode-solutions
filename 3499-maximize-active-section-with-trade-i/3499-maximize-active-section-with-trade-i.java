import java.util.*;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        String t = "1" + s + "1";

        int originalOnes = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') originalOnes++;
        }

        List<Character> chars = new ArrayList<>();
        List<Integer> lens = new ArrayList<>();

        int i = 0;
        while (i < t.length()) {
            char ch = t.charAt(i);
            int j = i;
            while (j < t.length() && t.charAt(j) == ch) {
                j++;
            }
            chars.add(ch);
            lens.add(j - i);
            i = j;
        }

        int bestGain = 0;

        for (int k = 1; k < chars.size() - 1; k++) {
            if (chars.get(k) == '1') {
                bestGain = Math.max(bestGain, lens.get(k - 1) + lens.get(k + 1));
            }
        }

        return originalOnes + bestGain;
    }
}