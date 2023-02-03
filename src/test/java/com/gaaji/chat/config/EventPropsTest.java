package com.gaaji.chat.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class EventPropsTest {

    @Autowired
    EventProps eventProps;

    @Test
    public void test() {
        Assertions.assertNotNull(eventProps);
        assertEquals("post-banzzakCreated", eventProps.getPost().getBanzzak().getCreated());
    }

}