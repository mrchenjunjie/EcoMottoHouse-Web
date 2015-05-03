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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class QueryController {
        //@Autowired
        //private IPersonService personService;
	@RequestMapping(value={"/api/query"}, method=RequestMethod.POST)
    public @ResponseBody String onSubmit(@RequestParam(value="data", required=false) String data,
                Model model) throws IOException, URISyntaxException, JSONException, SQLException {

                model.addAttribute("data", data);
              
                System.out.println("Testing into request controller?");
                JSONObject jsonObject = new JSONObject(data);
                JSONObject result = new JSONObject();
                Customer customer = new Customer();
                DataStore dataStore = new DataStore();
                System.out.println("Message from client request: "+jsonObject.toString());
                customer = dataStore.readCustomer(jsonObject.getString("username"));
                String query = jsonObject.getString("query");
                if(customer.getPassword().equals(jsonObject.get("password"))){
                	result = dataStore.selectQuery(query);
                }else{
                	result.put("result", "login failed!");
                }
                result.put("response", "testing response");
                return result.toString();
                
        }
}