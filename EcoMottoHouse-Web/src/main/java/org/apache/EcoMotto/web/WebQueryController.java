package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebQueryController {
        //@Autowired
        //private IPersonService personService;
	@RequestMapping(value={"/api/web-query"}, method=RequestMethod.POST, produces="text/plain")
    public @ResponseBody String onSubmit(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException, InterruptedException 
    		 {
                
                JSONObject requestFromClient = new JSONObject();
                requestFromClient.put("email", request.getParameter("email"));
                requestFromClient.put("password", request.getParameter("password"));
                requestFromClient.put("query", request.getParameter("query"));

                QueryThreadController queryThread = new QueryThreadController(requestFromClient);
    	        
    	        queryThread.start();
    	        Thread.sleep(5000);
                if(queryThread.getResult() != null){
                	return queryThread.getResult().toString();
                }else{
                	return "Fail to Exception!";
                }  
                //JSONObject request = new JSONObject(data);
              
        }
}