import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs,
                              int x, int[] queries) {

        // Sort logs by time
        Arrays.sort(logs, (a, b) ->
            Integer.compare(a[1], b[1]));

        int m = queries.length;

        // Store queries with their original indices
        int[][] sortedQueries = new int[m][2];

        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // Sort queries by time
        Arrays.sort(sortedQueries, (a, b) ->
            Integer.compare(a[0], b[0]));

        int[] freq = new int[n + 1];
        int[] ans = new int[m];

        int left = 0;
        int right = 0;
        int activeServers = 0;

        for (int[] query : sortedQueries) {

            int q = query[0];
            int index = query[1];

            int start = q - x;

            // Add logs within the upper time limit
            while (right < logs.length &&
                   logs[right][1] <= q) {

                int server = logs[right][0];

                if (freq[server] == 0) {
                    activeServers++;
                }

                freq[server]++;
                right++;
            }

            // Remove logs outside the time window
            while (left < right &&
                   logs[left][1] < start) {

                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    activeServers--;
                }

                left++;
            }

            // Servers with zero requests
            ans[index] = n - activeServers;
        }

        return ans;
    }
}