class Solution {
    public boolean predictTheWinner(int[] nums) {
        Integer[][] memo = new Integer[nums.length][nums.length];

        return solve(nums, 0, nums.length - 1, memo) >= 0;
    }

    private int solve(int[] nums, int left, int right, Integer[][] memo) {
        // Only one number remains
        if (left == right) {
            return nums[left];
        }

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        // Take left
        int takeLeft = nums[left] - solve(nums, left + 1, right, memo);

        // Take right
        int takeRight = nums[right] - solve(nums, left, right - 1, memo);

        memo[left][right] = Math.max(takeLeft, takeRight);

        return memo[left][right];
    }
}