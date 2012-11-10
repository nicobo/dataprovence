package bma.groomservice.data.dataprovence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.AsyncTask;
import bma.groomservice.data.Poi;
import bma.groomservice.data.PoiListener;

public class DataprovenceManager implements PoiListener {

	Logger logger = LoggerFactory.getLogger(DataprovenceManager.class);

	//
	// Constantes des "thèmes"
	//

	public static final String THEME_PLEINAIR = "PLEIN AIR";
	public static final String THEME_RESTAURATION = "RESTAURATION";
	public static final String THEME_SPORT = "SPORT";
	public static final String THEME_CULTURE = "CULTURE";

	//
	// Associations themes <-> datasets
	//

	public static final Map<String, String[]> DATASETS = new HashMap<String, String[]>();
	static {
		DATASETS.put(THEME_PLEINAIR, new String[] { "ServicesDeGuides",
				"LocationDeVelo", "CentresEquestres",
				"AccompagnateurDeMoyenneMontagneEtMoniteursDEscalade",
				"ParcsAThemesEtAnimaliers", "GolfsEtMinigolfs",
				"ParcsAcrobatiquesForestiers", "ParcsEtJardins",
				"SitesNaturelsIncontournables", "Plages" });
		DATASETS.put(THEME_RESTAURATION, new String[] {
				"RestaurantsGastronomiques", "Restaurants" });
		DATASETS.put(THEME_SPORT, new String[] { "SportNautique",
				"LocationDeVelo", "CentresEquestres",
				"AccompagnateurDeMoyenneMontagneEtMoniteursDEscalade",
				"GolfsEtMinigolfs", "ParcsAcrobatiquesForestiers" });
		DATASETS.put(THEME_CULTURE, new String[] { "ServicesDeGuides",
				"SitesNaturelsIncontournables", "Musees",
				"MonumentsEtStesCulturels" });
	}

	private final TreeSet<Poi> pois = new TreeSet<Poi>();

	private final PoiListener listener;

	private int count = 0;

	private class LoadDataTask extends
			AsyncTask<DataprovenceHelper, Integer, Long> {
		List<Poi> taskPois;
		PoiListener listener;

		public LoadDataTask(PoiListener listener) {
			this.listener = listener;
		}

		@Override
		protected Long doInBackground(DataprovenceHelper... dph) {
			try {
				taskPois = dph[0].find(null);
				return 1L;
			} catch (Exception e) {
				logger.error("Erreur lors du chargement de " + dph, e);
				return 0L;
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onPostExecute(Long result) {
			listener.onPoiReceived(taskPois);
		}
	}

	public DataprovenceManager(PoiListener listener) {
		super();
		this.listener = listener;
	}

	private static Collection<DataprovenceHelper> findHelpers(
			Collection<String> tags) {
		ArrayList<DataprovenceHelper> helpers = new ArrayList<DataprovenceHelper>();
		for (String tag : tags) {
			String[] datasets = DATASETS.get(tag);
			for (int d = 0; d < datasets.length; d++) {
				helpers.add(new DataprovenceHelper(datasets[d]));
			}
		}
		return helpers;
	}

	@Override
	public void onPoiReceived(List<Poi> pois) {
		synchronized (this) {
			this.pois.addAll(pois);
			count--;
			// quand on a recu toutes les reponses, on notifie le listener
			// ("externe")
			if (count == 0) {
				listener.onPoiReceived(new ArrayList<Poi>(this.pois));
			}
		}
	}

	public void findAll(Collection<String> tags) throws IOException {

		Set<DataprovenceHelper> helpers = new HashSet<DataprovenceHelper>();
		helpers.addAll(findHelpers(tags));

		// lance une AsyncTask pour chaque helper
		for (DataprovenceHelper helper : findHelpers(tags)) {
			synchronized (this) {
				count++;
				new LoadDataTask(this).execute(helper);
			}
		}
	}

	public void findAll(String[] tags) throws IOException {
		findAll(Arrays.asList(tags));
	}
}
