package router.controllers;

import greeter.GreeterService;
import greeter.GreetingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RouterController {

    @Autowired
    private GreeterService greeterService;

    @RequestMapping(path = "/greetings", method = RequestMethod.GET)
    public ResponseEntity<String> getGreetings() {
        GreetingProperties greetingProperties = new GreetingProperties();
        greetingProperties.setName("User");
        greetingProperties.setGreetingMessage("welcome to test app!");
        return ResponseEntity.ok(greeterService.greet(greetingProperties).getGreeting());
    }

}
