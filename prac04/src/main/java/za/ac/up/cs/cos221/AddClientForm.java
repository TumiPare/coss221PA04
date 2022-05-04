package za.ac.up.cs.cos221;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComponent;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AddClientForm extends JDialog implements ActionListener{
    private boolean add = false;
    private boolean edit = false;
    private JPanel mainPnl;
    private JButton btnSave = new JButton("Save");
    private Database db;
    public AddClientForm(JFrame parent,Database db){
        super(parent,"Enter data",true);
        this.db = db;
        add = true;
        edit = false;
        
        String[] labels = { "First Name: ", "Last Name: ", "Email: ","Store: ",
                            "Active: ", "Membership Since: ","Address: ","District: ", "Postal_code", "Tel Num:"};
        mainPnl = db.createForm(labels);
        // mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.PAGE_AXIS));
        this.add(mainPnl);
        btnSave.addActionListener(this);
        mainPnl.add(btnSave);
 
        this.pack();
    }
    public AddClientForm(JFrame parent,Database db,String fields[]){
        super(parent,"Enter data",true);
        add = false;
        edit = true;
    
        String[] labels = { "First Name: ", "Last Name: ", "Email: ","Store: ",
            "Active: ", "Membership Since: ","Address: ","District: ", "Postal_code", "Tel Num:"};
        mainPnl = db.createForm(labels,fields);
        this.add(mainPnl);
        btnSave.addActionListener(this);
        mainPnl.add(btnSave);
        this.pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSave){
            String[] fields = getFields();
            if(validForm(fields)){
                if(add){
                    db.addClient(fields);
                }
                else if(edit){
                    db.editClient(fields);
                }
            }
            else{
                JOptionPane.showMessageDialog(mainPnl, "Please fill in all fields");
            }
        }   
    }
    private boolean validForm(String[] fields){
        for(int i = 0; i<fields.length;i++){
            if(fields[i].isEmpty()){
                return false;
            }
        }
        return true;
    }
    private String[] getFields(){
        String[] fields = new String[10];
        int i = 0;
        for (Component c : mainPnl.getComponents()) {
            if(c instanceof JPanel){
                for (Component cj : ((JPanel)c).getComponents()) {
                    if (cj instanceof JTextField) {
                        fields[i] = ((JTextField)cj).getText();
                        System.out.println(fields[i]);
                        i++;
                    }
                }
            }  
        }
        return fields;
    }
    
}
