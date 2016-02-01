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

    public int x;
    public int y;
    private int width;
    private int height;
    private String name;

    public Quad(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public Quad(Rectangle rect, String name) {
        this(rect.x, rect.y, rect.width, rect.height, name);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Quad t) {
        return this.width * this.height - (t.width * t.height);
    }

}
