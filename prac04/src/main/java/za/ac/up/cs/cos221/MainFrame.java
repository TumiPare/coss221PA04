package za.ac.up.cs.cos221;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
public class MainFrame extends JFrame {
    public MainFrame(Database db){
        this.setSize(1000,500);  
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.setBounds(50,50,200,200);  
        StaffPanel staff = new StaffPanel(db);
        FilmsPanel films = new FilmsPanel(db);
        InventoryPanel inventory = new InventoryPanel(db);
        ClientsPanel clients = new ClientsPanel(db);
        tabbedPane.add("Staff", staff);
        tabbedPane.add("Films", films);  
        tabbedPane.add("Inventory",inventory);
        tabbedPane.add("Clients", clients);
        this.add(tabbedPane);
        this.pack();
    }
    
}
