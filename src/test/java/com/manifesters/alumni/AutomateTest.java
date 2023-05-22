package com.manifesters.alumni;

import java.net.MalformedURLException;

public interface AutomateTest {
    void setUp(String browser) throws MalformedURLException;
    public void tearDown();

}
