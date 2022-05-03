package za.ac.up.cs.cos221;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddClientForm extends JDialog{
    public AddClientForm(JFrame parent,Database db){
        super(parent,"Enter data",true);    
        String[] labels = {"Store: ", "First Name: ", "Last Name: ", "Email: ",
                            "Active: ", "Membership Since: "};
        JPanel mainPnl = db.createForm(labels);                   
        this.add(mainPnl);
        this.pack();
    }
    
}
