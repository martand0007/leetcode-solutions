class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int prev = -1;
        int minDist = Integer.MAX_VALUE;
        
        ListNode prevNode = head;
        ListNode curr = head.next;
        
        int position = 1;

        while (curr.next != null) {
            ListNode nextNode = curr.next;

            // Check if curr is a critical point
            boolean isMax = curr.val > prevNode.val && curr.val > nextNode.val;
            boolean isMin = curr.val < prevNode.val && curr.val < nextNode.val;

            if (isMax || isMin) {
                if (first == -1) {
                    // First critical point
                    first = position;
                } else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, position - prev);
                }

                prev = position;
            }

            prevNode = curr;
            curr = nextNode;
            position++;
        }

        // Fewer than two critical points
        if (first == -1 || first == prev) {
            return new int[]{-1, -1};
        }

        // Distance between first and last critical point
        int maxDist = prev - first;

        return new int[]{minDist, maxDist};
    }
}