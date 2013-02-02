package edu.pitt.mwk21.infsci2130.finalproject;

public class Result implements Comparable<Result> {
	private Hardware hardware;
	private Price realPrice;

	public Result(Hardware h, Price p) {
		hardware = h;
		realPrice = p;
	}

	public Hardware getHardware() {
		return hardware;
	}

	public Price getRealPrice() {
		return realPrice;
	}

	@Override
	public int compareTo(Result other) {
		return realPrice.compareTo(other.realPrice);
	}
}
