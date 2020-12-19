package Shape;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class png extends Shape {
    private Image imageto;
    public png (Point point,Image image, double angle) {
        super(point, null, null, false, angle);//stroke width and fill is not usefull
        this.imageto = image;//store the actual image
        p1 = new Point(point.x+50,point.y);
        p2 = new Point(point.x,point.y+50);
        p3 = new Point(point.x+50,point.y+50);;//default size of the image is 50*50
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

    public void setEnd(Point point) {
        p3 = point;
        p1 = new Point(p3.x,p.y);
        p2 = new Point(p.x,p3.y);

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D graphics = (Graphics2D)g.create();
        ((Graphics2D) graphics).rotate(angle,centre().x,centre().y);
        graphics.drawImage(imageto, getMin().x, getMin().y, size().x, size().y,null);//draw an image instead
        if (isSelected()) {
            ((Graphics2D) graphics).setStroke(new BasicStroke(1.5f));
            graphics.setColor(new Color(0,150,255));
            graphics.drawRoundRect(getMin().x, getMin().y, size().x, size().y, 5,5);
            graphics.fillOval(p.x-4,p.y-4,8,8);
            graphics.fillOval(p1.x-4,p1.y-4,8,8);
            graphics.fillOval(p2.x-4,p2.y-4,8,8);
            graphics.fillOval(p3.x-4,p3.y-4,8,8);
            rotate = new Point(getMin().x+ size().x/2,getMin().y-size().y/2);
            graphics.drawLine(getMin().x+ size().x/2,getMin().y+ size().y/2, rotate.x, rotate.y);
            graphics.drawImage(this.image,rotate.x-15, rotate.y-15, 30,30,null);
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));
        }
        graphics.dispose();
    }

    @Override
    public String toString() {
        return "Image,"+p.x+","+p.y+","+angle+","+p3.x+","+p3.y;
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
