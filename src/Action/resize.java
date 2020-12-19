package Action;

import Shape.Shape;
import java.awt.*;

public class resize implements DrawAction{//this action resizes a singe shape.
    private Point initial, latest;//initial location and the latest location of the of the node.
    private Shape shape;//the shape
    private int node;//which node is being resized.
    public resize(Point initial, Point latest,Shape s, int node) {
        this.initial = initial;
        this.latest = latest;
        this.shape = s;
        this.node = node;
    }//get the properties
    @Override
    public void execute() {//maths eqaution for roation from https://stackoverflow.com/questions/2259476/rotating-a-point-about-another-point-2d accessed on 25th of november 2020.
        latest = new Point ((int)((latest.x-shape.centre().x)*Math.cos(-shape.getAngle()) - (latest.y-shape.centre().y)*Math.sin(-shape.getAngle())+ shape.centre().x),
                (int)((latest.x-shape.centre().x)*Math.sin(-shape.getAngle()) + (latest.y-shape.centre().y)*Math.cos(-shape.getAngle())+ shape.centre().y));
        //apply reverse rotate to get the non rotated nodes for the corners.
        if (node ==1) {//appies to all shapes excpet text
            shape.setP(latest);
        }
        else if (node == 2) {//appies to all shapes excpet text
            shape.setP1(latest);
        }
        else if (node == 3) {//appies to all shapes excpet text and line
            shape.setP2(latest);
        }
        else if (node == 4) {//appies to all shapes excpet text and line and triangle
            shape.setP3(latest);
        }//changes the point thst being the dragged
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public void undo() {
        initial = new Point ((int)((initial.x-shape.centre().x)*Math.cos(-shape.getAngle()) - (initial.y-shape.centre().y)*Math.sin(-shape.getAngle())+ shape.centre().x),
                (int)((initial.x-shape.centre().x)*Math.sin(-shape.getAngle()) + (initial.y-shape.centre().y)*Math.cos(-shape.getAngle())+ shape.centre().y));

        //apply the opposite of the transformation to get the orignal node location
        if (node ==1) {
            shape.setP(initial);
        }
        else if (node == 2) {
            shape.setP1(initial);
        }
        else if (node == 3) {
            shape.setP2(initial);
        }
        else if (node == 4) {
            shape.setP3(initial);
        }
        else shape.setEnd(initial);
    }//resets the node to its original position
}
