package crm.interfaces.prospecting;

import java.util.*;
import javax.ejb.Local;
import crm.entities.prospecting.*;

@Local
public interface IContractLocal {
	public List<Contract> allContracts();
	public List<Contract> searchForContract(String name);
	public boolean addContract(String title , Date startDate, Date endDate, float salary, String comment,String status, int idAgent ) ;
	public boolean updateContract(int id ,String title, Date startDate, Date endDate, float salary, String comment,String status, int idAgent) ;
	public boolean deleteContract(int id);

}
