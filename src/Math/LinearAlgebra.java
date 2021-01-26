package Math;

import java.util.Arrays;

public class LinearAlgebra {
	static double EPS = 1e-9;

	static int gaussGeneral(double[][] a, double[] ans) {
		int n = a.length;
		int m = a[0].length - 1;
		int[] where = new int[m];
		Arrays.fill(where, -1);
		for (int col = 0, row = 0; col < m && row < n; ++col) {
			int sel = row;
			for (int i = row; i < n; ++i)
				if (Math.abs(a[i][col]) > Math.abs(a[sel][col]))
					sel = i;
			if (Math.abs(a[sel][col]) < EPS)
				continue;
			for (int i = col; i <= m; ++i) {
				double tem = a[sel][i];
				a[sel][i] = a[row][i];
				a[row][i] = tem;
			}
			where[col] = row;

			for (int i = 0; i < n; ++i)
				if (i != row) {
					double c = a[i][col] / a[row][col];
					for (int j = col; j <= m; ++j)
						a[i][j] -= a[row][j] * c;
				}
			++row;
		}

		ans = new double[m];
		for (int i = 0; i < m; ++i)
			if (where[i] != -1)
				ans[i] = a[where[i]][m] / a[where[i]][i];
		for (int i = 0; i < n; ++i) {
			double sum = 0;
			for (int j = 0; j < m; ++j)
				sum += ans[j] * a[i][j];
			if (Math.abs(sum - a[i][m]) > EPS)
				return 0;
		}

		for (int i = 0; i < m; ++i)
			if (where[i] == -1)
				return 2;
		return 1;
	}

// gauess elimination for mod 2
// for any other mod use gauessGeneral and in division use inverse mod
	static int[] sol;

	static int gauess(int[][] a) {
		int n = a.length;
		int m = a[0].length - 1;
		int[] where = new int[m];
		Arrays.fill(where, -1);
		for (int col = 0, row = 0; col < m && row < n; col++) {
			for (int i = row; i < n; i++) {
				if (a[i][col] == 1) {
					int[] s = a[i].clone();
					for (int j = 0; j <= m; j++)
						a[i][j] = a[row][j];
					for (int j = 0; j <= m; j++)
						a[row][j] = s[j];
					break;
				}
			}
			if (a[row][col] != 1)
				continue;
			where[col] = row;
			for (int i = 0; i < n; i++)
				if (i != row && a[i][col] == 1) {
					for (int j = 0; j <= m; j++)
						a[i][j] ^= a[row][j];
				}

			row++;
		}
		sol = new int[m];
		for (int i = 0; i < m; ++i)
			if (where[i] != -1)
				sol[i] = a[where[i]][m];
		for (int i = 0; i < n; ++i) {
			int sum = 0;
			for (int j = 0; j < m; ++j)
				sum ^= (sol[j] * a[i][j]);
			if (sum != a[i][m])
				return 0;
		}
		for (int i = 0; i < m; ++i)
			if (where[i] == -1)
				return 2;
		return 1;
	}

	static int[] basis; // basis[i] keeps the mask of the vector whose f value is i

	static int sz; // Current size of the basis

	static void insertVector(int mask) {
		for (int i = 0; i < basis.length; i++) {
			if ((mask & 1 << i) == 0)
				continue; // continue if i != f(mask)

			if (basis[i] == 0) { // If there is no basis vector with the i'th bit set, then insert this vector
									// into the basis
				basis[i] = mask;
				++sz;

				return;
			}

			mask ^= basis[i]; // Otherwise subtract the basis vector from this vector
		}
	}

}
