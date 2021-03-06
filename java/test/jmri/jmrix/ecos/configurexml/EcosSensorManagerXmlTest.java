package jmri.jmrix.ecos.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * EcosSensorManagerXmlTest.java
 *
 * Test for the EcosSensorManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class EcosSensorManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("EcosSensorManagerXml constructor",new EcosSensorManagerXml());
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}

