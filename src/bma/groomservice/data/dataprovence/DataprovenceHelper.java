package bma.groomservice.data.dataprovence;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bma.groomservice.data.Poi;
import bma.groomservice.data.PoiList;

import com.google.gson.Gson;

public class DataprovenceHelper {

	Logger logger = LoggerFactory.getLogger(DataprovenceHelper.class);

	private final String rootUrl;
	private final String datasetName;
	private final Map<String, Object> filters;

	public DataprovenceHelper(String rootUrl, String datasetName,
			Map<String, Object> filters) {
		this.rootUrl = rootUrl;
		this.datasetName = datasetName;
		this.filters = filters;
	}

	public DataprovenceHelper(String rootUrl, String datasetName) {
		this(rootUrl, datasetName, null);
	}

	public DataprovenceHelper(String datasetName) {
		this("http://dataprovence.cloudapp.net:8080/v1/dataprovencetourisme",
				datasetName, null);
	}

	/**
	 * Lit le contenu total du flux dans une {@link String}
	 */
	protected String readStream(InputStream is) throws IOException {
		StringBuilder b = new StringBuilder();
		for (int c = is.read(); c != -1; c = is.read()) {
			b.append((char) c);
		}
		return b.toString();
	}

	/**
	 * Lit un {@link PoiList} depuis un {@link InputStream} donné
	 * <b>Attention</b> à bien fermer l'input stream après.
	 */
	protected PoiList getContent(InputStream is) throws IOException,
			ParseException {

		String json = readStream(is);
		logger.debug("json={}", json);
		Gson gson = new Gson();

		PoiList all = gson.fromJson(json, PoiList.class);
		return all;
	}

	protected PoiList parse(Map<String, Object> filters)
			throws IOException, ParseException {

		Map<String, Object> ff = filters;
		if (ff == null) {
			ff = this.filters;
		}

		StringBuilder surl = new StringBuilder(rootUrl + "/" + datasetName
				+ "?format=json");
		if (ff != null) {
			for (String filter : filters.keySet()) {
				// ex : filter = "type" -> value = "cdt:Restaurant"
				surl.append("&$filter=eq%20'").append(filters.get(filter))
						.append("'");
			}
		}

		URL url = new URL(surl.toString());

		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		InputStream in = null;
		try {
			in = new BufferedInputStream(urlConnection.getInputStream());
			return getContent(in);
		} finally {
			if (in != null) {
				in.close();
			}
			urlConnection.disconnect();
		}
	}

	public List<Poi> find(Map<String, Object> filters)
			throws IOException, ParseException {
		PoiList gl = parse(filters);
		return Arrays.asList(gl.d);
	}
}
