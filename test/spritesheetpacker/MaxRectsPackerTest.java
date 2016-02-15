/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maconi
 */
public class MaxRectsPackerTest {
    
    public MaxRectsPackerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateLayout method, of class MaxRectsPacker.
     */
    @Test
    public void testGenerateLayout() throws Exception {
        System.out.println("generateLayout");
        ArrayList<Quad> quads = null;
        int maxWidth = 0;
        MaxRectsPacker instance = new MaxRectsPacker();
        QuadLayout expResult = null;
        QuadLayout result = instance.generateLayout(quads, maxWidth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
