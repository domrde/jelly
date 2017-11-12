package clientautogeneration;

import greeter.GreeterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class RestClientGeneratorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private RestTemplate restTemplate = new RestTemplate();

    private ServiceMethodsRestClientHandlerCreator serviceMethodsRestClientHandlerCreator =
            new ServiceMethodsRestClientHandlerCreator(restTemplate);

    private List<Class<?>> serviceInterfaces = Collections.singletonList(GreeterService.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (Class<?> serviceInterface : serviceInterfaces) {
            String beanName = serviceInterface.getSimpleName() + "Client";
            beanFactory.registerSingleton(beanName, generateClient(serviceInterface));
        }
    }

    private Object generateClient(Class<?> serviceInterface) {
        Map<Method, Function<Object[], Object>> handlersCache = Stream.of(serviceInterface.getMethods()).collect(toMap(
                Function.identity(),
                method -> serviceMethodsRestClientHandlerCreator.createHandlerForMethod(method)
        ));

        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{serviceInterface},
                (proxy, method, args) -> handlersCache.get(method).apply(args));
    }

}
