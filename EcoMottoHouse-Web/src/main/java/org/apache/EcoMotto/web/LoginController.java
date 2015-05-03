package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class LoginController {
        //@Autowired
        //private IPersonService personService;
        @RequestMapping("/home")
    public String onSubmit(@RequestParam(value="userId", required=false) String userId,
                @RequestParam(value="password", required=false) String password,
                Model model) throws IOException, URISyntaxException, JSONException, SQLException {
                //model.addAttribute("msg", "Hello "+personService.getPersonName() );
                model.addAttribute("userId", userId);
                model.addAttribute("password", password);
                JSONObject result = new JSONObject();
                String query = "select product from PROD_MATRIX where STATE = 'TX'";
                Customer customer = new Customer();
                DataStore dataStore = new DataStore();
                customer = dataStore.readCustomer(userId);
                if(customer.getPassword().equals(password)){
                	result = dataStore.selectQuery(query);
                	System.out.println("Query Result: "+result.toString());
                	return "home";
                }
                return "error";
        }
}