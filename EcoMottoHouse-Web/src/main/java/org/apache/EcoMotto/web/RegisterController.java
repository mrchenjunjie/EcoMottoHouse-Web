package org.apache.EcoMotto.web;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.EcoMotto.web.Customer;
@Controller
public class RegisterController {
        //@Autowired
        //private IPersonService personService;
        @RequestMapping("/register")
    public String onSubmit(@RequestParam(value="username", required=false) String username,
                @RequestParam(value="password", required=false) String password,
                @RequestParam(value="email", required=false) String email,
                Model model) throws Exception {
                model.addAttribute("username", username);
                model.addAttribute("password", password);
                model.addAttribute("email", email);
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setEmail(email);
            DataStore dataStore = new DataStore();
            dataStore.writeCustomer(customer);

        return "register";
    }
}