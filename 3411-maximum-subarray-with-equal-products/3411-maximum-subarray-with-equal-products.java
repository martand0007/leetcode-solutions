class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            int gcd = 0;
            int lcm = 1;

            for (int j = i; j < n; j++) {
                prod *= nums[j];

                gcd = findGCD(gcd, nums[j]);

                lcm = findLCM(lcm, nums[j]);

                if (prod == (long) gcd * lcm) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private int findLCM(int a, int b) {
        return (a / findGCD(a, b)) * b;
    }
}