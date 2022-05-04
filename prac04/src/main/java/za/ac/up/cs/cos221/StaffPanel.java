package za.ac.up.cs.cos221;
import java.sql.SQLException;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffPanel extends JPanel implements ActionListener {
	private JButton btnForm = new JButton("Search");
        private JTable table;
        private JTextField t1;
	private Database db;
	private JScrollPane scrollPane;
    public StaffPanel(Database db){
	this.db = db;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	JLabel label = new JLabel("Search:");
	this.add(label);
        t1=new JTextField();
        t1.setBounds(50,100, 200,30);
        this.add(t1);
	btnForm.addActionListener(this);
	this.add(btnForm);
        String query = "SELECT first_name as 'First Name', last_name as 'Last Name',address as 'Address',address2 as 'Address2',district as 'District',city as 'City',postal_code as 'Postal Code', CONCAT(city,',', country) as Store"
        + " FROM staff"
        + " INNER JOIN address"
        + " ON staff.address_id = address.address_id"
        + " INNER JOIN city"
        + " ON address.city_id = city.city_id"
        + " INNER JOIN country"
        + " ON city.country_id = country.country_id;";
        try {
            table = new JTable(db.getTableModel(query));
            scrollPane = new JScrollPane(table);
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if(arg0.getSource()==this.btnForm){
	String text = t1.getText();
	try {
	  String query = "SELECT first_name as 'First Name', last_name as 'Last Name',address as 'Address',address2 as 'Address2',district as 'District',city as 'City',postal_code as 'Postal Code', CONCAT(city,',', country) as Store"
	  + " FROM staff"
	  + " INNER JOIN address"
	  + " ON staff.address_id = address.address_id"
	  + " INNER JOIN city"
	  + " ON address.city_id = city.city_id"
	  + " INNER JOIN country"
	  + " ON city.country_id = country.country_id WHERE first_name LIKE '%"
	  + text +"%' OR last_name LIKE '%" + text + "%' OR address LIKE '%"
	  + text +"%' OR address2 LIKE '%" + text + "%' OR district LIKE '%"
	  + text +"%' OR city LIKE '%" + text + "%' OR postal_code LIKE '%"
	  + text +"%' OR country LIKE '%" + text + "%';";
	  System.out.println(query);
	  table.setModel(db.getTableModel(query));
	  table.repaint();
	  this.repaint();
	} catch (Exception e) {
	  System.out.println(e);
	}
      }
    }
}
