package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ZipQueryController {
        //@Autowired
        //private IPersonService personService;
	@RequestMapping(value={"/api/zipQuery"}, method=RequestMethod.POST)
    public @ResponseBody String onSubmit(@RequestParam(value="data", required=false) String data,
                Model model) throws IOException, URISyntaxException, JSONException, SQLException, InterruptedException {

                model.addAttribute("data", data);
                JSONObject request = new JSONObject(data);

                ZipQueryThreadController queryThread = new ZipQueryThreadController(request);
    	        
    	        queryThread.start();
    	        Thread.sleep(5000);
                if(queryThread.getResult() != null){
                	return queryThread.getResult().toString();
                }else{
                	return "Fail to Exception!";
                }                
        }
}
