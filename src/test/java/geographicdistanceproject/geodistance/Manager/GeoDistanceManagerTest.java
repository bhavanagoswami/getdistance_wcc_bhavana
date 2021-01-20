package geographicdistanceproject.geodistance.Manager;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import geographicdistanceproject.geodistance.DataObject.GeoDistanceReqObject;
import geographicdistanceproject.geodistance.DataObject.GeoDistanceResObject;
import geographicdistanceproject.geodistance.DataObject.LocationDetail;
import geographicdistanceproject.geodistance.DataObject.PostCodeLatLng;
import geographicdistanceproject.geodistance.Repository.PostCodeLatLegRepository;

@SpringBootTest
public class GeoDistanceManagerTest {
	
	@Mock
	private PostCodeLatLegRepository postCodeLatLegRepository;
	@InjectMocks
	private GeoDistanceManager geoDistanceManager;

	@Test
	public void testGetDistance() throws Exception {
		GeoDistanceReqObject req = getGeoDistanceValues();
		Mockito.when(postCodeLatLegRepository.findByPostalCode(req.getPostalcodes().get(0))).thenReturn(oneLocationDetail());
		Mockito.when(postCodeLatLegRepository.findByPostalCode(req.getPostalcodes().get(1))).thenReturn(twoLocationDetail());
		GeoDistanceResObject resObject = geoDistanceManager.getDistance(req);
		assertEquals(resObject.getClass(),getResponse().getClass());
	}
	
	@Test
	public void testGetDistance_ifDataNull() throws Exception {
		GeoDistanceReqObject req = getGeoDistanceValues();
		Mockito.when(postCodeLatLegRepository.findByPostalCode(null)).thenReturn(null);
		Mockito.when(postCodeLatLegRepository.findByPostalCode(req.getPostalcodes().get(1))).thenReturn(twoLocationDetail());
		GeoDistanceResObject resObject = geoDistanceManager.getDistance(req);
		assertEquals(resObject.getDistance(),0.0,Double.MAX_VALUE);
	}
	
	@Test
	public void testGetDistance_ifReqNull() throws Exception {
		GeoDistanceReqObject req = null;
		Mockito.when(postCodeLatLegRepository.findByPostalCode(null)).thenReturn(new PostCodeLatLng());
		Mockito.when(postCodeLatLegRepository.findByPostalCode(null)).thenReturn(twoLocationDetail());
		GeoDistanceResObject resObject = geoDistanceManager.getDistance(req);
		assertEquals(resObject.getDistance(),0.0,Double.MAX_VALUE);
	}
	
	@Test
	public void testUpdateCoordinates() throws Exception {
		String postcode = "AB10 1XG";
		double latitude = 57.144165160;
		double longitude = -2.114847769;
		Mockito.when(postCodeLatLegRepository.findByPostalCode(postcode)).thenReturn(existingValue());
		Mockito.when(postCodeLatLegRepository.save(Mockito.any(PostCodeLatLng.class))).thenReturn(updatedValue());
		PostCodeLatLng resObject = geoDistanceManager.updateCoordinates(postcode,latitude,longitude);
		assertEquals(resObject.getPostalCode(),updatedValue().getPostalCode());
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
	
	public PostCodeLatLng updatedValue() {
		PostCodeLatLng loc = new PostCodeLatLng();
		loc.setPostalCode("AB10 1XG");
		loc.setLongitude(57.144165160);
		loc.setLatitude(-2.114847769);
		return loc;
	}
	
	public PostCodeLatLng existingValue() {
		PostCodeLatLng loc = new PostCodeLatLng();
		loc.setPostalCode("AB10 1XG");
		loc.setLongitude(57.144165160);
		loc.setLatitude(-2.114847768);
		return loc;
	}
}
