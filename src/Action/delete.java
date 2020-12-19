package Action;

import Main.Drawing;
import Shape.*;

public class delete implements DrawAction {//this action deletes a shape from the drawing.
    Drawing d;
    Shape s;

    public delete(Drawing d, Shape s) {
        this.d = d;
        this.s = s;
    }//get the shape and the drawing

    @Override
    public void execute() {
        d.removeShape(s);
    }//execute by removing the shape from the canvas.

    @Override
    public void redo() {
        execute();
    }//redo by doing the execute.

    @Override
    public void undo() {
        d.insertShape(s);
    }//undo by inserting the shape that was removed.
}
