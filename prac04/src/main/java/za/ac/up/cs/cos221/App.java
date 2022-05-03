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
	try {
	for (int count = 0; count < args.length; count++) {
	  if (args[count].contains("--database")|| args[count].contains("-d")) {
	    count += 1;
	    if (!(count < args.length)) {
	      throw new Exception("incorrect arguments");
	    }
	    App.database = args[count];
	  }
	  else if (args[count].contains("--username")|| args[count].contains("-u")) {
	    count += 1;
	    if (!(count < args.length)) {
	      throw new Exception("incorrect arguments");
	    }
	    App.username = args[count];
	  }
	  else if (args[count].contains("--port")) {
	    count += 1;
	    if (!(count < args.length)) {
	      throw new Exception("incorrect arguments");
	    }
	    App.port = Integer.parseInt(args[count]);
	  }
	  else if (args[count].contains("--password")|| args[count].contains("-p")) {
	    count += 1;
	    if (!(count < args.length)) {
	      throw new Exception("incorrect arguments");
	    }
	    App.password = args[count];
	  } else {
	    throw new Exception("incorrect arguments");
	  }
	}
	} catch (Exception e) {
	  System.out.println("Incorrect arguments specified.");
	  System.out.println("Optional Arguments are:");
	  System.out.println("-p --password : specify a password to access the database with");
	  System.out.println("-u --username : specify a username to access the database with");
	  System.out.println("-d --database : specify a database name to access the database with");
	  System.out.println("--port : specify a port to access the database with");
	  System.out.println("example: App -p pwd2logo -u daniel -d database --port 3306");
	  System.exit(1);
	}
        MainFrame mf = new MainFrame(setConnection());
        mf.setVisible(true);
    }
    private static Database setConnection(){
        Database db = new Database(driver,host,port, database,username,password);
        return db;
    }
}
