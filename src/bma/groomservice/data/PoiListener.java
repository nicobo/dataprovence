package bma.groomservice.data;

import java.util.List;

public interface PoiListener {
	void onPoiReceived(List<Poi> pois);
}
