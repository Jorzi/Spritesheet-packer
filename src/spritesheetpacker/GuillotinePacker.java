/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import utils.CustomLinkedList;

/**
 *
 * @author Maconi
 */
public class GuillotinePacker implements QuadPacker {

    private CustomLinkedList<Rectangle> freeQuads;

    /**
     * Default constructor
     */
    public GuillotinePacker() {
    }

    /**
     *
     * @param quads List of bounding boxes for the images
     * @param maxWidth Maximum bounds in the x direction. No part of the final
     * layout can extend beyond this limit
     * @return A QuadLayout object containing all the quads, stacked without
     * overlap, as well as the bounding rectangle of the whole layout
     * @throws Exception if maxWidth is too small to accomodate all images
     */
    @Override
    public QuadLayout generateLayout(Quad[] quads, int maxWidth) throws Exception {
        //reset list, add initial bounds
        freeQuads = new CustomLinkedList<>();
        freeQuads.addFirst(new Rectangle(maxWidth, Integer.MAX_VALUE));
        Quad[] output = new Quad[quads.length];
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        int index = 0;
        for (Quad quad : quads) {
            if (quad.getWidth() > maxWidth) {
                throw new Exception("maxWidth too low to include" + quad.getName());
            }
            Rectangle freeArea = getBestFreeArea(quad);
            quad.x = freeArea.x;
            quad.y = freeArea.y;
            split(freeArea, quad);
            output[index++] = quad;

            minX = quad.x < minX ? quad.x : minX;
            minY = quad.y < minY ? quad.y : minY;
            maxX = quad.x + quad.getWidth() > maxX ? quad.x + quad.getWidth() : maxX;
            maxY = quad.y + quad.getHeight() > maxY ? quad.y + quad.getHeight() : maxY;

        }
        return new QuadLayout(output, new Rectangle(minX, minY, maxX - minX, maxY - minY));
    }

    private void split(Rectangle freeArea, Quad quad) {
        int x1 = freeArea.x + quad.getWidth();
        int y1 = freeArea.y;
        int x2 = freeArea.x;
        int y2 = freeArea.y + quad.getHeight();
        //current heuristic: split along short edge
        //TODO: add possibility to change heuristic
        Rectangle rect1;
        Rectangle rect2;
        if (freeArea.width - x1 < freeArea.height - y1) {
            rect1 = new Rectangle(x1, y1, freeArea.width - quad.getWidth(), quad.getHeight());
            rect2 = new Rectangle(x2, y2, freeArea.width, freeArea.height - quad.getHeight());
        } else {
            rect1 = new Rectangle(x1, y1, freeArea.width - quad.getWidth(), freeArea.height);
            rect2 = new Rectangle(x2, y2, quad.getWidth(), freeArea.height - quad.getHeight());
        }

        freeQuads.remove(freeArea);
        freeQuads.addLast(rect1);
        freeQuads.addLast(rect2);
    }

    private boolean quadFits(Quad quad, Rectangle bounds) {
        return quad.getWidth() <= bounds.width && quad.getHeight() <= bounds.height;
    }

    private Rectangle getBestFreeArea(Quad quad) {
        //current heuristic: minimize difference in height
        //TODO: add possibility to change heuristic
        int bestHeight = Integer.MAX_VALUE;
        Rectangle currentRect = freeQuads.getFirst();
        freeQuads.goToFirst();
        for (int i = 0; i < freeQuads.size; i++) {
            Rectangle rect = freeQuads.getActive();
            freeQuads.goToNext();
            if (rect.height < bestHeight && quadFits(quad, rect)) {
                currentRect = rect;
                bestHeight = rect.height;
            }
        }
        return currentRect;
    }
    /**
     *
     * @return List of rectangles representing the free space left in the sprite sheet
     */
    public CustomLinkedList<Rectangle> getFreeQuads() {
        return freeQuads;
    }
}
