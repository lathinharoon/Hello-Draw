package Menu;

import Menu.ColorDialog;
import Main.Controls;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class ToolBar extends JToolBar implements ItemListener, ActionListener {//the tool bar which is responsible for
    //tools available . has its own listeners.

    private Controls c;
    private JToggleButton select, line, square, circle, tri, text, imp;
    private JCheckBox fill;
    private boolean isfill;
    private JButton colors;//components for the toolbare
    private Color color;

    public ToolBar(Controls c) {
        super("Tools", VERTICAL);//intsatntiate a new JToolbar
        this.c = c;
        color = Color.black;//set default colour black

        Image image = new ImageIcon("img/select.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        select = new JToggleButton("", new ImageIcon(image));
        select.setToolTipText("Select and modify shapes and texts, hold shift to select multiple");
        select.addActionListener(this);//select tool buuton, having toolbar as the listener

        image = new ImageIcon("img/line.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        line = new JToggleButton("", new ImageIcon(image));
        line.setToolTipText("Draw straight lines, hold shift to draw horizontal lines");
        line.addActionListener(this);//line

        image = new ImageIcon("img/square.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        square = new JToggleButton("", new ImageIcon(image));
        square.setToolTipText("Draw squares and rectangles, hold shift to draw perfect squares");
        square.addActionListener(this::actionPerformed);//square

        image = new ImageIcon("img/circle.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        circle = new JToggleButton("", new ImageIcon(image));
        circle.setToolTipText("Draw circles and ovals, hold shift to draw perfect ovals");
        circle.addActionListener(this::actionPerformed);//circle

        image = new ImageIcon("img/tri.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        tri = new JToggleButton("", new ImageIcon(image));
        tri.setToolTipText("Draw Triangles, hold shift to draw equilateral triangles");
        tri.addActionListener(this::actionPerformed);//triangle

        image = new ImageIcon("img/text.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        text = new JToggleButton("", new ImageIcon(image));
        text.setToolTipText("Draw a Text Box");
        text.addActionListener(this::actionPerformed);//text

        image = new ImageIcon("img/import.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        imp = new JToggleButton("", new ImageIcon(image));
        imp.setToolTipText("Import a png image");
        imp.addActionListener(this::actionPerformed);//import image

        image = new ImageIcon("img/nofill.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        fill = new JCheckBox("",new ImageIcon(image));
        fill.setToolTipText("Fill");
        image = new ImageIcon("img/fill.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        fill.setSelectedIcon(new ImageIcon(image));
        fill.addItemListener(this);//fill checkkbox

        image = new ImageIcon("img/colors.png").getImage().getScaledInstance(55,55, Image.SCALE_SMOOTH);
        colors = new JButton("", new ImageIcon(image));
        colors.setToolTipText("Choose a colour to draw");
        colors.addActionListener(this::actionPerformed);//colur chooser

        add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(select);
        this.add(line);
        this.add(circle);
        this.add(tri);
        this.add(square);
        this.add(fill);
        add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(colors);
        add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(text);
        this.add(imp);//add them all to the toolbare
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            isfill = false;
        }//if the checkbox is deselcted is fill = false;
        else {
            isfill = true;
        }
        c.fillSelected(isfill);//fill or un fill all the selected shapes.
        c.getDrawing().repaint();//update

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object selection = e.getSource();
        if (selection.equals(select)) c.setTool(Tool.select);//if user clicks on the selecte tool, set the controls active tool as select
        else if (selection.equals(line)) {
            c.clearSelection();//clear any selected shapes.
            c.setTool(Tool.line);//set the active tool as line
        } else if (selection.equals(tri)) {
            c.clearSelection();
            c.setTool(Tool.triangle);
        } else if (selection.equals(square)) {
            c.clearSelection();
            c.setTool(Tool.square);
        }
        else if (selection.equals(circle)) {
            c.clearSelection();
            c.setTool(Tool.circle);
        }
        else if (selection.equals(text)) {
            c.clearSelection();
            c.setTool(Tool.text);
        }
        else if (selection.equals(colors)) {
            ColorDialog cd = new ColorDialog(this);//open a new color dialogue to choose a color
            c.colorSelected(color);
            c.getDrawing().repaint();//repaint.
        }
        else if (selection.equals(imp)) {
            c.clearSelection();
            c.setTool(Tool.image);

        }
        c.getDrawing().repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        c.colorSelected(color);//update controls
    }

    public boolean getFill() {
        return isfill;
    }

    public void setFill(boolean fill) {
        this.fill.setSelected(fill);
    }

}
