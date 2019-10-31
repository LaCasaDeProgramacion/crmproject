package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.NotificationComplaint;

@Local
public interface INotificationLocal {

	void addNotification(NotificationComplaint notificationComplaint,int idComp);
	void MarkAllAsRead();
	void MarkNotifAsRead(int idnotif);
	List<NotificationComplaint> getallnotif();
}
