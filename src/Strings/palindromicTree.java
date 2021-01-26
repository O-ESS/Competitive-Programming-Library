package Strings;
public class palindromicTree {
		node[] a;
		String s;
		int num, suff;

		public palindromicTree(int n, String s) {
			a = new node[n + 4];
			this.s = s;
			node root1 = new node(1);
			root1.length = -1;
			root1.suff = 1;
			node root2 = new node(2);
			root2.length = 0;
			root2.suff = 1;
			a[1] = root1;
			a[2] = root2;
			suff = 2;
			num = 2;
		}

		public void add(int pos) {
			int letter = s.charAt(pos) - 'a';
			while (true) {
				if (pos - 1 - a[suff].length >= 0 && s.charAt(pos) == s.charAt(pos - 1 - a[suff].length))
					break;
				suff = a[suff].suff;
			}
			if (a[suff].nxt[letter] > 0) {
				suff = a[suff].nxt[letter];
				return;
			}
			num++;
			a[num] = new node(num);
			a[num].length = 2 + a[suff].length;
			a[suff].nxt[letter] = num;
			int cur = a[suff].suff;

			suff = num;
			if (a[num].length == 1) {
				a[num].suff = 2;
				return;
			}
			while (true) {
				if (pos - 1 - a[cur].length >= 0 && s.charAt(pos) == s.charAt(pos - 1 - a[cur].length))
					break;
				cur = a[cur].suff;
			}
			a[num].suff = a[cur].nxt[letter];

		}

		class node {
			int id, length, suff;
			int[] nxt;

			public node(int i) {
				id = i;
				nxt = new int[26];
			}
		}
}
