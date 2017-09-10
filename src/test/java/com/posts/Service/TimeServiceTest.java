package com.posts.Service;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mumarm45 on 09/08/2017.
 */

public class TimeServiceTest {
    @Test
    public void now(){
        assertThat(new TimeService().now()).isCloseTo(new Date(),1000);
    }
}
