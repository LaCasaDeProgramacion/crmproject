package crm.interfaces.prospecting;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Roles;
import crm.entities.prospecting.Category_Forum;
import crm.entities.prospecting.Forum;
import crm.entities.prospecting.Post;
import crm.entities.prospecting.Topic;



@Remote
public interface IForumServiceRemote {
	
	/* ----------------------- CRUD FORUM ----------------------- */
	public List<Forum> allForums();
	public void addForum(String name, String description, Roles auth_view, Roles auth_post, Category_Forum category_Forum);
	public boolean deleteForum(int id);
	public boolean updateForum(int id, String name, String description, Roles auth_view, Roles auth_post,
			Category_Forum category_Forum);
	/* ----------------------- CRUD Topic ----------------------- */
	public List<Topic> allTopics();
	public int addTopic(String title, int idForum);
	public int deleteTopic(int id);
	public int updateTopic(int id, String title, int idForum);
	/* ----------------------- CRUD Posts ----------------------- */

	public List<Post> allPosts();
	public int addPost(String text, int idTopic);
	public int deletePost(int id);
	public int updatePost(int id, String text, int idTopic);
	/* ----------------------- des affichages  ----------------------- */
	public List<Object> TopicsPerForum(int idForum);
	public int checkView(int idUser, int idTopic);
	public List<Object> PostsPerTopics(int idTopic);
	
	/* ----------------------- suite aux vus  ----------------------- */
	public void sendEmailProspect();
	

}
