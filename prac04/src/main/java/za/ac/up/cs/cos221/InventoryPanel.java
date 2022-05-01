package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;   
public class InventoryPanel extends JPanel {
    public InventoryPanel(Database db){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String query = "SELECT "
        +     " `film`.`title` as 'Title',"
        +     " CONCAT(city,',', country) as Store,"
        +     " `inventory`.`last_update`"
        + " FROM `u21452832_sakila`.`inventory`"
        + " INNER JOIN film"
        + " ON film.film_id = inventory.film_id"
        + " INNER JOIN store"
        + " ON inventory.store_id = store.store_id"
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
