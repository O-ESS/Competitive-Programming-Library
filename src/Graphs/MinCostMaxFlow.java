package Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinCostMaxFlow {
	static int n, cost, flow, s, t,inf=(int)1e9;
	static int[][] cap, val;
	static int[] dist, p;
	static ArrayList<Integer>[] ad;

	static void MCMF() {
		while (true) {
			belmanford();
			// System.out.println(Arrays.toString(dist));
			if (dist[t] == inf)
				return;
			int f = inf;
			int cur = t;
			while (cur != s) {
				f = Math.min(f, cap[p[cur]][cur]);
				cur = p[cur];
			}
			flow += f;
			cost += f * dist[t];
			cur = t;
			// System.out.println(flow+" "+cost);
			while (cur != s) {
				cap[p[cur]][cur] -= f;
				cap[cur][p[cur]] += f;
				cur = p[cur];
			}
		}
	}

	static void belmanford() {
		dist = new int[n + 26 + 5];
		Arrays.fill(dist, inf);
		boolean[] in = new boolean[n + 26 + 5];
		Queue<Integer> q = new LinkedList<Integer>();
		p = new int[n + 26 + 5];
		int[] cnt = new int[n + 26 + 5];
		Arrays.fill(p, -1);
		q.add(s);
		dist[s] = 0;
		while (!q.isEmpty()) {
			int u = q.poll();
			in[u] = false;
			for (int v : ad[u]) {
				if (cap[u][v] > 0 && dist[v] > dist[u] + val[u][v]) {
					dist[v] = dist[u] + val[u][v];
					p[v] = u;
					if (!in[v]) {
						cnt[v]++;
						// if (cnt[v] > n + 5 + 26)
						// return;
						in[v] = true;
						q.add(v);
					}
				}
			}
		}
	}
}
