//Group name:21
//class name:SQLServer
//This Java class, named SQLServer, facilitates interaction with a SQL Server database.
// It includes methods for dropping and creating tables,
// executing various SQL queries related to crime data analysis,
// and reading SQL scripts to populate the database.The connection to the
//uranium server is done in the constructor

import java.io.*;
import java.sql.*;
import java.util.Properties;




public class SQLServer {


    private Connection connection;//connection to the database



    //function to drop all the tables
    public void dropTables(){
        try {

            this.connection.createStatement().executeUpdate("use cs3380;");
            this.connection.createStatement().executeUpdate("drop table if exists Victim;");
            this.connection.createStatement().executeUpdate("drop table if exists Weapon;");
            this.connection.createStatement().executeUpdate("drop table if exists Status;");
            this.connection.createStatement().executeUpdate("drop table if exists Premis;");
            this.connection.createStatement().executeUpdate("drop table if exists Area;");
            this.connection.createStatement().executeUpdate("drop table if exists Adress;");
            this.connection.createStatement().executeUpdate("drop table if exists CrimeCode;");
            this.connection.createStatement().executeUpdate("drop table if exists Crime;");
            this.connection.createStatement().executeUpdate("drop table if exists MoCode;");
            this.connection.createStatement().executeUpdate("drop table if exists WeaponDecsp;");
            this.connection.createStatement().executeUpdate("drop table if exists Premiscodes;");
            this.connection.createStatement().executeUpdate("drop table if exists Areacodes;");
            this.connection.createStatement().executeUpdate("drop table if exists StatusCodes;");
            this.connection.createStatement().executeUpdate("drop table if exists Date;");

        } catch (SQLException var2) {
            var2.printStackTrace(System.out);
        }
    }
  //function to create the tables
    private void createTables() {
        try {

            this.connection.createStatement().executeUpdate("use cs3380;");
            this.connection.createStatement().executeUpdate("drop table if exists Victim;");
            this.connection.createStatement().executeUpdate("drop table if exists Weapon;");
            this.connection.createStatement().executeUpdate("drop table if exists Status;");
            this.connection.createStatement().executeUpdate("drop table if exists Premis;");
            this.connection.createStatement().executeUpdate("drop table if exists Area;");
            this.connection.createStatement().executeUpdate("drop table if exists Adress;");
            this.connection.createStatement().executeUpdate("drop table if exists CrimeCode;");
            this.connection.createStatement().executeUpdate("drop table if exists Crime;");
            this.connection.createStatement().executeUpdate("drop table if exists MoCode;");
            this.connection.createStatement().executeUpdate("drop table if exists WeaponDecsp;");
            this.connection.createStatement().executeUpdate("drop table if exists Premiscodes;");
            this.connection.createStatement().executeUpdate("drop table if exists Areacodes;");
            this.connection.createStatement().executeUpdate("drop table if exists StatusCodes;");
            this.connection.createStatement().executeUpdate("drop table if exists Date;");


            this.connection.createStatement().executeUpdate("create table StatusCodes(StatusCode VARCHAR(4) PRIMARY KEY, StatusDesc VARCHAR(255));");
            this.connection.createStatement().executeUpdate("create table Premiscodes(PremisCd float primary key,PremisName varchar(255));");
            this.connection.createStatement().executeUpdate("create table Date(DR_NO integer primary key,DateRptd datetime2(7),DateOcc datetime2(7),TimeOcc time);");
            this.connection.createStatement().executeUpdate("create table Victim(DR_NO integer,VictAge integer,VictSex varchar(255),VictDescent varchar(255),primary key(DR_NO),FOREIGN KEY (DR_NO) REFERENCES Date(DR_NO) ON DELETE CASCADE,);");
            this.connection.createStatement().executeUpdate("create table Crime(DR_NO integer,CrmCd1 float,CrmCd2 float,CrmCd3 float,CrmCd4 float,primary key(DR_NO),FOREIGN KEY (DR_NO) REFERENCES Date(DR_NO) ON DELETE CASCADE);");
            this.connection.createStatement().executeUpdate("create table Status(DR_NO integer,StatusCode varchar(4),primary key(DR_NO),FOREIGN KEY (StatusCode) REFERENCES StatusCodes(StatusCode) ON DELETE SET NULL,FOREIGN KEY (DR_NO) REFERENCES Date(DR_NO) ON DELETE CASCADE);");
            this.connection.createStatement().executeUpdate("create table Areacodes(AreaCode integer primary key,AreaName varchar(255));");
            this.connection.createStatement().executeUpdate("create table Adress(DR_NO integer,LAT float,LON float,Location varchar(255),Cross_Street varchar(255),AreaCode integer,PremisCd float,FOREIGN KEY (PremisCd) REFERENCES Premiscodes(PremisCd) ON DELETE SET NULL,FOREIGN KEY (AreaCode) REFERENCES Areacodes(AreaCode) ON DELETE SET NULL,primary key(DR_NO),FOREIGN KEY (DR_NO) REFERENCES Date(DR_NO)ON DELETE CASCADE);");
            this.connection.createStatement().executeUpdate("create table WeaponDecsp (WeaponID FLOAT primary key,Weapondecp VARCHAR(255));");
            this.connection.createStatement().executeUpdate("create table Weapon(DR_NO integer ,WeaponID float ,FOREIGN KEY (WeaponID) REFERENCES WeaponDecsp(WeaponID) ON DELETE SET NULL,primary key(DR_NO),FOREIGN KEY (DR_NO) REFERENCES Date(DR_NO)ON DELETE CASCADE);");

        } catch (SQLException var2) {
            var2.printStackTrace(System.out);
        }

    }

    


    //query1
    public void arrests(String st) {
        try {



            String sql = "SELECT AreaCode, COUNT(StatusCode) AS numArrests " +
                    "FROM Status " +
                    "JOIN Adress ON Status.DR_NO = Adress.DR_NO " +
                    "WHERE Status.StatusCode = ? " +
                    "GROUP BY AreaCode " +
                    "ORDER BY AreaCode ASC";


            PreparedStatement stmt = connection.prepareStatement(sql);

            // Bind the user input to the placeholder in the query
            stmt.setString(1, st);

            // Execute the query
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------+-------------+");
            System.out.printf("| %-8s | %-11s |\n", "AreaCode", "Num Arrests");
            System.out.println("+----------+-------------+");

            // Print table rows
            while (set.next()) {
                int areaCode = set.getInt("AreaCode");
                int numArrests = set.getInt("numArrests");
                System.out.printf("| %-8d | %-11d |\n", areaCode, numArrests);
            }
            System.out.println("+----------+-------------+");

            // Close resources
            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   //query2
    public void AreasmostReported(int values, int minage) {
        try {

            String sql = "SELECT TOP " + values + " Areacodes.AreaName, COUNT(*) AS TOTAL_CRIMES " +
                    "FROM Areacodes " +
                    "JOIN Adress ON Areacodes.AreaCode = Adress.AreaCode " +
                    "JOIN Victim ON Adress.DR_NO = Victim.DR_NO " +
                    "WHERE Victim.VictAge > ? " +
                    "GROUP BY Areacodes.AreaName " +
                    "ORDER BY TOTAL_CRIMES DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, minage);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------------------+--------------+");
            System.out.printf("| %-20s | %-12s |\n", "Area Name", "Total Crimes");
            System.out.println("+----------------------+--------------+");

            // Print table rows
            while (set.next()) {
                String areaName = set.getString("AreaName");
                int totalCrimes = set.getInt("TOTAL_CRIMES");
                System.out.printf("| %-20s | %-12d |\n", areaName, totalCrimes);
            }
            System.out.println("+----------------------+--------------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





   //query3
    public void victimAge(int limit) {
        try {
            String sql = "SELECT TOP " + limit + " Areacodes.AreaName, AVG(Victim.VictAge) AS AVG_AGE " +
                    "FROM Areacodes " +
                    "JOIN Adress ON Areacodes.AreaCode = Adress.AreaCode " +
                    "JOIN Victim ON Adress.DR_NO = Victim.DR_NO " +
                    "GROUP BY Areacodes.AreaName " +
                    "ORDER BY AVG_AGE DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------------------+------------+");
            System.out.printf("| %-20s | %-10s |\n", "Area Name", "Average Age");
            System.out.println("+----------------------+------------+");

            // Print table rows
            while (set.next()) {
                String areaName = set.getString("AreaName");
                double avgAge = set.getDouble("AVG_AGE");
                System.out.printf("| %-20s | %-10.2f |\n", areaName, avgAge);
            }
            System.out.println("+----------------------+------------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //query4
    public void crimeAgainst() {
        try {
            String sql = "SELECT VictSex, COUNT(*) AS total " +
                    "FROM Victim " +
                    "WHERE VictSex IS NOT NULL " +
                    "GROUP BY VictSex " +
                    "ORDER BY total DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------+-------+");
            System.out.printf("| %-8s | %-5s |\n", "Sex", "Total");
            System.out.println("+----------+-------+");

            // Print table rows
            while (set.next()) {
                String sex = set.getString("VictSex");
                int total = set.getInt("total");
                System.out.printf("| %-8s | %-5d |\n", sex, total);
            }
            System.out.println("+----------+-------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  //query to get number of crimes between the time by areacode
    public void crimesBetween(int startTime, int endTime) {
        try {
            String sql = "SELECT Areacodes.AreaCode, COUNT(*) AS TotalCrimes " +
                    "FROM Date " +
                    "JOIN Adress ON Date.DR_NO = Adress.DR_NO " +
                    "JOIN Areacodes ON Adress.AreaCode = Areacodes.AreaCode " +
                    "WHERE DATEPART(hour, TimeOcc) >= ? AND DATEPART(hour, TimeOcc) < ? " +
                    "GROUP BY Areacodes.AreaCode";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, startTime);
            stmt.setInt(2, endTime);

            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------+--------------+");
            System.out.printf("| %-8s | %-12s |\n", "AreaCode", "Total Crimes");
            System.out.println("+----------+--------------+");

            // Print table rows
            while (set.next()) {
                String areaCode = set.getString("AreaCode");
                int totalCrimes = set.getInt("TotalCrimes");
                System.out.printf("| %-8s | %-12d |\n", areaCode, totalCrimes);
            }
            System.out.println("+----------+--------------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //query to get the number of times wepaons used in the carime table
    public void weaponUses() {
        try {
            String sql = "SELECT Weapon.WeaponID, WeaponDecsp.Weapondecp, COUNT(*) AS NumUses " +
                    "FROM Weapon " +
                    "JOIN WeaponDecsp ON Weapon.WeaponID = WeaponDecsp.WeaponID " +
                    "GROUP BY Weapon.WeaponID, WeaponDecsp.Weapondecp";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------+-------------------------------------------------------------+-----------+");
            System.out.printf("| %-8s | %-50s | %-9s |\n", "WeaponID", "Description", "Num Uses");
            System.out.println("+----------+-------------------------------------------------------------+-----------+");

            // Print table rows
            while (set.next()) {
                int weaponID = set.getInt("WeaponID");
                String description = set.getString("Weapondecp");
                int numUses = set.getInt("NumUses");
                System.out.printf("| %-8d | %-50s | %-9d |\n", weaponID, description, numUses);
            }
            System.out.println("+----------+-------------------------------------------------------------+-----------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //query to print to get victims per descant by area code
    public void victimsPerDescentInArea(int areaCode) {
        try {
            String sql = "SELECT Areacodes.AreaCode, Victim.VictDescent, COUNT(*) AS num " +
                    "FROM Adress " +
                    "JOIN Victim ON Adress.DR_NO = Victim.DR_NO " +
                    "JOIN Areacodes ON Adress.AreaCode = Areacodes.AreaCode " +
                    "WHERE Areacodes.AreaCode = ? " +
                    "GROUP BY Areacodes.AreaCode, Victim.VictDescent " +
                    "ORDER BY num DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, areaCode);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+----------+--------------+-------+");
            System.out.printf("| %-8s | %-12s | %-5s |\n", "AreaCode", "VictimDescent", "Num");
            System.out.println("+----------+--------------+-------+");

            // Print table rows
            while (set.next()) {
                String victimDescent = set.getString("VictDescent");
                int numVictims = set.getInt("num");
                System.out.printf("| %-8d | %-12s | %-5d |\n", areaCode, victimDescent, numVictims);
            }
            System.out.println("+----------+--------------+-------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //to print all the victims in the table
    public void printVictims() {
        try {
            String sql = "SELECT  Top 50 * FROM Victim ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+---------+---------+---------------+");
            System.out.printf("| %-7s | %-7s | %-13s |\n", "Age", "Sex", "Descent");
            System.out.println("+---------+---------+---------------+");

            // Print table rows
            while (set.next()) {
                int age = set.getInt("VictAge");
                String sex = set.getString("VictSex");
                String descent = set.getString("VictDescent");

                System.out.printf("| %-7d | %-7s | %-13s |\n", age, sex, descent);
            }
            System.out.println("+---------+---------+---------------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //query to print cross streets with most crims
    public void printCrossStreets() {
        try {
            String sql = "SELECT TOP 10 Cross_Street, COUNT(*) AS Frequency " +
                    "FROM Adress " +
                    "WHERE Cross_Street IS NOT NULL " +
                    "GROUP BY Cross_Street " +
                    "ORDER BY Frequency DESC ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            // Print table header
            System.out.println("+---------------------------------------------------+-----------+");
            System.out.printf("| %-50s | %-9s |\n", "Cross Street", "Frequency");
            System.out.println("+---------------------------------------------------+-----------+");

            // Print table rows
            while (set.next()) {
                String crossStreet = set.getString("Cross_Street");
                if(!crossStreet.equals("nan")) {
                    int frequency = set.getInt("Frequency");
                    System.out.printf("| %-50s | %-9d |\n", crossStreet, frequency);
                }
                }
            System.out.println("+---------------------------------------------------+-----------+");

            set.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //query to delete the table
    public void deleteCrimeRecord(int drNumber) {
        try {
            String sql = "DELETE FROM Date WHERE DR_NO = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, drNumber);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " record(s) deleted.");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void readTable() {


        this.dropTables();//function to drop tables while repopulating the server with data again
        this.createTables();
        readsql("sqlfiles/statuscodes.sql");//reading inserts for status code table
        readsql("sqlfiles/premisecd.sql");//reading inserts for premise code table
        readsql("sqlfiles/insert_statemnt_time_formatted.sql");//reading inserts for date table,victim table and crime tabel
        readsql("sqlfiles/wescp.sql");//reading inserts for weapon description table
        readsql("sqlfiles/woutput.sql");//reading inserts for weapon table
        readsql("sqlfiles/areacode.sql");//reading inserts for area codes table
        readsql("sqlfiles/address.sql");//reading inserts for address table




    }
    //function to read sql statements from the sql files
    private void readsql(String st){ try {
        BufferedReader reader1 = new BufferedReader(new FileReader(st));
        String line = reader1.readLine();
        // assumes each query is its own lin
        int count = 0;
        while (line != null) {
            System.out.println("Loading database:"+count+"///.  Please wait for the process to complete");
            System.out.println(line);
            this.connection.createStatement().execute(line);
            line = reader1.readLine();
            count++;
        }
        System.out.println("Database successfully loaded!");
        reader1.close();
    } catch (IOException var7) {
        var7.printStackTrace();
    } catch (SQLException var8) {
        var8.printStackTrace(System.out);
        System.out.println();
    }
    }


    // class constructor
    //connects to the university of manitoba uranium server using the cfg file given
    public SQLServer() {
        Properties var1 = new Properties();
        String var2 = "auth.cfg";

        try {
            FileInputStream var3 = new FileInputStream(var2);
            var1.load(var3);
            var3.close();
        } catch (FileNotFoundException var9) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException var10) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }

        String username = (var1.getProperty("username"));
        String password = (var1.getProperty("password"));
        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String var5 = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;database=cs3380;user=" +  username + ";password=" + password + ";encrypt=false;trustServerCertificate=false;loginTimeout=30;";
        Object var6 = null;

        try {
            this.connection = DriverManager.getConnection(var5);
            Statement var7 = this.connection.createStatement();
            System.out.println("check");
            System.out.println("Database loading");

            System.out.println("Database succesfully loaded");
        } catch (Exception var8) {
            System.out.println("Issue connecting to uranium");
            var8.printStackTrace();
        }

    }
}