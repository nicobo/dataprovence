package bma.groomservice.data;

public class Poi {

	public String entityid;
	public String raisonsociale;
	public String tlphone;
	public String adresseWeb;
	public double latitude;
	public double longitude;

	@Override
	public String toString() {
		return "Poi [entityid=" + entityid + ", raisonsociale=" + raisonsociale
				+ ", tlphone=" + tlphone + ", adresseWeb=" + adresseWeb
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
