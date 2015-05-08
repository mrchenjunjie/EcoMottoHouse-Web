package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;


public class QueryThreadController extends Thread{
	private JSONObject request;
	private JSONObject result = null;
	
	public QueryThreadController(JSONObject request){
		this.request = request;
	}
	public void run(){
		//JSONObject resultJson = new JSONObject();
        Customer customer = new Customer();
        DataStore dataStore = new DataStore();
        JSONObject tempResult = new JSONObject();
        try {
        	//JSONObject jsonObject = new JSONObject(data);
            
            //System.out.println("Message from client request: "+jsonObject.toString());
        	if(dataStore.ifCustomerExist(this.request.getString("email")) == true){
        		if(dataStore.ifAPIRole(this.request.getString("email")) == true){
        			customer = dataStore.readCustomer(this.request.getString("email"));
                    String query = this.request.getString("query");
                    if(customer.getPassword().equals(this.request.get("password"))){
                    	System.out.println("After user validation, start querying!");
                    	tempResult = dataStore.selectQuery(query);
                    	System.out.println("Result from database: "+tempResult.toString());
                    }else{
                    	tempResult.put("result", "User ID does not match with password");
                    }
        		}else{
        			tempResult.put("result", "This user is not allowed to use API function");
        		}
        	}else{
        		tempResult.put("result", "User ID does not exist in the system");
        	}
            
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
