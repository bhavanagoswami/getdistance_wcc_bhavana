package geographicdistanceproject.geodistance.Manager;

import geographicdistanceproject.geodistance.DataObject.*;
import geographicdistanceproject.geodistance.Repository.PostCodeLatLegRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GeoDistanceManager {
	Logger logger = LoggerFactory.getLogger(GeoDistanceManager.class);
    @Autowired
    private PostCodeLatLegRepository postCodeLatLegRepository;

    private static final String unit ="KM";
    private final static double EARTH_RADIUS = 6371;
    public GeoDistanceResObject getDistance(GeoDistanceReqObject req) {
     logger.info("getDistance method started ");
	 PostCodeLatLng pcDtl1 = null;
	 PostCodeLatLng pcDtl2 = null;
	 GeoDistanceResObject res = new GeoDistanceResObject();
	 if(Objects.nonNull(req)) {
		 logger.info("request postcodes : ");
        if(Objects.nonNull(req.getPostalcodes().get(0)))
        	logger.info("postcode1:"+req.getPostalcodes().get(0));
        	pcDtl1 = postCodeLatLegRepository.findByPostalCode(req.getPostalcodes().get(0));
        if(Objects.nonNull(req.getPostalcodes().get(1)))
        	logger.info("postcode2:"+req.getPostalcodes().get(1));
        	pcDtl2 = postCodeLatLegRepository.findByPostalCode(req.getPostalcodes().get(1));
        if(!Objects.isNull(pcDtl1) && !Objects.isNull(pcDtl2)) {
	        setPoints(pcDtl1,pcDtl2,res);
	        double distance = calculateDistance(pcDtl1.getLatitude(),pcDtl1.getLongitude(),pcDtl2.getLatitude(), pcDtl2.getLongitude());
	        res.setDistance(distance);
	        res.setUnit(unit);
        }
	 }
	 logger.info("getDistance method end ");
     return res;
    }
    
    
    @Transactional
    public PostCodeLatLng updateCoordinates(String postcode,double latitude,double longitude) {
    	logger.info("updateCoordinates method started "+postcode+" "+latitude+" "+longitude);
		PostCodeLatLng existingPostcodeDtl = null;
		PostCodeLatLng updatePostcodeDtl = new PostCodeLatLng();
		PostCodeLatLng newPostcode = null;
		try {
			if(Objects.nonNull(postcode)) {
				existingPostcodeDtl = postCodeLatLegRepository.findByPostalCode(postcode);
		    	if(Objects.nonNull(existingPostcodeDtl)) {
		    		updatePostcodeDtl.setPostalCode(existingPostcodeDtl.getPostalCode());
		    		updatePostcodeDtl.setLatitude(latitude);
		    		updatePostcodeDtl.setLongitude(longitude);
		    	}else {
		    		updatePostcodeDtl.setPostalCode(postcode);
		    		updatePostcodeDtl.setLatitude(latitude);
		    		updatePostcodeDtl.setLongitude(longitude);
		    	}
		    	newPostcode = postCodeLatLegRepository.save(updatePostcodeDtl);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		logger.info("updateCoordinates method end ");
		return newPostcode;
    }


    private void setPoints(PostCodeLatLng pcDtl1,PostCodeLatLng pcDtl2,GeoDistanceResObject res){
        List<LocationDetail> locDetailsList = new ArrayList<LocationDetail>();
        LocationDetail detail1 = new LocationDetail();
        detail1.setPostalCode(pcDtl1.getPostalCode());
        detail1.setLatitude(pcDtl1.getLatitude());
        detail1.setLongitude(pcDtl1.getLongitude());
        locDetailsList.add(detail1);

        LocationDetail detail2 = new LocationDetail();
        detail2.setPostalCode(pcDtl2.getPostalCode());
        detail2.setLatitude(pcDtl2.getLatitude());
        detail2.setLongitude(pcDtl2.getLongitude());
        locDetailsList.add(detail2);
        res.setLocationDetails(locDetailsList);
    }

    private double calculateDistance(double latitude, double longitude, double latitude2, double
            longitude2) {
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }
    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }
    private double square(double x) {
        return x * x;
    }

}
