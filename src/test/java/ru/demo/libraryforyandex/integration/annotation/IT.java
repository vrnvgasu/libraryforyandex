package ru.demo.libraryforyandex.integration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.libraryforyandex.LibraryforyandexApplication;

/**
 * Annotation for starting an integration test
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles(profiles = "localtest")
@Transactional
@SpringBootTest(classes = LibraryforyandexApplication.class)
public @interface IT {

}
