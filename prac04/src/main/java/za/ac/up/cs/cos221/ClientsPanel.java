package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ClientsPanel extends JPanel implements ActionListener{
    JButton btnAddClient = new JButton("Add new client");
    private Database db;
    public ClientsPanel(Database db){
        this.db = db;
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
        btnAddClient.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnAddClient){
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            FilmsForm filmsForm = new FilmsForm(parentForm, db);
            filmsForm.setVisible(true);
        }    
    }
    
}
