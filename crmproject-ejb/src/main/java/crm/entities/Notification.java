package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Notification")
public class Notification implements Serializable {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	  @Column(name="id")
	    private int id;
		
		@Column(name="content")
	    private String content;
		@Column(name="url")
	    private String url;
		
		
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		
	
}
