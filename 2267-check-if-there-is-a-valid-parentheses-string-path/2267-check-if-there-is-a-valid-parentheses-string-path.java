class Solution {
    int m, n;
    char[][] grid;
    boolean[][][] visited;

    public boolean hasValidPath(char[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        // Path length must be even
        if ((m + n - 1) % 2 == 1) {
            return false;
        }

        // First must be '(' and last must be ')'
        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        visited = new boolean[m][n][m + n];

        return dfs(0, 0, 0);
    }

    private boolean dfs(int row, int col, int balance) {

        // Add current cell
        if (grid[row][col] == '(') {
            balance++;
        } else {
            balance--;
        }

        // Invalid prefix
        if (balance < 0) {
            return false;
        }

        // Not enough cells remaining to close all '('
        int remaining = (m - row - 1) + (n - col - 1);

        if (balance > remaining) {
            return false;
        }

        // Reached destination
        if (row == m - 1 && col == n - 1) {
            return balance == 0;
        }

        // Same state already explored
        if (visited[row][col][balance]) {
            return false;
        }

        visited[row][col][balance] = true;

        // Move Down
        if (row + 1 < m) {
            if (dfs(row + 1, col, balance)) {
                return true;
            }
        }

        // Move Right
        if (col + 1 < n) {
            if (dfs(row, col + 1, balance)) {
                return true;
            }
        }

        return false;
    }
}