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
}
