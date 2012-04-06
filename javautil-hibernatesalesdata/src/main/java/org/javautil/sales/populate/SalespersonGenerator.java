package org.javautil.sales.populate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.javautil.sales.dto.Salesperson;

public class SalespersonGenerator {
	// todo Dennis Ritchie spelled Well

	private final Logger logger = Logger.getLogger(getClass());
	private final List<Salesperson> salesPeople = new ArrayList<Salesperson>();

	private static final String[] lastNames = new String[] { //
	"Adams", "Babbage", "Burleson-Sheisskopf", "Codd", "Date", "Ellison",
			"Fowler", "Fulton", "Gates", "Grant", "Hopper", "Hugh", "Issacson",
			"Joy", "Gates", "Kernighan", "Knuth", "Kyte", "Lewis", "Lovelace",
			"Millsap", "Moore", "Morrison", "Nooney", "Oscar", "Peters",
			"Potter", "Quayle", "Ritchie", "Rivest", "Schmidt", "Stevens",
			"Stroustrup", "Thompson", "Torvalis", "Wahl", "Ziv" };

	private static final String[] firstNames = new String[] { //
	"Ada", "Bill", "Bjarne", "Brian", "Cary", "Charles", "Chris", "Dennis",
			"Donald", "Edgar", "Eric", "Frank", "Grace", "Jim", "Jonathon",
			"Kathy", "Larry", "Linus", "Martin", "Peter", "Steve", "Tanel",
			"Toon" };

	private final long seed = 817481717376092L;

	private final Random random = new Random();

	private final boolean initted = false;

	private int quantityToGenerate = 50;

	public int getQuantityToGenerate() {
		return quantityToGenerate;
	}

	public void setQuantityToGenerate(final int quantityToGenerate) {
		this.quantityToGenerate = quantityToGenerate;
	}

	public void init() {
		if (!initted) {
			random.setSeed(seed);
		}
	}

	public Salesperson getSalesperson() {
		final Salesperson sp = new Salesperson();

		final int lastNameIndex = random.nextInt(lastNames.length);
		sp.setLastName(lastNames[lastNameIndex]);

		final int firstNameIndex = random.nextInt(firstNames.length);
		sp.setFirstName(firstNames[firstNameIndex]);
		sp.setDisplayName(sp.getLastName() + ", " + sp.getFirstName());

		salesPeople.add(sp);
		return sp;
	}

	public Salesperson getRandomSalesperson() {
		if (salesPeople.size() == 0) {
			throw new IllegalStateException(
					"No salespersons have been generated");
		}
		final int index = (int) (Math.random() * salesPeople.size());
		return salesPeople.get(index);
	}

	public List<Salesperson> getSalesPeople() {
		if (salesPeople.size() == 0) {
			populateSalespeople();
		}
		return salesPeople;
	}

	public List<Salesperson> populateSalespeople() {
		for (int i = 0; i < quantityToGenerate; i++) {
			getSalesperson();
		}
		final String message = "quantityToGenerate " + quantityToGenerate + " "
				+ " count " + getSalesPeople().size();
		logger.info(message);
		return getSalesPeople();
	}
}
