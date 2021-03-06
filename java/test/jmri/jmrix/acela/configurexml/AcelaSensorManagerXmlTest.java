package jmri.jmrix.acela.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * AcelaSensorManagerXmlTest.java
 *
 * Test for the AcelaSensorManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class AcelaSensorManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("AcelaSensorManagerXml constructor",new AcelaSensorManagerXml());
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

