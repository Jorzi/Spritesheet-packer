/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

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
public class QuadTest {
    
    public QuadTest() {
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
     * Test of compareTo method, of class Quad.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Quad t = new Quad(0, 0, 100, 100, "Test1");
        Quad instance = new Quad(0, 0, 110, 90, "Test2");
        int result = instance.compareTo(t);
        assertTrue(result < 0);
        instance = new Quad(5, 10, 200, 50, "Test2");
        result = instance.compareTo(t);
        assertEquals(0, result);
    } 
}
