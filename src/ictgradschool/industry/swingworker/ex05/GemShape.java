package ictgradschool.industry.swingworker.ex05;

import java.awt.*;

/**
 * Class to represent a gem.
 * 
 * @author Ian Warren
 */
public class GemShape extends Shape {
	/**
	 * Default constructor that creates a OvalShape instance whose instance
	 * variables are set to default values.
	 */
	public GemShape() {
		super();
	}

	/**
	 * Creates a GemShape instance with specified values for instance
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed and direction for horizontal axis.
	 * @param deltaY speed and direction for vertical axis.
	 */
	public GemShape(int x, int y, int deltaX, int deltaY) {
		super(x,y,deltaX,deltaY);
	}

	/**
	 * Creates a GemShape instance with specified values for instance
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed (pixels per move call) and direction for horizontal
	 *        axis.
	 * @param deltaY speed (pixels per move call) and direction for vertical
	 *        axis.
	 * @param width width in pixels.
	 * @param height height in pixels.
	 */
	public GemShape(int x, int y, int deltaX, int deltaY, int width, int height) {
		super(x,y,deltaX,deltaY,width,height);
	}
	
	/**
	 * Paints this GemShape object using the supplied Painter object.
	 */
	@Override
	public void paint(Painter painter) {

        Polygon polygon = new Polygon();

		if (getWidth() <= 40) {
		    polygon.addPoint(fX, fY + fHeight / 2);
		    polygon.addPoint(fX + fWidth / 2, fY);
		    polygon.addPoint(fX + fWidth, fY + fHeight / 2);
		    polygon.addPoint(fX + fWidth / 2, fY + fHeight);

        }

        else {
            polygon.addPoint(fX, fY + fHeight / 2);
            polygon.addPoint(fX + 20, fY);
            polygon.addPoint(fX + fWidth - 20, fY);
            polygon.addPoint(fX + fWidth, fY + fHeight / 2);
            polygon.addPoint(fX + fWidth - 20, fY + fHeight);
            polygon.addPoint(fX + 20, fY + fHeight);
        }

        painter.drawPolygon(polygon);

	}
}
