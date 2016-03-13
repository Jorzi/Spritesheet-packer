/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

/**
 * Interface for generating layout information in text format. Enables the user 
 * to easily select the markup style
 * @author Maconi
 */
public interface LayoutWriter {
    
    /**
     * Write a layout description based on a generated quad layout.
     * @param layout
     * @return
     */
    public String WriteLayout(QuadLayout layout);
    
}
