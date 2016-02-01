/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
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
public class QuadLayoutTest {
    
    public QuadLayoutTest() {
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
     * Test of getMappings method, of class QuadLayout.
     */
    @Test
    public void testGetMappings() throws Exception {
        System.out.println("getMappings");
        ArrayList<Quad> quads = new ArrayList<>();
        quads.add(new Quad(0,0, 100, 200, "test1"));
        quads.add(new Quad(0,0, 150, 175, "test2"));
        int maxWidth = 1024;
        ScanlinePacker packer = new ScanlinePacker();
        QuadLayout instance = packer.generateLayout(quads, maxWidth);
        HashMap<String, Rectangle> result = instance.getMappings();
        assertEquals(2, result.size());
        assertEquals(150, result.get("test2").width);
    }
    
}
