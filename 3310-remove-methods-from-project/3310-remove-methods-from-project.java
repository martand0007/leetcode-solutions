import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
        }

        // 1. Find all suspicious methods reachable from k.
        boolean[] suspicious = new boolean[n];
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(k);
        suspicious[k] = true;

        while (!queue.isEmpty()) {
            int method = queue.poll();

            for (int next : graph[method]) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    queue.offer(next);
                }
            }
        }

        // 2. Check whether an outside method invokes a suspicious method.
        for (int[] edge : invocations) {
            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {
                // Cannot remove suspicious methods.
                List<Integer> result = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    result.add(i);
                }
                return result;
            }
        }

        // 3. Removal is valid: return only non-suspicious methods.
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }
}