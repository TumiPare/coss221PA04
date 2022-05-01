package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.JPanel;   
import javax.swing.JTable;   
import javax.swing.JScrollPane;  
import javax.swing.JTextField;
import javax.swing.JButton; 

public class StaffPanel extends JPanel {
    String driver = "jdbc:mariadb";
    String host = "localhost";
    int port = 3307;
    String database = "u21452832_sakila";
    String username = "admin";
    String password = "luffy";
    public StaffPanel(){
        JTextField t1;  
        t1=new JTextField("Welcome to Javatpoint.");  
        t1.setBounds(50,100, 200,30);  
        this.add(t1);
        Database db = new Database(driver,host,port, database,username,password);
        String query = "SELECT first_name, last_name,address,address2,district,city,postal_code, store_id,CONCAT(city,',', country) as Store"
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
        // JTextArea ta=new JTextArea(200,200);   
        // this.add(ta); 
    }
   
    
}
