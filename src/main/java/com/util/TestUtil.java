package com.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: HeQi
 * @Date:Create in 9:30 2018/7/16
 */
@Component
@ConfigurationProperties(prefix = "rabbit")
public class TestUtil {


    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
