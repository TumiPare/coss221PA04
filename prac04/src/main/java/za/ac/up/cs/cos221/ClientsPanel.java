package za.ac.up.cs.cos221;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.JComponent;

import java.awt.Font;
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
        JLabel heading = new JLabel("List of All Clients (Right click on client for more options)");
        heading.setFont(new Font("Serif", Font.PLAIN, 17));
        this.add(heading);
        table = new JTable(getTableModel());
        table.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane(table);    
        this.add(scrollPane);
        btnAddClient.addActionListener(this);
        this.add(btnAddClient);
    }
    private DefaultTableModel getTableModel(){
        String query = "SELECT " 
            + " `customer`.`first_name` AS 'First Name',"
            + " `customer`.`last_name` AS 'Last Name',"
            + " `customer`.`email` AS 'Email',"
            + " store1.store,"
            + " IF(`customer`.`active` = 1, 'YES', 'NO') AS 'Active',"
            + " `customer`.`create_date` AS 'Membership Since',"
            + " address.address as 'Address',"
            + " address.district as 'District',"
            + " address.postal_code as 'Postal Code',"
            + " address.phone as 'Tel Num',"
            + " `customer`.`last_update` AS 'Last Update'"
        + " FROM`customer`"
        + " INNER JOIN address"
        + " ON customer.address_id = address.address_id"
        + " INNER JOIN " 
        + " (SELECT store_id, CONCAT(city, ',', country) AS Store"
        + " FROM store"
        + " INNER JOIN address "
        + " ON store.store_id = address.address_id"
        + " INNER JOIN   city"
        + " ON address.city_id = city.city_id"
        + " INNER JOIN    country" 
        + " ON city.country_id = country.country_id) as store1"
        + " ON customer.store_id = store1.store_id;";
        System.out.println(query);
        try {
            return db.getTableModel(query);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
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
            int row = table.getSelectedRow();
            Object fn = table.getModel().getValueAt(row, 0);
            Object ln = table.getModel().getValueAt(row, 1);
            Object email = table.getModel().getValueAt(row, 2);
       
            if(db.deleteClient(fn, ln, email, this)){
                table.setModel(getTableModel());
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        }
        else if(e.getSource()==menuEdit){
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddClientForm acf = new AddClientForm(parentForm, db,getFieldsForEdit());
            acf.setVisible(true);
        }
    }
    private String[] getFieldsForEdit(){
        String fields[] = new String [10];
        int row = table.getSelectedRow();
        for(int i = 0; i<10;i++)
            fields[i] = table.getModel().getValueAt(row, i).toString();
        
        return fields;
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
