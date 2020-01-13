package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Remote;
import crm.entities.prospecting.Agent;

@Remote
public interface IAgentRemote {

	public List<Agent> allAgents();
	public List<Agent> searchForAgent(int cin);
	public Agent addAgent(Agent agent) ;
	public int deleteAgent(int id);
	public int updateAgent(Agent agent) ;
	public Agent getById(int id);
	public int getIdContract(int idAgent);
}
