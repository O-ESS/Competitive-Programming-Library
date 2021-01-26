package Graphs;

public class GraphColoring {
	static	int ChromaticNumber(int []g) {
	    int n = g.length;
	    if (n == 0) return 0;
	    //randomly choose a large prime
	    int mod = 1077563119;
	    int all = 1 << n;
	    long [] ind=new long[all];
	    long []s=new long[all];
	    int []logs=new int [1000000];
	    for(int i=2;i<logs.length;i++)
	    	logs[i]=1+logs[i/2];
	    for (int i = 0; i < all; i ++) 
	    	s[i] = (((n - Integer.bitCount(i))& 1)==1 ? -1 : 1);
	    ind[0] = 1;
	    for (int i = 1; i < all; i ++) {
	            int ctz =  logs[i&-i];
	            ind[i] = ind[i - (1 << ctz)] + ind[(i - (1 << ctz)) & ~g[ctz]];
	            if (ind[i] >= mod) ind[i] -= mod;
	    }
	    //compute the chromatic number (= \sum (-1)^{n - |i|} * ind(i)^k)
	    for (int k = 1; k < n; k ++) {
	            long sum = 0;
	            for (int i = 0; i < all; i ++) {
	                    long cur = (s[i] *1l* ind[i]) % mod;
	                    s[i] = cur;
	                    sum += cur;
	            }
	           if (sum % mod != 0) return k;
	    }
	    return n;
	}
}
