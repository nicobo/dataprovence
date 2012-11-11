package bma.groomservice.data.dataprovence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import bma.groomservice.data.Filter;
import bma.groomservice.data.PoiList;

/**
 * Utiliser pour les tests locaux : lit les données à partir d'un fichier JSON
 * local
 * 
 * @author nicobo
 */
public class DataprovenceFileHelper extends DataprovenceHelper {

	String filename;

	/**
	 * @param filename
	 *            Nom du fichier (dans le CLASSPATH) Ex:
	 *            "RestaurantsGastronomiques_cdtRestaurant.json"
	 */
	public DataprovenceFileHelper(String filename, String theme) {
		super(null, null, null);
		this.filename = filename;
		this.theme = theme;
	}

	@Override
	protected PoiList parse(Collection<Filter> filters) throws IOException {
		InputStream fis = null;
		try {
			fis = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filename);
			return getContent(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	@Override
	public String toString() {
		return "DataprovenceFileHelper [filename=" + filename + "]";
	}
}
