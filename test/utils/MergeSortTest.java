/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spritesheetpacker.Quad;

/**
 *
 * @author Maconi
 */
public class MergeSortTest {
    
    public MergeSortTest() {
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
     * Test of mergeSort method, of class MergeSort.
     */
    @Test
    public void testMergeSort() {
        System.out.println("mergeSort");
        Quad q1 = new Quad(0, 0, 100, 200, "Q1");
        Quad q2 = new Quad(0, 0, 120, 200, "Q2");
        Quad q3 = new Quad(0, 0, 10, 20, "Q3");
        Quad q4 = new Quad(0, 0, 200, 200, "Q4");
        Quad q5 = new Quad(0, 0, 100, 100, "Q5");
        Quad[] array = {q1, q2, q3, q4, q5};
        Quad[] expResult = {q4, q2, q1, q5, q3};
        Quad[] result = MergeSort.mergeSort(array);
        assertArrayEquals(expResult, result);
    }
    
}
