/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.image.BufferedImage;

/**
 * Wrapper class for the generated image and its layout description.
 * Only needed because Java can't return multiple variables
 * @author Maconi
 */
public class SpriteSheet {

    /**
     *
     */
    public BufferedImage image;

    /**
     *
     */
    public String layout;

    /**
     *
     * @param image
     * @param layout
     */
    public SpriteSheet(BufferedImage image, String layout) {
        this.image = image;
        this.layout = layout;
    }
    
}
