/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * This program reads all image files from its current folder, or optionally a
 * specified folder, consolidating them all into one image and saving it in a
 * folder called output, along with a file containing coordinate specifications
 *
 * @author Maconi
 */
public class SpritesheetPacker {

    /**
     * @param args the available command line arguments are:
     * source=folder_path specifies the path to the folder containing the sprites
     * output=folder_path specifies the folder in which to save the result
     * width=N specifies a maximum horizontal resolution of N (1024 default)
     * -po2 forces power-of-two dimensions on the output
     * -scanline specifies that the scanline algorithm will be used
     * -guillotine specifies that the guillotine algorithm will be used
     */
    public static void main(String[] args) {

        //Default parameters
        String filePath = ".";
        String outputPath = "output";
        int width = 1024;
        boolean powerOfTwo = false;
        QuadPacker packer = new MaxRectsPacker();

        //Parse command line arguments
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith("source=")) {
                    filePath = arg.split("=")[1];
                } else if (arg.startsWith("output=")) {
                    outputPath = arg.split("=")[1];
                } else if (arg.startsWith("width=")) {
                    width = Integer.parseInt(arg.split("=")[1]);
                } else if (arg.equals("-po2")) {
                    powerOfTwo = true;
                } else if (arg.equals("-scanline")) {
                    packer = new ScanlinePacker();
                } else if (arg.equals("-guillotine")) {
                    packer = new GuillotinePacker();
                }
            }
        }
        //scan the selected folder for files
        File f = new File(filePath);
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        ArrayList<File> imageFiles = imagesOnly(files);
        System.out.println(imageFiles);

        //load the images and create the sprite sheet
        LayoutWriter writer = new SimpleLayoutWriter();
        SpriteSheet spriteSheet;
        try {
            spriteSheet = generateSpritesheet(imageFiles, packer, writer, width);
        } catch (Exception ex) {
            System.out.println("Could not create the sprite sheet; " + ex.getMessage());
            return;
        }
        //resize if specified
        if (powerOfTwo) {
            int newWidth = getPowerOfTwo(spriteSheet.image.getWidth());
            int newHeight = getPowerOfTwo(spriteSheet.image.getHeight());
            BufferedImage resizeImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
            resizeImage.setData(spriteSheet.image.getData());
            spriteSheet.image = resizeImage;
        }

        //create the output path
        File outputFolder = new File(outputPath);
        outputFolder.mkdir();
        File output = new File(outputPath + File.separator + "output.png");
        File outputText = new File(outputPath + File.separator + "output.txt");

        //write sprite sheet to output file
        try {
            if (!output.exists()) {
                output.createNewFile();
            }
            ImageIO.write(spriteSheet.image, "PNG", output);
        } catch (IOException ex) {
            System.out.println("could not write image");
        }
        try {
            if (!outputText.exists()) {
                outputText.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(outputText);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(spriteSheet.layout);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("could not write coordinates file");
        }
    }

    /**
     * Filters a list of file handles to only contain images of the png format
     *
     * @param files
     * @return a new list object containing only entries whose file extension is
     * .png
     */
    public static ArrayList<File> imagesOnly(ArrayList<File> files) {
        ArrayList<File> images = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                images.add(file);
            }
        }
        return images;
    }

    /**
     * Load multiple image files as BufferedImage objects. Prints an error
     * message for each failed load attempt
     *
     * @param files List of image files. If the file cannot be read by
     * imageIO.read, it will be skipped and a message will be printed.
     * @return List of BufferedImage objects from successfully loaded files.
     */
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

    /**
     * Loads the image data from the specified files using the specified
     * algorithm, limiting the stacking to the specified width.
     *
     * @param imageFiles list of files. Files that cannot be read as images are
     * not included in the result
     * @param packer algorithm implementing the QuadPacker interface
     * @param writer
     * @param maxWidth specifies the horizontal pixel limit of the sprite sheet
     * @return the generated sprite sheet image
     * @throws java.lang.Exception if any of the images are wider than maxWidth
     */
    public static SpriteSheet generateSpritesheet(ArrayList<File> imageFiles, QuadPacker packer, LayoutWriter writer, int maxWidth) throws Exception {
        ArrayList<BufferedImage> images = loadImages(imageFiles);
        ArrayList<Quad> quads = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            quads.add(new Quad(images.get(i).getData().getBounds(), imageFiles.get(i).getName()));
        }
        QuadLayout layout = packer.generateLayout(quads, maxWidth);
        String layoutString = writer.WriteLayout(layout);
        HashMap<String, Rectangle> mappings = layout.getMappings();
        int height = layout.bounds.height + layout.bounds.y;
        BufferedImage spritesheet = new BufferedImage(maxWidth, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D canvas = spritesheet.createGraphics();
        for (int i = 0; i < images.size(); i++) {
            Rectangle coords = mappings.get(imageFiles.get(i).getName());
            canvas.drawImage(images.get(i), coords.x, coords.y, null);
        }
        return new SpriteSheet(spritesheet, layoutString);
    }

    /**
     * Gives the lowest power of two which is higher or equal to the input
     *
     * @param input
     * @return
     */
    public static int getPowerOfTwo(int input) {
        int exponent = (int) Math.ceil(Math.log(input) / Math.log(2));
        return (int) Math.pow(2, exponent);
    }
}
