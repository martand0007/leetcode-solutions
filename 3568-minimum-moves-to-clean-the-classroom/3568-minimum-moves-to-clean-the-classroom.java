import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1, startC = -1;
        List<int[]> litter = new ArrayList<>();

        // Find starting position and litter
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litter.add(new int[]{r, c});
                }
            }
        }

        int k = litter.size();

        // No litter
        if (k == 0) {
            return 0;
        }

        // Assign an ID to every litter cell
        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < k; i++) {
            int r = litter.get(i)[0];
            int c = litter.get(i)[1];
            litterId[r][c] = i;
        }

        int allCollected = (1 << k) - 1;

        // visited[row][col][mask][energy]
        boolean[][][][] visited =
                new boolean[m][n][1 << k][energy + 1];

        // row, col, mask, remainingEnergy, moves
        Queue<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[]{
                startR, startC, 0, energy, 0
        });

        visited[startR][startC][0][energy] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] state = queue.poll();

            int r = state[0];
            int c = state[1];
            int mask = state[2];
            int currentEnergy = state[3];
            int moves = state[4];

            // All litter collected
            if (mask == allCollected) {
                return moves;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Cannot move without energy
                if (currentEnergy == 0) {
                    continue;
                }

                int newEnergy = currentEnergy - 1;
                int newMask = mask;

                // Collect litter
                if (litterId[nr][nc] != -1) {
                    newMask |= (1 << litterId[nr][nc]);
                }

                // Reset energy at R
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (!visited[nr][nc][newMask][newEnergy]) {
                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(new int[]{
                            nr, nc, newMask, newEnergy, moves + 1
                    });
                }
            }
        }

        return -1;
    }
}