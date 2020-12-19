package Main;

import Menu.*;
import Shape.*;
import Shape.Shape;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class MouseListeners extends MouseAdapter {//adapter class for receiving mouse events within the canvas.
    private Controls controls;
    private ToolBar toolBar;//to hold the controls and the toolbar
    private Point mouse;
    private Point cursor,clicked;//pointer to hold when the mosue was clicked, current mosue location
    private Shape drawingCurrent;//to hold the current drawing shape.
    private int reSizing;//if the user is re-sizing which node are they re-sizing.
    private boolean rotating;//i f the user is rotation,
    private double oldangle;//store theold angle for record

    MouseListeners(Controls controls,ToolBar toolBar) {
        this.controls = controls;
        this.toolBar = toolBar;
        reSizing = 0;
        rotating =false;//set up the default properties.

    }

    @Override
    public void mousePressed(MouseEvent e) {//if the mouse is pressed,
        mouse = e.getPoint();
        clicked = mouse;//store the point at which the mosue was pressed
        if (controls.getTool() == Tool.select) {//if the select tool was enabled.
            drawingCurrent = controls.getDrawing().shapeAt(mouse);//chekc if theres an existing shape present at tht point, if not will have null
            if (((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0) && (drawingCurrent ==null || !controls.getSelected().contains(drawingCurrent) )) {
               //if shit is not pressed, and either theres no shape present or the shape currently clicked is not in the selected list.
                controls.clearSelection();//so clear the selected lists
            }

            if ((drawingCurrent != null) && (!controls.getSelected().contains(drawingCurrent))) {
                //if the shape is not null, and the shape selected wasnt on the list before.
                toolBar.setFill(drawingCurrent.isFill());//in the toolbar, set the fill property as the smae as the selected tool.
                drawingCurrent.setSelected(true);
                controls.getSelected().add(drawingCurrent);//now clasifiy the @drawingCurrent shape as selected and add to the list.
            }
            if ((drawingCurrent != null) && (controls.getSelected().contains(drawingCurrent)) && controls.getSelected().size()==1) {
                //if only one shape is selected.
                if ((reSizing = drawingCurrent.reSizing(mouse)) != 0) controls.reSize(mouse,mouse,reSizing);//if the usre is re-sizing a node, make a new action.
                if ((rotating = drawingCurrent.rotating(mouse))) {
                    //if the user is rotating, set the boolean as true, and begin the rotating aciion.
                    double angle = Math.atan2(drawingCurrent.centre().y - mouse.y, drawingCurrent.centre().x - mouse.x) - Math.PI / 2;
                    clicked = drawingCurrent.centre();//store for usage when the mosue is releaesd and recording the total rotation
                    //by passing the ange beetween the original rotaion node and the new mouse point
                    oldangle = drawingCurrent.getAngle();
                    controls.rotate(drawingCurrent.getAngle(), angle);//make a new rotation action.

                }
            }
            controls.getDrawing().repaint();//update the drawing

        }
        else controls.clearSelection();//if the tool is not select of the shape is null clear selection.

        if (controls.getTool() == Tool.line) {// if the active tool is a line
            drawingCurrent = new Line(mouse,toolBar.getColor(),controls.getBasicStroke(),0.0f);//make a new instance of a line
            //with info from the tool bar and controls which holds the stroke width from the menu bar.
            controls.addShape(drawingCurrent);//add the shape to the canvas
        }
        if (controls.getTool() == Tool.square) {
            drawingCurrent = new Square(mouse,toolBar.getColor(), controls.getBasicStroke(),toolBar.getFill(),0.0f);
            //make a new instance of a square
            //with info from the tool bar and controls which holds the stroke width from the menu bar.
            controls.addShape(drawingCurrent);//add the shape to the canvas
        }
        if (controls.getTool() == Tool.circle) {
            drawingCurrent = new Circle(mouse,toolBar.getColor(), controls.getBasicStroke(),toolBar.getFill(),0.0f);
            //make a new instance of a circle
            //with info from the tool bar and controls which holds the stroke width from the menu bar.
            controls.addShape(drawingCurrent);//add the shape to the canvas
        }
        if (controls.getTool() == Tool.triangle) {
            drawingCurrent = new Triangle(mouse,toolBar.getColor(),controls.getBasicStroke(),toolBar.getFill(),0.0f);
            //make a new instance of a trinagle
            //with info from the tool bar and controls which holds the stroke width from the menu bar.
            controls.addShape(drawingCurrent);//add the shape to the canvas
        }
        if (controls.getTool() == Tool.text) {
           TextDialogue t =  new TextDialogue();// make a new text dialogue
           if (t.getText()!= null) {
               drawingCurrent = new Texts(mouse, toolBar.getColor(), t.getText(), t.getFont(),0.0f);
               //make a new instance of a text
               //with info from the tool bar and the Text dialogue which holds the string info and the fonts.
               controls.addShape(drawingCurrent);//add the shape to the canvas
           }
        }
        if (controls.getTool() == Tool.image) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter filter = new FileNameExtensionFilter("Image files", "png");
            fc.addChoosableFileFilter(filter);
            fc.setFileFilter(filter);//opne a file chooser and allow the user to only open .png files.
            int x = fc.showOpenDialog(null);

            if (x == JFileChooser.APPROVE_OPTION) {//if the user clicks open.
                File f = fc.getSelectedFile();//get the selected file
                if (f!=null) {//if the file is not null.
                    Image image = new ImageIcon(f.getAbsolutePath()).getImage();//get the image.
                    drawingCurrent = new png(mouse, image,0.0f);
                    //make a new png shape
                    controls.addShape(drawingCurrent);//add the shape to the canvas
                }
            }
        }
        controls.getDrawing().repaint();//update the drawing.
        cursor = e.getPoint();//keep track of the mouse point
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursor = e.getPoint();//keep track of the mouse point
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (controls.getTool() == Tool.select) {//if the active tool is selec
            if (rotating) {//if the user is rotating a shape.
                double angle = Math.atan2(drawingCurrent.centre().y - e.getY(), drawingCurrent.centre().x - e.getX()) - Math.PI / 2;//calculte the angle between the current
                //rotation node and the current point
                controls.rotate(drawingCurrent.getAngle(), angle);//use that to rotate. but do not add it to the stack, as mentioned in the controls.
            }
            else if (reSizing != 0) {//if the user is re sizing a shape by dragging a node.
                controls.reSize(clicked,e.getPoint(),reSizing);//execute re size by making a resize action, but dont add it to stack.
            }
            else {
                Point p = new Point( e.getPoint().x - cursor.x, e.getPoint().y - cursor.y);//if not then they are moving 0 or more shapes.
                controls.moveShape(p);//call the move function to move by change in x, y. if no shape is selected nothing will happen. if ther are
                //shapes they will be moved.
            }
        }
//
        if (controls.getTool() == Tool.line || controls.getTool() == Tool.square || controls.getTool() == Tool.circle || controls.getTool() == Tool.triangle) {
            //for line, square, circle, triangle
            if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK) {
                //if shift is down
                int dy = drawingCurrent.getP().y +Math.abs(drawingCurrent.getP().x - e.getX());//they are drawing
                //perf. circle, perf. square, horiz. line, equilateral triangle. by varying y by the equal value of x.
                if (controls.getTool() == Tool.line) dy =mouse.y;//if its a line keep y const.
                drawingCurrent.setEnd(new Point(e.getX(),dy));//set the end point.
            }
            else drawingCurrent.setEnd(e.getPoint());//if shift is off, set the end point as the mouse point
        }
        controls.getDrawing().repaint();//updat the drawing
        cursor = e.getPoint();//keep track of the mouse point
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point mouseRel = e.getPoint();//when the mouse is released get the mosue location.
        if (controls.getTool() == Tool.select&&!controls.getSelected().isEmpty()) {//if the tool is select and the list of selected shape is not emepty
            if (rotating) {//if the user is rotating
                double angle = Math.atan2(drawingCurrent.centre().y - clicked.y, drawingCurrent.centre().x - clicked.x) - Math.PI / 2;
                //this angle, records the total angle between the rotation
                controls.recordRotate(oldangle, angle);//make a new action for this angle. without executing this action, add this action to the undo stack
                rotating = false;//set rotating boolean to false
            }
            else if (reSizing!=0) controls.recordReSize(clicked,e.getPoint(),reSizing);//if resizing, record the clicked and the released actions as a new re size action
            else {//else if the shape has been moved by more then 2 pixels in either direction record the movement, since there are accidental movements.
                Point p = new Point(mouseRel.x-clicked.x,mouseRel.y-clicked.y);
                if (Math.abs(p.x)>2||Math.abs(p.y)>2) controls.recordMovement(p);
            }
        }
        reSizing = 0;//set resing to 0
        if (controls.getTool() == Tool.line || controls.getTool() == Tool.square || controls.getTool() == Tool.circle || controls.getTool() == Tool.triangle) {
            //for line, square, circle, triangle
            if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK) {
                //if shift is down
                int dy = drawingCurrent.getP().y + Math.abs(drawingCurrent.getP().x - e.getX());//they are drawing
                //perf. circle, perf. square, horiz. line, equilateral triangle. by varying y by the equal value of x.
                if (controls.getTool() == Tool.line) dy = mouse.y;//if its a line keep y const.
                drawingCurrent.setEnd(new Point(e.getX(),dy));//set the end point.
            }
            else drawingCurrent.setEnd(mouseRel);//if shift is off, set the end point as the mouse point
        }
        controls.getDrawing().repaint();//updat the drawing
        cursor = e.getPoint();//keep track of the mouse point

    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    private class TextDialogue extends JDialog implements ActionListener, ChangeListener {//a text dialogue to get the text from the user as well as font and size.
        String string;//and the string to draw on the canvas
        Font font;//fied to hold the users font
        JComboBox fonts;
        JSpinner fontSize;
        JTextField example,input;
        JButton ok, cancel;//component for the text JDialoque

        public TextDialogue() {
            super( controls.getGui(),"Input text", true);//instantiate the dialogue
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));//set the box layout

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontList = ge.getAvailableFontFamilyNames();//get the list of available fonts.

            JPanel jp = new JPanel();
            jp.add(new JLabel("Font"));
            fonts = new JComboBox(fontList);//mke a combobox of fonts and add to the dioalg
            jp.add(fonts);
            this.add(jp);
            jp = new JPanel();
            jp.add(new JLabel("Font Size"));
            fontSize = new JSpinner(new SpinnerNumberModel(12, 6, 96, 1));
            jp.add(fontSize);//add Jspiinner of increasing font sizes.
            this.add(jp);

            example = new JTextField("Example");
            example.setHorizontalAlignment(SwingConstants.CENTER);
            example.setFont(new Font("sanserif", Font.PLAIN, 28));
            example.setEditable(false);
            example.setPreferredSize(new Dimension(200,50));//add an example text field to show a live preview.

            ok = new JButton("Apply");
            cancel = new JButton("Cancel");
            ok.setPreferredSize(cancel.getPreferredSize());//make 2 new buttons.

            input = new JTextField("Example");
            input.setHorizontalAlignment(SwingConstants.CENTER);
            input.setPreferredSize(new Dimension(100,50));//a text field to input users text
            input.getDocument().addDocumentListener(new DocumentListener() {//a documnet listener to get changes in text change events.
                @Override
                public void insertUpdate(DocumentEvent e) { update(); }
                @Override
                public void removeUpdate(DocumentEvent e) { update(); }
                @Override
                public void changedUpdate(DocumentEvent e) { update(); }
                //an update method is called to record any changes.
            });
            ok.addActionListener(this);
            cancel.addActionListener(this);
            input.addActionListener(this);
            fonts.addActionListener(this);
            fontSize.addChangeListener(this);//add action and change listenrs for the comps.
            this.add(input);
            this.add(example);
            this.add(cancel);
            this.add(ok);//add to the dialoquel.
            this.setPreferredSize(new Dimension(500,500));
            this.setMinimumSize(new Dimension(500,500));
            this.setVisible(true);//set it visible.

        }
        public Font getFont() {
            return this.font;
        }

        public int getFontSize() { return (Integer) fontSize.getValue(); }

        public String getText() {
            return this.string;
        }


        public void update() {
            string = input.getText();//get the text form the text field,
            font = new Font((String) fonts.getSelectedItem(), Font.PLAIN, getFontSize());// make a new font from the font name and font size.
            example.setFont(font);
            example.setText(string);//update the preview text field.
        }


        @Override
        public void actionPerformed(ActionEvent e) {//listne for any actions.
            Object source = e.getSource();

            if (source == ok) {//if ok button is pressed
                update();//update and close
                this.setVisible(false);
            }

            else if (source == cancel) {
                //if cancel, no need to update close. set the string to null, so on the top the shape is not added.
                string = null;
                this.setVisible(false);
            }else {
                update();//if any other cahnges ahppen update
            }

        }

        @Override
        public void stateChanged(ChangeEvent e) {
            update();//if font size changes update.
        }
    }
}
