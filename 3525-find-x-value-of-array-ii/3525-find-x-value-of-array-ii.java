class Solution {

    class Node {
        int product;   // Product of the complete segment % k
        int[] count;   // count[r] = number of prefixes having product % k == r

        Node(int k) {
            count = new int[k];
            product = 1 % k;
        }
    }

    int k;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query [start ... n-1]
            Node result = query(1, 0, n - 1, start, n - 1);

            ans[i] = result.count[x];
        }

        return ans;
    }

    // Build Segment Tree
    private void build(int node, int left, int right, int[] nums) {

        if (left == right) {
            tree[node] = new Node(k);

            int value = nums[left] % k;

            tree[node].product = value;
            tree[node].count[value] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Merge two nodes
    private Node merge(Node left, Node right) {

        Node result = new Node(k);

        // Product of complete segment
        result.product = (left.product * right.product) % k;

        // Prefixes completely inside left segment
        for (int r = 0; r < k; r++) {
            result.count[r] += left.count[r];
        }

        // Prefixes that cross from left into right
        for (int r = 0; r < k; r++) {

            int newRemainder = (left.product * r) % k;

            result.count[newRemainder] += right.count[r];
        }

        return result;
    }

    // Point update
    private void update(int node, int left, int right,
                        int index, int value) {

        if (left == right) {

            tree[node] = new Node(k);

            value %= k;

            tree[node].product = value;
            tree[node].count[value] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Range query
    private Node query(int node, int left, int right,
                       int queryLeft, int queryRight) {

        if (queryLeft <= left && right <= queryRight) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (queryRight <= mid) {
            return query(node * 2, left, mid,
                         queryLeft, queryRight);
        }

        if (queryLeft > mid) {
            return query(node * 2 + 1, mid + 1, right,
                         queryLeft, queryRight);
        }

        Node leftResult = query(
                node * 2,
                left,
                mid,
                queryLeft,
                queryRight
        );

        Node rightResult = query(
                node * 2 + 1,
                mid + 1,
                right,
                queryLeft,
                queryRight
        );

        return merge(leftResult, rightResult);
    }
}