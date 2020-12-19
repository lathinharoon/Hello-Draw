package Shape;

import java.awt.*;

public class Square extends Shape {
    public Square (Point point,Color color, BasicStroke strokeWidth, Boolean fill, double angle) {
        super(point, color, strokeWidth, fill, angle);
        p1 = point;
        p2 = point;
        p3 = point;//mostly same as the circle
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        p = new Point(p.x,p1.y);
        p3 = new Point(p1.x,p3.y);
    }

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
    public void draw(Graphics g) {
        Graphics2D graphics = (Graphics2D)g.create();
        graphics.setColor(color);
        ( graphics).setStroke(strokeWidth);
        (graphics).rotate(angle,centre().x,centre().y);
        if (fill) graphics.fillRect(getMin().x, getMin().y, size().x, size().y);
        else {
            graphics.drawRect(getMin().x, getMin().y, size().x, size().y);//draw a rectangle
        }
        if (selected) {
            ((Graphics2D) graphics).setStroke(new BasicStroke(1.5f));
            graphics.setColor(new Color(0,150,255));
            graphics.drawRoundRect(getMin().x, getMin().y, size().x, size().y, 5,5);
            graphics.fillOval(p.x-4,p.y-4,8,8);
            graphics.fillOval(p1.x-4,p1.y-4,8,8);
            graphics.fillOval(p2.x-4,p2.y-4,8,8);
            graphics.fillOval(p3.x-4,p3.y-4,8,8);
            rotate = new Point(getMin().x+ size().x/2,getMin().y-size().y/2);
            graphics.drawLine(getMin().x+ size().x/2,getMin().y+ size().y/2, rotate.x, rotate.y);
            graphics.drawImage(image,rotate.x-15, rotate.y-15, 30,30,null);
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));
        }
        graphics.dispose();
    }





    @Override
    public String toString() {
        return "Square,"+p.x+","+p.y+","+color.getRGB()+","+strokeWidth.getLineWidth()+","+fill+","+angle+","+p3.x+","+p3.y;
    }

    @Override
    public void setEnd(Point point) {
        p3 = point;
        p1 = new Point(p3.x,p.y);
        p2 = new Point(p.x,p3.y);


    }

    public Point getMin(){
        return new Point(Math.min(p.x,p3.x),Math.min(p.y,p3.y));
    }

    public Point size(){
        return new Point(Math.abs(p.x-p3.x),Math.abs(p.y-p3.y));
    }

    public Point centre(){
        return new Point(getMin().x+size().x/2,getMin().y+size().y/2);
    }

}
