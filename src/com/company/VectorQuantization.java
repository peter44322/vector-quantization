package com.company;


import java.util.ArrayList;
import java.util.Arrays;

public class VectorQuantization {
    private ArrayList<Vector> originalVectors;
    private Vector originalAverage;
    private int vectorWidth, vectorHeight, codeBookSize;
    ArrayList<Node> codeBook;
    double[][] pixels;

    VectorQuantization(String path, int vectorWidth, int vectorHeight, int codeBookSize) {
        ImageProcessor imageProcessor = new ImageProcessor(path);
        pixels = imageProcessor.toArray();
        this.vectorHeight = vectorHeight;
        this.vectorWidth = vectorWidth;
        this.codeBookSize = codeBookSize;
        setOriginalVectors();
    }

    VectorQuantization(double[][] pixels, int vectorWidth, int vectorHeight, int codeBookSize) {
        this.pixels = pixels;
        this.vectorHeight = vectorHeight;
        this.vectorWidth = vectorWidth;
        this.codeBookSize = codeBookSize;
        setOriginalVectors();
    }

    private void setOriginalVectors() {
        originalVectors = new ArrayList<>();
        for (int y = 0; y < pixels.length; y += vectorHeight) {
            for (int x = 0; x < pixels[0].length; x += vectorWidth) {
                Vector vector = new Vector(vectorWidth, vectorHeight);
                vector.content = Arrays.copyOfRange(pixels, y, y + vectorHeight);
                vector.takeColumnsInRange(x, x + vectorWidth);
                originalVectors.add(vector);
            }
        }
        setOriginalAverage();
    }

    private void setOriginalAverage() {
        originalAverage = Vector.average(originalVectors);
    }

    void compress() {
        codeBook = new ArrayList<>();
        Node rootNode = new Node(originalAverage, true);
        codeBook.add(rootNode);
        while (true) {
            ArrayList<Node> newNodes = new ArrayList<>();
            if (codeBook.size() < codeBookSize) {
                for (Node i : codeBook) {
                    ArrayList<Node> childs = i.split();
                    newNodes.addAll(childs);
                }
            } else {
                newNodes = codeBook;
                for (Node i : newNodes) {
                    i.emptyAssociation();
                }
            }
            associate(newNodes);
            for (Node i : newNodes) {
                i.setAverageVector();
            }
            if (codeBook.equals(newNodes)) break;
            codeBook = newNodes;
        }
    }

    public Vector[][] constructed() {
        int index = 0;
        int rows = pixels.length / vectorWidth;
        int cols = pixels[0].length / vectorHeight;
        Vector[][] constructedVectors = new Vector[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                constructedVectors[i][j] = originalVectors.get(index++).nearest.averageVector;
            }
        }
        return constructedVectors;
    }

    public double[][] toPixels() {
        Vector[][] constructed = constructed();
        double[][] newPixels = pixels;
        for (int i = 0; i < constructed.length; i++) {
            for (int j = 0; j < constructed[0].length; j++) {
                for (int x = 0; x < vectorHeight; x++) {
                    for (int y = 0; y < vectorWidth; y++) {
                        int anchor1 = (i * vectorHeight) + y;
                        int anchor2 = (j * vectorWidth) + x;
                        newPixels[anchor1][anchor2] = constructed[i][j].content[x][y];
                    }
                }
            }
        }
        return newPixels;
    }

    public void decompress(String path) {
        ImageProcessor imageProcessor = new ImageProcessor(toPixels());
        imageProcessor.save(path);
    }

    private void associate(ArrayList<Node> nodes) {
        int minDistanceValue, distance;
        Node minDistanceNode;
        for (Vector i : originalVectors) {
            minDistanceValue = i.distanceFrom(nodes.get(0).vector);
            minDistanceNode = nodes.get(0);
            for (Node j : nodes) {
                distance = i.distanceFrom(j.vector);
                if (distance < minDistanceValue) {
                    minDistanceValue = distance;
                    minDistanceNode = j;
                }
            }
            i.setNearest(minDistanceNode);
            minDistanceNode.associate(i);
        }
    }

}
