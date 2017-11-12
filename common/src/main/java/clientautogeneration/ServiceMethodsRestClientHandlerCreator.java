package clientautogeneration;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.function.Function;

public class ServiceMethodsRestClientHandlerCreator {

    private RestTemplate restTemplate;

    public ServiceMethodsRestClientHandlerCreator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Function<Object[], Object> createHandlerForMethod(Method method) {
        GenerationProperties generationProperties = method.getAnnotation(GenerationProperties.class);

        if (generationProperties == null) {
            throw new ClientGenerationException("Generation properties not specified");
        }

        if (generationProperties.method() == null) {
            throw new ClientGenerationException("Request method not specified");
        }

        if (generationProperties.uri() == null) {
            throw new ClientGenerationException("Request URI not specified");
        }

        return (args) -> {
            HttpEntity<?> body;
            if (args.length == 0) {
                body = HttpEntity.EMPTY;
            } else {
                if (args.length <= generationProperties.indexOfArgument()) {
                    throw new ClientGenerationException("Argument index out of arguments list bounds");
                }
                body = new HttpEntity<>(args[generationProperties.indexOfArgument()]);
            }

            return restTemplate.exchange(generationProperties.uri(), generationProperties.method(), body, method.getReturnType()).getBody();
        };
    }
}
