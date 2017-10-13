package org.reactome.server.tools.diagram.exporter.raster.renderers.layout;

import org.reactome.server.tools.diagram.data.layout.DiagramObject;
import org.reactome.server.tools.diagram.data.layout.Node;
import org.reactome.server.tools.diagram.data.layout.NodeProperties;
import org.reactome.server.tools.diagram.exporter.raster.renderers.common.RendererProperties;
import org.reactome.server.tools.diagram.exporter.raster.renderers.common.ShapeFactory;

import java.awt.*;

/**
 * Renderer for complexes, which are corned rectangles.
 *
 * @author Lorente-Arencibia, Pascual (pasculorente@gmail.com)
 */
public class ComplexRenderer extends NodeAbstractRenderer {

	@Override
	protected Shape backgroundShape(DiagramObject item) {
		final Node node = (Node) item;
		final NodeProperties prop = node.getProp();
		final int corner = (int) RendererProperties.COMPLEX_RECT_ARC_WIDTH;
		final double x = prop.getX();
		final double y = prop.getY();
		final double width = prop.getWidth();
		final double height = prop.getHeight();
		return ShapeFactory.getCornedRectangle(x, y, width, height, corner);
	}

}
