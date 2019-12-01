package com.company;

import java.util.ArrayList;

public class Node {
    Vector averageVector;
    Vector vector;
    private ArrayList<Vector> associated;

    Node(Vector vector, boolean avg) {
        associated = new ArrayList<>();
        if (avg) {
            this.averageVector = vector;
        } else {
            this.vector = vector;
        }
    }

    public ArrayList<Node> split() {
        ArrayList<Node> childs = new ArrayList<>();
        childs.add(new Node(averageVector.ceil(), false));
        childs.add(new Node(averageVector.floor(), false));
        return childs;
    }

    public void associate(Vector vector) {
        associated.add(vector);
    }

    public void setAverageVector() {
        averageVector = Vector.average(associated);
        vector = averageVector;
    }

    public void emptyAssociation() {
        associated = new ArrayList<>();
    }
}
