/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import utils.CustomLinkedStructure;
import utils.MergeSort;

/**
 * A wrapper for the MaxRects packer, that sorts the quads in descending order
 * before generating the layout.
 * @author Maconi
 */
public class SortedMaxRectsPacker implements QuadPacker {

    private CustomLinkedStructure<Rectangle> freeQuads;
    
    /**
     * Default constructor
     */
    public SortedMaxRectsPacker() {
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
    public QuadLayout generateLayout(Quad[] quads, int maxWidth) throws Exception {
        Quad[] quads2 = MergeSort.mergeSort(quads);
        MaxRectsPacker packer = new MaxRectsPacker();
        QuadLayout layout = packer.generateLayout(quads2, maxWidth);
        freeQuads = packer.getFreeQuads();
        return layout;
    }

    /**
     *
     * @return List of rectangles representing the free space left in the sprite
     * sheet
     */
    public CustomLinkedStructure<Rectangle> getFreeQuads() {
        return freeQuads;
    }
    
    
    
}
