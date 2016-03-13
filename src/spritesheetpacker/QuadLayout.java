/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;

/**
 * Wrapper class containing a list of Quad objects and their bounding box.
 *
 * @author Maconi
 */
public class QuadLayout {

    /**
     * List of quads and their positions
     */
    public Quad[] quads;

    /**
     * Bounding rectangle of all quads combined
     */
    public Rectangle bounds;

    /**
     *
     * @param quads List of quads
     * @param bounds Bounding rectangle of all quads combined
     */
    public QuadLayout(Quad[] quads, Rectangle bounds) {
        this.quads = quads;
        this.bounds = bounds;
    }
    
    
}
