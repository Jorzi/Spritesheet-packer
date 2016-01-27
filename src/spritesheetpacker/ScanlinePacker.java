/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Maconi
 */
public class ScanlinePacker implements QuadPacker {

    public ScanlinePacker() {
    }

    @Override
    public ArrayList<Quad> generateLayout(ArrayList<Quad> quads, int maxWidth) {
        ArrayList<Quad> output = new ArrayList<>();
        Collections.sort(quads);
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
                    break;
                }
                j++;
            }
        }

        return output;
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
