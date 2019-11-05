package crm.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import crm.entities.Complaints;
import crm.entities.NotificationComplaint;
@Singleton
@Startup
public class Customtask {

	@EJB
	ComplaintsImpl complaintImpl;
	@EJB
	NotificationImpl notifImpl;
	@Resource
    private TimerService timerService;

    //private long repeatInterval = 60000L; // in milliseconds
    private long repeatInterval = 86400000L; // in milliseconds

    
    @PostConstruct
    public void init() {
        timerService.createIntervalTimer(0L,
                repeatInterval, new TimerConfig(null, false));
    }
/*
    @Timeout
    public void process(Timer timer) {
        doAction();
    }
    */
/*
    public void doAction() {
        System.out.println("Action called!");
        List<Complaints> listcomp=complaintImpl.GetComplaintsByState("In_progress");
        for(Complaints c:listcomp)
        {
        	NotificationComplaint nc=new NotificationComplaint(c.getComplaintObject());
        	notifImpl.addNotification(nc, c.getId());
        }
    }
    */

}
