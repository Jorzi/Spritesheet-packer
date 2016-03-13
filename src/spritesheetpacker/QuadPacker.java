/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

/**
 * Interface for all the packing algorithms in order to easily plug them into
 * the main application
 *
 * @author Maconi
 *
 */
public interface QuadPacker {

    /**
     *
     * @param quads List of bounding boxes for the images
     * @param maxWidth Maximum bounds in the x direction. No part of the final
     * layout can extend beyond this limit
     * @return A QuadLayout object containing all the quads, stacked without
     * overlap, as well as the bounding rectangle of the whole layout
     * @throws Exception if maxWidth is too small to accomodate all images
     */
    public QuadLayout generateLayout(Quad[] quads, int maxWidth) throws Exception;

}
