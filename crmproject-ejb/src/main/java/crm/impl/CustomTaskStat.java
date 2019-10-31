package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import crm.entities.ComplaintState;
import crm.entities.Complaints;
import crm.entities.ComplaintsStatistics;
import crm.entities.NotificationComplaint;
import crm.entities.Services;

@Singleton
@Startup
public class CustomTaskStat {

	@EJB
	ComplaintsImpl complaintImpl;
	@EJB
	TelphoneLinesImpl tellineImpl;
	@EJB
	ServicesImpl serviceImpl;
	@EJB
	ComplaintStatisticsImpl complaintStatImpl;
	@Resource
    private TimerService timerService;

    //private long repeatInterval = 60000L; // in milliseconds

    private long repeatInterval = 2628000000L; // in milliseconds
    
    @PostConstruct
    public void init() {
        timerService.createIntervalTimer(0L,
                repeatInterval, new TimerConfig(null, false));
    }

    @Timeout
    public void process(Timer timer) {
        doAction();
    }

    public void doAction() {
        System.out.println("Stat called!");
        int nbtechnical =complaintImpl.NbComplaintByType(1);
        int nbrelational=complaintImpl.NbComplaintByType(2);
        int nbfinancial=complaintImpl.NbComplaintByType(3);
        int nbinprogress=complaintImpl.NbComplaintByState("In_progress");
        int nbopened=complaintImpl.NbComplaintByState("Opened");
        int nbtreated=complaintImpl.NbComplaintByState("treated");
        int nbclosed=complaintImpl.NbComplaintByState("Closed_without_Solution");
        List<Complaints> listcomp=complaintImpl.GetAllComplaints();       
		ComplaintsStatistics cs=new ComplaintsStatistics(nbtechnical, nbfinancial, nbrelational, nbinprogress, nbopened, nbtreated, nbclosed, listcomp.size());
		complaintStatImpl.AddStatComplaint(cs);
		//service
		List<Services> listService=serviceImpl.GetAll();
		Map<Services, Integer> mapservicenb = new HashMap<>();
		for(Services s : listService)
		{
			int nb =serviceImpl.NbServiceUsed(s.getId());
			mapservicenb.put(s, nb);
		}
		Entry<Services, Integer> min = Collections.min(mapservicenb.entrySet(),
                Comparator.comparing(Entry::getValue));
		//serviceImpl.DeleteService(min.getKey().getId());
		System.out.println(min.getKey().getId());
    }
}
