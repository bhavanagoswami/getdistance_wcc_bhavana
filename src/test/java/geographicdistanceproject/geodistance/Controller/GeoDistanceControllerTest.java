package geographicdistanceproject.geodistance.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import geographicdistanceproject.geodistance.DataObject.GeoDistanceReqObject;
import geographicdistanceproject.geodistance.DataObject.GeoDistanceResObject;
import geographicdistanceproject.geodistance.DataObject.LocationDetail;
import geographicdistanceproject.geodistance.DataObject.PostCodeLatLng;
import geographicdistanceproject.geodistance.Manager.GeoDistanceManager;
import geographicdistanceproject.geodistance.Repository.PostCodeLatLegRepository;

import static org.mockito.Mockito.when;

@SpringBootTest
public class GeoDistanceControllerTest {
	
	@InjectMocks
	GeoDistanceController geoDistanceController;
	
	@Mock
	private GeoDistanceManager geoDistanceManager;
	
	@Mock
	private PostCodeLatLegRepository postCodeLatLegRepository;
	

	@Test
	public void getDistanceTest() throws Exception {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(postCodeLatLegRepository.findByPostalCode(Mockito.any(String.class))).thenReturn(oneLocationDetail());
		when(postCodeLatLegRepository.findByPostalCode(Mockito.any(String.class))).thenReturn(twoLocationDetail());
		
		when(geoDistanceManager.getDistance(Mockito.any(GeoDistanceReqObject.class))).thenReturn(getResponse());
		ResponseEntity<GeoDistanceResObject> response = geoDistanceController.getGeoDistanceWithCoordinates(getGeoDistanceValues());
		assertEquals(200,response.getStatusCodeValue());
	}

	public GeoDistanceResObject getResponse() {
		GeoDistanceResObject res = new GeoDistanceResObject();
		LocationDetail loc = new LocationDetail();
		loc.setPostalCode("AB10 6RN");
		loc.setLongitude(-2.121486688);
		loc.setLatitude(57.13787976);
		
		LocationDetail loc2 = new LocationDetail();
		loc2.setPostalCode("AB11 5QN");
		loc2.setLongitude(-2.093295);
		loc2.setLatitude(57.14270109);
		List<LocationDetail> list = new ArrayList<LocationDetail>();
		list.add(loc);
		list.add(loc2);
		double distance = 1.7833659945479678;
		String unit = "KM";
		res.setLocationDetails(list);
		res.setDistance(distance);
		res.setUnit(unit);
		return res;
	}
	
	 
	public GeoDistanceReqObject getGeoDistanceValues() {
		GeoDistanceReqObject locDtl = new GeoDistanceReqObject() ;
		List<String> postcode = new ArrayList<>();
		postcode.add("AB10 6RN");
		postcode.add("AB11 5QN");
		locDtl.setPostalcodes(postcode);
		return locDtl;
	}
	
	public PostCodeLatLng oneLocationDetail() {
		PostCodeLatLng loc = new PostCodeLatLng();
		loc.setPostalCode("AB10 6RN");
		loc.setLongitude(-2.121486688);
		loc.setLatitude(57.13787976);
		return loc;
	}
	
	public PostCodeLatLng twoLocationDetail() {
		PostCodeLatLng loc2 = new PostCodeLatLng();
		loc2.setPostalCode("AB11 5QN");
		loc2.setLongitude(-2.093295);
		loc2.setLatitude(57.14270109);
		return loc2;
	}
}
