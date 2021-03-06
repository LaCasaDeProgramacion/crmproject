package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Store")
public class Store implements Serializable {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	  @Column(name="store_id")
	    private int store_id;
		
		@Column(name="store_name")
	    private String store_name;
	

		@Column(name="store_longitude")
	    private String store_longitude;
		
		@Column(name="store_latitude")
	    private String store_latitude;
		@Column(name="store_city")
	    private String store_city;

		@Column(name="start")
	    private Timestamp start;
		@Column(name="end")
	    private Timestamp end;
		
		@Column(name="store_image")
	    private String store_image;

		public String getStore_image() {
			return store_image;
		}

		public void setStore_image(String store_image) {
			this.store_image = store_image;
		}

		@Column(name="distance")
	    private double distance;
		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

		@OneToMany(cascade = CascadeType.ALL,mappedBy = "store")
	    private Set<Product> product;



		public Timestamp getStart() {
			return start;
		}

		public void setStart(Timestamp start) {
			this.start = start;
		}

		public Timestamp getEnd() {
			return end;
		}

		public void setEnd(Timestamp end) {
			this.end = end;
		}

		public String getStore_city() {
			return store_city;
		}

		public void setStore_city(String store_city) {
			this.store_city = store_city;
		}

		public int getStore_id() {
			return store_id;
		}

		public void setStore_id(int store_id) {
			this.store_id = store_id;
		}

		public String getStore_name() {
			return store_name;
		}

		public void setStore_name(String store_name) {
			this.store_name = store_name;
		}

		public String getStore_longitude() {
			return store_longitude;
		}

		public void setStore_longitude(String store_longitude) {
			this.store_longitude = store_longitude;
		}

		public String getStore_latitude() {
			return store_latitude;
		}

		public void setStore_latitude(String store_latitude) {
			this.store_latitude = store_latitude;
		}

		


		
}