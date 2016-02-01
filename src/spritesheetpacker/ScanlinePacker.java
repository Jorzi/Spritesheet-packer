/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Maconi This algorithm sorts quads from big to small and places them
 * at the first possible location, testing for fit in a scanline pattern (left
 * to right, top to bottom).
 */
public class ScanlinePacker implements QuadPacker {

    public ScanlinePacker() {
    }

    @Override
    public QuadLayout generateLayout(ArrayList<Quad> quads, int maxWidth) {
        ArrayList<Quad> output = new ArrayList<>();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Collections.sort(quads, Collections.reverseOrder());
        for (Quad quad : quads) {
            int j = 0;
            while (true) {
                quad.y = j;
                boolean quadFits = false;
                for (int i = 0; i < maxWidth - quad.getWidth(); i++) {
                    quad.x = i;
                    if (!collisionDetection(quad, output)) {
                        quadFits = true;
                        break;
                    }

                }
                if (quadFits) {
                    output.add(quad);
                    minX = quad.x < minX ? quad.x : minX;
                    minY = quad.y < minY ? quad.y : minY;
                    maxX = quad.x + quad.getWidth() > maxX ? quad.x + quad.getWidth() : maxX;
                    maxY = quad.y + quad.getHeight() > maxY ? quad.y + quad.getHeight() : maxY;
                    break;
                }
                j++;
            }
        }

        return new QuadLayout(output, new Rectangle(minX, minY, maxX - minX, maxY - minY));
    }

    private boolean collisionDetection(Quad testQuad, ArrayList<Quad> quads) {
        for (Quad quad : quads) {
            if (testQuad.x < quad.x + quad.getWidth()
                    && testQuad.y < quad.y + quad.getHeight()
                    && quad.x < testQuad.x + testQuad.getWidth()
                    && quad.y < testQuad.y + testQuad.getHeight()) {
                return true;
            }
        }
        return false;
    }

}
