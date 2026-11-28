
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int[] freq = new int[n + 1];

        long minPrice = Long.MAX_VALUE;

        // Find minimum price and count factors
        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Calculate free-item opportunities
        int[] gain = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                gain[i] += freq[j];
            }
            gain[i]--;
        }

        // Collect profitable purchases
        List<long[]> options = new ArrayList<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (gain[factor] > 0 && price < 2L * minPrice) {
                options.add(new long[]{price, gain[factor]});
            }
        }

        // Sort by price
        options.sort(Comparator.comparingLong(a -> a[0]));

        long remaining = budget;
        long answer = 0;

        // Buy double-value copies
        for (long[] option : options) {
            long price = option[0];
            long count = option[1];

            long take = Math.min(count, remaining / price);

            answer += take * 2;
            remaining -= take * price;
        }

        // Buy ordinary copies
        answer += remaining / minPrice;

        return (int) answer;
    }
}
