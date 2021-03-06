package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.ComplaintComments;

@Local
public interface IComplaintCommentsLocal {

	public void addComment(ComplaintComments c ,int idComplaint);
	public boolean updateComment(ComplaintComments c);
	public boolean DeleteComment(int idComment);
	public List<ComplaintComments> GetCommentsByComplaint(int idComplaint);
	public void LikeComment(int idComment);
	public int GetNbLikes(int idComment);
}
