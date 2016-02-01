/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Maconi
 */
public class SpritesheetPacker {

    /**
     * @param args optionally specify the source folder path
     */
    public static void main(String[] args) {

        String filePath = ".";
        if (args.length > 0) {
            filePath = args[0];
        }

        File f = new File(filePath);
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        ArrayList<File> imageFiles = imagesOnly(files);
        System.out.println(imageFiles);
        QuadPacker packer = new ScanlinePacker();
        BufferedImage spriteSheet = generateSpritesheet(imageFiles, packer);

        File output = new File("output" + File.separator + "output.png");
        
        try {
            if (!output.exists()) {
                output.createNewFile();
            }
            ImageIO.write(spriteSheet, "PNG", output);
        } catch (IOException ex) {
            System.out.println("could not write image");
        }
    }

    public static ArrayList<File> imagesOnly(ArrayList<File> files) {
        ArrayList<File> images = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                images.add(file);
            }
        }
        return images;
    }

    public static ArrayList<BufferedImage> loadImages(ArrayList<File> files) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (File file : files) {
            try {
                images.add(ImageIO.read(file));
            } catch (IOException ex) {
                System.out.println("could not open file: " + file.getName());
            }
        }
        return images;
    }

    public static BufferedImage naiveSpritesheetGenerator(ArrayList<BufferedImage> images) {
        int totalHeight = 0;
        int totalWidth = 0;
        for (BufferedImage image : images) {
            totalHeight = Math.max(totalHeight, image.getHeight());
            totalWidth += image.getWidth();
        }
        BufferedImage spritesheet = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D canvas = spritesheet.createGraphics();
        int displaceX = 0;
        for (BufferedImage image : images) {
            canvas.drawImage(image, displaceX, 0, null);
            displaceX += image.getWidth();
        }
        return spritesheet;
    }
    
    public static BufferedImage generateSpritesheet(ArrayList<File> imageFiles, QuadPacker packer) {
        int width = 1024;
        ArrayList<BufferedImage> images = loadImages(imageFiles);
        ArrayList<Quad> quads = new ArrayList<>();
        for(int i = 0; i < images.size(); i++){
            quads.add(new Quad(images.get(i).getData().getBounds(), imageFiles.get(i).getName()));
        }
        QuadLayout layout = packer.generateLayout(quads, width);
        HashMap<String, Rectangle> mappings = layout.getMappings();
        BufferedImage spritesheet = new BufferedImage(width, layout.bounds.height + layout.bounds.y, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D canvas = spritesheet.createGraphics();
        for(int i = 0; i < images.size(); i++){
            Rectangle coords = mappings.get(imageFiles.get(i).getName());
            canvas.drawImage(images.get(i), coords.x, coords.y, null);
        }
        return spritesheet;
    }
}
