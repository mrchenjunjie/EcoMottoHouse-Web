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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.EcoMotto.web.Customer;

@Controller
public class DataStore {
	public void writeCustomer(Customer customer)throws IOException, URISyntaxException, SQLException{
		
//		try {		 
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			System.out.println("Driver Found!");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		Connection connection = null;
// 
//		try {
// 
//			connection = DriverManager.getConnection(
//					"jdbc:oracle:thin:@169.54.208.180:1521/prodcatalog1pdb", "System",
//					"EcoMotto#1");
//			System.out.println("Database Connected!");
//		} catch (SQLException e) {
//			e.printStackTrace(); 
//		}
		
		String url = "jdbc:oracle:thin:@169.54.208.180:1521/prodcatalog1pdb"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "nrg_prd");
        props.setProperty("password", "admin1");
      
        //creating connection to Oracle database using JDBC
        Connection conn = DriverManager.getConnection(url,props);
        System.out.println("Testing the customer: "+customer.getUsername());
        String sql ="INSERT INTO customer (username, password, email) VALUES ('"+customer.getUsername()+
        		"','"+customer.getPassword()+"','"+customer.getEmail()+"')";
        System.out.println("Testing the query before execution: "+sql);
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

        String sql ="select password from customer where username = '"+customerUsername+"'";

        //creating PreparedStatement object to execute query
        PreparedStatement preStatement = conn.prepareStatement(sql);
    
        ResultSet result = preStatement.executeQuery();
        result.next();
        customer.setPassword(result.getString("password"));
      
        return customer;
	}
	
	public JSONObject selectQuery(String query) throws SQLException, JSONException{
		JSONObject queryResult = new JSONObject();
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
        int count = 1;
        while(result.next()){
        	queryResult.put("result"+Integer.toString(count), result.getString("product"));
        	count++;
        }
        finalResult.put("result", queryResult);
		
		return finalResult;
	}
}
