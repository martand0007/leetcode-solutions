import java.util.*;

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        int[] rev = new int[n];
        for (int i = 0; i < n; i++) rev[i] = nums[n - 1 - i];

        int[][] dist = new int[2][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int fl = cur[0], off = cur[1];
            int d = dist[fl][off];

            // Check if current state is sorted
            int[] arr = (fl == 0) ? nums : rev;
            boolean sorted = true;
            for (int i = 0; i < n; i++) {
                if (arr[(off + i) % n] != i) { sorted = false; break; }
            }
            if (sorted) return d;

            // Op 1: Rotate Left
            int newOff = (off + 1) % n;
            if (dist[fl][newOff] > d + 1) {
                dist[fl][newOff] = d + 1;
                queue.offer(new int[]{fl, newOff});
            }

            // Op 2: Flip
            // rotation `off` of nums reversed = rotation `(n - off) % n` of rev(nums)
            int newFl = 1 - fl;
            int newOff2 = (n - off) % n;  // ✅ Fixed formula
            if (dist[newFl][newOff2] > d + 1) {
                dist[newFl][newOff2] = d + 1;
                queue.offer(new int[]{newFl, newOff2});
            }
        }

        return -1;
    }
}