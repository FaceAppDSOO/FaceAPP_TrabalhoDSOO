package br.com.dsoo.facebook.logic.constants;

public enum AppData{
	AUTH_APP_ID("505235762921334"),
	AUTH_APP_SECRET("2d32e3f33eeb205f3a86f0319e58323f"),
	AUTH_APP_PERMISSIONS("email,publish_stream,user_birthday,read_stream,user_relationships,user_friends,user_activities,user_events,friends_events,read_friendlists,read_mailbox,read_page_mailboxes,user_checkins,user_likes,friends_birthday,friends_mobile_phone,user_groups,user_location,user_relationships,user_relationship_details,publish_actions"),
	AUTH_URL("http://www.example.com/"),
	
	APP_EMAIL("faceapp.dsoo@gmail.com"),
	APP_EMAIL_LOGIN("faceapp.dsoo"),
	APP_EMAIL_PASS("trabalhodsoo");

	private String value;
	AppData(String v){
		value = v;
	}

	public String getValue(){
		return value;
	}
}
