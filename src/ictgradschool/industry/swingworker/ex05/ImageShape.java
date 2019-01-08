package ictgradschool.industry.swingworker.ex05;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

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

        ImageGetter imageGetter = new ImageGetter(url,width,height,image);
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

    private class ImageGetter extends SwingWorker<Void,Image>{
        URL url;
        int width;
        int height;
        Image image;

        public ImageGetter(URL url,int width,int height,Image image){
            this.url=url;
            this.width=width;
            this.image=image;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                Image image = ImageIO.read(url);
                if (width == image.getWidth(null) && height == image.getHeight(null)) {
                    this.image = image;
                } else {
                    this.image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            publish(image);
            return null;
        }

        @Override
        protected void process(List<Image> chunks) {
            super.process(chunks);
        }
    }


    @Override
    public void paint(Painter painter) {

        painter.drawImage(this.image, fX, fY, fWidth, fHeight);

    }
}
