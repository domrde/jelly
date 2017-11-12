package clientautogeneration;

import org.springframework.http.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GenerationProperties {
    HttpMethod method();

    String uri();

    int indexOfArgument() default 0;
}
