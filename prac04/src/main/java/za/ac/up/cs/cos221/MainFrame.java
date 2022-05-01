package za.ac.up.cs.cos221;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
public class MainFrame extends JFrame {
    public MainFrame(){
        this.setSize(400,400);  
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.setBounds(50,50,200,200);  
        StaffPanel staff = new StaffPanel();
        tabbedPane.add("Films", new JPanel());
        tabbedPane.add("Staff", staff);
        tabbedPane.add("Inventory",new JPanel());
        tabbedPane.add("Clients", new JPanel());
        this.add(tabbedPane);
    }
    
}
