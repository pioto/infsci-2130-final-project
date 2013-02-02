package edu.pitt.mwk21.infsci2130.finalproject;

public class Price implements Comparable<Price> {
	private long cents;

	public Price(long c) {
		cents = c;
	}
	
	public Price(double d) {
		cents = (long)(d * 100);
	}

	@Override
	public String toString() {
		return "$" + (cents/100) + "." + (cents%100);
	}

	@Override
	public int compareTo(Price arg0) {
		return (new Long(cents)).compareTo(arg0.cents);
	}

	public double toDouble() {
		return (cents / 100.0);
	}
}