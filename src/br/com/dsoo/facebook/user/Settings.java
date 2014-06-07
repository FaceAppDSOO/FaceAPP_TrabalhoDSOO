package br.com.dsoo.facebook.user;

public class Settings{

	private final String id;
	private int activitiesReportSince, agendaSince, newsFeedSize;
	private boolean sendActivitiesReportEmail, likePhotosWhenTagged, downloadPhotosWhenTagged = true, likeUsersListStatuses;
	private String activitiesReportEmail, downloadPhotoFilePath;
	private String[] usersIdsToLikeStatuses;


	public Settings(String userId){
		id = userId;
	}

	public int getActivitiesReportSince(){
		return activitiesReportSince;
	}

	public void setActivitiesReportSince(int activitiesReportSince){
		this.activitiesReportSince = activitiesReportSince;
	}

	public int getAgendaSince(){
		return agendaSince;
	}

	public void setAgendaSince(int agendaSince){
		this.agendaSince = agendaSince;
	}

	public int getNewsFeedSize(){
		return newsFeedSize;
	}

	public void setNewsFeedSize(int newsFeedSize){
		this.newsFeedSize = newsFeedSize;
	}

	public String getId(){
		return id;
	}
	
	public boolean isSendActivitiesReportEmail(){
		return sendActivitiesReportEmail;
	}

	public void setSendActivitiesReportEmail(boolean sendActivitiesReportEmail){
		this.sendActivitiesReportEmail = sendActivitiesReportEmail;
	}

	public String getActivitiesReportEmail(){
		return activitiesReportEmail;
	}

	public void setActivitiesReportEmail(String activitiesReportEmail){
		this.activitiesReportEmail = activitiesReportEmail;
	}

	public boolean isLikePhotosWhenTagged(){
		return likePhotosWhenTagged;
	}

	public void setLikePhotosWhenTagged(boolean likePhotosWhenTagged){
		this.likePhotosWhenTagged = likePhotosWhenTagged;
	}

	public boolean isDownloadPhotosWhenTagged(){
		return downloadPhotosWhenTagged;
	}

	public void setDownloadPhotosWhenTagged(boolean downloadPhotosWhenTagged){
		this.downloadPhotosWhenTagged = downloadPhotosWhenTagged;
	}

	public boolean isLikeUsersListStatuses(){
		return likeUsersListStatuses;
	}

	public void setLikeUsersListStatuses(boolean likeUsersListStatuses){
		this.likeUsersListStatuses = likeUsersListStatuses;
	}

	public String[] getUsersIdsToLikeStatuses(){
		return usersIdsToLikeStatuses;
	}

	public void setUsersIdsToLikeStatuses(String[] usersIdsToLikeStatuses){
		this.usersIdsToLikeStatuses = usersIdsToLikeStatuses;
	}

	public String getDownloadPhotoFilePath(){
		return downloadPhotoFilePath;
	}

	public void setDownloadPhotoFilePath(String downloadPhotoFilePath){
		this.downloadPhotoFilePath = downloadPhotoFilePath;
	}
}
