import java.sql.*;
import java.util.Scanner;

// import jdk.internal.util.xml.impl.Input;

public class Demo {

    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/rzunjar";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    static Scanner InputAttrs = new Scanner(System.in);

    public static void insertBook() {
        System.out.println("Enter publication id");
        int pubID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter publication title");
        String title = InputAttrs.nextLine();
        System.out.println("Enter publication typical topics");
        String typicaltopics = InputAttrs.nextLine();
        System.out.println("Enter publication periodicity");
        String periodicity = InputAttrs.nextLine();
        System.out.println("Enter publication audience");
        String audience = InputAttrs.nextLine();
        System.out.println("Enter publication price");
        float price = InputAttrs.nextFloat();
        System.out.println("Enter publication number of copies");
        int numOfCopies = InputAttrs.nextInt();

        String insertStmt = "insert into Publication values (%d, %s, %s, %s, %s, %f, %d)";
        insertStmt = String.format(insertStmt, pubID, title, typicaltopics, periodicity, audience, price, numOfCopies);

        System.out.println("Enter book edition\n");
        int edition = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter book publication date");
        String pubDate = InputAttrs.nextLine();
        System.out.println("Enter book date of creation");
        String dofCreation = InputAttrs.nextLine();
        System.out.println("Enter book content");
        String text = InputAttrs.nextLine();
        System.out.println("Enter book ISBN number");
        int ISBN = InputAttrs.nextInt();

        // insert into Book values(16,2, '2020-02-14', '2019-01-09','adjivfividh',450);

        String insertbookStmt = "insert into Book values (%d, %d, %s, %s, %s, %d)";
        insertbookStmt = String.format(insertbookStmt, pubID, edition, pubDate, dofCreation, text, ISBN);

        try {
            stmt.executeUpdate(insertStmt);
            stmt.executeUpdate(insertbookStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIssue(int journal) {
        System.out.println("Enter publication id");
        int pubID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter publication title");
        String title = InputAttrs.nextLine();
        System.out.println("Enter publication typical topics");
        String typicaltopics = InputAttrs.nextLine();
        System.out.println("Enter publication periodicity");
        String periodicity = InputAttrs.nextLine();
        System.out.println("Enter publication audience");
        String audience = InputAttrs.nextLine();
        System.out.println("Enter publication price");
        float price = InputAttrs.nextFloat();
        System.out.println("Enter publication number of copies");
        int numOfCopies = InputAttrs.nextInt();
        InputAttrs.nextLine();

        String insertStmt = "insert into Publication values (%d, %s, %s, %s, %s, %f, %d)";
        insertStmt = String.format(insertStmt, pubID, title, typicaltopics, periodicity, audience, price, numOfCopies);

        System.out.println("Enter data of release");
        String doRelease = InputAttrs.nextLine();

        // insert into Book values(16,2, '2020-02-14', '2019-01-09','adjivfividh',450);

        String insertJoStmt;
        if (journal == 1) {
            insertJoStmt = "insert into Issues values (%d, %s, 'journal')";
        } else {
            insertJoStmt = "insert into Issues values (%d, %s, 'magazine')";
        }
        insertJoStmt = String.format(insertJoStmt, pubID, doRelease);

        try {
            stmt.executeUpdate(insertStmt);
            stmt.executeUpdate(insertJoStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignEditor() {
        System.out.println("Enter publication id");
        int pubID = InputAttrs.nextInt();
        System.out.println("Enter editor id");
        int edID = InputAttrs.nextInt();

        String assignStmt = "insert into assignedTo values (%d, %d)";
        assignStmt = String.format(assignStmt, edID, pubID);

        try {
            stmt.executeUpdate(assignStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editorView() {
        InputAttrs.nextLine();
        System.out.println("Enter editor name");
        String name = InputAttrs.nextLine();

        String editorviewstmt = "select * from Publication where pubID IN (Select pubID from assignedTo where edID = (select edID from Editors where name= %s));";
        editorviewstmt = String.format(editorviewstmt, name);

        try {
            rs = stmt.executeQuery(editorviewstmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addArticles() {
        System.out.println("Enter publication id");
        int pubID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter date of creation");
        String dofCreation = InputAttrs.nextLine();
        System.out.println("Enter article title");
        String title = InputAttrs.nextLine();
        System.out.println("Enter article text");
        String text = InputAttrs.nextLine();
        System.out.println("Enter article topic");
        String topic = InputAttrs.nextLine();

        String addarticlestmt = "insert into Articles values(%s, %s, %d, %s, %s)";
        addarticlestmt = String.format(addarticlestmt, title, dofCreation, pubID, text, topic);

        try {
            stmt.executeUpdate(addarticlestmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticles() {
        InputAttrs.nextLine();
        System.out.println("Enter article title");
        String title = InputAttrs.nextLine();

        String deletearticlestmt = "delete from Articles where aTitle = %s";
        deletearticlestmt = String.format(deletearticlestmt, title);

        try {
            stmt.executeUpdate(deletearticlestmt);
            rs = stmt.executeQuery("select * from Articles");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addChapter() {
        System.out.println("Enter publication id");
        int pubID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter chapter name");
        String title = InputAttrs.nextLine();
        System.out.println("Enter article topic");
        String topic = InputAttrs.nextLine();

        String addchapterstmt = "insert into Chapters values(%s,%d, %s)";
        addchapterstmt = String.format(addchapterstmt, title, pubID, topic);

        try {
            stmt.executeUpdate(addchapterstmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteChapter() {
        InputAttrs.nextLine();
        System.out.println("Enter chapter name");
        String title = InputAttrs.nextLine();

        String deletechapterstmt = "delete from Chapters where name= %s";
        deletechapterstmt = String.format(deletechapterstmt, title);

        try {
            stmt.executeUpdate(deletechapterstmt);
            rs = stmt.executeQuery("select * from Chapters");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertBookEdition() {
        InputAttrs.nextLine();
        System.out.println("Enter pubID");
        int pubID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter book name");
        String title = InputAttrs.nextLine();
        System.out.println("Enter book edition");
        int edition = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter book publication date");
        String pubDate = InputAttrs.nextLine();
        System.out.println("Enter book date of creation");
        String dofCreation = InputAttrs.nextLine();
        System.out.println("Enter book content");
        String text = InputAttrs.nextLine();
        System.out.println("Enter book ISBN number");
        int ISBN = InputAttrs.nextInt();

        String insertBookEdstmt = "insert into Publication select %d,title,typicalTopics,periodicity,audience,24,10 from Publication where title=%s";
        insertBookEdstmt = String.format(insertBookEdstmt, pubID, title);

        String insertBookEdstmt1 = "insert into Book values(%d,%d, %s, %s,%s,%d)";
        insertBookEdstmt1 = String.format(insertBookEdstmt1, pubID, edition, pubDate, dofCreation, text, ISBN);

        try {
            stmt.executeUpdate(insertBookEdstmt);
            stmt.executeUpdate(insertBookEdstmt1);
            rs = stmt.executeQuery("select * from Publication");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from Book");

            rsmd = rs.getMetaData();
            rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBookEdition() {
        InputAttrs.nextLine();
        System.out.println("Enter book name");
        String title = InputAttrs.nextLine();
        System.out.println("Enter book edition");
        int edition = InputAttrs.nextInt();

        String deletebookstmt = "delete from Publication where title = %s and pubID IN (select pubID from Book where edition=%d) ";
        deletebookstmt = String.format(deletebookstmt, title, edition);

        try {
            stmt.executeUpdate(deletebookstmt);
            rs = stmt.executeQuery("select * from Publication");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from Book");

            rsmd = rs.getMetaData();
            rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteIssue() {
        InputAttrs.nextLine();
        System.out.println("Enter issue name");
        String title = InputAttrs.nextLine();

        String deleteissuestmt = "delete from Publication where title = %s";
        deleteissuestmt = String.format(deleteissuestmt, title);

        try {
            stmt.executeUpdate(deleteissuestmt);
            rs = stmt.executeQuery("select * from Publication");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from Issues");

            rsmd = rs.getMetaData();
            rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findBook() {
        InputAttrs.nextLine();
        System.out.println("Enter book typical topic");
        String topic = InputAttrs.nextLine();
        System.out.println("Enter book publication date");
        String date = InputAttrs.nextLine();
        System.out.println("Enter book author's name");
        String author = InputAttrs.nextLine();

        String findbookstmt = "select title from Publication where typicalTopics= %s and pubID IN (select Book.pubID from Book,bookWrritenBy, Author where Author.name= %s and Book.pubDate=%s and Author.authID= bookWrritenBy.authID and Book.pubID= bookWrritenBy.pubID)";
        findbookstmt = String.format(findbookstmt, topic, authir, date);

        try {
            rs = stmt.executeQuery(findbookstmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    public static void enterPaymentAuthEdit(int author) {
        InputAttrs.nextLine();
        System.out.println("Enter payment id");
        int pID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter payment date");
        String date = InputAttrs.nextLine();
        System.out.println("Enter payment amount");
        int amount = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter payment periodicity");
        String periodicity = InputAttrs.nextLine();
        System.out.println("Enter author/editor name");
        String authname = InputAttrs.nextLine();

        String enterPaymentstmt = "insert into Payment values(%d, NULL,%s, %d,%s);";
        enterPaymentstmt = String.format(enterPaymentstmt, pID, date, amount, periodicity);

        String enterPaymentstmt1;

        if (author == 1) {
            enterPaymentstmt1 = "insert into payToAuth select authID,%d from Author where name=%s;";
        } else {
            enterPaymentstmt1 = "insert into payTo select authID,%d from Author where name=%s;";
        }

        enterPaymentstmt1 = String.format(enterPaymentstmt1, pID, authname);

        try {
            stmt.executeUpdate(enterPaymentstmt);
            stmt.executeUpdate(enterPaymentstmt1);

            rs = stmt.executeQuery("select * from Payment");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from payToAuth");

            rsmd = rs.getMetaData();
            rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertDistributor() {
        
        System.out.println("Enter distributor name");
        int distName = InputAttrs.nextLine();
        System.out.println("Enter distributor type");
        String type = InputAttrs.nextLine();
        System.out.println("Enter distributor streetname");
        String streetName = InputAttrs.nextLine();
        System.out.println("Enter distributor city");
        String city = InputAttrs.nextLine();
        System.out.println("Enter distributor contact");
        String contact = InputAttrs.nextLine();
        

        String insertDistributorStmt = "insert into Distributor values(%s,%s,%s,%s,%s)";
        insertDistributorStmt = String.format(insertDistributorStmt, distName,type,streetName,city,contact);

        

        try {
            stmt.executeUpdate(insertDistributorStmt);   

             rs = stmt.executeQuery("select * from Distributor");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }         
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDistributor() {
        InputAttrs.nextLine();
        System.out.println("Enter distributor name");
        String title = InputAttrs.nextLine();

        String deleteDistributorstmt = "delete from Distributor where distName= %s";
        deleteDistributorstmt = String.format(deleteDistributorstmt, name);

        try {
            stmt.executeUpdate(deletearticlestmt);
            rs = stmt.executeQuery("select * from Distributor");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  orderBookIssue() {
        // insert into Orders values(615,"Where there was a way",1,"2020-04-02",3);

        System.out.println("Enter order id");
        int orderID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        System.out.println("Enter publication title");
        String title = InputAttrs.nextLine();
        System.out.println("Enter edition");
        String edition = InputAttrs.nextLine();
        System.out.println("Enter date");
        String date = InputAttrs.nextLine();
        System.out.println("Enter num of Copies");
        String numOfCopies = InputAttrs.nextInt();
        InputAttrs.nextLine();

        String insertOrderStmt = "insert into Orders values(%d, %s,%s,%s,%d)";
        insertOrderStmt = String.format(insertOrderStmt, orderIDm title, edition,date,numOfCopies);

        
        System.out.println("Enter name of distributor");
        String distName = InputAttrs.nextLine();
        
        String placeOrderStmt = "insert into placesOrd values(%s, %d, %s)";
        placeOrderStmt = String.format(placeOrderStmt, distName, orderID,title);


        // insert into Book values(16,2, '2020-02-14', '2019-01-09','adjivfividh',450);

        
        try {
            stmt.executeUpdate(insertOrderStmt);
            stmt.executeUpdate(placeOrderStmt);


            rs = stmt.executeQuery("select * from Orders");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }


            rs = stmt.executeQuery("select * from placesOrd");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    


    public static void billDistributor() {
        //insert into Bill values(37,73.50,9.0,1);

        System.out.println("Enter bill id");
        int billID = InputAttrs.nextInt();
        System.out.println("Enter bill cost");
        String cost = InputAttrs.nextFloat();;
        System.out.println("Enter shipping cost");
        String shippingcost = InputAttrs.nextFloat();
        System.out.println("Enter paid");
        String paid = InputAttrs.nextInt();
        InputAttrs.nextLine();

        String billStmt = "insert into Bill values(%d,%f,%f, %d)";
        billStmt = String.format(billStmt, billID,cost,shippingcost,paid);

        //insert into associatedWith values(615,37);
        System.out.println("Enter order id");
        String orderID = InputAttrs.nextInt();

        String associatedStmt = "insert into associatedWith values(%d,%d)";
        associatedStmt = String.format(associatedStmt, orderID, billID);

        InputAttrs.nextLine();

        System.out.println("Enter staff Id ");
        String staffID = InputAttrs.nextLine();

        String staffStmt = "insert into generateBill values(%d,%d);";
        staffStmt = String.format(staffStmt,billID,staffID );

        try {
            stmt.executeUpdate(billStmt);
            stmt.executeUpdate(associatedStmt);
            stmt.executeUpdate(staffStmt);
            rs = stmt.executeQuery("select * from Bill");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from associatedWith");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            rs = stmt.executeQuery("select * from generateBill");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void changeOutstandingBalance() {
        
        
        System.out.println("Enter dist name");
        int distName = InputAttrs.nextLine();
        String distStmt = "select accID from hasAcc where distName= %s";
        distStmt = String.format(distStmt, distName);

        System.out.println("Enter bill id");
        int billID = InputAttrs.nextInt();
        InputAttrs.nextLine();
        
        String priceStmt = "select price from Bill where billID=%d";
        priceStmt = String.format(priceStmt, billID);

        String shippingcostStmt = "select shippingCost from Bill where billID=%d";
        shippingcostStmt = String.format(shippingcostStmt, billID);

        String balanceStmt = "select balance from Bill where billID=%d";
        balanceStmt = String.format(balanceStmt, billID);



        try{
            rs = stmt.executeQuery(distStmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            String accID = rs.getString(0) 
            int accIDs = Integer.parseInt(accID);
            
            rs = stmt.executeQuery(shippingcostStmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            String shippingCost = rs.getString(0) 
            float shippingCosts = Float.parseFloat(shippingCost);

            rs = stmt.executeQuery(priceStmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            String price = rs.getString(0) 
            float prices = Float.parseFloat(price);

            rs = stmt.executeQuery(balanceStmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            String balance = rs.getString(0) 
            float balances = Float.parseFloat(balance);
            
            float total = prices + shippingCosts + balances

            String updateStmt = "update Account set balance=%f where accID=%d";
            
            updateStmt = String.format(updateStmt, total, accIDs);
            stmt.executeUpdate(updateStmt);

            rs = stmt.executeQuery("select * from Account");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

    

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void getTotalPrice() {
        InputAttrs.nextLine();
        System.out.println("Enter start date");
        String start = InputAttrs.nextLine();
        System.out.println("Enter end date");
        String end = InputAttrs.nextLine();
        

        String gettotalpricestmt = "select title,distName,edition,noofCopies,noofCopies*price as cost from (select Distributor.distName,Orders.title,Orders.edition,SUM(noOfCopies) as noofCopies from placesOrd,Orders,Distributor,Publication where Orders.title=Publication.title and placesOrd.distName=Distributor.distName and placesOrd.orderID=Orders.orderID and deliveryDate between %s and %s group by Distributor.distName,Orders.title,Orders.edition) as t1 natural join Publication group by title";
        gettotalpricestmt = String.format(gettotalpricestmt, start, end);

        try {
            rs = stmt.executeQuery(gettotalpricestmt);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
    
	public static void totalRev() {
        

        String totalRev = "select (select sum(price) from Bill where paid=1) - (select sum(amount) from Payment where claimDate is NOT NULL) + (select sum(shippingCost) from Bill) as Revenue";
		totalRev = String.format(totalRev);

        try {
            rs = stmt.executeQuery(totalRev);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void totalExp() {
        

        String totalExp = "select (select sum(amount) from Payment where claimDate is NOT NULL) + (select sum(shippingCost) from Bill) as Expense";
        totalExp = String.format(totalExp);

        try {
            rs = stmt.executeQuery(totalExp);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

	
	public static void totalDist() {
        

        String totalDist = "select count(*) from Distributor where distName IN ( select distName from hasAcc)";
        findbookstmt = String.format(totalDist);

        try {
            rs = stmt.executeQuery(totalDist);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void totalRevCity() {
        

        String totalRevCity = "select city,sum(price-shippingCost) as revenue from ((((Bill natural join associatedWith)natural join Orders) natural join placesOrd) natural join Distributor) group by city";
        totalRevCity = String.format(totalRevCity);

        try {
            rs = stmt.executeQuery(totalRevCity);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void totalRevDist() {
        

        String totalRevDist = "select distName,sum(price-shippingCost) as revenue from ((((Bill natural join associatedWith) natural join Orders) natural join placesOrd) natural join Distributor) group by city";
        totalRevDist = String.format(totalRevDist);

        try {
            rs = stmt.executeQuery(totalRevDist);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void totalRevLoc() {
        

        String totalRevLoc = "select streetName,city,sum(price-shippingCost) as revenue from ((((Bill natural join associatedWith) natural join Orders) natural join placesOrd) natural join Distributor) group by city,streetName";
        totalRevLoc = String.format(totalRevLoc);

        try {
            rs = stmt.executeQuery(totalRevLoc);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void totalEdAuthPay() {
        InputAttrs.nextLine();
        System.out.println("Enter start date");
        String start = InputAttrs.nextLine();
        System.out.println("Enter end date");
        String end = InputAttrs.nextLine();

        String totalEdAuthPay = "select Author.type,sum(amount) from Payment natural join payToAuth natural join Author where date between %s and %s group by Author.type UNION select Editors.type,sum(amount) from Payment natural join payTo natural join Editors where date between %s and %s group by Editors.type";
        totalEdAuthPay = String.format(totalEdAuthPay, start, end, start, end);

        try {
            rs = stmt.executeQuery(totalEdAuthPay);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnvalue = rs.getString(i);
                    System.out.print(columnvalue + ": " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
        
        



    

    public static void main(String[] args) {
        try {

            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make MariaDB Thin
            // driver, available to clients.

            Class.forName("org.mariadb.jdbc.Driver");

            String user = "rzunjar";
            String passwd = "200311962";

            // Connection conn = null;
            // Statement stmt = null;
            // ResultSet rs = null;

            try {
                // Get a connection from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL

                conn = DriverManager.getConnection(jdbcURL, user, passwd);

                // Create a statement object that will be sending your
                // SQL statements to the DBMS

                stmt = conn.createStatement();
                System.out.println(
                        "1. Insert new book\n2. Insert new journal\n3. Insert new magazine\n4. Update book\n5. Update Issue\n6. Assign editor to publication\n7. Print Editor's view\n8. Adding article for a publication\n9. Deleting article\n10. Adding chapter\n11. Deleting a chapter\n12. Enter new book edition\n13. Delete book edition\n14. Delete issue\n15. Update article\n 16. Update chapter\n17. Find books and articles by topic, date, author’s name\n18. Enter payment for author\n19. Enter payment for editor \n20. Enter new distributor\n 21. Update distributor information\n 22. Delete a distributor\n 23.Input orders from distributors, for a book edition or an issue of a publication per distributor,
for a certain date\n 24. Bill distributor for an order\n 25. Change outstanding balance of a distributor on receipt of a payment");
                int choice = InputAttrs.nextInt();
                switch (choice) {
                    case 1: {
                        insertBook();
                        break;
                    }
                    case 2: {
                        insertIssue(1);
                        break;
                    }
                    case 3: {
                        insertIssue(0);
                        break;
                    }
                    case 4: {
                        System.out.println( "1. Book title  \n2. Book edition \n3. Book publication date \n4.Book date of creation City \n5.text \n6.ISBN\n");
                          
                        int choose = InputAttrs.nextInt();
                        switch(choose){
                            case 1: {

                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                break;
                            }
                            case 5: {
                                break;
                            }
                            case 6: {
                                break;
                            }


                            default:
                                break;

                        }
                        break;
                    }
                    case 5: {
                        System.out.println( "1. Issue date of release\n 2. Journal or Maagzine \n");
                         
                        int choose = InputAttrs.nextInt();
                        switch(choose){
                            case 1: {

                                break;

                            }
                            case 2: {
                                break;
                            }
                            
                            default:
                                break;

                        }
                        break;
                    }
                    case 6: {
                        assignEditor();
                        break;
                    }
                    case 7: {
                        editorView();
                        break;
                    }
                    case 8: {
                        addArticles();
                        break;
                    }
                    case 9: {
                        deleteArticles();
                        break;
                    }
                    case 10: {
                        addChapter();
                        break;
                    }
                    case 11: {
                        deleteChapter();
                        break;
                    }
                    case 12: {
                        insertBookEdition();
                        break;
                    }
                    case 13: {
                        deleteBookEdition();
                        break;
                    }
                    case 14: {
                        deleteIssue();
                        break;
                    }
                    case 15: {
                        System.out.println("1. Article title \n2. Article Date of Creation\n 3. text \n4.Topic\n ");
                        Update article
                        int choose = InputAttrs.nextInt();
                        switch(choose){
                            case 1: {

                                break;

                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                break;
                            }
                        
                            default:
                                break;

                        }
                        break;
                    }
                    case 16: {
                        System.out.println( "1. Chapter Name\n 2. Chapter topic\n ");
                         
                        int choose = InputAttrs.nextInt();
                        switch(choose){
                            case 1: {

                                break;

                            }
                            case 2: {
                                break;
                            }
                            
                            default:
                                break;

                        }
                        break;
                    }
                    case 17: {
                        findBook();
                        break;
                    }
                    case 18: {
                        enterPaymentAuthEdit(1);
                        break;
                    }
                    case 19: {
                        enterPaymentAuthEdit(0);
                        break;
                    }
                    case 20: {
                        insertDistributor();
                        break;
                    }
                    case 21: {

                        System.out.println( "1. Distributor Name\n 2. Distributor type\n 3. Distributor StreetName\n 4.Distributor City\n 5.Distributor contact\n");
                         
                        int choose = InputAttrs.nextInt();
                        switch(choose){
                            case 1: {

                                break;

                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                break;
                            }
                            case 5: {
                                break;
                            }

                            default:
                                break;

                        }
                        
                        break;
                    }
                    case 22: {
                        deleteDistributor();
                        break;
                    }
                    case 23: {
                        orderBookIssue();
                        break;
                    }
                    case 24: {
                        billDistributor();
                        break;
                    }
                    case 25: {
                        changeOutstandingBalance();
                        break;
                    }
					case 26: {
                        getTotalPrice();
                        break;
						
					case 27: {
                        totalRev();
                        break;
					
					case 28: {
                        totalExp();
                        break;
						
					case 29: {
                        totalDist();
                        break;
						
					case 30: {
                        totalRevCity();
                        break;
						
					case 31: {
                        totalRevDist();
                        break;
						
					case 32: {
                        totalRevLoc();
                        break;
						
					case 33: {
                        totalEdAuthPay();
                        break;	
					default:
                        break;
                }
                // // Create the BOOKS table

                // stmt.executeUpdate("CREATE TABLE BOOKS " + "(BOOK_TITLE VARCHAR(32), ID
                // INTEGER, "
                // + "PRICE FLOAT, AVAILABLE INTEGER)");

                // // Populate the BOOKS table

                // stmt.executeUpdate("INSERT INTO BOOKS " + "VALUES ('All about DBMS', 17,
                // 13.49, 5)");

                // stmt.executeUpdate("INSERT INTO BOOKS " + "VALUES ('Jack the Ripper', 13,
                // 9.99, 1)");

                // stmt.executeUpdate("INSERT INTO BOOKS " + "VALUES ('Queen Lucia', 72, 5.99,
                // 0)");

                // stmt.executeUpdate("INSERT INTO BOOKS " + "VALUES ('A Calendar of Sonnets',
                // 101, 3.49, 15)");

                // stmt.executeUpdate("INSERT INTO BOOKS " + "VALUES ('Napoleon and Blucher', 5,
                // 9.99, 0)");

            } finally {
                close(rs);
                // close(stmt);
                close(conn);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
