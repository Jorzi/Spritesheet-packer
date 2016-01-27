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
 */
public interface QuadPacker {
    
    public ArrayList<Quad> generateLayout(ArrayList<Quad> quads, int maxWidth);
    
    
}
