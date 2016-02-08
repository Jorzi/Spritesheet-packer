/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritesheetpacker;

/**
 * A simple way of producing a text containing all the information needed to
 * reproduce the Quad layout in another program
 * @author Maconi
 */
public class SimpleLayoutWriter implements LayoutWriter {

    /**
     * Default constructor
     */
    public SimpleLayoutWriter() {
    }

    /**
     * Write a layout description based on a generated quad layout. 
     * The first line contains bounding box information. 
     * Its format will be "Bounds x y width height"
     * The rest of the lines will be "imageName x y width height"
     * @param layout
     * @return
     */
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
