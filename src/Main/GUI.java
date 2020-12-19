package Main;

import Menu.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {//Main class which is subclass of JFrame.
    DrawingContainer drawingContainer;//Drawing Container which holds the cnavas,
    JScrollPane scrollPane;//scrollpane to make the cnavas scrollable
    Controls controls;//control object to control the app and the painting.
    ToolBar toolBar;//toolbar to access the tools
    MouseListeners mouseListener;//instance of the mouse listener for the canvas.

    public static void main(String[] args) {
        new GUI();
    }//main method makes a new GUI class.

    GUI() {
        this.setTitle("Hello Draw");//since its a subclass of Jframe, set a title for this jframe.
        ImageIcon logo = new ImageIcon("img/logo.png");//set a logo
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit on close

        drawingContainer = new DrawingContainer();//make  new drawing container for the canvas to be placed.
        scrollPane = new JScrollPane(drawingContainer);//make it scrollable.

        controls = new Controls(this);//make a new control using this GUI object.
        toolBar = new ToolBar(controls);//make a new toolbar with the controls object to do some clean up within the canvas when running.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//get the size fo the screen
        controls.newDrawing(new Dimension(d.width-150,d.height-150));//make new drawing canvas smaller then the screen
        //which will call the update drawing method below to update the gui with the canvas.
        MenuControl menuControl = new MenuControl(controls); //make a new menu bar similar to the toolbar by passing the control objct.
        //the tool bar and the menu bar are their own action listeners, who can handle the changes and the commands given by th user.

        this.setJMenuBar(menuControl);//add the menu bar to the frame
        this.add(toolBar,BorderLayout.WEST);//add the tool bar
        this.add(scrollPane, BorderLayout.CENTER);//add the scrollpane i.e. canvas to the frame
        pack();// fit it preferred size.
        setVisible(true);//make it visible.

    }
    public void updateDrawing(Drawing drawing) {//method called from the control, to update the canvas,
        drawingContainer.setUP(drawing);// call the drawing container class, set the new canvas in the centre of the jframe
        scrollPane.setPreferredSize(drawing.getPreferredSize());//set the size of the scroll pane as the same as the canvas.
        pack();//pack
        repaint();//repaint the frame. i.e. update
    }

    private class DrawingContainer extends JPanel {//private class which extends the JPanel class and will contain the Canvas
        DrawingContainer() {
            super(new GridBagLayout());
        }//make a new instance of the JPanel a layout.
        public void setUP(Drawing drawing) {//set up method called from outter class to update the drawing, and add the mouse listeners for the canvas
            this.removeAll();//remove any prev. canvas.
            this.add(drawing);// add the new canvas.
            mouseListener = new MouseListeners(controls,toolBar);// make a new instance of mouseListeners class with the controls and the tool bar.
            drawing.addMouseListener(mouseListener);
            drawing.addMouseMotionListener(mouseListener);//add the mouse listener object as a mouse listner and mouse Mouse motion listener
            //to track the movements of the mouse within the canvas.
            setPreferredSize(drawing.getPreferredSize());
            pack();//set the size for the jpanel.
        }
    }
}
