package geographicdistanceproject.geodistance.Controller;

import geographicdistanceproject.geodistance.DataObject.GeoDistanceReqObject;
import geographicdistanceproject.geodistance.DataObject.GeoDistanceResObject;
import geographicdistanceproject.geodistance.DataObject.PostCodeLatLng;
import geographicdistanceproject.geodistance.DataObject.UpdateCoordinatesReqObject;
import geographicdistanceproject.geodistance.Manager.GeoDistanceManager;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeoDistanceController {

    @Autowired
    private GeoDistanceManager geoDistanceManager;

    @RequestMapping(value = "api/getDistance",method = RequestMethod.POST)
    public ResponseEntity <GeoDistanceResObject> getGeoDistanceWithCoordinates(@RequestBody GeoDistanceReqObject req){
        GeoDistanceResObject res = null;
        try {
             res = geoDistanceManager.getDistance(req);
        if(Objects.isNull(res)) {
        	return new ResponseEntity<GeoDistanceResObject>(HttpStatus.BAD_REQUEST);
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<GeoDistanceResObject>(res,HttpStatus.OK);
    }
    
    @RequestMapping(value = "api/updateCoordinates",method = RequestMethod.POST)
    public ResponseEntity<PostCodeLatLng> updatePostalCodeWithCoordinates(@RequestBody UpdateCoordinatesReqObject req) {
    	PostCodeLatLng postcodeDtl = null;
    	try {
    		postcodeDtl = geoDistanceManager.updateCoordinates(req.getPostcode(),req.getLetitude(),req.getLongitude());
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return new ResponseEntity<PostCodeLatLng>(postcodeDtl,HttpStatus.OK);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
