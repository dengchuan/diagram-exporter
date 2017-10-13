package org.reactome.server.tools.diagram.exporter.raster.renderers.layout;

import org.reactome.server.tools.diagram.data.layout.Bound;
import org.reactome.server.tools.diagram.data.layout.Compartment;
import org.reactome.server.tools.diagram.data.profile.diagram.DiagramProfile;
import org.reactome.server.tools.diagram.exporter.raster.DiagramCanvas;
import org.reactome.server.tools.diagram.exporter.raster.profiles.ColorFactory;
import org.reactome.server.tools.diagram.exporter.raster.renderers.common.ShapeFactory;
import org.reactome.server.tools.diagram.exporter.raster.renderers.common.StrokeProperties;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Renderer for compartments
 */
public class CompartmentRenderer extends NodeAbstractRenderer {

	private Shape outer(Compartment node) {
		return ShapeFactory.roundedRectangle(node.getProp());
	}

	private Shape inner(Compartment item) {
		if (item.getInsets() == null)
			return null;
		final Bound bound = item.getInsets();
		return ShapeFactory.roundedRectangle(bound.getX(), bound.getY(),
				bound.getWidth(), bound.getHeight());
	}


	public void draw(DiagramCanvas canvas, List<Compartment> compartments, DiagramProfile profile) {
		final String fillString = profile.getCompartment().getFill();
		final Paint fill = ColorFactory.parseColor(fillString);
		// Inner color is the sum of the fill color with itself
		final String innerColor = ColorFactory.asRgba(ColorFactory.blend((Color) fill, (Color) fill));
		final String border = profile.getCompartment().getStroke();
		final Stroke stroke = StrokeProperties.BORDER_STROKE;
		final String text = profile.getCompartment().getText();

		final List<Shape> outer = compartments.stream()
				.map(compartment -> outer(compartment))
				.collect(Collectors.toCollection(ArrayList::new));
		final List<Shape> inner = compartments.stream()
				.map(compartment -> inner(compartment))
				.collect(Collectors.toCollection(ArrayList::new));

		// Instead of painting both rectangles for each compartment
		// we fill the inner one, but for the outer we paint only the residual
		// space. That means that we are setting each pixel only once
		for (int i = 0; i < outer.size(); i++) {
			final Area out = new Area(outer.get(i));
			if (inner.get(i) != null) {
				final Area inn = new Area(inner.get(i));
				out.subtract(inn);
				canvas.getCompartmentFill().add(innerColor, inn);
				canvas.getCompartmentBorder().add(border, stroke, inn);
			}
			canvas.getCompartmentFill().add(fillString, out);
			canvas.getCompartmentBorder().add(border, stroke, outer.get(i));
		}

		compartments.forEach(compartment -> {
			canvas.getCompartmentText().add(text, compartment.getDisplayName(), compartment.getTextPosition());
		});
	}

}
