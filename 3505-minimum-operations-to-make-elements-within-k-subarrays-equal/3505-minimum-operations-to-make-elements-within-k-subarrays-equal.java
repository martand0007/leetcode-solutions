class Solution {
    class Fenwick {
        int n;
        long[] count, sum;

        Fenwick(int n) {
            this.n = n;
            count = new long[n + 1];
            sum = new long[n + 1];
        }

        void update(int i, long c, long s) {
            while (i <= n) {
                count[i] += c;
                sum[i] += s;
                i += i & -i;
            }
        }

        long queryCount(int i) {
            long ans = 0;
            while (i > 0) {
                ans += count[i];
                i -= i & -i;
            }
            return ans;
        }

        long querySum(int i) {
            long ans = 0;
            while (i > 0) {
                ans += sum[i];
                i -= i & -i;
            }
            return ans;
        }

        int kth(long k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && count[next] < k) {
                    idx = next;
                    k -= count[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;
        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            ) + 1;
        }

        Fenwick bit = new Fenwick(m);

        // Calculate cost of every window of size x
        int windows = n - x + 1;
        long[] cost = new long[windows];

        for (int i = 0; i < x; i++) {
            bit.update(rank[i], 1, nums[i]);
        }

        for (int i = 0; i < windows; i++) {

            // Lower median
            int mid = bit.kth((x + 1) / 2);
            long median = sorted[mid - 1];

            long leftCount = bit.queryCount(mid);
            long leftSum = bit.querySum(mid);

            long totalSum = bit.querySum(m);
            long totalCount = x;

            long rightCount = totalCount - leftCount;
            long rightSum = totalSum - leftSum;

            cost[i] =
                median * leftCount - leftSum +
                rightSum - median * rightCount;

            // Slide window
            if (i + x < n) {
                bit.update(rank[i], -1, -nums[i]);
                bit.update(rank[i + x], 1, nums[i + x]);
            }
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];

        for (int j = 1; j <= k; j++) {
            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Do not select a window ending at i
                curr[i] = curr[i - 1];

                // Select window [i-x, i-1]
                if (i >= x && prev[i - x] != INF) {
                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}