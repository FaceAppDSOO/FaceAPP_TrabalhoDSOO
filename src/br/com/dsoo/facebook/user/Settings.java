package br.com.dsoo.facebook.user;

public class Settings{

	private final String id;
	private int activitiesReportSince, agendaSince, newsFeedSize;
	private boolean sendActivitiesReportEmail;
	private String activitiesReportEmail;


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
}
