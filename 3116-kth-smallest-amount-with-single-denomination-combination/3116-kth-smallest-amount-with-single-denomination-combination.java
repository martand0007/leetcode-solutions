class Solution {
    public long findKthSmallest(int[] coins, int k) {
        // Remove redundant coins.
        // If a coin is divisible by a smaller coin, it never creates
        // a new amount, so it can be ignored.
        java.util.Arrays.sort(coins);

        java.util.List<Integer> useful = new java.util.ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (int x : useful) {
                if (coin % x == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                useful.add(coin);
            }
        }

        int n = useful.size();

        // The kth answer is at most minCoin * k.
        long lo = 1;
        long hi = (long) useful.get(0) * k;

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;

            if (count(mid, useful, n) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private long count(long x, java.util.List<Integer> coins, int n) {
        long result = 0;

        // Inclusion-exclusion over all non-empty subsets.
        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long g = gcd(lcm, coins.get(i));
                    long next = lcm / g * coins.get(i);

                    // If LCM > x, this subset contributes 0.
                    if (next > x) {
                        overflow = true;
                        break;
                    }

                    lcm = next;
                }
            }

            if (overflow) {
                continue;
            }

            long multiples = x / lcm;

            if ((bits & 1) == 1) {
                result += multiples;
            } else {
                result -= multiples;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}