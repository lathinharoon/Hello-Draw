package Action;

import Shape.*;
import java.util.ArrayList;
import java.util.List;

public class fill implements DrawAction {//this action changes the fill property of the selected shape/s.

    List<Shape> selected;//list of selected shapes
    boolean fill;//if the action is suppose to fill or not fill the shape

    public fill(ArrayList<Shape> s, boolean fill) {
        selected = (List<Shape>) s.clone();
        this.fill = fill;
    }

    public void execute() {
        for (Shape shape : selected) {
            shape.setFill(fill);
        }//iterate through the shape and set the boolean
    }

    public void redo() {
        this.execute();
    }//if redo, execute the action again.

    public void undo() {
        for (Shape shape : selected) {
            shape.setFill(!fill);
        }//if undo, iterate the list and inverse the action.
    }

}

