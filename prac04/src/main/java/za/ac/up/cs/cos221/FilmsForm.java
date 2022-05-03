package za.ac.up.cs.cos221;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
// import javax.swing.SpringLayout;
// import java.awt.Frame;

// import java.awt.Container;
import java.awt.Dimension;
public class FilmsForm extends JDialog {
    public FilmsForm(JFrame parent,Database db){
        super(parent,"Enter data",true);    
        String[] labels = {"Title: ", "Description: ", "Release Year: ", "Language: ",
                            "Rental Duration: ", "Rental Rate: ","Length: ","Cost: ",
                            "Rating: ", "Last Update: "};
        JPanel mainPnl = db.createForm(labels);                   
        this.add(mainPnl);
        this.pack();
    }
}
