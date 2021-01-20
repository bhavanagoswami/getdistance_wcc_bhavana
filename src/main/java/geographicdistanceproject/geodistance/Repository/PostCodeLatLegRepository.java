package geographicdistanceproject.geodistance.Repository;

import geographicdistanceproject.geodistance.DataObject.PostCodeLatLng;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PostCodeLatLegRepository extends CrudRepository<PostCodeLatLng, Serializable> {
    public PostCodeLatLng findByPostalCode(String postcode);
    @SuppressWarnings("unchecked")
	public PostCodeLatLng save(PostCodeLatLng p);
}
