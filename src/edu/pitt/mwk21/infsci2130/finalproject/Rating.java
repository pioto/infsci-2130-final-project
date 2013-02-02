package edu.pitt.mwk21.infsci2130.finalproject;

import java.util.ArrayList;
import java.util.List;

public class Rating {

	private long stars1;
	private long stars2;
	private long stars3;
	private long stars4;
	private long stars5;

	public Rating(long s1, long s2, long s3, long s4, long s5) {
		stars1 = s1;
		stars2 = s2;
		stars3 = s3;
		stars4 = s4;
		stars5 = s5;
	}

	public long getStars1() {
		return stars1;
	}

	public long getStars2() {
		return stars2;
	}

	public long getStars3() {
		return stars3;
	}

	public long getStars4() {
		return stars4;
	}

	public long getStars5() {
		return stars5;
	}
	
	public List<Double> getNormalizedList() {
		double sum = stars1+stars2+stars3+stars4+stars5;
		List<Double> r = new ArrayList<Double>();
		r.add(stars1/sum);
		r.add(stars2/sum);
		r.add(stars3/sum);
		r.add(stars4/sum);
		r.add(stars5/sum);
		return r;
	}
	
	public double getAverage() {
		double totalCount = stars1+stars2+stars3+stars4+stars5;
		double sum = stars1+2*stars2+3*stars3+4*stars4+5*stars5;
		return (sum/totalCount);
	}
}
