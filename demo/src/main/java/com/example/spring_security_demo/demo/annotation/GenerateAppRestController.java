package com.example.spring_security_demo.demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Composite annotation that is an alias for {@link RestController}
 * and applies a base request mapping of "/generate/app" to the controller.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping("/generate/app")
public @interface GenerateAppRestController {
    /**
     * Alias for {@link RequestMapping#value} so users can override the path if needed.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value() default {};
}

