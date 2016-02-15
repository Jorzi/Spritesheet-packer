/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Maconi
 */
public class MaxRectsPacker implements QuadPacker {

    private ArrayList<Quad> usedQuads;
    private LinkedList<Rectangle> freeQuads;

    /**
     *
     */
    public MaxRectsPacker() {
    }

    /**
     *
     * @param quads List of bounding boxes for the images
     * @param maxWidth Maximum bounds in the x direction. No part of the final
     * layout can extend beyond this limit
     * @return A QuadLayout object containing all the quads, stacked without
     * overlap, as well as the bounding rectangle of the whole layout
     * @throws Exception if maxWidth is too small to accommodate all images
     */
    @Override
    public QuadLayout generateLayout(ArrayList<Quad> quads, int maxWidth) throws Exception {
        freeQuads = new LinkedList<>();
        freeQuads.add(new Rectangle(maxWidth, Integer.MAX_VALUE));
        ArrayList<Quad> output = new ArrayList<>();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        for (Quad quad : quads) {
            if (quad.getWidth() > maxWidth) {
                throw new Exception("maxWidth too low to include " + quad.getName());
            }
            Rectangle freeArea = getBestFreeArea(quad);
            quad.x = freeArea.x;
            quad.y = freeArea.y;
            splitFreeArea(freeArea, quad);
            ArrayList<Rectangle> splitRects = new ArrayList<>();
            for (int i = 0; i < freeQuads.size(); i++) {
                if(splitFreeArea(freeQuads.get(i), quad)){
                    splitRects.add(freeQuads.get(i));
                }    
            }
            for (Rectangle rect:splitRects){
                freeQuads.remove(rect);
            }
            output.add(quad);

            minX = quad.x < minX ? quad.x : minX;
            minY = quad.y < minY ? quad.y : minY;
            maxX = quad.x + quad.getWidth() > maxX ? quad.x + quad.getWidth() : maxX;
            maxY = quad.y + quad.getHeight() > maxY ? quad.y + quad.getHeight() : maxY;

        }
        return new QuadLayout(output, new Rectangle(minX, minY, maxX - minX, maxY - minY));
    }

    //from guillotine algorithm, to be phased out
    private void split(Rectangle freeArea, Quad quad) {
        int x1 = freeArea.x + quad.getWidth();
        int y1 = freeArea.y;
        int x2 = freeArea.x;
        int y2 = freeArea.y + quad.getHeight();

        Rectangle rect1;
        Rectangle rect2;
        rect1 = new Rectangle(x1, y1, freeArea.width - quad.getWidth(), freeArea.height);
        rect2 = new Rectangle(x2, y2, freeArea.width, freeArea.height - quad.getHeight());

        freeQuads.remove(freeArea);
        freeQuads.add(rect1);
        freeQuads.add(rect2);
    }
    
    private boolean splitFreeArea(Rectangle freeArea, Quad quad) {
        if (!collisionDetection(quad, freeArea)) {
            return false; //rectangle wasnt't split and doesn't need to be deleted
        }
        if (quad.y > freeArea.y && quad.y < freeArea.y + freeArea.width) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.height = quad.y - newRect.y;
            freeQuads.add(newRect);
        }
        if (quad.y + quad.getHeight() < freeArea.y + freeArea.height) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.y = quad.y + quad.getHeight();
            newRect.height = freeArea.y + freeArea.height - newRect.y;
            freeQuads.add(newRect);
        }
        if (quad.x > freeArea.x && quad.x < freeArea.x + freeArea.width) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.width = quad.getWidth() + newRect.x;
            freeQuads.add(newRect);
        }
        if (quad.x + quad.getWidth() < freeArea.x + freeArea.width) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.x = quad.x + quad.getWidth();
            newRect.width = freeArea.x + freeArea.width - newRect.x;
            freeQuads.add(newRect);
        }
        return true;
    }

    private boolean quadFits(Quad quad, Rectangle bounds) {
        return quad.getWidth() <= bounds.width && quad.getHeight() <= bounds.height;
    }

    private Rectangle getBestFreeArea(Quad quad) {
        //current heuristic: minimize difference in width
        //TODO: add possibility to change heuristic
        int bestWidth = Integer.MAX_VALUE;
        Rectangle currentRect = freeQuads.getFirst();
        for (Rectangle rect : freeQuads) {
            if (rect.width < bestWidth && quadFits(quad, rect)) {
                currentRect = rect;
                bestWidth = rect.width;
            }
        }
        return currentRect;
    }

    private boolean collisionDetection(Quad quad, Rectangle rect) {

        if (quad.x < rect.x + rect.getWidth()
                && quad.y < rect.y + rect.getHeight()
                && rect.x < quad.x + quad.getWidth()
                && rect.y < quad.y + quad.getHeight()) {
            return true;
        }

        return false;
    }

}
