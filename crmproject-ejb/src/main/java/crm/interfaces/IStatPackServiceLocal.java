package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Pack;
import crm.entities.StatPack;


@Local
public interface IStatPackServiceLocal {
	public void addstatpack(Pack packid);
	public void updatestatpack(Pack packid, double gainmoney, int quantityselled, boolean changetitle);
	public StatPack getStatpack(Pack packid);
	public List<StatPack> getallStatPack();
}
