package edu.pitt.mwk21.infsci2130.finalproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import smile.Network;

public class Results {
	List<Result> results;
	Network net;

	public Results(List<Result> r, Network n) {
		results = r;
		net = n;
	}
	
	public List<Result> getResults() {
		return results;
	}

	public Network getNet() {
		return net;
	}
	
	public InputStream getInputStream() throws IOException {
		File tmp = File.createTempFile("test", ".xdsl");
		net.writeFile(tmp.getAbsolutePath());
		return new FileInputStream(tmp.getAbsolutePath());
	}

	public Iterator<Result> iterator() {
		return results.iterator();
	}
}
