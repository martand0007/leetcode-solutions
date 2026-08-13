class Solution {
    int[] pre, suf, best, len;
    char[] lc, rc;
    char[] s;

    public int[] longestRepeating(String str, String q, int[] idx) {
        s = str.toCharArray();
        int n = s.length, k = idx.length;
        pre = new int[4*n]; suf = new int[4*n];
        best = new int[4*n]; len = new int[4*n];
        lc = new char[4*n]; rc = new char[4*n];

        build(1, 0, n-1);

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            s[idx[i]] = q.charAt(i);
            update(1, 0, n-1, idx[i]);
            ans[i] = best[1];
        }
        return ans;
    }

    void build(int p, int l, int r) {
        if (l == r) {
            pre[p] = suf[p] = best[p] = len[p] = 1;
            lc[p] = rc[p] = s[l];
            return;
        }
        int m = (l+r)/2;
        build(p*2,l,m);
        build(p*2+1,m+1,r);
        merge(p,p*2,p*2+1);
    }

    void update(int p, int l, int r, int i) {
        if (l == r) {
            pre[p] = suf[p] = best[p] = len[p] = 1;
            lc[p] = rc[p] = s[i];
            return;
        }
        int m = (l+r)/2;
        if (i <= m) update(p*2,l,m,i);
        else update(p*2+1,m+1,r,i);
        merge(p,p*2,p*2+1);
    }

    void merge(int p, int a, int b) {
        len[p] = len[a] + len[b];
        lc[p] = lc[a];
        rc[p] = rc[b];

        pre[p] = pre[a];
        if (pre[a] == len[a] && rc[a] == lc[b])
            pre[p] += pre[b];

        suf[p] = suf[b];
        if (suf[b] == len[b] && rc[a] == lc[b])
            suf[p] += suf[a];

        best[p] = Math.max(best[a], best[b]);
        if (rc[a] == lc[b])
            best[p] = Math.max(best[p], suf[a] + pre[b]);
    }
}