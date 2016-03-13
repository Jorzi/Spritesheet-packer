/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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
     * Flag for drawing debug output on top of the sprite sheet
     */
    public static boolean quadOutlines = false;

    /**
     * @param args the available command line arguments are: source=folder_path
     * specifies the path to the folder containing the sprites
     *
     * output=folder_path specifies the folder in which to save the result
     *
     * width=N specifies a maximum horizontal resolution of N (1024 default)
     *
     * -po2 forces power-of-two dimensions on the output
     *
     * -scanline specifies that the scanline algorithm will be used
     *
     * -guillotine specifies that the guillotine algorithm will be used
     *
     * -maxrects specifies that the maxRects algorithm will be used
     * 
     * -sortedmaxrects specifies that the MaxRects will be used with input quads
     * sorted in descending order
     *
     * -outlines makes the program draw quad outlines for debug purposes
     *
     * -benchmark makes the program compare algorithms, generating test output
     */
    public static void main(String[] args) {

        //Default parameters
        String filePath = ".";
        String outputPath = "output";
        int width = 1024;
        boolean powerOfTwo = false;
        QuadPacker packer = new MaxRectsPacker();
        boolean benchmark = false;

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
                } else if (arg.equals("-maxrects")) {
                    packer = new MaxRectsPacker();
                } else if (arg.equals("-sortedmaxrects")) {
                    packer = new SortedMaxRectsPacker();
                } else if (arg.equals("-outlines")) {
                    quadOutlines = true;
                } else if (arg.equals("-benchmark")) {
                    benchmark = true;
                }

            }
        }
        if (benchmark) {
            performancetest.PackerComparison.runTest(500, 0, width, outputPath);
            return;
        }
        //scan the selected folder for files
        File f = new File(filePath);
        File[] files = f.listFiles();
        File[] imageFiles = imagesOnly(files);
        System.out.println(Arrays.toString(imageFiles));

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
    public static File[] imagesOnly(File[] files) {
        int length = 0;
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                length++;
            }
        }
        File[] images = new File[length];
        int i = 0;
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                images[i] = file;
                i++;
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
    public static BufferedImage[] loadImages(File[] files) {
        BufferedImage[] images = new BufferedImage[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                images[i] = ImageIO.read(files[i]);
            } catch (IOException ex) {
                System.out.println("could not open file: " + files[i].getName());
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
    public static SpriteSheet generateSpritesheet(File[] imageFiles, QuadPacker packer, LayoutWriter writer, int maxWidth) throws Exception {
        BufferedImage[] images = loadImages(imageFiles);
        Quad[] quads = new Quad[images.length];
        for (int i = 0; i < images.length; i++) {
            quads[i] = new Quad(images[i].getData().getBounds(), imageFiles[i].getName());
        }
        QuadLayout layout = packer.generateLayout(quads, maxWidth);
        System.out.println("Packing ratio: " + getPackingRatio(layout));
        String layoutString = writer.WriteLayout(layout);
        int height = layout.bounds.height + layout.bounds.y;
        BufferedImage spritesheet = new BufferedImage(maxWidth, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D canvas = spritesheet.createGraphics();
        for (int i = 0; i < images.length; i++) {
            Rectangle coords = linearSearch(layout.quads, imageFiles[i].getName());
            canvas.drawImage(images[i], coords.x, coords.y, null);
            if (quadOutlines) {
                canvas.setColor(Color.red);
                canvas.drawRect(coords.x, coords.y, coords.width - 1, coords.height - 1);
                String number = "" + i;
                canvas.drawString(number, coords.x, coords.y + coords.height);

            }
        }
        if (quadOutlines && packer.getClass() == MaxRectsPacker.class) {
            canvas.setColor(Color.green);
            MaxRectsPacker blah = (MaxRectsPacker) packer;
            for (Object obj : blah.getFreeQuads().toArray()) {
                Rectangle rect = (Rectangle) obj;
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        if (packer.getClass() == SortedMaxRectsPacker.class) {
            canvas.setColor(Color.green);
            SortedMaxRectsPacker blah = (SortedMaxRectsPacker) packer;
            for (Object obj : blah.getFreeQuads().toArray()) {
                Rectangle rect = (Rectangle) obj;
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        if (quadOutlines && packer.getClass() == GuillotinePacker.class) {
            canvas.setColor(Color.green);
            GuillotinePacker blah = (GuillotinePacker) packer;
            for (Object obj : blah.getFreeQuads().toArray()) {
                Rectangle rect = (Rectangle) obj;
                canvas.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
        return new SpriteSheet(spritesheet, layoutString);
    }

    /**
     * Gives the lowest power of two which is higher or equal to the input
     *
     * @param input a positive integer
     * @return the next power of two
     */
    public static int getPowerOfTwo(int input) {
        int exponent = (int) Math.ceil(Math.log(input) / Math.log(2));
        return (int) Math.pow(2, exponent);
    }

    /**
     * Calculates packing ratio, defined as the sum of rectangle areas divided 
     * by the bounding rectangle area
     * @param layout a quad layout, generated by the packing algorithm.
     * @return Packing ratio, in the range of 0 (all space wasted) to 1 (no
     * space wasted)
     */
    public static double getPackingRatio(QuadLayout layout) {
        int areaSum = 0;
        int boundingArea = layout.bounds.height * layout.bounds.width;
        for (Quad quad : layout.quads) {
            areaSum += quad.getHeight() * quad.getWidth();
        }
        return 1.0 * areaSum / boundingArea;
    }

    /**
     * Searches for the first quad whose name matches the input
     * @param array Array to search from
     * @param name String that the Quad's name should match
     * @return the position and dimensions of the quad
     */
    public static Rectangle linearSearch(Quad[] array, String name) {
        for (Quad quad : array) {
            if (quad.getName().equals(name)) {
                return new Rectangle(quad.x, quad.y, quad.getWidth(), quad.getHeight());
            }
        }
        return null;
    }
}
