package org.reactome.server.tools.diagram.exporter.pptx.model;

import com.aspose.slides.IAutoShape;
import com.aspose.slides.ILineFormat;
import com.aspose.slides.IShapeCollection;
import org.reactome.server.tools.diagram.data.layout.Connector;
import org.reactome.server.tools.diagram.data.layout.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
public abstract class PPTXNode {

    private Long id;
    private float x;
    private float y;
    private float width = 1;
    private float height = 1;

    private Long reactomeId;
    private String schemaClass;
    private String displayName;
    private List<Connector> connectors;

    @SuppressWarnings("WeakerAccess")
    protected IAutoShape iAutoShape;
    PPTXShape pptxShape;
    Stoichiometry stoichiometry;

    public PPTXNode(Node node) {
        this.reactomeId = node.getReactomeId();
        this.schemaClass = node.getSchemaClass();

        this.id = node.getId();
        this.x = node.getProp().getX().floatValue();
        this.y = node.getProp().getY().floatValue();
        this.width = node.getProp().getWidth().floatValue();
        this.height = node.getProp().getHeight().floatValue();
        this.displayName = node.getDisplayName();
        this.connectors = node.getConnectors();
    }

    public Long getId() {
        return id;
    }

    public IAutoShape getiAutoShape() {
        return iAutoShape;
    }

    public List<Connector> getConnectors() {
        return connectors != null ? connectors : new LinkedList<>();
    }

    public List<Connector> getConnectors(Long edgeId, String type) {
        List<Connector> rtn = new ArrayList<>();
        for (Connector connector : getConnectors()) {
            if (connector.getEdgeId().equals(edgeId) && connector.getType().equals(type)) rtn.add(connector);
        }
        return rtn;
    }

    public abstract void render(IShapeCollection shapes);

    final void render(IShapeCollection shapes, int shapeType, int lineWidth, byte lineStyle, byte lineFillStyle, Color lineColor, byte shapeFillType, Color fillColor) {
        iAutoShape = shapes.addAutoShape(shapeType, x, y, width, height);

        iAutoShape.getFillFormat().setFillType(shapeFillType);
        iAutoShape.getFillFormat().getSolidFillColor().setColor(fillColor);

        ILineFormat lineFormat = iAutoShape.getLineFormat();
        lineFormat.setWidth(lineWidth);
        lineFormat.setStyle(lineStyle);
        lineFormat.getFillFormat().setFillType(lineFillStyle);
        lineFormat.getFillFormat().getSolidFillColor().setColor(lineColor);

    }


    @Override
    public String toString() {
        return schemaClass + "{" +
                "id=" + reactomeId +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
