/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.image.BufferedImage;
import java.io.File;
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
public class SpritesheetPackerTest {

    public SpritesheetPackerTest() {
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
     * Test of imagesOnly method, of class SpritesheetPacker.
     */
    @Test
    public void testImagesOnly() {
        System.out.println("imagesOnly");
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("test1.png"));
        files.add(new File("test2.txt"));
        files.add(new File("test3png"));
        files.add(new File("test4.html"));

        ArrayList<File> result = SpritesheetPacker.imagesOnly(files);
        assertEquals(1, result.size());
        assertEquals(files.get(0), result.get(0));
    }

    /**
     * Test of generateSpritesheet method, of class SpritesheetPacker.
     */
    @Test
    public void testGenerateSpritesheet() throws Exception {
        System.out.println("generateSpritesheet");
        ArrayList<File> imageFiles = null;
        QuadPacker packer = null;
        int maxWidth = 0;
        BufferedImage expResult = null;
        LayoutWriter writer = new SimpleLayoutWriter();
        SpriteSheet result = SpritesheetPacker.generateSpritesheet(imageFiles, packer, writer, maxWidth);
        assertEquals(expResult, result.image);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPowerOfTwo method, of class SpritesheetPacker.
     * 
     */
    @Test
    public void testGetPowerOfTwo() {
        System.out.println("getPowerOfTwo");
        int input = 1;
        //powers of two should equal themselves
        for (int i = 1; i < 30; i++) {
            int result = SpritesheetPacker.getPowerOfTwo(input);
            assertEquals(input, result);
            input *= 2;
        }
        //always round up
        input = 257;
        int result = SpritesheetPacker.getPowerOfTwo(input);
        assertEquals(512, result);
    }

    /**
     * Test of loadImages method, of class SpritesheetPacker.
     */
    @Test
    public void testLoadImages() {
        System.out.println("loadImages");
        ArrayList<File> files = null;
        ArrayList<BufferedImage> expResult = null;
        ArrayList<BufferedImage> result = SpritesheetPacker.loadImages(files);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
