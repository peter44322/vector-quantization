package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    private BufferedImage image = null;
    private int width = 0;
    private int height = 0;

    ImageProcessor(String path) {
        File file;
        try {
            file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.getStackTrace();
        }
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }
    }

    ImageProcessor(double[][] pixels) {
        image = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < pixels[0].length; x++) {
            for (int y = 0; y < pixels.length; y++) {
                image.setRGB(x, y, ((int)pixels[y][x]<<16)|((int)pixels[y][x]<<8)|((int)pixels[y][x]));
            }
        }
    }

    void save(String path) {
        File outPutFile=new File(path);
        try {
            ImageIO.write(image, "jpg", outPutFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    double[][] toArray() {
        double[][] pixels = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x <width ; x++) {
                pixels[y][x] = (image.getRGB(x, y) >> 16) & 0xff;
            }
        }
        return pixels;
    }

    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }
}
