package Action;

import Shape.Shape;

import java.awt.*;
import java.lang.management.MemoryType;

public class rotate implements DrawAction{
    double old,newDegrees;//
    Shape s;

    public rotate(double old,double newDegrees, Shape s) {
        this.s = s;
        this.old = old;
        this.newDegrees = newDegrees;

    }
    @Override
    public void execute() {
        s.setAngle(newDegrees);
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public void undo() {
        s.setAngle(old);
    }//returns the shape to its original rotation.
}
