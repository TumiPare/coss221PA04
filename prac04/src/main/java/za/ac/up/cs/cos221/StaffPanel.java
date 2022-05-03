package za.ac.up.cs.cos221;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JPanel;   
import javax.swing.JTable;   
import javax.swing.JScrollPane;  
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton; 

public class StaffPanel extends JPanel {
    
    public StaffPanel(Database db){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JTextField t1;  
        t1=new JTextField("search...");  
        t1.setBounds(50,100, 200,30);  
        this.add(t1);
        String query = "SELECT first_name as 'First Name', last_name as 'Last Name',address as 'Address',address2 as 'Address2',district as 'District',city as 'City',postal_code as 'Postal Code', CONCAT(city,',', country) as Store"
        + " FROM staff"
        + " INNER JOIN address" 
        + " ON staff.address_id = address.address_id"
        + " INNER JOIN city"
        + " ON address.city_id = city.city_id"
        + " INNER JOIN country"
        + " ON city.country_id = country.country_id;";
        try {
            JTable table = new JTable(db.getTableModel(query));
            JScrollPane scrollPane = new JScrollPane(table);    
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
    }
   
}
