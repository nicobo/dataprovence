package bma.groomservice.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Poi implements Parcelable {

	/** Ex: "65c6fb5e-aa10-4711-8002-74d1ec31f9d6" */
	public String entityid;
	/** Ex: "La Galinière - Mercure" */
	public String raisonsociale;
	/** Ex: "04 42 20 21 51" */
	public String tlphone;
	/**
	 * Ex:
	 * "http://www.restaurantlagaliniere.fr↵http://www.hotelmercureaixsaintevictoire.fr "
	 */
	public String adresseWeb;
	/** Ex: "43.4896481" */
	public double latitude;
	/** Ex: "5.5689422" */
	public double longitude;

	/** Ex: "0402648822_0000000000" */
	public String PartitionKey;
	/** Ex: "1fb5e5aa-6385-4eae-b350-df0c50bbb94c" */
	public String RowKey;
	/** Ex: "2012-10-04T06:53:42.4684973Z" */
	public String Timestamp;
	/** Ex: "CHATEAUNEUF LE ROUGE" */
	public String bureaudistributeur;
	/** Ex: "13790"" */
	public String codepostal;
	/** Ex: "cdt:Restaurant" */
	public String type;
	/** Ex: "Châteauneuf le Rouge" */
	public String ville;
	/** Ex: "RD7" */
	public String voie;

	public Poi() {
		super();
	}

	public Poi(Parcel p) {
		this();
		entityid = p.readString();
		raisonsociale = p.readString();
		tlphone = p.readString();
		adresseWeb = p.readString();
		latitude = p.readDouble();
		longitude = p.readDouble();
		PartitionKey = p.readString();
		RowKey = p.readString();
		Timestamp = p.readString();
		bureaudistributeur = p.readString();
		codepostal = p.readString();
		type = p.readString();
		ville = p.readString();
		voie = p.readString();
	}

	public static final Parcelable.Creator<Poi> CREATOR = new Parcelable.Creator<Poi>() {
		@Override
		public Poi createFromParcel(Parcel in) {
			return new Poi(in);
		}

		@Override
		public Poi[] newArray(int size) {
			return new Poi[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int flags) {
		p.writeString(entityid);
		p.writeString(raisonsociale);
		p.writeString(tlphone);
		p.writeString(adresseWeb);
		p.writeDouble(latitude);
		p.writeDouble(longitude);
		p.writeString(PartitionKey);
		p.writeString(RowKey);
		p.writeString(Timestamp);
		p.writeString(bureaudistributeur);
		p.writeString(codepostal);
		p.writeString(type);
		p.writeString(ville);
		p.writeString(voie);
	}

	@Override
	public String toString() {
		return "Poi [entityid=" + entityid + ", raisonsociale=" + raisonsociale
				+ ", tlphone=" + tlphone + ", adresseWeb=" + adresseWeb
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", PartitionKey=" + PartitionKey + ", RowKey=" + RowKey
				+ ", Timestamp=" + Timestamp + ", bureaudistributeur="
				+ bureaudistributeur + ", codepostal=" + codepostal + ", type="
				+ type + ", ville=" + ville + ", voie=" + voie + "]";
	}

}
