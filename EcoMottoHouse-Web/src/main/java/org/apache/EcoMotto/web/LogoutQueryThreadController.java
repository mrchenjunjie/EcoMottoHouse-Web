package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;


public class LogoutQueryThreadController extends Thread{
	private JSONObject request;
	private JSONObject result = null;
	
	public LogoutQueryThreadController(JSONObject request){
		this.request = request;
	}
	public void run(){
		//JSONObject resultJson = new JSONObject();
        //Customer customer = new Customer();
        //DataStore dataStore = new DataStore();
        JSONObject tempResult = new JSONObject();
        QuerySessionController session = new QuerySessionController();
        try {
        	session.deleteSession(this.request.getString("email"));
        	tempResult.put("result", "Logout Success!");
        	            
        	this.setResult(tempResult);
        	
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
}
