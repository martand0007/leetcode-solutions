class Solution {

    static final int LIMIT = 1_000_000;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        String mid = "";
        int[] half = new int[26];
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                mid = String.valueOf((char) ('a' + i));
            }
            half[i] = freq[i] / 2;
            halfLen += half[i];
        }

        PrimeHelper helper = new PrimeHelper(halfLen);

        if (countWays(half, halfLen, helper) < k) {
            return "";
        }

        StringBuilder left = new StringBuilder();

        int remaining = halfLen;

        while (remaining > 0) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                half[c]--;

                long ways = countWays(half, remaining - 1, helper);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    remaining--;
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        String right = left.reverse().toString();
        left.reverse();

        return left.toString() + mid + right;
    }

    private long countWays(int[] cnt, int total, PrimeHelper helper) {
        int[] exp = new int[helper.primes.length];

        System.arraycopy(helper.factExp[total], 0, exp, 0, exp.length);

        for (int x : cnt) {
            if (x == 0) continue;
            int[] arr = helper.factExp[x];
            for (int i = 0; i < exp.length; i++) {
                exp[i] -= arr[i];
            }
        }

        long res = 1;

        for (int i = 0; i < exp.length; i++) {
            int e = exp[i];
            int p = helper.primes[i];

            while (e-- > 0) {
                res *= p;
                if (res >= LIMIT) return LIMIT;
            }
        }

        return res;
    }

    static class PrimeHelper {
        int[] primes;
        int[][] factExp;

        PrimeHelper(int n) {
            buildPrimes(n);
            buildFactorialExponents(n);
        }

        private void buildPrimes(int n) {
            boolean[] comp = new boolean[n + 1];
            java.util.ArrayList<Integer> list = new java.util.ArrayList<>();

            for (int i = 2; i <= n; i++) {
                if (!comp[i]) {
                    list.add(i);
                    for (long j = 1L * i * i; j <= n; j += i) {
                        comp[(int) j] = true;
                    }
                }
            }

            primes = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                primes[i] = list.get(i);
            }
        }

        private void buildFactorialExponents(int n) {
            factExp = new int[n + 1][primes.length];

            for (int i = 1; i <= n; i++) {
                System.arraycopy(factExp[i - 1], 0, factExp[i], 0, primes.length);

                int x = i;

                for (int j = 0; j < primes.length && 1L * primes[j] * primes[j] <= x; j++) {
                    int p = primes[j];
                    while (x % p == 0) {
                        factExp[i][j]++;
                        x /= p;
                    }
                }

                if (x > 1) {
                    int idx = java.util.Arrays.binarySearch(primes, x);
                    factExp[i][idx]++;
                }
            }
        }
    }
}