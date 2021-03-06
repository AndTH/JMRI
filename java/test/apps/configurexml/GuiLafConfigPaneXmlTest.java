package apps.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * GuiLafConfigPaneXmlTest.java
 *
 * Test for the GuiLafConfigPaneXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class GuiLafConfigPaneXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("GuiLafConfigPaneXml constructor",new GuiLafConfigPaneXml());
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

