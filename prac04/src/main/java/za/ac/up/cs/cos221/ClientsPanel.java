package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;   
public class ClientsPanel extends JPanel {
    public ClientsPanel(Database db){
        String query = "SELECT " 
    +     " CONCAT(city,',', country) as Store,"
    +     " `customer`.`first_name` as 'First Name',"
    +     " `customer`.`last_name` as 'Last Name',"
    +     " `customer`.`email` as 'Email',"
    +     " `customer`.`address_id`,"
    +     " IF(`customer`.`active` = 1, 'YES', 'NO') as 'Active',"
    +     " `customer`.`create_date` as 'Membership Since',"
    +     " `customer`.`last_update` as 'Last Update'"
    + " FROM `u21452832_sakila`.`customer`"
    + " INNER JOIN store"
    + " ON customer.store_id = store.store_id"
    + " INNER JOIN address"
    + " ON store.store_id = address.address_id"
    + " INNER JOIN city"
    + " ON address.city_id = city.city_id"
    + " INNER JOIN country "
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
