package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Vector {
    int width, height;
    double[][] content;
    Node nearest;

    Vector(int width, int height) {
        this.width = width;
        this.height = height;
        content = new double[height][width];
        this.zeros();
    }

    public void takeColumnsInRange(int start, int end) {
        for (int i = 0; i < content.length; i++) {
            if (content[i] != null)
                content[i] = Arrays.copyOfRange(content[i], start, end);
        }
        fillMissing();
    }

    public void fillMissing() {
        for (int i = 0; i < content.length; i++) {
            if (content[i] == null) {
                content[i] = content[i - 1];
            }
        }
    }

    public Vector plus(Vector other) {
        Vector result = new Vector(width, height);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result.content[i][j] = content[i][j] + other.content[i][j];
            }
        }

        return result;
    }

    public Vector divideBy(Vector other) {
        Vector result = new Vector(width, height);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result.content[i][j] = content[i][j] / other.content[i][j];
            }
        }
        return result;
    }

    public void zeros() {
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                content[i][j] = 0;
            }
        }
    }

    Vector divideByNumber(int number) {
        Vector result = new Vector(width, height);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result.content[i][j] = content[i][j] / number;
            }
        }
        return result;
    }

    Vector ceil() {
        Vector result = new Vector(width, height);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result.content[i][j] = Math.ceil(content[i][j]);
            }
        }
        return result;
    }

    Vector floor() {
        Vector result = new Vector(width, height);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result.content[i][j] = Math.floor(content[i][j]);
            }
        }
        return result;
    }

    int distanceFrom(Vector other) {
        int result = 0;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                result += Math.pow(
                        Math.abs(content[i][j]) - Math.abs(other.content[i][j]), 2
                );
            }
        }
        return result;
    }

    static Vector average(ArrayList<Vector> vectors) {
        Vector firstVector = vectors.get(0);
        Vector sum = new Vector(firstVector.width, firstVector.height);
        sum.zeros();
        for (Vector i : vectors) {
            sum = sum.plus(i);
        }
        return sum.divideByNumber(vectors.size());
    }

    public void setNearest(Node nearest) {
        this.nearest = nearest;
    }
}
