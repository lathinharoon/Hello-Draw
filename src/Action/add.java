package Action;

import Main.Drawing;
import Shape.Shape;

public class add implements DrawAction {//an implementation of the draw action interface.
    //this action adds a shape to the drawing.

    Drawing d;
    Shape s;//properties to store the drawing and the shape.

    public add(Drawing d, Shape s) {//constructor to create this action
        this.d = d;
        this.s = s;
    }

    @Override
    public void execute() {
        d.insertShape(s);
    }//executes this action by adding the shape into the drawing canvas.


    @Override
    public void redo() {
        execute();
    }//when you redo the action, repeat execution.

    @Override
    public void undo() {
        d.removeShape(s);
    }//if undo, remove the shape from the canvas.
}
