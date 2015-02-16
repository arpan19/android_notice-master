package com.example.arp.start.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;


public class fullTestSuite {

    public static Test suite()
    {
        return new TestSuiteBuilder(fullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public fullTestSuite()
    {
        super();
    }

}

