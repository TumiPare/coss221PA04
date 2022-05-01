package za.ac.up.cs.cos221;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;


public class Database {
    protected Connection connection = null;
    // private static String driver = "jdbc:mariadb";
    // private static String host = "localhost";
    // private static int port = 3307;
    // private static String database = "u21452832_sakila";
    // private static String username = "admin";
    // private static String password = "luffy";
    public Database(String driver,String host,int port,String database,String username,String password)
    {
        String url = new StringBuilder()
                .append(driver).append("://")
                .append(host).append(":").append(port).append("/")
                .append(database)
                .toString();
        try{
            connection = DriverManager.getConnection(url, username, password);
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
        
            //Create new table model
            DefaultTableModel tableModel = new DefaultTableModel();
    
            //Retrieve meta data from ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
    
            //Get number of columns from meta data
            int columnCount = metaData.getColumnCount();
    
            //Get all column names from meta data and add columns to table model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }
    
            //Create array of Objects with size of column count from meta data
            Object[] row = new Object[columnCount];
    
            //Scroll through result set
            while (rs.next()){
                //Get object from column with specific index of result set to array of objects
                for (int i = 0; i < columnCount; i++){
                    row[i] = rs.getObject(i+1);
                }
                //Now add row to table model with that array of objects as an argument
                tableModel.addRow(row);
            }
            
            return tableModel;
        
    }
}
