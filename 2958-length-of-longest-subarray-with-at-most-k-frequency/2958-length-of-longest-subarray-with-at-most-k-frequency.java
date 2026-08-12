import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {
            // Add nums[right] to the window
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);

            // Shrink window if nums[right] occurs more than k times
            while (freq.get(nums[right]) > k) {
                freq.put(nums[left], freq.get(nums[left]) - 1);
                left++;
            }

            // Current window [left, right] is good
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}