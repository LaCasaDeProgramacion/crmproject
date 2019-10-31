package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.NotificationComplaint;

@Remote
public interface INotificationRemote {

	void addNotification(NotificationComplaint notificationComplaint,int idComp);
	void MarkAllAsRead();
	void MarkNotifAsRead(int idnotif);
	List<NotificationComplaint> getallnotif();

}
