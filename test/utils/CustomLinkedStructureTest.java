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

/**
 *
 * @author Maconi
 */
public class CustomLinkedStructureTest {
    
    public CustomLinkedStructureTest() {
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
     * Test of addLast method, of class CustomLinkedStructure.
     */
    @Test
    public void testAddLast() {
        System.out.println("addLast");
        int item1 = 8;
        int item2 = 5;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addLast(item1);
        instance.addLast(item2);
        assertEquals(2, instance.size);
        assertEquals(item2, instance.getLast());
        assertEquals(item1, instance.getFirst());
    }

    /**
     * Test of addFirst method, of class CustomLinkedStructure.
     */
    @Test
    public void testAddFirst() {
        System.out.println("addFirst");
        int item1 = 8;
        int item2 = 5;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addFirst(item1);
        instance.addFirst(item2);
        assertEquals(2, instance.size);
        assertEquals(item1, instance.getLast());
        assertEquals(item2, instance.getFirst());
    }

    /**
     * Test of insertNext method, of class CustomLinkedStructure.
     */
    @Test
    public void testInsertNext() {
        System.out.println("insertNext");
        int item1 = 8;
        int item2 = 5;
        int item3 = 23;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.insertNext(item1);
        instance.addLast(item2);
        instance.goToFirst();
        instance.insertNext(item3);
        
        assertEquals(3, instance.size);
        assertEquals(item1, instance.getFirst());
        assertEquals(item3, instance.getNext());
        assertEquals(item2, instance.getLast());
    }

    /**
     * Test of removeActive method, of class CustomLinkedStructure.
     */
    @Test
    public void testRemoveActive() {
        System.out.println("removeActive");
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.removeActive();
        assertEquals(0, instance.size);
        
        int item1 = 8;
        int item2 = 5;
        int item3 = 23;
        instance.insertNext(item1);
        instance.addLast(item2);
        instance.goToLast();
        instance.insertNext(item3);
        instance.removeActive();
        assertEquals(2, instance.size);
        assertEquals(item1, instance.getFirst());
        assertEquals(item3, instance.getLast());

    }

    /**
     * Test of remove method, of class CustomLinkedStructure.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Object item = "blah";
        int item1 = 8;
        int item2 = 5;
        int item3 = 23;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.insertNext(item1);
        instance.insertNext(item2);
        instance.insertNext(item3);
        instance.insertNext(item3);
        instance.remove(item);
        assertEquals(4, instance.size);
        instance.remove(item3);
        assertEquals(3, instance.size);
    }

    /**
     * Test of getNext method, of class CustomLinkedStructure.
     */
    @Test
    public void testGetNext() {
        System.out.println("getNext");
        int item1 = 8;
        int item2 = 5;
        CustomLinkedStructure<Integer> instance = new CustomLinkedStructure();
        instance.addFirst(item1);
        instance.addFirst(item2);
        instance.goToFirst();
        int result = instance.getNext();
        assertEquals(item1, result);
    }

    /**
     * Test of goToFirst method, of class CustomLinkedStructure.
     */
    @Test
    public void testGoToFirst() {
        System.out.println("goToFirst");
        int item1 = 8;
        int item2 = 5;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addLast(item1);
        instance.addLast(item2);
        instance.goToFirst();
        assertEquals(item1, instance.getActive());
    }

    /**
     * Test of goToLast method, of class CustomLinkedStructure.
     */
    @Test
    public void testGoToLast() {
        System.out.println("goToLast");
        int item1 = 8;
        int item2 = 5;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addFirst(item1);
        instance.addFirst(item2);
        instance.goToLast();
        assertEquals(item1, instance.getActive());
    }

    /**
     * Test of goToNext method, of class CustomLinkedStructure.
     */
    @Test
    public void testGoToNext() {
        System.out.println("goToNext");
        int item1 = 8;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addLast(item1);
        instance.goToNext();
        assertNull(instance.getActive());
    }

    /**
     * Test of toArray method, of class CustomLinkedStructure.
     */
    @Test
    public void testToArray() {
        System.out.println("toArray");
        int item1 = 8;
        int item2 = 5;
        int item3 = 23;
        CustomLinkedStructure instance = new CustomLinkedStructure();
        instance.addLast(item1);
        instance.addLast(item2);
        instance.addLast(item3);
        Object[] expResult = {8, 5, 23};
        Object[] result = instance.toArray();
        assertArrayEquals(expResult, result);
    }
    
}
