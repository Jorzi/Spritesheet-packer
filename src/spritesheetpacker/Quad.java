/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;

/**
 * A Quad contains the bounding box of an image. The name is used to identify
 * which image it represents. Its x and y can be freely changed to move it
 * around, but its height, width and name are read-only.
 *
 * @author Maconi
 *
 */
public class Quad implements Comparable<Quad> {

    /**
     * Horizontal position of upper-left corner
     */
    public int x;

    /**
     * Vertical position of upper-left corner
     */
    public int y;
    private int width;
    private int height;
    private String name;

    /**
     *
     * @param x Horizontal position of upper-left corner
     * @param y Vertical position of upper-left corner
     * @param width Width of the rectangle in pixels
     * @param height Height of the rectangle in pixels
     * @param name Name of the image corresponding to this quad
     */
    public Quad(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    /**
     *
     * @param rect Rectangle specifying the dimensions of the image
     * @param name Name of the image corresponding to this quad
     */
    public Quad(Rectangle rect, String name) {
        this(rect.x, rect.y, rect.width, rect.height, name);
    }

    /**
     *
     * @return Width of the quad in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return Height of the quad in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return Name of the image which the quad represents
     */
    public String getName() {
        return name;
    }

    /**
     * Compares Quads according to their surface area
     * @param t
     * @return
     */
    @Override
    public int compareTo(Quad t) {
        return this.width * this.height - (t.width * t.height);
    }

}
