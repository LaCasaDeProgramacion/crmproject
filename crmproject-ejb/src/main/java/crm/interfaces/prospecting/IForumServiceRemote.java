package crm.interfaces.prospecting;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Roles;
import crm.entities.User;
import crm.entities.prospecting.Category_Forum;
import crm.entities.prospecting.Forum;
import crm.entities.prospecting.Post;
import crm.entities.prospecting.Topic;



@Remote
public interface IForumServiceRemote {
	
	/* ----------------------- CRUD FORUM ----------------------- */
	public List<Forum> allForums();
	public Forum addForum(String name, String description, String picture);
	public int deleteForum(int id);
	public int updateForum(int id, String name, String description,  String picture);
	public List<Forum> popularForums(); 
	public List<Topic> topicsOfForum(int idForum); 

	/* ----------------------- CRUD Topic ----------------------- */
	public List<Topic> allTopics();
	public User getUser(int id);
	public int addTopic(String title, int idForum,  int idUser);
	public int deleteTopic(int id);
	public int updateTopic(int id, String title, int idForum );
	public Topic getById(int id); 

	/* ----------------------- CRUD Posts ----------------------- */

	public List<Post> allPosts();
	public int addPost(String text,int idTopic , int idUser );
	public int deletePost(int id);
	public int updatePost(int id, String text, int idTopic);
	public Object nbPostPerTopic (int idTopic); 

	/* ----------------------- des affichages  ----------------------- */
	public List<Object> TopicsPerForum(int idForum);
	public int checkView(int idUser, int idTopic);
	public List<Object> PostsPerTopics(int idTopic, int idUser); //update nb see
	
	/* ----------------------- suite aux vus  ----------------------- */
	public boolean sendEmailProspect();
	public void contactUs(String subject, String message); 


}
