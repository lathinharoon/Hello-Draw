package Main;

import Shape.*;
import Action.*;
import Menu.*;
import Shape.Shape;

import java.awt.*;
import java.util.ArrayList;

import java.util.Stack;

public class Controls {//this class controls the majority of the canvas and the drawing mechanisms.
    private GUI gui;//gui Class which rns the code and joins all the parts of the program such as menu bar, tool bar and canvas.
    private Drawing d;//the canvas
    private ArrayList<Shape> Selected;//list of selected shapes, which are slected by the select tool.
    private Stack<DrawAction> undo,redo;//stack for undo and redo actions. undo stack holds the actions that are done.redo holds the avtion that are undone and might be redone in the future,
    private Tool tool;//enum tool to get the tools we have access to.
    private BasicStroke basicStroke = new BasicStroke(1.0f);//deafult stroke width.

    public Controls(GUI gui) {//created with the GUI object.
        this.gui = gui;
        d = null;//initially drawing s set to null, but will be later adderd.
        Selected = new ArrayList<>();
        undo = new Stack<>();
        redo = new Stack<>();//initalise the stacks and the slected shape list
        tool = Tool.select;//let the default tool the progrm strts with be the select tool
    }

    public GUI getGui() {
        return gui;
    }

    public Drawing getDrawing() {
        return d;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public BasicStroke getBasicStroke() {
        return basicStroke;
    }

    public void setBasicStroke(BasicStroke basicStroke) {
        this.basicStroke = basicStroke;
    }

    public void newDrawing(Dimension dimension) {//making a new canvas, at first from the GUI, then if the user opens a new Canavs, from the Menu.
        d = new Drawing(dimension);//make a new canavs with the user defined size
        Selected = new ArrayList<>();
        undo = new Stack<>();
        redo = new Stack<>();//re initialise the stacks and the arraylists.
        gui.updateDrawing(d);//update the gui with the new canvas to dispaly.
    }

    //public void addAction(DrawAction action) {
//        undo.push(action);
//    }

    public void undo() {//undo process.
        if (!undo.empty()) {//if the stcks not empty
            redo.push(undo.peek());//get the latest action and push it into the redo stack
            DrawAction a =  undo.pop();
            a.undo();//then take the action and do the inverse of the function, refer to the Draw action interface.
        }
    }

    public void redo() {
        if (!redo.empty()) {//if redo is not empty,
            DrawAction a = redo.pop();//get the latest action that was undone
            undo.push(a);//push it into the undo stack.
            a.redo();//re execute that action.
        }
    }

    public void addShape(Shape s) {//this action adds a new shape into the canvas
        DrawAction add = new add(d, s);
        add.execute();//performs the action
        undo.push(add);//pushes into the stack
    }


    public void moveShape(Point p) {//this function moves the slected shapes from the orignal position to a new location
        DrawAction move = new move(Selected, p);//instantiates the action,
        move.execute();//executes the action, but doesnt add it to the stack since, adding each frame of movement is useless,
        //the next methd will insted of executing will make a new action for the whole movement and add it to the list instead.
        //without executing
    }
    public void recordMovement(Point p) {
        DrawAction move = new move(Selected, p);
        undo.push(move);//no execute function. jut push
    }

    public void reSize(Point p, Point p1, int node) {//this function resizes the selceted shape, at a given node.
        for (Shape s: Selected) {//always only one shape should be selected when resizing
            DrawAction reSize = new resize(p,p1,s,node);//pass the old point, and the new point for the node that was dragged.
            reSize.execute();//similar to movemnt dont add to the stack just execute
        }
    }
    public void recordReSize(Point p, Point p1, int node) {
        for (Shape s: Selected) {
            DrawAction re = new resize(p,p1,s,node);
            undo.push(re);
        }
    }

    public void rotate(Double old,Double angle) {//this action rotates a slected shape with the angle
        for (Shape s: Selected) {//
            DrawAction rotate = new rotate(old,angle,s);//make the new action, to rotate
            rotate.execute();//no stack puch just undo
        }
    }
    public void recordRotate(Double old,Double angle) {//record the rotation
        for (Shape s: Selected) {
            DrawAction ro = new rotate(old,angle, s);
            undo.push(ro);
        }//for all the 3 record functions, these will be called when the mouse is released and and the points will hold the start and end positions
    }

    public void deleteSelection() {//action to delete selcted shapes.
        for (Shape s: Selected) {
            DrawAction del = new delete(d,s);
            del.execute();
            undo.push(del);
        }//iterate through the list, make a new delet action for that shape, execute then push to the stack
    }

    public void selectAll() {//function to the select all the shapes and objects in the canvas.
        for (Shape s:d.getShapes()) {
            if (!s.isSelected()) {
                s.setSelected(true);
                Selected.add(s);
            }
        }//iterate through the lit of shapes in the canvas, if it s not slected, identify it as selected and add it to the list.
    }
    public void clearSelection() {//function to the clear the list of selected shapes objects in the canvas.
        for (Shape s:Selected) {
            s.setSelected(false);
        }//go through the list and tell each list as not selected
        Selected.clear();//clear the list.
    }

    public ArrayList<Shape> getSelected() {
        return Selected;
    }

    public void fillSelected(boolean fill){//action to fill all the selected shapes in the colour that the tool bar has been set.
        DrawAction f = new fill(Selected, fill);//make the fill action with the boolean and the list of sleected shapes
        f.execute();//exceute that action
        undo.push(f);//push onto the stack.
    }

    public void colorSelected(Color c) {//action to  chnage colour for  all the selected shapes in the colour that the tool bar has been set.
        DrawAction coloring = new ColorAc(Selected,c);//make the coour action with the new colour and the list of sleected shapes
        coloring.execute();//execute the action
        undo.push(coloring);//push to the stack
    }

    public Shape genShapeFromString(String line) {//generate a shape from a string used during opening a saved file.
        //cannot generate  an image shape.
        String[] s = line.split(",");
        Shape drawing = null;
        if (s[0].equals("Circle")){
            drawing = new Circle(new Point(Integer.parseInt(s[1]),Integer.parseInt(s[2])),new Color(Integer.parseInt(s[3])),new BasicStroke(Float.parseFloat(s[4])),Boolean.parseBoolean(s[5]),Double.parseDouble(s[6]));
            drawing.setEnd(new Point(Integer.parseInt(s[s.length-2]),Integer.parseInt(s[s.length-1])));
        }
        else if (s[0].equals("Line")) {
            drawing = new Line(new Point(Integer.parseInt(s[1]),Integer.parseInt(s[2])),new Color(Integer.parseInt(s[3])),new BasicStroke(Float.parseFloat(s[4])),Double.parseDouble(s[5]));
            drawing.setEnd(new Point(Integer.parseInt(s[s.length-2]),Integer.parseInt(s[s.length-1])));
        }
        else if (s[0].equals("Square")) {
            drawing = new Square(new Point(Integer.parseInt(s[1]),Integer.parseInt(s[2])),new Color(Integer.parseInt(s[3])),new BasicStroke(Float.parseFloat(s[4])),Boolean.parseBoolean(s[5]),Double.parseDouble(s[6]));
            drawing.setEnd(new Point(Integer.parseInt(s[s.length-2]),Integer.parseInt(s[s.length-1])));
        }
        else if (s[0].equals("Triangle")) {
            drawing = new Triangle(new Point(Integer.parseInt(s[1]),Integer.parseInt(s[2])),new Color(Integer.parseInt(s[3])),new BasicStroke(Float.parseFloat(s[4])),Boolean.parseBoolean(s[5]),Double.parseDouble(s[6]));
            drawing.setP2(new Point(Integer.parseInt(s[s.length-4]),Integer.parseInt(s[s.length-3])));
            drawing.setP3(new Point(Integer.parseInt(s[s.length-2]),Integer.parseInt(s[s.length-1])));
        }
        else if (s[0].equals("Text")) {
            drawing = new Texts(new Point(Integer.parseInt(s[1]),Integer.parseInt(s[2])),new Color(Integer.parseInt(s[3])),s[5],new Font(s[s.length-2],Font.PLAIN,Integer.parseInt(s[s.length-1])),Double.parseDouble(s[4]));
        }
        return drawing;
    }

}
