package ictgradschool.industry.swingworker.ex05;

import java.awt.*;

/**
 * Class to represent a simple rectangle.
 *
 * @author Ian Warren
 */
public class DynamicRectangleShape extends RectangleShape {

    private static final Color DEFAULT_COLOR = Color.black;

    private boolean filled = false;
    private Color color;

    /**
     * Default constructor that creates a ictgradschool.industry.lab12.bounce.DynamicRectangleShape instance whose instance
     * variables are set to default values.
     */
    public DynamicRectangleShape() {
        super();
        this.color = DEFAULT_COLOR;
    }

    /**
     * Creates a ictgradschool.industry.lab12.bounce.DynamicRectangleShape instance with specified values for instance
     * variables.
     *
     * @param x      x position.
     * @param y      y position.
     * @param deltaX speed and direction for horizontal axis.
     * @param deltaY speed and direction for vertical axis.
     */
    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY) {
        this(x, y, deltaX, deltaY, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_COLOR);
    }

    /**
     * Creates a ictgradschool.industry.lab12.bounce.DynamicRectangleShape instance with specified values for instance
     * variables.
     *
     * @param x      x position.
     * @param y      y position.
     * @param deltaX speed (pixels per move call) and direction for horizontal
     *               axis.
     * @param deltaY speed (pixels per move call) and direction for vertical
     *               axis.
     * @param width  width in pixels.
     * @param height height in pixels.
     */
    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height) {
        this(x, y, deltaX, deltaY, width, height, DEFAULT_COLOR);
    }

    /**
     * Creates a ictgradschool.industry.lab12.bounce.DynamicRectangleShape instance with specified values for instance
     * variables.
     *
     * @param x      x position.
     * @param y      y position.
     * @param deltaX speed (pixels per move call) and direction for horizontal
     *               axis.
     * @param deltaY speed (pixels per move call) and direction for vertical
     *               axis.
     * @param width  width in pixels.
     * @param height height in pixels.
     * @param color the color
     */
    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color color) {
        super(x, y, deltaX, deltaY, width, height);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void move(int width, int height) {

        int oldDx = fDeltaX;
        int oldDy = fDeltaY;

        super.move(width, height);

        if (oldDx != fDeltaX) {
            filled = true;
        } else if (oldDy != fDeltaY) {
            filled = false;
        }
    }

    /**
     * Paints this ictgradschool.industry.lab12.bounce.RectangleShape object using the supplied ictgradschool.industry.lab12.bounce.Painter object.
     */
    @Override
    public void paint(Painter painter) {

        if (filled) {
            Color temp = painter.getColor();

            painter.setColor(this.color);
            painter.fillRect(fX, fY, fWidth, fHeight);

            painter.setColor(temp);
        }

        super.paint(painter);
    }
}
