/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

/**
 *
 * @author Maconi
 */
public class SimpleLayoutWriter implements LayoutWriter {

    public SimpleLayoutWriter() {
    }

    @Override
    public String WriteLayout(QuadLayout layout) {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Bounds %d %d %d %d\n",
                layout.bounds.x, layout.bounds.y,
                layout.bounds.width, layout.bounds.height));
        for (Quad quad : layout.quads) {
            output.append(String.format("%s %d %d %d %d\n",
                    quad.getName(), quad.x, quad.y, quad.getWidth(), quad.getHeight()));
        }

        return new String(output);
    }

}
