//Group name:21
//class name:FinalProject
//This file contains the main class and it runs the interface by creating a connction to the uranium datbase using
//our SQLServer class and run the inteface run the quries using taht class



import java.util.Scanner;

public class FinalProject {
    //main class
    public static void main(String[] args) {
        SQLServer db = new SQLServer();//creating the database Sever

        runPrompt(db);

        System.out.println("Thank you for using our database!");
    }

   //interface
    public static void runPrompt(SQLServer db) {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to our Crime Statistics database!");
        System.out.println("Type 'help' for information about the database.");

        boolean quit = false;
        boolean admin = false;
       //if admin
        while(!quit){

            if(admin){
                System.out.println("Admin Ready for next input: ");
            }else{
                System.out.println("Ready for next input: ");
            }

            String line = console.nextLine();
            line = line.trim();
            System.out.println("'"+line+"' command received.");


            if(line.equals("quit")){
                quit = true;
            }else if(line.equals("help")){
                printHelp(admin);
            }else if(line.equals("victimage")){
                System.out.println("How many responese would you like to view? Integer only excepted.");
                String numResponses = console.nextLine();
                numResponses = numResponses.trim();
                if(checkifint(numResponses)){
                    db.victimAge(Integer.valueOf(numResponses));
                }
            }else if(line.equals("crimeagainst")){
                db.crimeAgainst();
            }else if(line.equals("crimesbetween")){
                System.out.println("Enter the starting time. Only excepts hours as input. Example 5 = 5am. 21 = 11pm");
                String firstnum = console.nextLine();
                firstnum = firstnum.trim();
                System.out.println("Enter the ending time. Only excepts hours as input. Example 5 = 5am. 21 = 11pm");
                String secondnum = console.nextLine();
                secondnum = secondnum.trim();
                if(checkifint(firstnum) && checkifint(secondnum)){
                    int first=Integer.valueOf(firstnum);
                    int secd=Integer.valueOf(secondnum)   ;
                    if(first < secd && (first>0 && first<24 ) &&(secd>0 && secd<24 )){
                    db.crimesBetween(first, secd);}
                    else{
                        System.out.println("Invalid entry returning to main menu");
                    }
                }
            }else if(line.equals("weaponuses")){
                db.weaponUses();
            }else if(line.equals("victimsperdescentinarea")){
                System.out.println("Enter the area code of the place you want to look at!only integers accepted");
                String firstnum = console.nextLine();


                firstnum = firstnum.trim();
                if(checkifint(firstnum)){
                    db.victimsPerDescentInArea(Integer.valueOf(firstnum));
                }
            }else if(line.equals("adultarrests")){
                System.out.println("Enter the status code ");
                String entered = console.nextLine();
                entered = entered.trim();
                entered.toUpperCase();
                db.arrests(entered);

            }else if(line.equals("areasmostreported")){
                System.out.println("How many responese would you like to view? Single integer only excepted.");
                String numResponses = console.nextLine();
                numResponses = numResponses.trim();
                System.out.println("What is the minimum victim to view crimes in this area");
                String minage = console.nextLine();
                minage = minage.trim();
                if(checkifint(numResponses) && checkifint(minage)){
                    db.AreasmostReported(Integer.valueOf(numResponses), Integer.valueOf(minage));
                }
            }else if(line.equals("printvictims")){
                if(admin){
                    db.printVictims();
                }else{
                    System.out.println("You dont have permission to run this! Please login as admin to run this command.");
                }
            }else if(line.equals("printcrossstreets")){
                db.printCrossStreets();
            }else if(line.equals("droptables")) {
                if (admin) {
                    db.dropTables();
                } else {
                    System.out.println("You do not have permission to do this!");
                }}else if(line.equals("Repopulate")) {
                    if (admin) {
                        
                        db.readTable();;
                    } else {
                        System.out.println("You do not have permission to do this!");
                    }
            }else if(line.equals("deletecrimerecord")){
                if(admin){
                    System.out.println("Enter the DR_NO of the crime record you would like to delete.");
                    String drnum = console.nextLine();
                    drnum = drnum.trim();
                    if(checkifint(drnum)){
                        db.deleteCrimeRecord(Integer.valueOf(drnum));
                    }
                }else{
                    System.out.println("You dont have permission to run this! Please login as admin to run this command.");
                }
            }else if(line.equals("admin")){
                boolean adminTry = false;
                if(admin){
                    System.out.println("You are already logged in as admin");
                }else{
                    while(adminTry == false){
                        System.out.println("Please enter Admin Passward: ");
                        String passwordAttempt = console.nextLine();
                        if(passwordAttempt.equals("1234")){
                            System.out.println("Admin permissions granted. Try typing 'help' for extra commands");
                            adminTry = true;
                            admin = true;
                        }else{
                            //System.out.println("Incorrect admin password. Please try again. Or type 'q' to return to main menu");
                            if(passwordAttempt.equals("q")){
                                adminTry = true;
                                admin = false;
                                break;//not needed but here as insurance.
                            }
                            else{
                                System.out.println("Incorrect admin password. Please try again. Or type 'q' to return to main menu");
                            }
                        }
                    }
                }
            }else{
                System.out.println("This is not a valid input. Please try again. Type 'help' for a list of available commands.");
            }

        }

    }
   //functioin to check if the string has integer
    private static boolean checkifint(String check){
        try{
            Integer.parseInt(check);
            return true;
        }catch (Exception e){
            System.out.println("The number entered is not an integer returning to main menu");
            return false;
        }
    }
   //function to print all the menu for the user
    private static void printHelp(boolean admin) {
        System.out.println("Crime Statistics database");
        System.out.println("Commands:");
        System.out.println("help - Get help.");
        System.out.println("quit - Exit the program.");
        System.out.println("admin - Will be prompted to enter admin password.");
        System.out.println("victimage - For all areas, print the average victim age.");
        System.out.println("crimeagainst - Total crimes commited against males, females, or sex not provided represented as 'X'.");
        System.out.println("crimesbetween - Crimes commited on any day commited inbetween the provided times. Will be promted to enter time.");
        System.out.println("weaponuses - Display whatever weapon is used the most.");
        System.out.println("victimsperdescentinarea - Displays area code input and the total number of victims for each ethnicity. Will be prompted to enter Input.");
        System.out.println("adultarrests - Displays the number of crimes commited with the status code provided.");
        System.out.println("areasmostreported - Total crimes in an area with victims age greater then the age provided. Will be prompted to enter the age.");
        System.out.println("printcrossstreets - Shows the top 5 cross streets with the most crimes occurring.");

        if(admin){
            printAdmin();
        }
        System.out.println("");
        System.out.println("---- end help ----- ");

    }
    //function to print all the menu for the admin
    private static void printAdmin() {
        System.out.println();
        System.out.println();
        System.out.println("Admin detected! Extra commands available below:");
        System.out.println("printvictims - Print 50 of the victims info.");
        System.out.println("deletecrimerecord - Delete the crime record and all info with the provided DR_NO. Will be prompted to enter DR_NO");
        System.out.println("droptables - drop all existing tables.");
        System.out.println("Repopulate -Repopulate the table again");
    }



}