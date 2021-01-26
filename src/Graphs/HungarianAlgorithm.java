package Graphs;

import java.io.PrintWriter;
import java.util.*;

public class HungarianAlgorithm {
	static PrintWriter out;
	static StringBuilder sb;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		out = new PrintWriter(System.out);

		out.close();
	}

	static int hungarian(int[][] a) { // a contains negative values 
		int n = a.length, m = a[0].length, p, q; // n <= m
		int[] fx = new int[n];
		Arrays.fill(fx, Integer.MIN_VALUE);
		int[] fy = new int[m];
		int[] x = new int[n];
		int[] y = new int[m];
		Arrays.fill(x, -1);
		Arrays.fill(y, -1);
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				fx[i] = Math.max(fx[i], a[i][j]);
		for (int i = 0; i < n;) {
			int[] t = new int[m];
			Arrays.fill(t, -1);
			int[] s = new int[n + 1];
			Arrays.fill(s, i);
			for (p = q = 0; p <= q && x[i] < 0; ++p) {
				for (int k = s[p], j = 0; j < m && x[i] < 0; ++j)
					if (fx[k] + fy[j] == a[k][j] && t[j] < 0) // be careful with comparison on doubles
					{
						s[++q] = y[j];
						t[j] = k;
						if (s[q] < 0)
							for (p = j; p >= 0; j = p) {
								y[j] = k = t[j];
								p = x[k];
								x[k] = j;
							}
					}

			}
			if (x[i] < 0) {
				int d = Integer.MAX_VALUE;
				for (int k = 0; k <= q; ++k)
					for (int j = 0; j < m; ++j)
						if (t[j] < 0)
							d = Math.min(d, fx[s[k]] + fy[j] - a[s[k]][j]);
				for (int j = 0; j < m; ++j)
					fy[j] += (t[j] < 0 ? 0 : d);
				for (int k = 0; k <= q; ++k)
					fx[s[k]] -= d;
			} else
				++i;
		}
		int ret = 0;
		for (int i = 0; i < n; ++i) {
			ret += a[i][x[i]];
		}
		return -ret;
	}

}
