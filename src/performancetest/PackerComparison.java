/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performancetest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import spritesheetpacker.GuillotinePacker;
import spritesheetpacker.LayoutWriter;
import spritesheetpacker.MaxRectsPacker;
import spritesheetpacker.Quad;
import spritesheetpacker.QuadLayout;
import spritesheetpacker.QuadPacker;
import spritesheetpacker.ScanlinePacker;
import spritesheetpacker.SimpleLayoutWriter;
import spritesheetpacker.SortedMaxRectsPacker;
import spritesheetpacker.SpriteSheet;
import static spritesheetpacker.SpritesheetPacker.getPackingRatio;
import static spritesheetpacker.SpritesheetPacker.loadImages;
import static spritesheetpacker.SpritesheetPacker.quadOutlines;

/**
 *
 * @author Maconi
 */
public class PackerComparison {

    public static void runTest(int numberOfQuads, int seed, int maxWidth, String outputPath) {
        //initialize stuff
        ArrayList<Quad> quads = generateRandomQuads(numberOfQuads, seed, maxWidth/8);
        QuadPacker packer1 = new ScanlinePacker();
        QuadPacker packer2 = new GuillotinePacker();
        QuadPacker packer3 = new MaxRectsPacker();
        QuadPacker packer4 = new SortedMaxRectsPacker();
        LayoutWriter writer = new SimpleLayoutWriter();
        
        //test scanline algorithm
        System.out.println("Scan line packer:");
        testPacker(packer1, quads, writer, maxWidth, outputPath);
        
        System.out.println("Guillotine packer:");
        testPacker(packer2, quads, writer, maxWidth, outputPath);
        
        System.out.println("MaxRects packer:");
        testPacker(packer3, quads, writer, maxWidth, outputPath);
        
        System.out.println("Sorted MaxRects packer:");
        testPacker(packer4, quads, writer, maxWidth, outputPath);
    }
    
    public static void testPacker(QuadPacker packer, ArrayList<Quad> quads, LayoutWriter writer, int maxWidth, String outputPath){
        SpriteSheet spritesheet1 = generateSpritesheet(quads, packer, writer, maxWidth);
         File outputFolder = new File(outputPath);
        outputFolder.mkdir();
        String name = packer.getClass().getSimpleName();
        File output = new File(outputPath + File.separator + name + ".png");
        File outputText = new File(outputPath + File.separator + name + ".txt");
        //write sprite sheet to output file
        try {
            if (!output.exists()) {
                output.createNewFile();
            }
            ImageIO.write(spritesheet1.image, "PNG", output);
        } catch (IOException ex) {
            System.out.println("could not write image");
        }
        try {
            if (!outputText.exists()) {
                outputText.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(outputText);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(spritesheet1.layout);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("could not write coordinates file");
        }
    }

    public static SpriteSheet generateSpritesheet(ArrayList<Quad> quads, QuadPacker packer, LayoutWriter writer, int maxWidth){
        
        QuadLayout layout;
        try {
            layout = packer.generateLayout(quads, maxWidth);
        } catch (Exception ex) {
            Logger.getLogger(PackerComparison.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("uh oh, failed to generate a layout");
            return null;
        }
        System.out.println("Packing ratio: " + getPackingRatio(layout));
        String layoutString = writer.WriteLayout(layout);
        int height = layout.bounds.height + layout.bounds.y;
        BufferedImage spritesheet = new BufferedImage(maxWidth, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D canvas = spritesheet.createGraphics();
        ArrayList<BufferedImage> images = imagesFromQuads(layout.quads);
        for (int i = 0; i < images.size(); i++) {
            Quad coords = layout.quads.get(i);
            canvas.drawImage(images.get(i), coords.x, coords.y, null);
            //draw quad outlines
            canvas.setColor(Color.red);
            canvas.drawRect(coords.x, coords.y, coords.getWidth() - 1, coords.getHeight() - 1);
            String number = "" + i;
            canvas.drawString(number, coords.x, coords.y + coords.getHeight());
        }
        if (packer.getClass() == MaxRectsPacker.class) {
            canvas.setColor(Color.green);
            MaxRectsPacker blah = (MaxRectsPacker) packer;
            for (Rectangle rect : blah.getFreeQuads().toArray()) {
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        if (packer.getClass() == SortedMaxRectsPacker.class) {
            canvas.setColor(Color.green);
            SortedMaxRectsPacker blah = (SortedMaxRectsPacker) packer;
            for (Rectangle rect : blah.getFreeQuads().toArray()) {
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        if (packer.getClass() == GuillotinePacker.class) {
            canvas.setColor(Color.green);
            GuillotinePacker blah = (GuillotinePacker) packer;
            for (Rectangle rect : blah.getFreeQuads()) {
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        return new SpriteSheet(spritesheet, layoutString);
    }

    public static ArrayList<Quad> generateRandomQuads(int count, int seed, int maxWidth) {
        Random rand = new Random(seed);
        ArrayList<Quad> quads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int width = rand.nextInt(maxWidth - 1) + 1;
            int height = rand.nextInt(maxWidth - 1) + 1;
            quads.add(new Quad(0, 0, width, height, "Q" + i));
        }
        return quads;
    }

    public static ArrayList<BufferedImage> imagesFromQuads(ArrayList<Quad> quads) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (Quad quad : quads) {
            BufferedImage img = new BufferedImage(quad.getWidth(), quad.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            images.add(img);
        }
        return images;
    }

}
