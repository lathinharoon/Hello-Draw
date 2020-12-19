package Menu;

import Main.Controls;
import Shape.*;
import Shape.Shape;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuControl extends JMenuBar implements ActionListener, ChangeListener {//the menu bar which is responsible for
    //file, edit, view menus features. has its own listeners.
    private Controls c;
    private List<Shape> copied;

    public MenuControl(Controls c) {
        this.c = c;//assign the controls objects
        JMenu file = new JMenu("File");//make a new menu file
        Image image = new ImageIcon("img/file.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        file.setIcon(new ImageIcon(image));//logo set up for menu

        image = new ImageIcon("img/newFile.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem newFile = new JMenuItem("New", new ImageIcon(image));//new file - menu item
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));//shortcut

        image = new ImageIcon("img/openFile.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem openFile = new JMenuItem("Open", new ImageIcon(image));//open file
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        image = new ImageIcon("img/saveFile.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem save = new JMenuItem("Save as", new ImageIcon(image));//save
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        image = new ImageIcon("img/export.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem export = new JMenuItem("Export as PNG", new ImageIcon(image));//export as png
        export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        image = new ImageIcon("img/close.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem exit = new JMenuItem("Exit", new ImageIcon(image));//exit
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        JMenu edit = new JMenu("Edit");
        image = new ImageIcon("img/edit.png").getImage();//menu - edit
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        edit.setIcon(new ImageIcon(image));//logo ste up

        image = new ImageIcon("img/copy.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem copy = new JMenuItem("Copy", new ImageIcon(image));//copy
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        image = new ImageIcon("img/paste.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem paste = new JMenuItem("Paste",new ImageIcon(image));//paste
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));;

        image = new ImageIcon("img/undo.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem undo = new JMenuItem("Undo", new ImageIcon(image));//undo
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

        image = new ImageIcon("img/redo.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem redo = new JMenuItem("Redo",new ImageIcon(image));//redo
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));;

        image = new ImageIcon("img/selectAll.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem selectAll = new JMenuItem("Select All",new ImageIcon(image));//select all
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));;

        image = new ImageIcon("img/delete.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem delete = new JMenuItem("Delete",new ImageIcon(image));//delete
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));;

        JMenu view = new JMenu("View");//menu - view
        image = new ImageIcon("img/view.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        view.setIcon(new ImageIcon(image));

        image = new ImageIcon("img/bg.png").getImage();
        image = image.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JMenuItem bg = new JMenuItem("Change Background", new ImageIcon(image));//change bg

        JLabel label = new JLabel("Stroke Width:");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel((1.50),(0.50),(10.00),0.50));
        spinner.setName("Stroke Width");
        spinner.setPreferredSize(new Dimension(50,10));
        spinner.setMaximumSize(new Dimension(50,20));//change stroke width

        spinner.addChangeListener(this::stateChanged);
        bg.addActionListener(this::actionPerformed);
        delete.addActionListener(this::actionPerformed);
        selectAll.addActionListener(this::actionPerformed);
        copy.addActionListener(this::actionPerformed);
        paste.addActionListener(this::actionPerformed);
        redo.addActionListener(this::actionPerformed);
        undo.addActionListener(this::actionPerformed);
        exit.addActionListener(this::actionPerformed);
        export.addActionListener(this::actionPerformed);
        save.addActionListener(this::actionPerformed);
        openFile.addActionListener(this::actionPerformed);
        newFile.addActionListener(this::actionPerformed);//add itself as either property change, or property change listener

        file.add(newFile);
        file.add(openFile);
        file.addSeparator();
        file.add(save);
        file.add(export);
        file.addSeparator();
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.addSeparator();
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(selectAll);
        edit.add(delete);

        view.add(bg);//add the respective menu items to relevent menus

        this.add(file);
        this.add(edit);
        this.add(view);
        this.add(label);
        this.add(spinner);//add all the menu and the stroke width to the menu.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Exit")) {
            System.exit(0);
        }//if exit; stop
        else if (command.equals("New")) {//if the user clicks on new
            JDialog info = new JDialog(c.getGui());//create a new JDialog
            info.setLayout(new BoxLayout(info.getContentPane(), BoxLayout.Y_AXIS));//set the layout
            info.add(new JLabel("Enter the dimension for the new template: "));//a label for the user
            JPanel jp = new JPanel();
            jp.add(new JLabel("Width"));
            JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(600, 10, 4096, 1));
            jp.add(widthSpinner);
            info.add(jp);
            jp = new JPanel();
            jp.add(new JLabel("Height"));
            JSpinner heightSpinner = new JSpinner(
                    new SpinnerNumberModel(600, 10, 4096, 1));
            jp.add(heightSpinner);
            info.add(jp);// 2 spinner to choose the height and the width
            jp = new JPanel();
            JButton cancel = new JButton("Cancel");
            JButton ok = new JButton("Ok");
            jp.add(cancel);
            jp.add(ok);
            info.add(jp);//2 buttons
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    info.setVisible(false);
                }
            });//anonymous actionlistner, if cancel is clicked, close the dialogue
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.newDrawing(new Dimension((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue()));
                    info.setVisible(false);
                }//if ok is clicked, call the newDraing method with the user defined dimensions. close the window
            });
            info.pack();
            info.setVisible(true);//set visible to true for the dialogue
        }
        else if (command.equals("Open")) {//if the user clicks on open
            JFileChooser fc = new JFileChooser();//create a file chooser
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//select only files.
            FileFilter filter = new FileNameExtensionFilter("Hello Draw files", "hd");
            fc.addChoosableFileFilter(filter);//filter to allow only .hd files.
            fc.setFileFilter(filter);
            int x = fc.showOpenDialog(null);

            if (x == JFileChooser.APPROVE_OPTION) {//if the user clcks on the open option
                File f = fc.getSelectedFile();//get the selected file
                if (f != null) {//if the file is not null.
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(f));//wrap buffered reader to the file reader of the specifed file
                        String[] s = reader.readLine().split(",");
                        Dimension d = new Dimension(Integer.parseInt(s[0]),Integer.parseInt(s[1]));//the 1st line has the dimensions and the colour.
                        c.newDrawing(d);
                        c.getDrawing().setBackground(new Color(Integer.parseInt(s[2])));//make a new drawing and set a colour.
                        String line;
                        while ((line = reader.readLine()) != null) {//iterate through each line
                            Shape temp = c.genShapeFromString(line);
                            if (temp!= null) c.addShape(temp);//call the function in controls to gen. a shape from stirng. then add the shape.
                            //this cannot save and load images
                        }
                        reader.close();//close the reader.

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        else if (command.equals("Save as")) {//if the user clicks save
            JFileChooser fc = new JFileChooser();//create a file chooser
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//select only files.
            fc.setSelectedFile(new File("temp.hd"));//set a new file with the name temp.hd as the file name for the thats going to be saved
            FileFilter filter = new FileNameExtensionFilter("Hello Draw files", "hd");
            fc.addChoosableFileFilter(filter);
            fc.setFileFilter(filter);//set filter to to allow only .hd files.
            fc.showSaveDialog(c.getGui());//show save dialogue instead of open dialogue
            File f = fc.getSelectedFile();
            if (f != null) {
                List<Shape> shapes = c.getDrawing().getShapes();//get the list of the shapes.
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(f));//open up a buffered writer to that file
                    writer.write(c.getDrawing().getDimension().width+","+c.getDrawing().getDimension().height+","+c.getDrawing().getBackground().getRGB());
                    writer.newLine();//1st write the dimension and the bg colour
                    for (Shape s:shapes) {
                        writer.write(s.toString());
                        writer.newLine();//then for each line write each shape to the list
                    }
                    writer.close();//once done close.
                } catch (FileNotFoundException ee) {
                    ee.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        else if (command.equals("Export as PNG")) {
            c.clearSelection();//clear selection so no highlighs appear on the image.
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setSelectedFile(new File("temp.png"));
            FileFilter filter = new FileNameExtensionFilter("Image File", "png");
            fc.addChoosableFileFilter(filter);
            fc.setFileFilter(filter);//allow only png files.
            fc.showSaveDialog(c.getGui());
            //reference http://www.javased.com/?post=4725320
            //https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html
            File f = fc.getSelectedFile();
            if (f != null) {
                BufferedImage img = new BufferedImage(c.getDrawing().getWidth(), c.getDrawing().getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = img.createGraphics();
                c.getDrawing().printAll(g2d);
                g2d.dispose();
                try {
                    ImageIO.write(img,"png",f);//write the image to the file
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        else if (command.equals("Copy")) {
            copied = (ArrayList<Shape>) c.getSelected().clone();
            //if copy, make a copy of the selected shapes and store them similar to a clip board. by making a clone can be used on another new drawing,
        }

        else if (command.equals("Paste")) {
            c.clearSelection();//clear any selected shape
            for (Shape s:copied) {//for each shape copied
                Shape new1 = c.genShapeFromString(s.toString());//generate a new instance using the .toString and gen form string method
                new1.move(10,10);//move the shape slightly to make it recognisabel
                c.addShape(new1);//add the sahpe to the canavas.
                new1.setSelected(true);//make it selected
                c.getSelected().add(new1);//add it to the selected list

            }
            //c.getSelected().addAll(copied);
            c.getDrawing().repaint();//update the drawing.

        }

        else if (command.equals("Undo")) {
            c.undo();//call the undo method in the the controls and then update the drawing
            c.getDrawing().repaint();

        }

        else if (command.equals("Redo")) {
            c.redo();//call the redo method
            c.getDrawing().repaint();

        }

        else if (command.equals("Select All")) {
            c.selectAll();//select all the shape in the canvas.
            c.setTool(Tool.select);//set the current tool as select tool

        }
        else if (command.equals("Delete")) {
            c.deleteSelection();//delete selected tools
        }
        else if (command.equals("Change Background")) {
            new ColorDialog(this);//opens a new colour dialogue to choose
        }
        c.getDrawing().repaint();
    }

    public Controls getControl() {
        return c;
    }

    @Override
    public void stateChanged(ChangeEvent e) {//listen to change which is invoked by the jspinnner for stroke width
        BasicStroke bs =new BasicStroke((float)(double) ((JSpinner)e.getSource()).getValue());
        for (Shape s:c.getSelected()) {
            s.setStrokeWidth(bs);
        }//go through the selected and set the weight as the current weight
        c.setBasicStroke(bs);//set the deafualt stroke as the current one.
    }
}
