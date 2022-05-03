package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class InventoryPanel extends JPanel implements ActionListener{
    private Database db;
    private JButton btnReport = new JButton("View Consolidated Report");

    public InventoryPanel(Database db){
        this.db = db;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String query = "SELECT "
        +     " `film`.`title` as 'Title',"
        +     " CONCAT(city,',', country) as Store,"
        +     " count(title) as 'Stock'"
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
        + " GROUP BY title,Store;";
        try {
            JTable table = new JTable(db.getTableModel(query));
            JScrollPane scrollPane = new JScrollPane(table);    
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
        btnReport.addActionListener(this);
        this.add(btnReport);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnReport){
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            MovieReport movieReport = new MovieReport(parentForm, db);
            movieReport.setVisible(true);
        }    
    }
}
