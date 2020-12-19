package Action;


import Shape.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class move implements DrawAction {//this action moves selected shape/s from one point in the canvas to another.
    List<Shape> selected;//list of selected shapes
    Point s;//the change in x and y, made b the movement.

    public move(ArrayList<Shape> selected, Point s) {
        this.selected = (List<Shape>) selected.clone();
        this.s = s;
    }//get  the shapes and the point

    @Override
    public void execute() {
        for (Shape shape : selected) {
            shape.move(s.x,s.y);
        }//iterate through the shape and move each shape
    }

    @Override
    public void redo() {
        execute();
    }//if redo, recall the execute method.

    @Override
    public void undo() {
        for (Shape shape : selected) {
            shape.move(-s.x,-s.y);
        }//iterate through the shapes and reverse the movement
    }
}
