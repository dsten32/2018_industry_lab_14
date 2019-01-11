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

//        try {
//            Image image = ImageIO.read(url);
//            if (width == image.getWidth(null) && height == image.getHeight(null)) {
//                this.image = image;
//            } else {
//                this.image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    //My Swinger
    private class ImageGetter extends SwingWorker<Image,Image>{
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
        protected Image doInBackground() {
            BufferedImage tempImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = tempImage.createGraphics();
            g2d.setColor(Color.lightGray);
            g2d.fillRect(0,0,width,height);
            g2d.setColor(Color.red);
            g2d.drawRect(0,0,width-1,height-1);
            g2d.drawString("Loading...",(int)(width/2.5),height/2);
            g2d.dispose();
            publish(tempImage);

            try {
                Image image = ImageIO.read(url);
                if (width == image.getWidth(null) && height == image.getHeight(null)) {
                    this.retImage = image;
                } else {
                    this.retImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                }
                retImage.getHeight(null);
                publish(retImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retImage;
        }

        @Override
        protected void process(List<Image> chunks) {
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
