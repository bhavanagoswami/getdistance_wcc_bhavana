package geographicdistanceproject.geodistance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import geographicdistanceproject.geodistance.Controller.GeoDistanceController;

@SpringBootTest
class GeodistanceApplicationTests {

	@Autowired
	private GeoDistanceController geoDistanceController;

    @Test
    void contextLoads() {
    	assertNotNull(geoDistanceController);
    }

}
