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
    public ArrayList<Quad> quads;
    public Rectangle bounds;

    public QuadLayout(ArrayList<Quad> quads, Rectangle bounds) {
        this.quads = quads;
        this.bounds = bounds;
    }
    
    public HashMap<String, Rectangle> getMappings(){
        HashMap<String, Rectangle> mapping = new HashMap<>();
        for(Quad quad:quads){
            mapping.put(quad.getName(), new Rectangle(quad.x, quad.y, quad.getWidth(), quad.getHeight()));
        }
        return mapping;
    }
    
}
