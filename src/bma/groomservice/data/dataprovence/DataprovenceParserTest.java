package bma.groomservice.data.dataprovence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.test.AndroidTestCase;
import bma.groomservice.data.Filter;
import bma.groomservice.data.Poi;

public class DataprovenceParserTest extends AndroidTestCase {

	Logger logger = LoggerFactory.getLogger(DataprovenceParserTest.class);

	// DataprovenceHelper parser = new DataprovenceHelper(
	// "RestaurantsGastronomiques");
	DataprovenceHelper parser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		parser = new DataprovenceFileHelper(
				"RestaurantsGastronomiques_cdtRestaurant.json");
	}

	@Override
	protected void tearDown() throws Exception {
		parser = null;
		super.tearDown();
	}

	public void testRestaurantsGastronomiques() {
		Collection<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("type", "cdt:Restaurant"));
		try {
			List<Poi> gs = parser.find(filters);
			logger.debug("RestaurantsGastronomiques={}", gs);

		} catch (Exception e) {
			e.printStackTrace(System.err);
			fail(e.getMessage());
		}
	}

	public void testParcsEtJardins() {
		try {
			List<Poi> gs = parser.find(null);
			logger.debug("ParcsEtJardins={}", gs);

		} catch (Exception e) {
			e.printStackTrace(System.err);
			fail(e.getMessage());
		}
	}

	// public void testfindHelpersCulture() {
	// try {
	// DataprovenceManager
	// .findAll(new String[] { "CULTURE" });
	// assertEquals(4, pois.size());
	// assertTrue(pois
	// .contains(new DataprovenceHelper("ServicesDeGuides")));
	// assertTrue(pois.contains(new DataprovenceHelper(
	// "SitesNaturelsIncontournables")));
	// assertTrue(pois.contains(new DataprovenceHelper("Musees")));
	// assertTrue(pois.contains(new DataprovenceHelper(
	// "MonumentsEtStesCulturels")));
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// fail(e.getMessage());
	// }
	// }
	//
	// public void testfindHelpersCultureSport() {
	// try {
	// List<Poi> pois = DataprovenceManager.findAll(new String[] {
	// "CULTURE", "PLEIN AIR" });
	// assertEquals(12, pois.size());
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// fail(e.getMessage());
	// }
	// }
}
