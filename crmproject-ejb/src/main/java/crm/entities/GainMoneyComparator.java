package crm.entities;

import java.util.Comparator;

public class GainMoneyComparator implements  Comparator<StatPack>  {

	@Override
	public int compare(StatPack o1, StatPack o2) {
		if (o1.getGainmoney() < o2.getGainmoney()) return -1; 
        if (o1.getGainmoney() >o2.getGainmoney()) return 1; 
        else return 0; 
	}

}
