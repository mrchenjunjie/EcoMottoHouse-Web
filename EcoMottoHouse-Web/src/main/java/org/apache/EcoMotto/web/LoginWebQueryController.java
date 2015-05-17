package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class LoginWebQueryController {
        //@Autowired
        //private IPersonService personService;
	@RequestMapping(value={"/api/web-login"}, method=RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException, InterruptedException {
		
		JSONObject requestFromClient = new JSONObject();
        requestFromClient.put("email", request.getParameter("email"));
        requestFromClient.put("password", request.getParameter("password"));

        LoginQueryThreadController queryThread = new LoginQueryThreadController(requestFromClient);
        
        queryThread.start();
        Thread.sleep(5000);
        if(queryThread.getResult().getString("result").equals("success")){
        	HttpSession session = request.getSession();
            session.setAttribute("user", request.getParameter("email"));
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(10*60);
            Cookie userName = new Cookie("user", request.getParameter("email"));
            userName.setMaxAge(10*60);
            response.addCookie(userName);
            //response.sendRedirect("LoginSuccess");
        	return "LoginSuccess";
        }else{
        	return "error";
        }
           
	}
}
