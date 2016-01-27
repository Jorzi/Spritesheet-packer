/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Maconi
 */
public class SpritesheetPacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        //BufferedImage image = ImageIO.read(new File("lol.png"));
        File f = new File("./");
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        ArrayList<File> imageFiles = imagesOnly(files);
        System.out.println(imageFiles);
        ArrayList<BufferedImage> images = loadImages(imageFiles);
        BufferedImage spriteSheet = naiveSpritesheetGenerator(images);

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
}
