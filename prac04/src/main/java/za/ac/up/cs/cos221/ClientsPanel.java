package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

// import java.awt.Event;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ClientsPanel extends JPanel implements ActionListener, MouseListener{
    JButton btnAddClient = new JButton("Add new client");
    private Database db;
    private JTable table;
    private JMenuItem menuEdit = new JMenuItem("Edit Client Details");
    private JMenuItem menuDelete = new JMenuItem("Delete Client");
    JPopupMenu rightClickMenu = new JPopupMenu();
    public ClientsPanel(Database db){
        this.db = db;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setUPRightClickMenu();
        JLabel heading = new JLabel("List of All Clients");
        this.add(heading);

        String query = "SELECT " 
    +     " CONCAT(city,',', country) as Store,"
    +     " `customer`.`first_name` as 'First Name',"
    +     " `customer`.`last_name` as 'Last Name',"
    +     " `customer`.`email` as 'Email',"
    +     " `customer`.`address_id`,"
    +     " IF(`customer`.`active` = 1, 'YES', 'NO') as 'Active',"
    +     " `customer`.`create_date` as 'Membership Since',"
    +     " `customer`.`last_update` as 'Last Update'"
    + " FROM `customer`"
    + " INNER JOIN store"
    + " ON customer.store_id = store.store_id"
    + " INNER JOIN address"
    + " ON store.store_id = address.address_id"
    + " INNER JOIN city"
    + " ON address.city_id = city.city_id"
    + " INNER JOIN country "
    + " ON city.country_id = country.country_id;";
        try {
            table = new JTable(db.getTableModel(query));
            table.addMouseListener(this);
            JScrollPane scrollPane = new JScrollPane(table);    
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
        btnAddClient.addActionListener(this);
        this.add(btnAddClient);
    }
    private void setUPRightClickMenu(){
        menuEdit.addActionListener(this);
        menuDelete.addActionListener(this);
        rightClickMenu.add(menuEdit);
        rightClickMenu.add(menuDelete);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnAddClient){
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddClientForm acf = new AddClientForm(parentForm, db);
            acf.setVisible(true);
        }    
        else if(e.getSource()==menuDelete){
           
        }
        else if(e.getSource()==menuEdit){
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddClientForm acf = new AddClientForm(parentForm, db);
            acf.setVisible(true);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
       
        if(SwingUtilities.isRightMouseButton(e)){
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            table.setRowSelectionInterval(row, row);
            rightClickMenu.show(e.getComponent(), e.getX(), e.getY());    
        }
   
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // rightClickMenu.setVisible(false);
        // this.remove(rightClickMenu);
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
       
        // rightClickMenu.setVisible(false);
    }
   
    
}
