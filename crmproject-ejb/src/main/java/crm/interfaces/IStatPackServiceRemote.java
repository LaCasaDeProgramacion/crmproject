package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.StatPack;

@Remote
public interface IStatPackServiceRemote {
	////system schedule method 
	public void addstatpack(Pack p);
	public void updatestatpack(Pack p);
    public void updatestatpacktitle();
	public void BestpackforToday();
	public StatPack mostgainMoneyPackToday();
	public StatPack mostSelledQuantitypacktoday();
	public void ArchivePackbyTitle();
	public void ArchiverCountDays();
	//jsonproducemethod
	public StatPack getStatpack(Pack p);
	public List<StatPack> getallStatPack();
	public StatPack jsonmostgainMoneyPackToday();
	public StatPack jsonSelledQuantitypacktoday();
	public StatPack jsonBestpackforToday();
	public StatPack jsonPackoftheMonth();
    public List<StatPack> jsonStatPacksByMonth(String Month);
    public List<StatPack> jsonStatPacksByYear(String Year);
    public List<StatPack> jsonStatPacksByMonthandYear(String Month ,String Year);
    public List<StatPack> jsonStatPackByTitle(String ts);
    public List<StatPack> jsonStatPackByEverething(String Month ,String Year,String ts);

}
