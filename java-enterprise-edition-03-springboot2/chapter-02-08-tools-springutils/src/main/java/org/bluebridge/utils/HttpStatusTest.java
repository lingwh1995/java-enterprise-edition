package org.bluebridge.utils;

import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * Spring框架预先定义了一些Http状态码，我们不用自己去定义了，直接拿来用即可
 */
public class HttpStatusTest {

    @Test
    public void testHttpStatus() {
        System.out.println(HttpStatus.OK);
        System.out.println(HttpStatus.NOT_FOUND);
        System.out.println(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
