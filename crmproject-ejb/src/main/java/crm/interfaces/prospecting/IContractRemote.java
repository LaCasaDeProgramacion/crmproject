package crm.interfaces.prospecting;


import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;
import crm.entities.prospecting.Contract;

@Remote
public interface IContractRemote {

	public List<Contract> allContracts();
	public List<Contract> searchForContract(String name);
	public boolean addContract(String title , Date startDate, Date endDate, float salary, String comment,String status, int idAgent ) ;
	public boolean updateContract(int id ,String title, Date startDate, Date endDate, float salary, String comment,String status, int idAgent) ;
	public boolean deleteContract(int id);
}
