package crm.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ComplaintComments")
public class ComplaintComments implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ComplaintCommentsId")
	private int id;
	@Column(name = "Comment")
	private String Comment;
	@Column(name = "CommentDate")
	private Date CommentDate;
	@Column(name = "Likes")
	private int Likes;
	@ManyToOne
	Complaints complaint;
	@ManyToOne
	User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public Date getCommentDate() {
		return CommentDate;
	}
	public void setCommentDate(Date commentDate) {
		CommentDate = commentDate;
	}
	public int getLikes() {
		return Likes;
	}
	public void setLikes(int likes) {
		Likes = likes;
	}
	public Complaints getComplaint() {
		return complaint;
	}
	public void setComplaint(Complaints complaint) {
		this.complaint = complaint;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ComplaintComments(int id, String comment, Date commentDate, int likes, Complaints complaint, User user) {
		super();
		this.id = id;
		Comment = comment;
		CommentDate = commentDate;
		Likes = likes;
		this.complaint = complaint;
		this.user = user;
	}
	public ComplaintComments() {
		super();
	}
	public ComplaintComments(String comment, Date commentDate, int likes) {
		super();
		Comment = comment;
		CommentDate = commentDate;
		Likes = likes;
	}
	public ComplaintComments(int id, String comment, Date commentDate, int likes) {
		super();
		this.id = id;
		Comment = comment;
		CommentDate = commentDate;
		Likes = likes;
	}
	

}
