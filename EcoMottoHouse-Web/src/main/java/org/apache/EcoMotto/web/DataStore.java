package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.EcoMotto.web.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.EcoMotto.web.Customer;

@Controller
public class DataStore {
	public void writeCustomer(Customer customer)throws IOException, URISyntaxException, SQLException{
		
		String url = "jdbc:oracle:thin:@169.54.208.180:1521/prodcatalog1pdb"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "nrg_prd");
        props.setProperty("password", "admin1");
      
        //creating connection to Oracle database using JDBC
        Connection conn = DriverManager.getConnection(url,props);
        //System.out.println("Testing the customer: "+customer.getUsername());
        String sql ="INSERT INTO customer (username, password, email) VALUES ('"+customer.getUsername()+
        		"','"+customer.getPassword()+"','"+customer.getEmail()+"')";
        //System.out.println("Testing the query before execution: "+sql);
        //PreparedStatement preStatement = connection.prepareStatement(sql);
        //preStatement.executeUpdate();
        //Statement stmt=connection.createStatement();
        //stmt.executeUpdate(sql);
        PreparedStatement preStatement = conn.prepareStatement(sql);
        preStatement.executeUpdate();
        //ResultSet result = stmt.executeQuery(sql);
        
        conn.close();
	}
	
	public Customer readCustomer(String customerUsername)throws IOException, URISyntaxException, SQLException{
		Customer customer = new Customer();
		customer.setUsername(customerUsername);
		
		String url = "jdbc:oracle:thin:@169.54.208.180:1521/prodcatalog1pdb"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "nrg_prd");
        props.setProperty("password", "admin1");
      
        //creating connection to Oracle database using JDBC
        Connection conn = DriverManager.getConnection(url,props);

        String sql ="select USER_PASSWORD from USERS where USER_EMAIL = '"+customerUsername+"'";

        //creating PreparedStatement object to execute query
        PreparedStatement preStatement = conn.prepareStatement(sql);
    
        ResultSet result = preStatement.executeQuery();
        result.next();
        customer.setPassword(result.getString("USER_PASSWORD"));
      
        return customer;
	}
	
	public JSONObject selectQuery(String query) throws SQLException, JSONException{
		//JSONObject queryResult = new JSONObject();
		JSONObject finalResult = new JSONObject();
		
		String url = "jdbc:oracle:thin:@169.54.208.180:1521/prodcatalog1pdb"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "nrg_prd");
        props.setProperty("password", "admin1");
      
        //creating connection to Oracle database using JDBC
        Connection conn = DriverManager.getConnection(url,props);

        //String sql ="select password from customer where username = '"+customerUsername+"'";

        //creating PreparedStatement object to execute query
        PreparedStatement preStatement = conn.prepareStatement(query);
    
        ResultSet result = preStatement.executeQuery();
        ResultSetMetaData rsMeta = result.getMetaData();
        int numberofColumns = rsMeta.getColumnCount();
        String[] columnNames = new String[numberofColumns];
        String[] dataTypes = new String[numberofColumns];
        for(int i = 0; i < numberofColumns; i++){
        	columnNames[i] = rsMeta.getColumnName(i+1);
        	System.out.println("Column Name: "+columnNames[i]);
        	dataTypes[i] = rsMeta.getColumnClassName(i+1);
        	System.out.println("Column Data Type: "+dataTypes[i]);
        }
        //String columnName = rsMeta.getColumnName(1);
        //System.out.println("Column Name: "+columnName);
        //String dataType = rsMeta.getColumnClassName(1);
        //System.out.println("Column Data Type: "+dataType);
        int count = 1;
        while(result.next()){
        	JSONObject tempResult = new JSONObject();
        	for(int j=0; j<numberofColumns; j++){
        		//String tempDataType = dataTypes[j];
        		//JSONObject tempResult = new JSONObject();
        		switch(dataTypes[j]){
	        		case "java.lang.String":
	        			tempResult.put(columnNames[j], result.getString(columnNames[j]));
	        			System.out.println("From database get String: "+result.getString(columnNames[j]));
	        			break;
	        		case "java.math.BigDecimal":
	        			tempResult.put(columnNames[j], result.getInt(columnNames[j]));
	        			System.out.println("From database get Integer: "+result.getString(columnNames[j]));
	        			break;
	        		default:
	        			break;
        		}
        		
        	}
        	finalResult.put("result"+Integer.toString(count), tempResult);
    		count++;
        	
        	//queryResult.put("result"+Integer.toString(count), result.toString());
        	//count++;
        }		
		return finalResult;
	}
}
