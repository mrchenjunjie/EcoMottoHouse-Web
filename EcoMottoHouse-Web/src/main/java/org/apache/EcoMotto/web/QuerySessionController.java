package org.apache.EcoMotto.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.RandomStringUtils;


public class QuerySessionController {
	//a session list contains user email, session id and timestamp
	public static HashMap<String, ArrayList<String>> session = new HashMap<String, ArrayList<String>>();
	
	public void addSession(String email){
		String sessionID = RandomStringUtils.randomAlphabetic(10);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String timestamp = dateFormat.format(date);
		ArrayList<String> newSession = new ArrayList<String>();
		newSession.add(sessionID);
		newSession.add(timestamp);
		QuerySessionController.session.put(email, newSession);
	}
	
	public void deleteSession(String email){
		QuerySessionController.session.remove(email);
	}
	
	public boolean checkTimeExpiration(String email) throws ParseException{
		boolean result = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentTime = new Date();
		Date sessionTime = dateFormat.parse(QuerySessionController.session.get(email).get(1));
		int timeDifference = (int)(currentTime.getTime() - sessionTime.getTime()) / (1000*60);
		if(timeDifference > 10){
			result = true;
			return result;
		}else{
			return result;
		}
		
	}
	
	public boolean checkUserAvailable(String email){
		boolean result = false;
		if(QuerySessionController.session.containsKey(email)){
			result = true;
			return result;
		}else{
			return result;
		}
		
	}
	
	public boolean checkSessionAvailable(String email){
		boolean result = false;
		
		return result;
	}
	
	public void updateSession(String email){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String timestamp = dateFormat.format(date);
		QuerySessionController.session.get(email).set(1, timestamp);
	}
	
	public String getSessionID(String email){
		String sessionID = "";
		
		return sessionID;
	}
}