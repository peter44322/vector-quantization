package com.company;

public class Main {

    public static void main(String[] args) {

        double[][] pixels = {
                {1,2,7,9,4,11},
                {3,4,6,6,12,12},
                {4,9,15,14,9,9},
                {10,10,20,18,8,8},
                {4,3,17,16,1,4},
                {4,5,18,18,5,6},
        };

//        VectorQuantization vectorQuantization = new VectorQuantization(pixels,2,2,4);
        VectorQuantization vectorQuantization = new VectorQuantization("/home/peter/Desktop/68425336_517755855627331_3303502940718759936_n.jpg",6,6,50);
        vectorQuantization.compress();
        vectorQuantization.decompress("rrr.jpg");
    }

}
