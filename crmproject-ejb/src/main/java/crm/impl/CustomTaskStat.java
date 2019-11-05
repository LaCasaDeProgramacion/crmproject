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
import javax.mail.MessagingException;

import crm.entities.ComplaintState;
import crm.entities.Complaints;
import crm.entities.ComplaintsStatistics;
import crm.entities.NotificationComplaint;
import crm.entities.Services;
import crm.entities.StatisticsService;
import crm.entities.TelephoneLines;

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
	@EJB
	ServiceStatisticsImpl servicestatimpl;
	@Resource
    private TimerService timerService;
	
	Mail_API mail;

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
		List<Services> listServiceALL=serviceImpl.GetAll();
		for(Services s : listServiceALL)
		{
			int nb =serviceImpl.NbServiceUsed(s.getId());
			StatisticsService st=new StatisticsService(nb, s);
			servicestatimpl.AddStatService(st);
		}
		List<Services> listService=serviceImpl.GetEnabledService();
		Map<Services, Integer> mapservicenb = new HashMap<>();
		for(Services s : listService)
		{
			int nb =serviceImpl.NbServiceUsed(s.getId());
			//StatisticsService st=new StatisticsService(nb, s);
			//servicestatimpl.AddStatService(st);
			mapservicenb.put(s, nb);
		}
		Entry<Services, Integer> min = Collections.min(mapservicenb.entrySet(),
                Comparator.comparing(Entry::getValue));
		Entry<Services, Integer> max = Collections.max(mapservicenb.entrySet(),
                Comparator.comparing(Entry::getValue));
		if(min.getValue()==0)
		{
			serviceImpl.DisableService(min.getKey().getId());

		}
		else
		{
		List<TelephoneLines> listTel=tellineImpl.GetTelephoneLinesByService(min.getKey().getId());
		for(TelephoneLines t:listTel)
		{
			System.out.println("**************"+t.getLineNumber());
			try {

				mail.sendMail(t.getUser().getEmail(), "Your service will be deactivated",
						t.getServices().getServiceName() + " will be deactivated and we will affect a new service to your line ");

			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
			tellineImpl.AffectService(t.getId(), max.getKey().getId());
			serviceImpl.DisableService(min.getKey().getId());
			
		}
		}
		
		System.out.println(min.getKey().getId());
		System.out.println(max.getKey().getId());

    }
}
