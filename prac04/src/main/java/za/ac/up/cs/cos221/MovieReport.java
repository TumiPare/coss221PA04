package za.ac.up.cs.cos221;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MovieReport extends JDialog{
    public MovieReport(JFrame parent,Database db){
        // JPanel mainPnl = new JPanel();
        super(parent,"consolidated report",true);
        String query = "SELECT "
        +     "`category`.`name` as Genre,"
        +     "CONCAT(city,',', country) as Store,"
        +     "count(title) as '#Movies'"
        + " FROM `inventory`"
        + " INNER JOIN film"
        + " ON film.film_id = inventory.film_id"
        + " INNER JOIN store"
        + " ON inventory.store_id = store.store_id"
        + " INNER JOIN address"
        + " ON store.store_id = address.address_id"
        + " INNER JOIN city"
        + " ON address.city_id = city.city_id"
        + " INNER JOIN country "
        + " ON city.country_id = country.country_id"
        + " INNER JOIN film_category "
        + " ON film.film_id = film_category.film_id"
        + " INNER JOIN category"
        + " ON category.category_id = film_category.category_id"
        + " GROUP BY Genre,Store;";
        try {
            JTable table = new JTable(db.getTableModel(query));
            JScrollPane scrollPane = new JScrollPane(table);    
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
        this.pack();
    }
    
}
