// calls FilmsForm and shows the films in
//the database.
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
public class FilmsPanel extends JPanel implements ActionListener{
    private JButton btnForm = new JButton("Add New Data");
    private Database db;
    public FilmsPanel(Database db){
        this.db = db;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String query = "SELECT "
        +    " `film`.`title` as 'Title',"
        +    " `film`.`description` as 'Description',"
        +    " `film`.`release_year` as 'Release Year',"
        +    " `language`.`name` as 'Language',"
        +    " `film`.`rental_duration` as 'Rental Duration',"
        +    " `film`.`rental_rate` as 'Rental Rate',"
        +    " `film`.`length` as 'length',"
        +    " `film`.`replacement_cost` as 'Replacement Cost',"
        +    " `film`.`rating` as 'Rating',"
        +    " `film`.`special_features` as 'Special Feactures',"
        +    " `film`.`last_update` as 'Last Update'"
        +" FROM `film`"
        +" INNER JOIN "
        +" language "
        +" ON film.language_id = language.language_id;";
        try {
            JTable table = new JTable(db.getTableModel(query));
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
        btnForm.addActionListener(this);
        this.add(btnForm);
    }
    // handle all the actions in the form
    public void actionPerformed(ActionEvent e) {
	// if the action is comming from the button.
        if(e.getSource()==this.btnForm){
	    // make the FilmsFormVisible and pass in this windows accesstor and
	    // our database.
            JFrame parentForm = (JFrame) SwingUtilities.getWindowAncestor(this);
            FilmsForm filmsForm = new FilmsForm(parentForm, db);
            filmsForm.setVisible(true);
        }
    }
}
