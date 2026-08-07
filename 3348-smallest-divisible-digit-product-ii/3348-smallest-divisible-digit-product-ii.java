class Solution {
    int[][] f={{0,0,0,0},{0,0,0,0},{1,0,0,0},{0,1,0,0},
        {2,0,0,0},{0,0,1,0},{1,1,0,0},{0,0,0,1},{3,0,0,0},{0,2,0,0}};

    public String smallestNumber(String s, long t) {
        int[] a=new int[4], p={2,3,5,7};
        for(int i=0;i<4;i++)
            while(t%p[i]==0){ a[i]++; t/=p[i]; }
        if(t>1) return "-1";

        int n=s.length(), z=n;
        int[][] pre=new int[n+1][4];
        pre[0]=a.clone();

        for(int i=0;i<n;i++){
            pre[i+1]=pre[i].clone();
            int d=s.charAt(i)-'0';
            if(d==0) z=Math.min(z,i);
            else sub(pre[i+1],d);
        }

        if(z==n && ok(pre[n])) return s;

        for(int i=n-1;i>=0;i--){
            if(i>z) continue;
            for(int d=Math.max(1,s.charAt(i)-'0'+1);d<=9;d++){
                int[] x=pre[i].clone();
                sub(x,d);
                String q=build(x,n-i-1);
                if(q!=null) return s.substring(0,i)+d+q;
            }
        }

        // Answer may need MANY more than n+1 digits
        int len=Math.max(n+1, need(a));
        return build(a,len);
    }

    void sub(int[] a,int d){
        for(int i=0;i<4;i++)
            a[i]=Math.max(0,a[i]-f[d][i]);
    }

    boolean ok(int[] a){
        for(int x:a) if(x>0) return false;
        return true;
    }

    int need(int[] a){
        int best=Integer.MAX_VALUE;
        for(int k=0;k<=Math.min(a[0],a[1]);k++)
            best=Math.min(best,k+(a[0]-k+2)/3+(a[1]-k+1)/2);
        return best+a[2]+a[3];
    }

    String build(int[] a,int len){
        if(need(a)>len) return null;
        StringBuilder s=new StringBuilder();

        while(len-->0){
            for(int d=1;d<=9;d++){
                int[] b=a.clone();
                sub(b,d);
                if(need(b)<=len){
                    s.append(d);
                    a=b;
                    break;
                }
            }
        }
        return s.toString();
    }
}