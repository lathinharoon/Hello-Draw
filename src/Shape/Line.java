package Shape;

import java.awt.*;

public class Line extends Shape{//a type of shape.

    public Line(Point point,Color color, BasicStroke strokeWidth,double angle){
        super(point, color, strokeWidth, false, angle);
        p1 = p;//call super to instantiate a general shape.
        //inlike other shapes, line only needs one other node.
    }

    @Override
    public void setP(Point p) {
        this.p = p;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    @Override
    public void setP2(Point latest) { }

    @Override
    public void setP3(Point latest) { }

    @Override
    public void draw(Graphics g) {
        Graphics2D graphics = (Graphics2D)g.create();
        (graphics).setStroke(strokeWidth);
        graphics.setColor(color);
        (graphics).rotate(angle,centre().x,centre().y);
        graphics.drawLine(p.x, p.y, p1.x, p1.y);//draw line

        if (isSelected()) {
            (graphics).setStroke(new BasicStroke(1.5f));
            graphics.setColor(new Color(0,150,255));
            graphics.drawRoundRect(getMin().x,getMin().y,size().x,size().y, 5,5);
            graphics.fillOval(p.x-4,p.y-4,8,8);
            graphics.fillOval(p1.x-4,p1.y-4,8,8);
            rotate = new Point(getMin().x+ size().x/2,getMin().y-size().y/2-25);
            graphics.drawLine(getMin().x+ size().x/2,getMin().y+ size().y/2, rotate.x, rotate.y);
            graphics.drawImage(image,rotate.x-15, rotate.y-15, 30,30,null);
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));//same as other shapes
        }
        graphics.dispose();
    }


    @Override
    public void move(int x, int y) {
        p.x += x;
        p.y += y;
        p1.x += x;
        p1.y += y;
    }

    @Override
    public boolean withIn(Point point) {
        if (point.x> getMin().x&&point.x<getMin().x+size().x&&point.y>getMin().y&&point.y<getMin().y+size().y) return true;
        //if its within a square where the line is diagonal
        if (reSizing(point)>0) return true;
        return rotating(point);//if resizing or rotiang return true as well.
    }

    @Override
    public int reSizing(Point point) {
        Point n = new Point ((int)((p.x-centre().x)*Math.cos(angle) - (p.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p.x-centre().x)*Math.sin(angle) + (p.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n1 = new Point ((int)((p1.x-centre().x)*Math.cos(angle) - (p1.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p1.x-centre().x)*Math.sin(angle) + (p1.y-centre().y)*Math.cos(angle)+ centre().y));
        if (n.distance(point)<=16) return 1;
        if (n1.distance(point)<=16) return 2;
        return 0;
    }//instaed of all 4 nodes  only for 2

    @Override
    public String toString() {
        return "Line,"+p.x+","+p.y+","+color.getRGB()+","+strokeWidth.getLineWidth()+","+angle+","+p1.x+","+p1.y;

    }

    @Override
    public void setEnd(Point point) {
        p1 = point;
    }

    public Point getMin(){
        return new Point(Math.min(p.x,p1.x),Math.min(p.y,p1.y));
    }

    public Point size(){
        return new Point(Math.abs(p.x-p1.x),Math.abs(p.y-p1.y));
    }

    public Point centre(){
        return new Point(getMin().x+size().x/2,getMin().y+size().y/2);
    }
}
