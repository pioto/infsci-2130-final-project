package edu.pitt.mwk21.infsci2130.finalproject;

public class Hardware {
	enum HardwareType {
		HARDDISK,
		MOTHERBOARD,
		CPU,
		RAM,
		POWERSUPPLY
	};



	private long id;
	private String description;
	private HardwareType hardwareType;
	private Price price;
	private Rating rating;
	private String url;

	public Hardware(long i, String d, HardwareType t, Price p, Rating r, String u) {
		id = i;
		description = d;
		hardwareType = t;
		price = p;
		rating = r;
		url = u;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public String getIdentifier() {
		return description.replaceAll(" ", "").replaceAll("-", "").replaceAll("/", "");
	}

	public HardwareType getHardwareType() {
		return hardwareType;
	}

	public Price getPrice() {
		return price;
	}

	public Rating getRating() {
		return rating;
	}
	
	public String getUrl() {
		return url;
	}
}
