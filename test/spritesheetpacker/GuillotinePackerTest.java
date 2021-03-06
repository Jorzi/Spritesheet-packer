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
public class GuillotinePackerTest {
    
    public GuillotinePackerTest() {
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
     * Test of generateLayout method, of class GuillotinePacker.
     */
    @Test
    public void testGenerateLayout() throws Exception {
        System.out.println("generateLayout");
        ArrayList<Quad> quads = new ArrayList<>();
        quads.add(new Quad(0,0, 100, 200, "test1"));
        quads.add(new Quad(0,0, 150, 175, "test2"));
        int maxWidth = 1024;
        GuillotinePacker instance = new GuillotinePacker();
        QuadLayout result = instance.generateLayout(quads, maxWidth);
        assertEquals(200, result.bounds.height);
        assertEquals(250, result.bounds.width);
        assertEquals(2, result.quads.size());
        boolean throwException = false;
        try{
            result = instance.generateLayout(quads, 149);
        }catch(Exception e){
            throwException = true;
        }
        assertEquals(true, throwException);
    }
    
}
