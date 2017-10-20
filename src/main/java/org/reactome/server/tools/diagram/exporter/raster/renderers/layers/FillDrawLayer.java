package org.reactome.server.tools.diagram.exporter.raster.renderers.layers;

import java.awt.*;

/**
 * Layer for rendering shapes with fill and border.
 *
 * @author Lorente-Arencibia, Pascual (pasculorente@gmail.com)
 */
public interface FillDrawLayer extends Layer {

	void add(Color fillColor, Color borderColor, Stroke borderStroke, Shape shape);
}