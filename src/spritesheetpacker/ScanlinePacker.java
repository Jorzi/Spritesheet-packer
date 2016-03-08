/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This algorithm sorts quads from big to small and places them at the first
 * possible location, testing for fit in a scan line pattern (left to right, top
 * to bottom).
 *
 * @author Maconi
 */
public class ScanlinePacker implements QuadPacker {

    /**
     * Default constructor
     */
    public ScanlinePacker() {
    }

    /**
     * Generates a layout using the scanline algorithm.
     *
     * @param quads List of bounding boxes for the images
     * @param maxWidth Maximum bounds in the x direction. No part of the final
     * layout can extend beyond this limit
     * @return A QuadLayout object containing all the quads, stacked without
     * overlap, as well as the bounding rectangle of the whole layout
     * @throws Exception if maxWidth is too small to accomodate all images
     */
    @Override
    public QuadLayout generateLayout(ArrayList<Quad> quads, int maxWidth) throws Exception {
        ArrayList<Quad> output = new ArrayList<>();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        ArrayList<Quad>quads2 = (ArrayList<Quad>)quads.clone();
        Collections.sort(quads2, Collections.reverseOrder());
        for (Quad quad : quads2) {
            if (quad.getWidth() > maxWidth) {
                throw new Exception("maxWidth too low to include" + quad.getName());
            }
            int j = 0;
            while (true) {
                quad.y = j;
                boolean quadFits = false;
                for (int i = 0; i < maxWidth - quad.getWidth(); i++) {
                    quad.x = i;
                    if (!collisionDetection(quad, output)) {
                        quadFits = true;
                        break;
                    }

                }
                if (quadFits) {
                    output.add(quad);
                    //System.out.println(output.size());
                    minX = quad.x < minX ? quad.x : minX;
                    minY = quad.y < minY ? quad.y : minY;
                    maxX = quad.x + quad.getWidth() > maxX ? quad.x + quad.getWidth() : maxX;
                    maxY = quad.y + quad.getHeight() > maxY ? quad.y + quad.getHeight() : maxY;
                    break;
                }
                j++;
            }
        }

        return new QuadLayout(output, new Rectangle(minX, minY, maxX - minX, maxY - minY));
    }

    private boolean collisionDetection(Quad testQuad, ArrayList<Quad> quads) {
        for (Quad quad : quads) {
            if (testQuad.x < quad.x + quad.getWidth()
                    && testQuad.y < quad.y + quad.getHeight()
                    && quad.x < testQuad.x + testQuad.getWidth()
                    && quad.y < testQuad.y + testQuad.getHeight()) {
                return true;
            }
        }
        return false;
    }

}
