package jmri.jmrit.display.layoutEditor;

import java.awt.GraphicsEnvironment;
import java.awt.geom.Point2D;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * Test simple functioning of LayoutSlipView
 *
 * @author Bob Jacobsen Copyright (C) 2020
 */
public class LayoutSlipViewTest extends LayoutTurnoutViewTest {

    @Test
    public void testCtor() {
        new LayoutSlipView(null);
    }

}
