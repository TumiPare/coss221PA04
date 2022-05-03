package za.ac.up.cs.cos221;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.sql.ResultSetMetaData;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Database {
    protected Connection connection = null;

    public Database(String driver,String host,int port,String database,String username,String password)
    {
        String url = new StringBuilder()
                .append(driver).append("://")
                .append(host).append(":").append(port).append("/")
                .append(database)
                .toString();
        try{
            connection = DriverManager.getConnection(url, username, password);
	    String [] tables = {"customer", "film", "address", "language"};
	    String [] keys = {"customer_id", "film_id", "address_id", "language_id"};
	    for (int count = 0; count < tables.length; count++) {
	      int max = 0;
	      String getmax = "SELECT MAX(?) FROM !";
	      String alterTable = "ALTER TABLE ? auto_increment=!";
	      Statement prepare = this.connection.createStatement();
	      getmax = getmax.replace("?", keys[count]);
	      getmax = getmax.replace("!", tables[count]);
	      System.out.println(getmax);
	      ResultSet res = prepare.executeQuery(getmax);
	      if (res.next()) {
		max = res.getInt(1) + 1;
		System.out.println(max);
	      }
	      prepare = this.connection.createStatement();
	      alterTable = alterTable.replace("?", tables[count]);
	      alterTable = alterTable.replace("!", Integer.toString(max));
	      System.out.println(alterTable);
	      prepare.executeUpdate(alterTable);
	    }

        }
        catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }

    }

    public ResultSet select(String query)
    {
        try {
            Statement statement = connection.createStatement();
            try (ResultSet result = statement.executeQuery(query)) {
                return result;
            }
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
    }

    public PreparedStatement prepareStatement(String query)
    {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new Error("Error: " + e.getMessage());
        }
    }
    public void close(){
        try {
            if (connection != null) {
                connection.close();
            }
          } catch (SQLException ex) {
              System.out.println(ex.getMessage());
          }
    }
    public DefaultTableModel getTableModel(String query) throws SQLException{
        ResultSet rs = select(query);
        DefaultTableModel tableModel = new DefaultTableModel();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++){
            tableModel.addColumn(metaData.getColumnLabel(i));
        }

        Object[] row = new Object[columnCount];

        while (rs.next()){
            for (int i = 0; i < columnCount; i++){
                row[i] = rs.getObject(i+1);
            }
            tableModel.addRow(row);
        }
        return tableModel;
    }
    public JPanel createForm(String [] labels){
        JPanel mainPnl = new JPanel();
        mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < labels.length; i++) {
            JPanel pnlRow = new JPanel();
            pnlRow.setLayout(new BoxLayout(pnlRow, BoxLayout.LINE_AXIS));
            JLabel label = new JLabel(labels[i]);
            JTextField textField = new JTextField(10);
            label.setPreferredSize(new Dimension(100,30));
            pnlRow.add(label);
            pnlRow.add(textField);
            mainPnl.add(pnlRow);
            label.setLabelFor(textField);
        }
        return mainPnl;
    }
    public boolean insertFilm(String title, String desc, int release,
	String lang, int duration, Double rate, int length, Double cost,
	String rating, String originalLang, String[] features) {
      // This is just capitalizing the language Strings
      lang = lang.substring(0, 1).toUpperCase() + lang.substring(1);
      if (!(originalLang.isEmpty())) {
	originalLang = originalLang.substring(0, 1).toUpperCase() +
	  originalLang.substring(1);
      }
      // this is setting all the querys we could use.
      int primaryLanguageID = -1;
      int secondLanguageKey = -1;
      String languageQuery = "SELECT language_id FROM language WHERE name = ?";
      String insertLanguage = "INSERT INTO language (name) VALUES (?)";
      // this fat mess will add a language if it does not exist and if it does
      // then it will give the key. in the end we get the key either way.
      try {
	PreparedStatement prepare = this.connection.prepareStatement(languageQuery);
	prepare.setString(1, lang);
	ResultSet results = prepare.executeQuery();
	if (results.next()) {
	  primaryLanguageID = results.getInt(1);
	} else {
	  prepare = this.connection.prepareStatement(insertLanguage);
	  prepare.setString(1,lang);
	  prepare.executeUpdate();
	  results = prepare.getGeneratedKeys();
	  if (results.next()) {
	    primaryLanguageID = results.getInt(1);
	  }
	}
      } catch (Exception e) {
	return false;
      }
      if (!(originalLang.isEmpty())) {
	try {
	  PreparedStatement prepare = this.connection.prepareStatement(languageQuery);
	  prepare.setString(1, originalLang);
	  ResultSet results = prepare.executeQuery();
	  if (results.next()) {
	    secondLanguageKey = results.getInt(1);
	  } else {
	    prepare = this.connection.prepareStatement(insertLanguage);
	    prepare.setString(1,originalLang);
	    prepare.executeUpdate();
	    results = prepare.getGeneratedKeys();
	    if (results.next()) {
	      secondLanguageKey = results.getInt(1);
	    }
	  }
	} catch (Exception e) {
	  return false;
	}
      }
      // end of language validation.
      String insertQuery = "INSERT INTO film (title, description, release_year," +
	     "language_id, original_language_id, rental_duration, rental_rate," +
	     "length, replacement_cost, rating, special_features) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      try {
	PreparedStatement prepare = this.connection.prepareStatement(insertQuery);
	prepare.setString(1, title);
	if (desc.isEmpty()) {
	  prepare.setNull(2, Types.NULL);
	} else {
	  prepare.setString(2, desc);
	}
	if (release == -1) {
	  prepare.setNull(3, Types.NULL);
	} else {
	  prepare.setString(3, String.valueOf(release));
	}
	prepare.setInt(4, primaryLanguageID);
	if (originalLang.isEmpty()) {
	  prepare.setNull(5, Types.NULL);
	} else {
	  prepare.setInt(5, secondLanguageKey);
	}
	prepare.setInt(6, duration);
	prepare.setDouble(7, rate);
	if (length == -1) {
	  prepare.setNull(8, Types.NULL);
	} else {
	  prepare.setInt(8,length);
	}
	prepare.setDouble(9,cost);
	prepare.setString(10,rating);
	String newstrarray = "";
	for (int count = 0; count < features.length-1; count++) {
	  newstrarray += features[count] + ",";
	}
	if (features.length != 0) {
	  newstrarray += features[features.length-1];
	}
	if (features.length == 0) {
	  prepare.setNull(11, Types.NULL);
	}else {
	  prepare.setString(11,newstrarray);
	}
	prepare.executeUpdate();
      } catch (Exception e) {
	return false;
      }
      return true;
    }
}
