package geographicdistanceproject.geodistance.DataObject;

import java.util.ArrayList;
import java.util.List;

public class GeoDistanceReqObject {
	List<String> postalcodes = new ArrayList<String>();

	public List<String> getPostalcodes() {
		return postalcodes;
	}

	public void setPostalcodes(List<String> postalcodes) {
		this.postalcodes = postalcodes;
	}
	
}
