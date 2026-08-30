
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIdx = 0;
        int maxIdx = 0;

        // Find indices of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }

            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Option 1: Remove both from the front
        int fromFront = right + 1;

        // Option 2: Remove both from the back
        int fromBack = n - left;

        // Option 3: Remove left one from front and right one from back
        int fromBothEnds = (left + 1) + (n - right);

        return Math.min(fromFront, Math.min(fromBack, fromBothEnds));
    }
}

