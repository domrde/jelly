package greeter;

import clientautogeneration.GenerationProperties;
import org.springframework.http.HttpMethod;

public interface GreeterService {

    @GenerationProperties(method = HttpMethod.POST, uri = "http://localhost:8083/greeter/greet")
    GreetingResponse greet(GreetingProperties properties);
}
