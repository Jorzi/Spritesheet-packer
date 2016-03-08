/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Maconi
 */
public class SortedMaxRectsPacker implements QuadPacker {

    private LinkedList<Rectangle> freeQuads;
    
    public SortedMaxRectsPacker() {
    }

    @Override
    public QuadLayout generateLayout(ArrayList<Quad> quads, int maxWidth) throws Exception {
        ArrayList<Quad>quads2 = (ArrayList<Quad>)quads.clone();
        Collections.sort(quads2, Collections.reverseOrder());
        MaxRectsPacker packer = new MaxRectsPacker();
        QuadLayout layout = packer.generateLayout(quads2, maxWidth);
        freeQuads = packer.getFreeQuads();
        return layout;
    }

    public LinkedList<Rectangle> getFreeQuads() {
        return freeQuads;
    }
    
    
    
}
