/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import utils.CustomLinkedList;
import utils.MergeSort;

/**
 *
 * @author Maconi
 */
public class SortedMaxRectsPacker implements QuadPacker {

    private CustomLinkedList<Rectangle> freeQuads;
    
    public SortedMaxRectsPacker() {
    }

    @Override
    public QuadLayout generateLayout(Quad[] quads, int maxWidth) throws Exception {
        Quad[] quads2 = MergeSort.mergeSort(quads);
        MaxRectsPacker packer = new MaxRectsPacker();
        QuadLayout layout = packer.generateLayout(quads2, maxWidth);
        freeQuads = packer.getFreeQuads();
        return layout;
    }

    public CustomLinkedList<Rectangle> getFreeQuads() {
        return freeQuads;
    }
    
    
    
}
