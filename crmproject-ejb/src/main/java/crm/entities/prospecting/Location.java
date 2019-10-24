package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Location implements Serializable {
	
	private float latitude; 
	private float longitude ;
	public Location(float latitude, float longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Location() {
		super();
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	} 

}
