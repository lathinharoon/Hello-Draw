package Main;

import javax.swing.*;
import Shape.Shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class Drawing extends JPanel {//canvas which is a subclass of JPanel, holds and displays the shapes.
    private List<Shape> shapes;
    private Dimension dimension;

    public Drawing(Dimension dimension) {//make a new canavs using a dimension.
        shapes = new LinkedList<>();//an empty list of shapes
        this.dimension =dimension;
        this.setPreferredSize(dimension);//set the size of he JPanel
        setBorder(BorderFactory.createLineBorder(Color.black));//set border.
        setBackground(Color.WHITE);//set default bg colour
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Shape shapeAt(Point p) {//this function is used when the select tool click on the cnavas, this function
        Shape shape = null;
        for (int i = 0; i < shapes.size(); i++) {//iterates through the list of shapes to see if the point whre the mouse clicked lies within any
            if (shapes.get(i).withIn(p)) shape = shapes.get(i);//shape is the canvas.
        }
        return shape;//will return the latest shape in the canvas, i.e. on the top.
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void insertShape(Shape s) {
        shapes.add(s);
    }//add new shape, which are manipulated from the actions class

    public void removeShape(Shape s) {
        shapes.remove(s);
    }//add new shape, delete shape , which are manipulated from the actions class

    public void paintComponent(Graphics g) {//the method to dispplay all the shapes on the
        super.paintComponent(g);//call the super method from the parent class
        for (Shape s : shapes) {
            s.draw(g);//itertate through the list and call the draw method to draw the shapes.
        }
    }
}
