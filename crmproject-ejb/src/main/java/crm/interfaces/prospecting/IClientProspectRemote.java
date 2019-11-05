package crm.interfaces.prospecting;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IClientProspectRemote {

	public boolean add();

	public List<Object> ViewProspectEvolution();

	public List<Object> ViewClientEvolution();

	public boolean  DeletePerDate(Date date);

}
