package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.StatPack;

@Remote
public interface IStatPackServiceRemote {
public void addstatpack(Pack packid);


public StatPack getStatpack(Pack packid);
public List<StatPack> getallStatPack();
public void updatestatpack(Pack packid, double gainmoney, int quantityselled , boolean changetitle);

}
