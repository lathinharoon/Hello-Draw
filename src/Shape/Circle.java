package Shape;

import java.awt.*;

public class Circle extends Shape{//subclass of shape,
    public Circle(Point point,Color color, BasicStroke strokeWidth, Boolean fill, double angle) {
        //instantite the shape class by passing in the the parementers.
        super(point, color, strokeWidth, fill,angle);
        p1 = point;
        p2 = point;
        p3 = point;//temp.ly set the the other nodes as the same.
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        p = new Point(p.x,p1.y);
        p3 = new Point(p1.x,p3.y);
    }//set the second node, and alter the other 2 adj nodes accoridngly.

    public void setP2(Point p2) {
        this.p2 = p2;
        p = new Point(p2.x,p.y);
        p3 = new Point(p3.x,p2.y);
    }

    public void setP3(Point p3) {
        this.p3 = p3;
        p1 = new Point(p3.x,p1.y);
        p2 = new Point(p2.x,p3.y);
    }

    @Override
    public void draw(Graphics g) {//drawing the shape on to the canvas.
        Graphics2D graphics = (Graphics2D)g.create();//make a new graphics
        graphics.setColor(color);
        (graphics).setStroke(strokeWidth);//set color and stroke width
        (graphics).rotate(angle,centre().x,centre().y);//rotate, the graphics around the centre of the shaoe.
        if (fill) graphics.fillOval(getMin().x, getMin().y, size().x, size().y);
        else {
            graphics.drawOval(getMin().x, getMin().y, size().x, size().y);
        }//if fill draw a filled shape, if not normal shape.
        if (selected) {//if the shape is selected
            ((Graphics2D) graphics).setStroke(new BasicStroke(1.5f));//set a new stroke width
            graphics.setColor(new Color(0,150,255));//colour to blue
            graphics.drawRoundRect(getMin().x, getMin().y, size().x, size().y, 5,5);//draw a rectanngle around the shape
            graphics.fillOval(p.x-4,p.y-4,8,8);
            graphics.fillOval(p1.x-4,p1.y-4,8,8);
            graphics.fillOval(p2.x-4,p2.y-4,8,8);
            graphics.fillOval(p3.x-4,p3.y-4,8,8);//highlight the nodes for reszing.
            //graphics.setColor(new Color(0,0,0));
            rotate = new Point(getMin().x+ size().x/2,getMin().y-size().y/2);//set the rotation node.
            graphics.drawLine(getMin().x+ size().x/2,getMin().y+ size().y/2, rotate.x, rotate.y);
            graphics.drawImage(image,rotate.x-15, rotate.y-15, 30,30,null);//draw the line and the image of the rotation icon
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));//UPDATE THE rotate node according to the
        }
        graphics.dispose();//dispose that graphic.
    }





    @Override
    public String toString() {
        return "Circle,"+p.x+","+p.y+","+color.getRGB()+","+strokeWidth.getLineWidth()+","+fill+","+angle+","+p3.x+","+p3.y;
    }//get he essential, data needed to create an identical shape.

    @Override
    public void setEnd(Point point) {
        p3 = point;
        p1 = new Point(p3.x,p.y);
        p2 = new Point(p.x,p3.y);
    }//when drawing set the point o the mosue as the end point of the shape.

    @Override
    public Point centre() {
        return new Point(getMin().x+size().x/2,getMin().y+size().y/2);
    }

    public Point getMin(){
        return new Point(Math.min(p.x,p3.x),Math.min(p.y,p3.y));
    }

    public Point size(){
        return new Point(Math.abs(p.x-p3.x),Math.abs(p.y-p3.y));
    }
}
