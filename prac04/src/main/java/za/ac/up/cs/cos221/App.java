package za.ac.up.cs.cos221;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String driver = "jdbc:mariadb";
    private static String host = "localhost";
    private static int port = 3307;
    private static String database = "u21452832_sakila";
    private static String username = "admin";
    private static String password = "luffy";
  
      
    public static void main( String[] args )
    {
        MainFrame mf = new MainFrame(setConnection());
        mf.setVisible(true);
    } 
    private static Database setConnection(){
        Database db = new Database(driver,host,port, database,username,password);
        return db;
    }
}
