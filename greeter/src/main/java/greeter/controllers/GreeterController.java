package greeter.controllers;

import greeter.GreeterService;
import greeter.GreetingProperties;
import greeter.GreetingResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GreeterController implements GreeterService {

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("running");
    }

    @RequestMapping(path = "/greet", method = RequestMethod.POST)
    @Override
    public GreetingResponse greet(@RequestBody GreetingProperties properties) {
        GreetingResponse response = new GreetingResponse();
        response.setGreeting(properties.getName() + ", " + properties.getGreetingMessage());
        return response;
    }
 
}