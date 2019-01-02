package ictgradschool.industry.swingworker.ex05;

/**
 * Class to represent a simple rectangle.
 * 
 * @author Ian Warren
 */
public class RectangleShape extends Shape {
	/**
	 * Default constructor that creates a ictgradschool.industry.lab12.bounce.RectangleShape instance whose instance
	 * variables are set to default values.
	 */
	public RectangleShape() {
		super();
	}
	
	/**
	 * Creates a ictgradschool.industry.lab12.bounce.RectangleShape instance with specified values for instance
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed and direction for horizontal axis.
	 * @param deltaY speed and direction for vertical axis.
	 */
	public RectangleShape(int x, int y, int deltaX, int deltaY) {
		super(x,y,deltaX,deltaY);
	}
	
	/**
	 * Creates a ictgradschool.industry.lab12.bounce.RectangleShape instance with specified values for instance
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
	public RectangleShape(int x, int y, int deltaX, int deltaY, int width, int height) {
		super(x,y,deltaX,deltaY,width,height);
	}
	
	/**
	 * Paints this ictgradschool.industry.lab12.bounce.RectangleShape object using the supplied ictgradschool.industry.lab12.bounce.Painter object.
	 */
	@Override
	public void paint(Painter painter) {
		painter.drawRect(fX,fY,fWidth,fHeight);
	}
}
