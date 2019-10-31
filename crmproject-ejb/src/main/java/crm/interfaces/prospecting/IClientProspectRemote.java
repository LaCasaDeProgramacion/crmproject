package crm.interfaces.prospecting;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IClientProspectRemote {

	public void add();

	public List<Object> ViewProspectEvolution();

	public List<Object> ViewClientEvolution();

	public void DeletePerDate(Date date);

}
