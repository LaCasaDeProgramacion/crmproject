package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.ComplaintComments;
@Remote
public interface IComplaintCommentsRemote {

	public void addComment(ComplaintComments c ,int idComplaint);
	public boolean updateComment(ComplaintComments c);
	public boolean DeleteComment(int idComment);
	public List<ComplaintComments> GetCommentsByComplaint(int idComplaint);
	public void LikeComment(int idComment);
	public int GetNbLikes(int idComment);
}
