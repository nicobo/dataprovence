package bma.groomservice.data.dataprovence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bma.groomservice.data.Poi;

public class DataprovenceParserTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(DataprovenceParserTest.class);

	public void testRestaurantsGastronomiques() {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", "cdt:Restaurant");
		try {
			// DataprovenceHelper parser = new DataprovenceHelper(
			// "RestaurantsGastronomiques");
			DataprovenceHelper parser = new DataprovenceFileHelper(
					"RestaurantsGastronomiques_cdtRestaurant.json");
			List<Poi> gs = parser.find(filters);
			logger.debug("RestaurantsGastronomiques={}", gs);

		} catch (Exception e) {
			e.printStackTrace(System.err);
			fail(e.getMessage());
		}
	}

	public void testParcsEtJardins() {
		try {
			DataprovenceHelper parser = new DataprovenceHelper("ParcsEtJardins");
			// DataprovenceHelper parser = new DataprovenceFileHelper(
			// "ParcsEtJardins.json");
			List<Poi> gs = parser.find(null);
			logger.debug("ParcsEtJardins={}", gs);

		} catch (Exception e) {
			e.printStackTrace(System.err);
			fail(e.getMessage());
		}
	}
}
