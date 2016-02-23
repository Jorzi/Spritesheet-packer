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

    private LinkedList<Rectangle> freeQuads;

    /**
     * Default constructor
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
            //splitFreeArea(freeArea, quad);
            ArrayList<Rectangle> splitRects = new ArrayList<>();
            int size = freeQuads.size();
            for (int i = 0; i < size; i++) {
                if(splitFreeArea(freeQuads.get(i), quad)){
                    splitRects.add(freeQuads.get(i));
                }    
            }
            for (Rectangle rect:splitRects){
                freeQuads.remove(rect);
            }
            pruneFreeAreas();
            output.add(quad);

            minX = quad.x < minX ? quad.x : minX;
            minY = quad.y < minY ? quad.y : minY;
            maxX = quad.x + quad.getWidth() > maxX ? quad.x + quad.getWidth() : maxX;
            maxY = quad.y + quad.getHeight() > maxY ? quad.y + quad.getHeight() : maxY;

        }
        return new QuadLayout(output, new Rectangle(minX, minY, maxX - minX, maxY - minY));
    }
    
    private boolean splitFreeArea(Rectangle freeArea, Quad quad) {
        if (!collisionDetection(quad, freeArea)) {
            return false; //rectangle wasnt't split and doesn't need to be deleted
        }
        // free area above quad
        if (quad.y > freeArea.y) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.height = quad.y - newRect.y;
            freeQuads.add(newRect);
        }
        // free area below quad
        if (quad.y + quad.getHeight() < freeArea.y + freeArea.height) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.y = quad.y + quad.getHeight();
            newRect.height = freeArea.y + freeArea.height - newRect.y;
            freeQuads.add(newRect);
        }
        // free area to the left of the quad
        if (quad.x > freeArea.x) {
            Rectangle newRect = new Rectangle(freeArea);
            newRect.width = quad.x - newRect.x;
            freeQuads.add(newRect);
        }
        // free area to the right of the quad
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
        //current heuristic: minimize difference in the shorter side
        //TODO: add possibility to change heuristic
        long bestWidth = Integer.MAX_VALUE;
        Rectangle currentRect = freeQuads.getFirst();
        for (Rectangle rect : freeQuads) {
            int shortSide = Math.min(rect.width - quad.getWidth(), rect.height - quad.getHeight());
            if (shortSide < bestWidth && quadFits(quad, rect)) {
                currentRect = rect;
                bestWidth = shortSide;
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
    
    private boolean isInside(Rectangle rect1, Rectangle rect2){
        //1 2
        //3 4
        boolean corner1 = rect1.x >= rect2.x && rect1.y >= rect2.y;
        boolean corner2 = rect1.x + rect1.width <= rect2.x + rect2.width;
        boolean corner3 = rect1.y + rect1.height <= rect2.y + rect2.height;
        return corner1 && corner2 && corner3;
    }
    
    private void pruneFreeAreas(){
        ArrayList<Rectangle> unnecessaryRects = new ArrayList<>();
        for(Rectangle rect1 : freeQuads){
            for(Rectangle rect2 : freeQuads){
                if(isInside(rect1, rect2) && rect1 != rect2){
                    unnecessaryRects.add(rect1);
                    break;
                }
            }
        }
        for(Rectangle rect: unnecessaryRects){
            freeQuads.remove(rect);
        }
    }

    /**
     *
     * @return List of rectangles representing the free space left in the sprite sheet
     */
    public LinkedList<Rectangle> getFreeQuads() {
        return freeQuads;
    }
    
    

}
