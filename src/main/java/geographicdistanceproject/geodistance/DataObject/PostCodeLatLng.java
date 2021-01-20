package geographicdistanceproject.geodistance.DataObject;

import javax.persistence.*;

@Entity
@Table(name = "postcodelatlng")
public class PostCodeLatLng {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name="postcode")
    private String postalCode;

    @Column(name="latitude")
    private double latitude;

    @Column(name="longitude")
    private double longitude;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
