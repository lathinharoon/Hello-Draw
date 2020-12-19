package Shape;

import javax.swing.*;
import javax.swing.plaf.SplitPaneUI;
import java.awt.*;
import java.sql.Struct;

public abstract class Shape {
    protected Point p,p1,p2,p3,rotate;//points for the veritces for the shapes and the node for the rotation anchor
    protected Color color;//hape colour
    protected BasicStroke strokeWidth;//width stroke
    protected boolean selected;
    protected boolean fill;
    protected Image image;//image for the rotation.
    protected Double angle;//angle by how much the shape has been rotated. intially its 0.00

    public Shape(Point point,Color color, BasicStroke strokeWidth, Boolean fill, double angle) {
        //instantiate the general shape by passing in a single node, then colour, then storke width, fill, and the angle. default 0.00
        this.p = point;
        this.rotate = point;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.fill = fill;
        this.selected = false;
        this.image =  new ImageIcon("img/rotate.png").getImage();//load the rotation image
        this.angle = angle;
    }

    public abstract void draw(Graphics graphics);//an abstract function to draw each sahpe that each subcalss muast implemennt

    public void move(int x, int y) {
        p.x += x;
        p.y += y;
        p1.x += x;
        p1.y += y;
        p2.x += x;
        p2.y += y;
        p3.x += x;
        p3.y += y;
    }//move a shape by moving all the nodes by the changes

    public boolean withIn(Point point) {//in order to check when the user clicks on th canvas a shape lies in the posisition,
        //i use the triangle method to calculate the area of the shape, then get the area of the traingle between 2 vertices of the shape and the point of the mosue acting as the trinagle
        //then find the areas for all 4 trinagles and add the total to see if thts equal to the aree of the shape.

        double tot = area(p.x,p.y,p1.x,p1.y,p2.x,p2.y)+area(p3.x,p3.y,p1.x,p1.y,p2.x,p2.y);//tot area
        double ar1 = area(p.x,p.y,p1.x,p1.y,point.x,point.y);
        double ar2 = area(p3.x,p3.y,p1.x,p1.y,point.x,point.y);
        double ar3 = area(p3.x,p3.y,p2.x,p2.y,point.x,point.y);
        double ar4 = area(p2.x,p2.y,p.x,p.y,point.x,point.y);//4 different triangles

        return tot == (ar1+ar2+ar3+ar4) ||rotating(point)||reSizing(point)!=0;//check if the user is resizing, or rotaing as well.


    }
    public double area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }//claculate the area of a trinagel.
    //https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/

    public int reSizing(Point point) {//resizing, get the mouse location and see, it the user is on top of the nodes
        Point n = new Point ((int)((p.x-centre().x)*Math.cos(angle) - (p.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p.x-centre().x)*Math.sin(angle) + (p.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n1 = new Point ((int)((p1.x-centre().x)*Math.cos(angle) - (p1.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p1.x-centre().x)*Math.sin(angle) + (p1.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n2 = new Point ((int)((p2.x-centre().x)*Math.cos(angle) - (p2.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p2.x-centre().x)*Math.sin(angle) + (p2.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n3 = new Point ((int)((p3.x-centre().x)*Math.cos(angle) - (p3.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p3.x-centre().x)*Math.sin(angle) + (p3.y-centre().y)*Math.cos(angle)+ centre().y));
        //first, translate the 4 nodes according to the rotaion,
        if (n.distance(point)<=16) return 1;//check if the translated 1st node is where the user is cleckking if it is return 1
        if (n1.distance(point)<=16) return 2;
        if (n2.distance(point)<=16) return 3;
        if (n3.distance(point)<=16) return 4;//same for others
        return 0;//if not return 0
    }

    public abstract String toString();//essential tostring method tht converts a shape to string.

    public abstract void setEnd(Point p);//essential set end method, to be used during drawing a new shape.

    public abstract Point centre();//get the centre of the shape

    public Double getAngle() {
        return angle;
    }//get the current angle of rotation

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStrokeWidth(BasicStroke strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Color getColor() { return color; }

    public boolean isFill() { return fill; }

    public void setFill(boolean fill) { this.fill = fill; }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
        p1 = new Point(p1.x,p.y);
        p2 = new Point(p.x,p2.y);
    }//set the 1st node to the new value and vary the other 2 adj. nodes.

    public abstract Point getMin();//get the top left edge

    public abstract Point size();//get the width and height

    public abstract void setP1(Point latest);

    public abstract void setP2(Point latest);

    public abstract void setP3(Point latest);

    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p1;
    }
    public Point getP3() {
        return p1;
    }

    public boolean rotating(Point p) {
        return p.distance(rotate)<10;
    }//check if the user is on top of the rotating node.

    public Point getRotate() {
        return rotate;
    }

    public void setRotate(Point rotate) {
        this.rotate = rotate;
    }

}
