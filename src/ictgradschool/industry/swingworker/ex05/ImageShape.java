package ictgradschool.industry.swingworker.ex05;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.imageio.ImageIO.*;

/**
 * A shape which is capable of loading and rendering images from files / Uris / URLs / etc.
 */
public class ImageShape extends Shape {

    private Image image;

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, String fileName) throws MalformedURLException {
        this(x, y, deltaX, deltaY, width, height, new File(fileName).toURI());
    }

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, URI uri) throws MalformedURLException {
        this(x, y, deltaX, deltaY, width, height, uri.toURL());
    }

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, URL url) {
        super(x, y, deltaX, deltaY, width, height);

        ImageGetter imageGetter = new ImageGetter(url,width,height);
        imageGetter.execute();
    }

    //My Swinger
    private class ImageGetter extends SwingWorker<Void,Image>{
        private URL url;
        private int width;
        private int height;
        private Image retImage;

        public ImageGetter(URL url,int width,int height){
            this.url=url;
            this.width=width;
            this.height=height;
        }

        @Override
        protected Void doInBackground() {
            //just saying loading is kinda boring, lets add a list and randomise the output.
            String[] loadMess = {"Pikachu, I choose you!..","Loading...","Gimmeie a sec..","hold ya horses", "wait for iiiiiit..", "ffs I'm getting there..", "maybe you'd like to come in here and do this?","Loading.., ya know, eventually","man, I sure wasted a lot of time in this.."};
            String mess = loadMess[(int)(Math.random()*loadMess.length)];
            //lets see if we can change up the fonts a bit too
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontList = env.getAvailableFontFamilyNames();
            String font = fontList[(int)(Math.random()*fontList.length)];

            //creates a temporary image to publish
            BufferedImage tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = tempImage.createGraphics();
            //draw the basic rectangle stuff in the image
            g2d.setColor(Color.lightGray);
            g2d.fillRect(0,0,width,height);
            g2d.setColor(Color.red);
            g2d.drawRect(0,0,width-1,height-1);
            // do some font stuff
            g2d.setFont(new Font(font, Font.PLAIN, (int)(Math.random()*10)+12));
            //lets center this stuff
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            int xText = (width - metrics.stringWidth(mess)) / 2;
            //draw that font stuff
            g2d.drawString(mess,xText,height/2);
            g2d.dispose();
            //send image to process method
            publish(tempImage);

            //load the real image
            try {
                Image image = ImageIO.read(url);
                if (width == image.getWidth(null) && height == image.getHeight(null)) {
                    this.retImage = image;
                } else {
                    this.retImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                }
                //accessing image to get scaling to do it's thing.
                retImage.getHeight(null);
                //publish the real image
                publish(retImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void process(List<Image> chunks) {
            //process the image list and set the shape image.
            for (Image timage:chunks
                 ) {
                   image = timage;
            }
        }
    }


    @Override
    public void paint(Painter painter) {

        painter.drawImage(this.image, fX, fY, fWidth, fHeight);

    }
}
