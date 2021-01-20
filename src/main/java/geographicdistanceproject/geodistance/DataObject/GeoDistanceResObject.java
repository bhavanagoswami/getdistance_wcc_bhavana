package geographicdistanceproject.geodistance.DataObject;

import java.util.ArrayList;
import java.util.List;

public class GeoDistanceResObject {
	
	private List<LocationDetail> locationDetails;
	private double distance;
	private String unit;
	
	public GeoDistanceResObject() {
		locationDetails = new ArrayList<>();
	}
	public List<LocationDetail> getLocationDetails() {
		return locationDetails;
	}
	public void setLocationDetails(List<LocationDetail> locationDetails) {
		this.locationDetails = locationDetails;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
