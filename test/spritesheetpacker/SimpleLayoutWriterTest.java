/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
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
public class SimpleLayoutWriterTest {
    
    public SimpleLayoutWriterTest() {
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
     * Test of WriteLayout method, of class SimpleLayoutWriter.
     */
    @Test
    public void testWriteLayout() {
        System.out.println("WriteLayout");
        ArrayList<Quad> quads = new ArrayList<>();
        quads.add(new Quad(0,0, 100, 200, "test1"));
        quads.add(new Quad(0,0, 150, 175, "test2"));
        Rectangle bounds = new Rectangle(0, 0, 150, 200);
        QuadLayout layout = new QuadLayout(quads, bounds);
        SimpleLayoutWriter instance = new SimpleLayoutWriter();
        String expResult = "Bounds 0 0 150 200\ntest1 0 0 100 200\ntest2 0 0 150 175\n";
        String result = instance.WriteLayout(layout);
        assertEquals(expResult, result);
    }
    
}
