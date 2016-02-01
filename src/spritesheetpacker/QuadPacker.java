/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.util.ArrayList;


/**
 *
 * @author Maconi
 * Interface for all the packing algorithms in order to easily plug them into the main application
 */
public interface QuadPacker {
    
    public QuadLayout generateLayout(ArrayList<Quad> quads, int maxWidth);
    
    
}
