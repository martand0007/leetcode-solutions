class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int x : stones) {
            cnt[x % 3]++;
        }

        // If the number of 0-mod-3 stones is even,
        // Alice wins iff there is at least one 1 and one 2.
        if (cnt[0] % 2 == 0) {
            return cnt[1] > 0 && cnt[2] > 0;
        }

        // If the number of 0-mod-3 stones is odd,
        // Alice wins iff the counts of 1 and 2 differ by more than 2.
        return Math.abs(cnt[1] - cnt[2]) > 2;
    }
}