package org.smart4j.hello;

import org.junit.Test;
import org.smart4j.chapter1.annotation.Service;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/15.
 */

@Service(value = "service",key = "key")
public class TestAnnotaion {

    @Test
    public void name() throws Exception {
        String value = TestAnnotaion.class.getAnnotation(Service.class).value();
        System.out.println(value);
    }

    @Override
    public String toString() {
        TestAnnotaion.class.getAnnotation(Service.class).value();
        return null;
    }
}
