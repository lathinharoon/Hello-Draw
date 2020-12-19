package Action;

import Shape.*;
import Shape.Shape;

import java.awt.Color;
import java.util.ArrayList;

public class ColorAc implements DrawAction {//this action changes the colour of the selected shape/s.

    ArrayList<Shape> s;//lsit of selected shapes.
    Color c;// new colour
    ArrayList<Color> prev;//list of old colours of the selected shapes.

    public ColorAc(ArrayList<Shape> s, Color c) {//get the shapes and the new colour
        this.s = (ArrayList<Shape>) s.clone();
        this.c = c;
        prev =  new ArrayList<>();
        for (Shape shape: s) {
            prev.add(shape.getColor());
        }//get the old colour and store them
    }

    @Override
    public void execute() {
        for (Shape shape: s) {
            shape.setColor(c);
        }//iterate through the list and change the colour
    }

    @Override
    public void redo() {
        execute();
    }//when you redo the action, repeat execution.

    @Override
    public void undo() {//undo by iterating through the shapes and getting the respective prev colours and change thecolours
        for (int i = 0; i < prev.size(); i++) {
            s.get(i).setColor(prev.get(i));
        }
    }
}