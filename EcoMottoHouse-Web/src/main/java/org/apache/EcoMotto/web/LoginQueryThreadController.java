package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginQueryThreadController extends Thread{
	private JSONObject request;
	private JSONObject result = null;
	
	public LoginQueryThreadController(JSONObject request){
		this.request = request;
	}
	public void run(){
		//JSONObject resultJson = new JSONObject();
        Customer customer = new Customer();
        DataStore dataStore = new DataStore();
        JSONObject tempResult = new JSONObject();
        QuerySessionController session = new QuerySessionController();
        try {
        	//JSONObject jsonObject = new JSONObject(data);
            if(session.checkUserAvailable(this.request.getString("email"))){
            	session.updateSession(this.request.getString("email"));
            	tempResult.put("result", "Login Success!");
            }else{
            	if(dataStore.ifCustomerExist(this.request.getString("email")) == true){
            		if(dataStore.ifAPIRole(this.request.getString("email")) == true){
            			customer = dataStore.readCustomer(this.request.getString("email"));
                        //JSONObject zip = this.request.getJSONObject("zip");
                        if(customer.getPassword().equals(this.request.get("password"))){
                        	tempResult.put("result", "Login Success!");
                        	session.addSession(this.request.getString("email"));
                        	//System.out.println("After user validation, start querying!");
                        	//tempResult = dataStore.zipQuery(zip);
                        	//System.out.println("Result from database: "+tempResult.toString());
                        }else{
                        	tempResult.put("result", "User ID does not match with password");
                        }
            		}else{
            			tempResult.put("result", "This user is not allowed to use API function");
            		}
            	}else{
            		tempResult.put("result", "User ID does not exist in the system");
            	}
            }
            //System.out.println("Message from client request: "+jsonObject.toString());
        	this.setResult(tempResult);
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
