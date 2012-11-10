package bma.groomservice.data.dataprovence;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
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
	public DataprovenceFileHelper(String filename) {
		super(null, null, null);
		this.filename = filename;
	}

	@Override
	protected PoiList parse(Collection<Filter> filters) throws IOException,
			ParseException {
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
}
