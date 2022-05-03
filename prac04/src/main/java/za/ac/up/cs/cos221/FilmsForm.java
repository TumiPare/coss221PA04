// Getting input from the user to add a new item to the database
// This is called from FilmsPanel.
package za.ac.up.cs.cos221;

import javax.swing.*;
// import javax.swing.SpringLayout;
// import java.awt.Frame;

// import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class FilmsForm extends JDialog implements ActionListener {
    private JButton btnForm = new JButton("Submit");
    private JLabel heading;
    private JLabel lTitle;
    private JTextField iTitle;

    private JLabel lDescription;
    private JTextField iDescription;

    private JLabel lRelease;
    private JTextField iRelease;

    private JLabel lLanguage;
    private JTextField iLanguage;
    private JLabel loLanguage;
    private JTextField ioLanguage;

    private JLabel lRental;
    private JTextField iRental;

    private JLabel lRate;
    private JTextField iRate;

    private JLabel lLenght;
    private JTextField iLenght;

    private JLabel lCost;
    private JTextField iCost;

    private JLabel lRating;
    private JComboBox<String> iRating;

    private JCheckBox [] ifeatures;
    private Database db;
    public FilmsForm(JFrame parent,Database db){
      // This all builds the visual form and sets the DB to a class variable.
      super(parent,"Enter data",true);
      this.db = db;
      this.setLayout(null);
      this.setPreferredSize(new Dimension(300,560));
      this.setSize(300,400);

      heading = new JLabel("Add a rental");
      heading.setSize(300,30);
      heading.setLocation(100,30);
      this.add(heading);

      lTitle = new JLabel("Title");
      lTitle.setLocation(0, 100);
      lTitle.setSize(100,20);
      this.add(lTitle);

      iTitle = new JTextField();
      iTitle.setLocation(100, 100);
      iTitle.setSize(190, 20);
      this.add(iTitle);

      lDescription = new JLabel("Description");
      lDescription.setLocation(0,130);
      lDescription.setSize(100,20);
      this.add(lDescription);

      iDescription = new JTextField();
      iDescription.setLocation(100,130);
      iDescription.setSize(190,20);
      this.add(iDescription);

      lRelease = new JLabel("Release year: ");
      lRelease.setLocation(0,160);
      lRelease.setSize(100,20);
      this.add(lRelease);

      iRelease = new JTextField();
      iRelease.setLocation(100,160);
      iRelease.setSize(190,20);
      this.add(iRelease);

      lLanguage = new JLabel("Language: ");
      lLanguage.setLocation(0,190);
      lLanguage.setSize(100,20);
      this.add(lLanguage);

      iLanguage = new JTextField();
      iLanguage.setLocation(100,190);
      iLanguage.setSize(190,20);
      this.add(iLanguage);

      lRental = new JLabel("Rental Duration(days): ");
      lRental.setLocation(0, 220);
      lRental.setSize(100,20);
      this.add(lRental);

      iRental = new JTextField();
      iRental.setLocation(100,220);
      iRental.setSize(190,20);
      this.add(iRental);

      lRate = new JLabel("Rental Rate(number): ");
      lRate.setLocation(0,250);
      lRate.setSize(100,20);
      this.add(lRate);

      iRate = new JTextField();
      iRate.setLocation(100,250);
      iRate.setSize(190,20);
      this.add(iRate);

      lLenght = new JLabel("Length(minutes): ");
      lLenght.setLocation(0,280);
      lLenght.setSize(100,20);
      this.add(lLenght);

      iLenght = new JTextField();
      iLenght.setLocation(100,280);
      iLenght.setSize(190,20);
      this.add(iLenght);


      lCost = new JLabel("Cost(number): ");
      lCost.setLocation(0,310);
      lCost.setSize(100,20);
      this.add(lCost);

      iCost = new JTextField();
      iCost.setLocation(100,310);
      iCost.setSize(190,20);
      this.add(iCost);


      String [] rateoptions = {"G","PG","PG-13", "R", "NC-17"};
      lRating = new JLabel("Rating: ");
      lRating.setLocation(0,340);
      lRating.setSize(100,20);
      this.add(lRating);

      iRating = new JComboBox<>(rateoptions);
      iRating.setLocation(100,340);
      iRating.setSize(190,20);
      this.add(iRating);

      loLanguage = new JLabel("Original Language: ");
      loLanguage.setLocation(0,370);
      loLanguage.setSize(100,20);
      this.add(loLanguage);

      ioLanguage = new JTextField();
      ioLanguage.setLocation(100,370);
      ioLanguage.setSize(190,20);
      this.add(ioLanguage);

      String [] features = { "Trailers", "Commentaries", "Deleted Scenes",
	"Behind the Scenes"};
      ifeatures = new JCheckBox[features.length];
      for (int count = 0; count < features.length; count++) {
	ifeatures[count] = new JCheckBox(features[count]);
	ifeatures[count].setLocation(150*(count%2),400+(30*(int)Math.floor(count/2)));
	ifeatures[count].setSize(150,20);
	this.add(ifeatures[count]);
      }

      btnForm.addActionListener(this);
      btnForm.setLocation(100,490);
      btnForm.setSize(100,40);
      this.add(btnForm);
      this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// handler for the button click event.
        if(arg0.getSource()==this.btnForm){
	  try {
	    if (this.iTitle.getText().isEmpty()) {
	      throw new Exception("You need a title");
	    }
	    if (this.iLanguage.getText().isEmpty()) {
	      throw new Exception("You must have a language");
	    }
	    int year, duration, length;
	    Double rate, cost;
	    if (this.iRelease.getText().length() > 0) {
	      year = Integer.parseInt(this.iRelease.getText());
	      if (!(year >= 1000 && year <=9999)) {
		throw new Exception("We don't have a time machine :/ please enter a valid year for a movie release date"); } } else {year = -0;}
	    if (this.iRental.getText().length() > 0) {
	      duration = Integer.parseInt(this.iRental.getText());
	    } else {duration = 3;}
	    if (this.iRate.getText().length() > 0) {
	      rate = Double.parseDouble(this.iRate.getText());
	    } else {rate = 4.99;}
	    if (this.iLenght.getText().length() > 0) {
	      length = Integer.parseInt(this.iLenght.getText());
	    } else {length = -0;}
	    if (this.iCost.getText().length() > 0) {
	      cost = Double.parseDouble(this.iCost.getText());
	    } else {cost = 19.99;}
	    // this code is trash but it works very well and it does what it
	    // needs to do. I don't feel like linked lists right now.
	    int extcount = 0;
	    for (int count = 0; count < this.ifeatures.length; count++) {
	      if (this.ifeatures[count].isSelected()) {
		extcount++;
	      }
	    }
	    String[] features = new String[extcount];
	    extcount = 0;
	    for (int count = 0; count < this.ifeatures.length; count++) {
	      if (this.ifeatures[count].isSelected()) {
		features[extcount] = ifeatures.toString();
		extcount++;
	      }
	    }
	    boolean worked = db.insertFilm(this.iTitle.getText(), this.iDescription.getText(),
		year, this.iLanguage.getText(), duration, rate, length, cost,
		this.iRating.getItemAt(this.iRating.getSelectedIndex()), this.ioLanguage.getText(),
		features);
	    if (!worked) {
	    JOptionPane.showMessageDialog(this, "Something whent wrong while trying to add the entry to the database.... Sorry for that..",
                                   "ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	  } catch(NumberFormatException e) {
	    JOptionPane.showMessageDialog(this, "Please check that fields that must be numbers are numbers.",
                                   "ERROR", JOptionPane.ERROR_MESSAGE);
	  } catch(Exception e) {
	    JOptionPane.showMessageDialog(this, e.getMessage(),
                                   "ERROR", JOptionPane.ERROR_MESSAGE);
	  }
	}
    }
}
