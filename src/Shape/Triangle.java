package Shape;
import java.awt.*;

public class Triangle extends Shape {
    public Triangle(Point point, Color color, BasicStroke strokeWidth, Boolean fill, double angle) {
        super(point, color, strokeWidth, fill, angle);
        p1 = point;
        p2 = point;//unlike other shapes triangle only need 3 nodes instaed of 4
    }

    @Override
    public void setP(Point p) {
        this.p = p;
    }

    public void setP1(Point p1) {
        this.p1 = p1;

    }

    public void setP2(Point p2) {
        this.p2 = p2;

    }

    @Override
    public void setP3(Point latest) {

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D graphics = (Graphics2D)g.create();
        ((Graphics2D) graphics).setStroke(strokeWidth);
        ((Graphics2D) graphics).rotate(angle,centre().x,centre().y);
        graphics.setColor(color);
        if (fill) graphics.fillPolygon(new int[]{p.x,p1.x,p2.x}, new int[]{p.y, p1.y, p2.y},3);//draw the triangle
        else {
            graphics.drawPolygon(new int[]{p.x,p1.x,p2.x}, new int[]{p.y, p1.y, p2.y},3);
        }
        if (isSelected()) {
            ((Graphics2D) graphics).setStroke(new BasicStroke(1.5f));
            graphics.setColor(new Color(0,150,255));
            graphics.drawPolygon(new int[]{p.x,p1.x,p2.x}, new int[]{p.y, p1.y, p2.y},3);//highlight the shape
            graphics.fillOval(p.x-4,p.y-4,10,10);
            graphics.fillOval(p1.x-4,p1.y-4,10,10);
            graphics.fillOval(p2.x-4,p2.y-4,10,10);
            rotate = new Point(p.x,getMin().y-60);
            graphics.drawLine(p.x,(p1.y+p.y)/2, rotate.x, rotate.y);
            graphics.drawImage(image,rotate.x-15, rotate.y-15, 30,30,null);
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));
        }
        graphics.dispose();
    }
    public Point getMin(){
        return new Point(Math.min(p.x,p2.x),Math.min(p.y,p2.y));
    }

    @Override
    public Point size() {
        return new Point(Math.abs((p.x-p2.x)*2),Math.abs(p.y-p2.y));
    }


    @Override
    public void move(int x, int y) {
        p.x += x;
        p.y += y;
        p1.x += x;
        p1.y += y;
        p2.x += x;
        p2.y += y;
    }

    @Override
    public boolean withIn(Point point) {
        /* Calculate area of triangle ABC */
        double A = area (p.x, p.y, p1.x, p1.y, p2.x, p2.y);

        /* Calculate area of triangle PBC */
        double A1 = area (point.x, point.y, p1.x, p1.y, p2.x, p2.y);

        /* Calculate area of triangle PAC */
        double A2 = area (p.x, p.y, point.x, point.y, p2.x, p2.y);

        /* Calculate area of triangle PAB */
        double A3 = area (p.x, p.y, p1.x, p1.y, point.x, point.y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3) || (reSizing(point)>0) || rotating(point);

    }//similar to other shapes but only for 3 trinagles within the triangle



    @Override
    public int reSizing(Point point) {
        Point n = new Point ((int)((p.x-centre().x)*Math.cos(angle) - (p.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p.x-centre().x)*Math.sin(angle) + (p.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n1 = new Point ((int)((p1.x-centre().x)*Math.cos(angle) - (p1.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p1.x-centre().x)*Math.sin(angle) + (p1.y-centre().y)*Math.cos(angle)+ centre().y));
        Point n2 = new Point ((int)((p2.x-centre().x)*Math.cos(angle) - (p2.y-centre().y)*Math.sin(angle)+ centre().x),
                (int)((p2.x-centre().x)*Math.sin(angle) + (p2.y-centre().y)*Math.cos(angle)+ centre().y));
        if (n.distance(point)<=16) return 1;
        if (n1.distance(point)<=16) return 2;
        if (n2.distance(point)<=16) return 3;
        return 0;
    }//for 3 nodes insted

    @Override
    public String toString() {
        return "Triangle,"+p.x+","+p.y+","+color.getRGB()+","+strokeWidth.getLineWidth()+","+fill+","+angle+","+p2.x+","+p2.y+","+p3.x+","+p3.y;
    }

    public void setEnd(Point point) {
        p2 = point;
        p1 = new Point(p.x+p.x-p2.x,p2.y);
    }

    public Point centre(){ return new Point(getMin().x,(p.y+p2.y)/2); }

}

