package DpOptimizations;

import java.util.TreeMap;
import java.util.TreeSet;

class DynamicCHT {

	// Minimum dynamic convex hull
	// tested 
	// for max reverse sorting and change add function
	
	static class Line implements Comparable<Line> {
		long m;
		long c;

		public Line(long m, long c) {
			this.m = m;
			this.c = c;
		}

		long eval(long x) {
			return x * m + c;
		}

		boolean parallel(Line o) {
			return o.m == m;
		}

		double intersect(Line o) {
			if (parallel(o))
				return 1e18;
			double v = 1.0 * (o.c - c) / (m * 1.0 - o.m);
			if (v == -0.0)
				v = 0.0;
			return v;
		}

		@Override
		public int compareTo(Line o) {
			// TODO Auto-generated method stub
			return Long.compare(o.m, m);
		}

		public String toString() {
			return m + " " + c;
		}
	}

	TreeSet<Line> hull;
	TreeMap<Double, Line> query;

	public String toString() {
		return hull + " " + query;
	}

	public DynamicCHT() {
		hull = new TreeSet<>();
		query = new TreeMap<>();
	}

	public void addLine(long m, long c) {
		Line l = new Line(m, c);
		add(l);
	}

	public void add(Line a) {

		if (hull.contains(a)) {
			Line l = hull.ceiling(a);
			if (a.c > l.c)
				return;
			hull.remove(l);
			if (hull.lower(l) != null)
				query.remove(l.intersect(hull.lower(l)));
			if (hull.higher(l) != null)
				query.remove(l.intersect(hull.higher(l)));
		}

		Line lower = hull.lower(a);
		Line higher = hull.higher(a);
		if (lower != null && higher != null && a.intersect(lower) >= a.intersect(higher))
			return;
		if (lower != null && higher != null)
			query.remove(lower.intersect(higher));
		while (lower != null && hull.lower(lower) != null) {
			Line ll = hull.lower(lower);
			if (a.intersect(lower) > ll.intersect(lower))
				break;
			hull.remove(lower);
			query.remove(ll.intersect(lower));
			lower = ll;
		}
		while (higher != null && hull.higher(higher) != null) {
			Line rr = hull.higher(higher);
			if (a.intersect(higher) < rr.intersect(higher))
				break;
			hull.remove(higher);
			query.remove(rr.intersect(higher));
			higher = rr;
		}

		hull.add(a);
		if (lower != null)
			query.put(a.intersect(lower), a);
		if (higher != null)
			query.put(a.intersect(higher), higher);
	}

	public long query(long x) {
		if (query.size() == 0)
			return hull.first().eval(x);
		if (query.floorKey((double) x) == null)
			return hull.first().eval(x);
		Line v = query.get(query.floorKey((double) x));
		return v.eval(x);
	}

	public void merge(DynamicCHT o) {
		for (Line a : o.hull)
			add(a);
	}

}