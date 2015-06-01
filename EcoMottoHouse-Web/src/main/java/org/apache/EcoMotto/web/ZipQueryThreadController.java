package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;


public class ZipQueryThreadController extends Thread{
	private JSONObject request;
	private JSONObject result = null;
	
	public ZipQueryThreadController(JSONObject request){
		this.request = request;
	}
	public void run(){
		//JSONObject resultJson = new JSONObject();
        //Customer customer = new Customer();
        DataStore dataStore = new DataStore();
        JSONObject tempResult = new JSONObject();
        QuerySessionController session = new QuerySessionController();
        try {
        	//JSONObject jsonObject = new JSONObject(data);
            if(session.checkUserAvailable(request.getString("email"))){
            	if(session.checkTimeExpiration(request.getString("email"))){
            		session.deleteSession(request.getString("email"));
            		tempResult.put("result", "session expired, please login!");
            	}else{
            		JSONObject zip = this.request.getJSONObject("zip");
            		tempResult = dataStore.zipQuery(zip);
            		session.updateSession(request.getString("email"));
            	}
            }else{
            	tempResult.put("result", "session error, please login!");
            }
            //System.out.println("Message from client request: "+jsonObject.toString());
        	
            
        	this.setResult(tempResult);
        	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
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
