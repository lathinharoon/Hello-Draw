package Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Texts extends Shape {
    private String s;
    private Font font;

    public Texts(Point point,Color color, String s, Font font, double angle) {
        super(point,color,null,false, angle);
        this.s =s;//get the font and the string
        this.font = font;
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        int textwidth = (int)(font.getStringBounds(s, frc).getWidth());
        int textheight = (int)(font.getStringBounds(s, frc).getHeight());//get the the size of the text
        p = new Point(point.x,point.y-textheight);
        p3 = new Point(p.x+textwidth,p.y+textheight);
        p1 = point;
        p2 = new Point(p3.x,p.y);//set the nodes according to the size

    }


    @Override
    public void draw(Graphics g) {
        Graphics2D graphics = (Graphics2D)g.create();
        ((Graphics2D) graphics).rotate(angle,centre().x,centre().y);
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(font);//set the font
        graphics.setColor(color);
        graphics.drawString(s, p1.x, p1.y);//draw the string
        if (isSelected()) {
            (graphics).setStroke(new BasicStroke(1.5f));
            graphics.setColor(new Color(0,150,255));
            graphics.drawRoundRect(p.x, p.y, size().x, size().y, 5,5);
            graphics.fillOval(p.x-4,p.y-4,8,8);
            graphics.fillOval(p1.x-4,p1.y-4,8,8);
            graphics.fillOval(p2.x-4,p2.y-4,8,8);
            graphics.fillOval(p3.x-4,p3.y-4,8,8);
            rotate = new Point(centre().x,p.y-size().y/2);
            graphics.drawLine(centre().x,centre().y , rotate.x, rotate.y);
            graphics.drawImage(image,rotate.x-15, rotate.y-15, 30,30,null);
            int x = (int) ((getRotate().x-centre().x)*Math.cos(angle) - (getRotate().y-centre().y)*Math.sin(angle))+centre().x;
            int y = (int) ((getRotate().x-centre().x)*Math.sin(angle) + (getRotate().y-centre().y)*Math.cos(angle))+centre().y;
            setRotate(new Point(x,y));
        }
        graphics.dispose();
    }



    @Override
    public boolean withIn(Point point) {
        if (point.x>p.x&&point.x<p3.x&&point.y>p.y&&point.y<p3.y) return true;
        return rotating(point);
    }


    @Override
    public String toString() {
        return "Text,"+p1.x+","+p1.y+","+color.getRGB()+","+angle+","+s+","+font.getFontName()+","+font.getSize();
    }

    public Point centre(){
        return new Point((p.x+p3.x)/2,(p.y+p3.y)/2);
    }

    @Override
    public Point getMin() {
        return null;
    }

    @Override
    public Point size() {
        return new Point(p3.x-p.x,p3.y-p.y);
    }

    @Override
    public void setEnd(Point p) {

    }

    @Override
    public void setP1(Point latest) {

    }

    @Override
    public void setP2(Point latest) {

    }

    @Override
    public void setP3(Point latest) {

    }
}
