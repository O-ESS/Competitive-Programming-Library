package Strings;

import java.io.*;
import java.util.*;

public class Manacher {
	private int[] p; // p[i] = length of longest palindromic substring of t, centered at i
	private String s; // original string
	private char[] t; // transformed string

	public Manacher(String s) {
		this.s = s;
		preprocess();
		p = new int[t.length];

		int center = 0, right = 0;
		for (int i = 1; i < t.length - 1; i++) {
			int mirror = 2 * center - i;

			if (right > i)
				p[i] = Math.min(right - i, p[mirror]);

			// attempt to expand palindrome centered at i
			while (t[i + (1 + p[i])] == t[i - (1 + p[i])])
				p[i]++;

			// if palindrome centered at i expands past right,
			// adjust center based on expanded palindrome.
			if (i + p[i] > right) {
				center = i;
				right = i + p[i];
			}
		}

	}

	// Transform s into t.
	// For example, if s = "abba", then t = "$#a#b#b#a#@"
	// the # are interleaved to avoid even/odd-length palindromes uniformly
	// $ and @ are prepended and appended to each end to avoid bounds checking
	private void preprocess() {
		t = new char[s.length() * 2 + 3];
		t[0] = '$';
		t[s.length() * 2 + 2] = '@';
		for (int i = 0; i < s.length(); i++) {
			t[2 * i + 1] = '#';
			t[2 * i + 2] = s.charAt(i);
		}
		t[s.length() * 2 + 1] = '#';
	}

	// longest palindromic substring
	public String longestPalindromicSubstring() {
		int length = 0; // length of longest palindromic substring
		int center = 0; // center of longest palindromic substring
		for (int i = 1; i < p.length - 1; i++) {
			if (p[i] > length) {
				length = p[i];
				center = i;
			}
		}
		return s.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
	}

	// longest palindromic substring centered at index i/2
	public void longestPalindromicSubstring(int i) {
		length = p[i + 2];
		center = i + 2;
		return;// s.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
	}

	static int length, center;
	static String st;
	static StringBuilder sb, sb1, ss, lp;
	static int n;

	// test client
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			st = sc.nextLine();
			n = st.length();
			int l = 0;
			int r = n - 1;
			sb = new StringBuilder();
			sb1 = new StringBuilder();
			for (int i = 0; i < n; i++)
				if (st.charAt(l) == st.charAt(r) && l < r) {
					sb.append(st.charAt(l));
					sb1.append(st.charAt(r));
					l++;
					r--;
				} else
					break;
			st = st.substring(l, r + 1);
			ss = new StringBuilder();
			ss.append(sb.toString());
			lp = new StringBuilder();
			// out.println(st);
			Manacher manacher = new Manacher(st);
			int ll = 0;
			int rr = st.length();
			for (int i = 0; i < 2 * st.length(); i++) {
				manacher.longestPalindromicSubstring(i);
				if ((center - 1 - length) / 2 == 0) {
					ll = Math.max(ll, (center - 1 + length) / 2);
				}
				if ((center - 1 + length) / 2 == st.length()) {
					rr = Math.min(rr, (center - 1 - length) / 2);
				}
				// out.println((center - 1 - length) / 2+" "+(center - 1 + length) / 2);
				// out.println(st.substring((center - 1 - length) / 2, (center - 1 + length) /
				// 2));
			}
			// out.println(i + ": " + manacher.longestPalindromicSubstring(i));
			String yl = st.substring(0, ll);
			String yr = st.substring(rr, st.length());
			// out.println(yl+" hhhhh "+ll);
			// out.println(yr+" hhhhh "+rr);
			if (yl.length() > yr.length()) {
				lp.append(yl);
			} else {
				lp.append(yr);
			}
			ss.append(lp);
			ss.append(sb1.reverse().toString());
			out.println(ss.toString());
		}
		out.flush();

	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream system) {
			br = new BufferedReader(new InputStreamReader(system));
		}

		public Scanner(String file) throws Exception {
			br = new BufferedReader(new FileReader(file));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public char nextChar() throws IOException {
			return next().charAt(0);
		}

		public Long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}
	}
}