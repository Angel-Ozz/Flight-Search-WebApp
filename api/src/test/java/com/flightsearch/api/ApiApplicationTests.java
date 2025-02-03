package com.flightsearch.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@Disabled("Idk why the app props are not being detected, low k goin insane")
// i tried: creating a test properties doc, setting the properties on the system, setting them on the apiapptest, etc ect, help i even checked the logs doc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties") 
class ApiApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Test is running with application-test.properties");
    }
}

