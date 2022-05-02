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
     
        JPanel mainPnl = new JPanel();
        mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.PAGE_AXIS));
        String[] labels = {"Title: ", "Description: ", "Release Year: ", "Language: ",
                            "Rental Duration: ", "Rental Rate: ","Length: ","Cost: ",
                            "Rating: ", "Last Update: "};
                            
 
        for (int i = 0; i < labels.length; i++) {
            JPanel pnlRow = new JPanel();
            pnlRow.setLayout(new BoxLayout(pnlRow, BoxLayout.LINE_AXIS));
            JLabel label = new JLabel(labels[i]);
            JTextField textField = new JTextField(10);
            label.setPreferredSize(new Dimension(100,30));
            pnlRow.add(label);
            pnlRow.add(textField);
            mainPnl.add(pnlRow);
            label.setLabelFor(textField);       
        }

        this.add(mainPnl);
        this.pack();
    }
}
