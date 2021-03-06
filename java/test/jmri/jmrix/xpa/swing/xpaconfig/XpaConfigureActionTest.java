package jmri.jmrix.xpa.swing.xpaconfig;

import java.awt.GraphicsEnvironment;
import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Paul Bender Copyright(C) 2016
 */
public class XpaConfigureActionTest {

    private jmri.jmrix.xpa.XpaSystemConnectionMemo memo = null;

    @Test
    public void testCtor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("XpaConfigureAction exists",new XpaConfigureAction("Test",memo) );
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();

        memo = new jmri.jmrix.xpa.XpaSystemConnectionMemo();
        jmri.InstanceManager.setDefault(jmri.jmrix.xpa.XpaSystemConnectionMemo.class,memo);

    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }
}
