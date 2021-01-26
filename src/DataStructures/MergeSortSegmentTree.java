package DataStructures;


public class MergeSortSegmentTree { // 1-based DS, OOP

	int N; // the number of elements in the array as a power of 2 (i.e. after padding)
	int[] array;
	int[][] sTree;

	MergeSortSegmentTree(int[] in) {
		array = in;
		N = in.length - 1;
		sTree = new int[N << 1][]; // no. of nodes = 2*N - 1, we add one to cross out index zero
		build(1, 1, N);
	}

	void build(int node, int b, int e) // O(n)
	{
		if (b == e)
			sTree[node] = new int[] { array[b] };
		else {
			int mid = b + e >> 1;
			build(node << 1, b, mid);
			build(node << 1 | 1, mid + 1, e);
			sTree[node] = new int[sTree[node << 1].length + sTree[node << 1 | 1].length];
			int l = 0;
			int r = 0;
			for (int i = 0; i < sTree[node].length; i++) {
				if (sTree[node << 1].length == l) {
					sTree[node][i] = sTree[node << 1 | 1][r];
					r++;
				} else if (sTree[node << 1 | 1].length == r) {
					sTree[node][i] = sTree[node << 1][l];
					l++;
				} else if (sTree[node << 1][l] < sTree[node << 1 | 1][r]) {
					sTree[node][i] = sTree[node << 1][l];
					l++;
				} else {
					sTree[node][i] = sTree[node << 1 | 1][r];
					r++;
				}
			}
		}
	}

	int query(int i, int j, int val) {
		return query(1, 1, N, i, j, val);
	}

	int query(int node, int b, int e, int i, int j, int val) // O(log n)
	{
		if (i > e || j < b)
			return 0;
		if (b >= i && e <= j) {
			int id = -1;
			int lo = 0;
			int hi = sTree[node].length - 1;
			// System.out.println(Arrays.toString(sTree[node]));
			while (lo <= hi) {
				int mid = lo + hi >> 1;
				if (sTree[node][mid] >= val) {
					id = mid;
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			if (id == -1)
				return 0;
			return sTree[node].length - (id);
		}
		int mid = b + e >> 1;
		int q1 = query(node << 1, b, mid, i, j, val);
		int q2 = query(node << 1 | 1, mid + 1, e, i, j, val);
		return q1 + q2;
	}
}

