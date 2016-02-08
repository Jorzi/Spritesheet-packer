/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Wrapper class containing a list of Quad objects and their bounding box.
 *
 * @author Maconi
 */
public class QuadLayout {

    /**
     * List of quads and their positions
     */
    public ArrayList<Quad> quads;

    /**
     * Bounding rectangle of all quads combined
     */
    public Rectangle bounds;

    /**
     *
     * @param quads List of quads
     * @param bounds Bounding rectangle of all quads combined
     */
    public QuadLayout(ArrayList<Quad> quads, Rectangle bounds) {
        this.quads = quads;
        this.bounds = bounds;
    }
    
    /**
     * Converts the list of quads into a hash map, so that quads can be easily accessed by their name
     * @return A hash map of rectangles with quad names as keys
     */
    public HashMap<String, Rectangle> getMappings(){
        HashMap<String, Rectangle> mapping = new HashMap<>();
        for(Quad quad:quads){
            mapping.put(quad.getName(), new Rectangle(quad.x, quad.y, quad.getWidth(), quad.getHeight()));
        }
        return mapping;
    }
    
}
