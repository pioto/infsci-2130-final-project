package edu.pitt.mwk21.infsci2130.finalproject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletContext;

import au.com.bytecode.opencsv.CSVReader;

import edu.pitt.mwk21.infsci2130.finalproject.Hardware.HardwareType;
import smile.*;

public class Model {
	private List<Hardware> allHardware = null;
	
	private ServletContext ctx;
	
	public Model(ServletContext c) {
		ctx = c;
		loadAllHardware();
	}
	
	private void loadAllHardware() {
		if (allHardware == null) {
			allHardware = new ArrayList<Hardware>();
			String csvPath = ctx.getRealPath("/hardware/hardware.csv");
			try {
				CSVReader csv = new CSVReader(new FileReader(csvPath));
				//String[] headers = csv.readNext();
				List<String[]> hw = csv.readAll();
				Iterator<String[]> iter = hw.iterator();
				// TODO: nicer parser here, that actually checks the headers instead of ignoring them?
				@SuppressWarnings("unused")
				String[] headers = iter.next();
				long hwId = 0;
				while (iter.hasNext()) {
					String[] row = iter.next();
					Hardware h = new Hardware(hwId++, row[0], Hardware.HardwareType.valueOf(row[1]), new Price(Double.parseDouble(row[2])),
							new Rating(
								Long.parseLong(row[3]),
								Long.parseLong(row[4]),
								Long.parseLong(row[5]),
								Long.parseLong(row[6]),
								Long.parseLong(row[7])
							),
							row[8]
					);
					allHardware.add(h);
				}
			} catch (IOException e) {
				throw new RuntimeException("Couldn't find the hardware CSV?");
			}
		}
	}

	//public List<Result> solve(HardwareType hw_type, Price max_price,
	public Results solve(HardwareType hw_type, Price max_price,
			Price reliability) throws IOException {
		List<Hardware> hardware = findHardware(hw_type, max_price);
		Network net = buildNetwork(hardware, reliability);
		net.updateBeliefs();
		double[] realPrices = net.getNodeValue("hardware");
		
		File tmp = File.createTempFile("test", ".xdsl");
		net.writeFile(tmp.getAbsolutePath());

		List<Result> results = new ArrayList<Result>();
		for (int i = 0; i < realPrices.length; i++) {
			Result r = new Result(hardware.get(i), new Price(-1 * realPrices[i]));
			results.add(r);
		}
		Collections.sort(results);
		return new Results(results, net);
	}

	private Network buildNetwork(List<Hardware> hardware, Price reliability) {
		Network net = new Network();
		net.readFile(ctx.getRealPath("/models/base.xdsl"));

		Price minPrice = null, maxPrice = null;
		ListIterator<Hardware> i = hardware.listIterator();
		int hwCnt = 0;
		double[] prices = new double[hardware.size()];
		double[] ratings = new double[hardware.size()*5];
		while (i.hasNext()) {
			Hardware h = i.next();
			Price p = h.getPrice();
			Rating r = h.getRating();
			if ((minPrice == null) || (minPrice.compareTo(p) > 0)) {
				minPrice = p;
			}
			if ((maxPrice == null) || (maxPrice.compareTo(p) < 0)) {
				maxPrice = p;
			}
			prices[hwCnt] = -1 * p.toDouble();
			net.addOutcome("hardware", h.getIdentifier());
			List<Double> norm = r.getNormalizedList();
			for (int i1=0; i1 < 5; i1++) {
				ratings[i1+(hwCnt*5)] = (norm.get(i1));
			}
			hwCnt++;
		}
		// nuke 'default' outcomes
		net.deleteOutcome("hardware", 0);
		net.deleteOutcome("hardware", 0);
		// set prices, ratings
		net.setNodeDefinition("price", prices);
		net.setNodeDefinition("rating", ratings);

		double[] cost_downtime = {(-1 * reliability.toDouble()), 0.0}; // (fails|notfail)
		net.setNodeDefinition("cost_downtime", cost_downtime);

		return net;
	}

	private List<Hardware> findHardware(HardwareType hw_type, Price max_price) {
		List<Hardware> hardware = new ArrayList<Hardware>();
		ListIterator<Hardware> i = allHardware.listIterator();
		while (i.hasNext()) {
			Hardware h = i.next();
			if (h.getHardwareType() == hw_type && h.getPrice().compareTo(max_price) < 0) {
				hardware.add(h);
			}
		}
		
		if (hardware.size() == 0) {
			throw new RuntimeException("No matching hardware found!");
		}
		if (hardware.size() == 1) {
			// TODO: give a nicer page for this, at least
			throw new RuntimeException("Only one matching item found! Try increasing your Max Price.");
		}
		return hardware;
	};
}
