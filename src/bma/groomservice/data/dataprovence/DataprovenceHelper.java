package bma.groomservice.data.dataprovence;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bma.groomservice.data.Filter;
import bma.groomservice.data.Poi;
import bma.groomservice.data.PoiList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class DataprovenceHelper {

	Logger logger = LoggerFactory.getLogger(DataprovenceHelper.class);

	private final String rootUrl;
	private final String datasetName;
	private final Collection<Filter> filters;

	public String theme;

	public DataprovenceHelper(String rootUrl, String datasetName,
			Collection<Filter> filters, String theme) {
		logger.debug("new DataprovenceHelper({},{},{})", new Object[] {
				rootUrl, datasetName, filters });
		this.rootUrl = rootUrl;
		this.datasetName = datasetName;
		this.filters = filters;
	}

	public DataprovenceHelper(String rootUrl, String datasetName, String theme) {
		this(rootUrl, datasetName, null, theme);
	}

	public DataprovenceHelper(String datasetName, String theme) {
		this("http://dataprovence.cloudapp.net:8080/v1/dataprovencetourisme",
				datasetName, null, theme);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datasetName == null) ? 0 : datasetName.hashCode());
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + ((rootUrl == null) ? 0 : rootUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataprovenceHelper other = (DataprovenceHelper) obj;
		if (datasetName == null) {
			if (other.datasetName != null)
				return false;
		} else if (!datasetName.equals(other.datasetName))
			return false;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (rootUrl == null) {
			if (other.rootUrl != null)
				return false;
		} else if (!rootUrl.equals(other.rootUrl))
			return false;
		return true;
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
	protected PoiList getContent(InputStream is) throws IOException {

		// String json = readStream(is);
		// logger.debug("json={}", json);
		Gson gson = new Gson();

		PoiList all = gson.fromJson(new JsonReader(new InputStreamReader(is,
				"UTF-8")), PoiList.class);
		return all;
	}

	protected PoiList parse(Collection<Filter> filters) throws IOException {

		Collection<Filter> ff = filters;
		if (ff == null) {
			ff = this.filters;
		}

		StringBuilder surl = new StringBuilder(rootUrl + "/" + datasetName
				+ "?format=json");
		if (ff != null) {
			for (Filter filter : filters) {
				// ex : filter = "type" -> value = "cdt:Restaurant"
				surl.append("&$filter=").append(filter.op).append("%20");
				if (filter.value instanceof String) {
					surl.append("'").append(filter.value).append("'");
				} else {
					surl.append(filter.value);
				}

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

	public List<Poi> find(Collection<Filter> filters) throws IOException {
		PoiList gl = parse(filters);
		return Arrays.asList(gl.d);
	}

	@Override
	public String toString() {
		return "DataprovenceHelper [rootUrl=" + rootUrl + ", datasetName="
				+ datasetName + ", filters=" + filters + "]";
	}

}
