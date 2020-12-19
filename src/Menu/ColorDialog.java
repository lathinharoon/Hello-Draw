package Menu;

import Menu.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorDialog extends JDialog implements ActionListener{// a colour dialogue which allows the user to choose a color.
    private JColorChooser colorChooser = new JColorChooser();
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");//buttons and color chppser
    private Color chosen;
    private Object type;//where the colour chooser is used at
    public ColorDialog(ToolBar tb) {//used at the toolbar for shapes
        this.type = tb;
        setTitle("Choose A Color");//set title
        setLayout(new BorderLayout());
        add(colorChooser,BorderLayout.CENTER);//set boder layout and add the colour chooser to the dialog
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp.add(cancel);
        jp.add(ok);//add the 2 buutons
        add(jp,BorderLayout.SOUTH);
        pack();
        setVisible(true);//make them visible
        ok.addActionListener(this::actionPerformed);
        cancel.addActionListener(this::actionPerformed);//add listeners to get the buttons actions.
        //since this class itself implements the ActionListner interface, we can handle the replies here.
    }
    public ColorDialog(MenuControl tb) {///used at the menu bar for backgorund
        this.type = tb;
        setTitle("Choose A Color");
        setLayout(new BorderLayout());
        add(colorChooser,BorderLayout.CENTER);
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp.add(cancel);
        jp.add(ok);
        add(jp,BorderLayout.SOUTH);
        pack();
        setVisible(true);
        ok.addActionListener(this::actionPerformed);
        cancel.addActionListener(this::actionPerformed);//mostly the same
    }

    public Color getChosen() {
        return chosen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {//when theres an action on the buttons
        Object o = e.getSource();
        if (o == ok) {//if its the ok button
            chosen = colorChooser.getColor();//get the color
            if (type instanceof ToolBar) ((ToolBar)type).setColor(chosen);//if its the Toolbar, set the the tool bar colour to the chosen color
            else ((MenuControl)type).getControl().getDrawing().setBackground(chosen);//else, put the backgorund color as the chosen color.
        }
        setVisible(false);//close the color chooser.

    }
}
