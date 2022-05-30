package DataIngestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.GameDrawResults;
import model.WinningColumn;
import tzokerstats.FoundEntityException;
import tzokerstats.NotFoundEntityException;

/*
    Class that abstracts all db related operations:
	    - Create
		- Delete
		- Update
		- Delete 
    
	Reads from db and uses the POJO Classes in order to 
	manipulate the data.
	Furthermore it executes direct SQL Queries against
	the target server.
	
	Important! It contains hardcoded values for the 
	    -DB connection string
		-DB Credentials
	that should be migrated in an ecrypted external config file (TODO)
*/
public class DBIntegrator {

    //TODO: encrypt in a outside file
    private final static String DB_USER_NAME = "TzokerStats";
    private final static String DB_PASSWORD = "pass";
    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/L10L";

    static public void setDrawResults(List<WinningColumn> winningColumnsList, List<GameDrawResults> gameDrawResultsList, Operation op) throws FoundEntityException {
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        //We should write both tables to one transaction
        //to avoid inconsistancies.        
        //Begin transaction
        try {
            em.getTransaction().begin();
            //Loop through current winning columns
            for (WinningColumn winningColumn : winningColumnsList) {
                if (!existsWinningColumn(winningColumn)) {
                    //Write to DB Winning Columns
                    em.persist(winningColumn);
                } else {
                    //throw Found Exception per user choice
                    if (!op.equals(Operation.FORCE_INSERT)) {
                        throw new FoundEntityException("Τα επιλεγμένα δεδομένα υπάρχουν ήδη στην βάση.");
                    }
                }
            }
            //Loop through Draw Details
            for (GameDrawResults gameDrawResults : gameDrawResultsList) {
                if (!existsDrawResults(gameDrawResults)) {
                    //Write to DB Winning Columns
                    em.persist(gameDrawResults);
                } else {
                    //throw Found Exception per user choice
                    if (!op.equals(Operation.FORCE_INSERT)) {
                        throw new FoundEntityException("Τα επιλεγμένα δεδομένα υπάρχουν ήδη στην βάση.");
                    }
                }
            }
            //Commit all changes at once
            em.getTransaction().commit();
            //destroy EntityManager and EntityManagerFactory (why wait for the GC?)  
        } catch (FoundEntityException e) {
            System.out.println("Draw data allready exists");
            em.getTransaction().rollback();
            throw (e);
        } catch (Exception e) {
            System.out.println("Error during DB Insert");
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
            emf.close();
        }
    }

    /*
      Checks draws in the input arguments and
      returns true if no rows found
      false if some draws are found.
      If all rows are found raises error
     */
    static public boolean isDrawResultsReadyForInsert(List<WinningColumn> winningColumnsList, List<GameDrawResults> gameDrawResultsList) throws FoundEntityException {
        //First check for existance
        int howManyEntriesFound = 0;
        int howManyEntries = winningColumnsList.size();

        for (WinningColumn winningColumn : winningColumnsList) {
            if (existsWinningColumn(winningColumn)) {
                howManyEntriesFound++;
            }
        }

        if (howManyEntriesFound == 0) {
            return true;
        } else if (howManyEntriesFound < howManyEntries) {
            return false;
        } else if (howManyEntriesFound == howManyEntries) {
            //All rows found raise error
            throw new FoundEntityException("Τα επιλεγμένα δεδομένα υπάρχουν ήδη στην βάση.");
        }
        return false;
    }

    /*
      Delete for selected Draw ID
      Using transaction control to avoid inconsistency among the tables!
     */
    public static int deleteDrawDataWithID(String gameID, String drawID) throws SQLException, NotFoundEntityException {
        AggregatedDrawData aggregatedDrawData = null;
        int deletedDraws = 0;
        String deleteGameDrawResultsSQL = "Delete From TZOKERSTATS.GAME_DRAW_RESULTS Where DRAW_ID = ? and GAME_ID = ?";
        String deleteWinningColumnSQL = "Delete From TZOKERSTATS.WINNING_COLUMN Where DRAW_ID = ? and GAME_ID = ?";
        Connection connection = null;
        try {
            connection = connect();
            connection.setAutoCommit(false);
            //Delete statement for GAME_DRAW_RESULTS table
            PreparedStatement deleteGameDrawResults = connection.prepareStatement(deleteGameDrawResultsSQL);
            deleteGameDrawResults.setString(1, drawID);
            deleteGameDrawResults.setString(2, gameID);
            deleteGameDrawResults.executeUpdate();

            //Delete statement for WINNING_COLUMN table
            PreparedStatement deleteWinningColumn = connection.prepareStatement(deleteWinningColumnSQL);
            deleteWinningColumn.setString(1, drawID);
            deleteWinningColumn.setString(2, gameID);
            deletedDraws = deleteWinningColumn.executeUpdate();
            connection.commit();
            connection.close();

            if (deletedDraws < 1) {
                throw new NotFoundEntityException("Δεν βρέθηκαν δεδομένα στην βάση με τα επιλεγμένα κριτήρια.");
            }
        } catch (SQLException ex) {
            if (connection != null) {
                System.err.print("Transaction is being rolled back");
                connection.rollback();
                deletedDraws = 0;
            }
            throw (ex);
        }
        return deletedDraws;
    }

    /*
      Delete for selected Draw ID
      Using transaction control to avoid inconsistency among the tables!
     */
    public static int deleteDrawDataWithDateRange(String gameID, String dateFrom, String dateTo) throws SQLException, NotFoundEntityException {
        AggregatedDrawData aggregatedDrawData = null;
        int deletedDraws = 0;
        String deleteGameDrawResultsSQL = "Delete\n"
                + "From TZOKERSTATS.GAME_DRAW_RESULTS\n"
                + "Where game_id = ?\n"
                + "and DRAW_ID in (\n"
                + "    SELECT DRAW_ID \n"
                + "    FROM TZOKERSTATS.WINNING_COLUMN \n"
                + "    where game_id = ? \n"
                + "    and cast(draw_time as date) between ? and ?\n"
                + ")";
        String deleteWinningColumnSQL = "Delete \n"
                + "    FROM TZOKERSTATS.WINNING_COLUMN \n"
                + "    where game_id = ? \n"
                + "    and cast(draw_time as date) between ? and ?";
        Connection connection = null;
        try {
            connection = connect();
            connection.setAutoCommit(false);
            //Delete statement for GAME_DRAW_RESULTS table
            PreparedStatement deleteGameDrawResults = connection.prepareStatement(deleteGameDrawResultsSQL);
            deleteGameDrawResults.setString(1, gameID);
            deleteGameDrawResults.setString(2, gameID);
            deleteGameDrawResults.setString(3, dateFrom);
            deleteGameDrawResults.setString(4, dateTo);
            deleteGameDrawResults.executeUpdate();

            //Delete statement for WINNING_COLUMN table
            PreparedStatement deleteWinningColumn = connection.prepareStatement(deleteWinningColumnSQL);
            deleteWinningColumn.setString(1, gameID);
            deleteWinningColumn.setString(2, dateFrom);
            deleteWinningColumn.setString(3, dateTo);
            deletedDraws = deleteWinningColumn.executeUpdate();
            connection.commit();
            connection.close();

            if (deletedDraws < 1) {
                throw new NotFoundEntityException("Δεν βρέθηκαν δεδομένα στην βάση με τα επιλεγμένα κριτήρια.");
            }
        } catch (SQLException ex) {
            if (connection != null) {
                System.err.print("Transaction is being rolled back");
                connection.rollback();
                deletedDraws = 0;
            }
            throw (ex);
        }
        return deletedDraws;
    }

    static public void deleteALLDrawResults() {
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        //We should write both tables to one transaction
        //to avoid inconsistencies.        
        //Begin transaction
        try {
            em.getTransaction().begin();
            em.createNativeQuery("truncate table TZOKERSTATS.WINNING_COLUMN").executeUpdate();
            em.createNativeQuery("truncate table TZOKERSTATS.GAME_DRAW_RESULTS").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error during DB delete");
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
            emf.close();
        }
    }

    //Check for rows in table by draw ID
    //It is being used inside this class for checking the existance
    //of a record before the execution of an insert operation.
    static private boolean existsDrawResults(GameDrawResults gameDrawResults) {
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        int drawID = gameDrawResults.getGameDrawResultsPK().getDrawId();
        int gameID = gameDrawResults.getGameDrawResultsPK().getGameId();
        //prepare query
        Query q = em.createNamedQuery("GameDrawResults.findByDrawId", GameDrawResults.class);
        q.setParameter("drawId", drawID);
        q.setParameter("gameId", gameID);
        ArrayList<GameDrawResults> eList = new ArrayList<GameDrawResults>(q.getResultList());

        em.close();
        emf.close();

        if (eList.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    //Check for rows in table by draw ID
    static private boolean existsWinningColumn(WinningColumn winningColumn) {
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        int drawID = winningColumn.getWinningColumnPK().getDrawId();
        int gameID = winningColumn.getWinningColumnPK().getGameId();
        //prepare query
        Query q = em.createNamedQuery("WinningColumn.findByDrawId", WinningColumn.class);
        q.setParameter("drawId", drawID);
        q.setParameter("gameId", gameID);

        ArrayList<WinningColumn> eList = new ArrayList<WinningColumn>(q.getResultList());

        em.close();
        emf.close();

        if (eList.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    //Get Aggregated data for a game in date range
    public static AggregatedDrawData getDataForMonth(int gameID, String dateFrom, String dateTo) throws SQLException {

        AggregatedDrawData aggregatedDrawData = null;

        try {
            Connection connection = connect();

            String selectSQL = "SELECT count(DISTINCT t1.DRAW_ID) As DRAW_COUNT\n"
                    + "      ,SUM(SUCCESSES_CNT * DIVIDENT_AMN) As PAYOUT_AMN\n"
                    + "      ,SUM(CASE When WINNING_CATEGORY_DESCR in ('1') And SUCCESSES_CNT = 0 Then 1 Else 0 END) As JACKPOTS_COUNT\n"
                    + "  From TZOKERSTATS.GAME_DRAW_RESULTS t1\n"
                    + " Inner\n"
                    + "  Join TZOKERSTATS.WINNING_COLUMN t2\n"
                    + "    On t1.DRAW_ID = t2.DRAW_ID \n"
                    + "   And t1.GAME_ID = t2.GAME_ID\n"
                    + " Where Cast(t2.DRAW_TIME as Date) Between ? And ? And t2.GAME_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, dateFrom);
            preparedStatement.setString(2, dateTo);
            preparedStatement.setString(3, String.valueOf(gameID));
            ResultSet resultSet = preparedStatement.executeQuery();
            int drawCount = 0;
            double payout = 0;
            int jackpotCount = 0;

            if (resultSet.next()) {
                drawCount = resultSet.getInt("DRAW_COUNT");
                payout = resultSet.getDouble("PAYOUT_AMN");
                jackpotCount = resultSet.getInt("JACKPOTS_COUNT");
            }
            preparedStatement.close();
            connection.close();
            aggregatedDrawData = new AggregatedDrawData(drawCount, payout, jackpotCount);

        } catch (SQLException ex) {
            throw (ex);
        }
        return aggregatedDrawData;

    }

    //Get Aggregated data for a game by drawid in date range
    public static List<AggregatedDrawDataByDraw> getAggregatedDrawDataByDraw(int gameID, String dateFrom, String dateTo) throws SQLException {

        List<AggregatedDrawDataByDraw> aggregatedDrawDataByDrawList = new ArrayList<>();

        try {
            Connection connection = connect();

            String selectSQL = "SELECT t1.DRAW_ID As DRAW_ID\n"
                    + "      ,SUM(SUCCESSES_CNT * DIVIDENT_AMN) As PAYOUT_AMN\n"
                    + "      ,SUM(CASE When WINNING_CATEGORY_DESCR in ('1') And SUCCESSES_CNT = 0 Then 1 Else 0 END) As JACKPOTS_COUNT\n"
                    + "  From TZOKERSTATS.GAME_DRAW_RESULTS t1\n"
                    + " Inner\n"
                    + "  Join TZOKERSTATS.WINNING_COLUMN t2\n"
                    + "    On t1.DRAW_ID = t2.DRAW_ID \n"
                    + "   And t1.GAME_ID = t2.GAME_ID\n"
                    + " Where Cast(t2.DRAW_TIME as Date) Between ? And ? And t2.GAME_ID = ? \n"
                    + " Group\n"
                    + "    By t1.DRAW_ID\n"
                    + " Order \n"
                    + "    By t1.DRAW_ID\n";

            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, dateFrom);
            preparedStatement.setString(2, dateTo);
            preparedStatement.setString(3, String.valueOf(gameID));
            ResultSet resultSet = preparedStatement.executeQuery();
            int drawID = 0;
            double payout = 0;
            int jackpotCount = 0;

            while (resultSet.next()) {
                drawID = resultSet.getInt("DRAW_ID");
                payout = resultSet.getDouble("PAYOUT_AMN");
                jackpotCount = resultSet.getInt("JACKPOTS_COUNT");
                AggregatedDrawDataByDraw aggregatedDrawDataByDraw = new AggregatedDrawDataByDraw(drawID, payout, jackpotCount);
                aggregatedDrawDataByDrawList.add(aggregatedDrawDataByDraw);
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            throw (ex);
        }
        return aggregatedDrawDataByDrawList;

    }

    //Get Aggregated data for a game in date range
    public static List<AggregatedDrawDataByMonth> getAggregatedDrawDataByMonth(int gameID, String dateFrom, String dateTo) throws SQLException {

        List<AggregatedDrawDataByMonth> aggregatedDrawDataByMonthList = new ArrayList<>();

        try {
            Connection connection = connect();

            String selectSQL = "SELECT month(t2.draw_time) As MONTH_ID\n"
                    + "      ,SUM(SUCCESSES_CNT * DIVIDENT_AMN) As PAYOUT_AMN\n"
                    + "      ,SUM(CASE When WINNING_CATEGORY_DESCR in ('1') And SUCCESSES_CNT = 0 Then 1 Else 0 END) As JACKPOTS_COUNT\n"
                    + "  From TZOKERSTATS.GAME_DRAW_RESULTS t1\n"
                    + " Inner\n"
                    + "  Join TZOKERSTATS.WINNING_COLUMN t2\n"
                    + "    On t1.DRAW_ID = t2.DRAW_ID \n"
                    + "   And t1.GAME_ID = t2.GAME_ID\n"
                    + " Where Cast(t2.DRAW_TIME as Date) Between ? And ? And t2.GAME_ID = ? \n"
                    + " Group\n"
                    + "    By month(t2.draw_time)\n"
                    + " Order \n"
                    + "    By month(t2.draw_time)\n";

            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, dateFrom);
            preparedStatement.setString(2, dateTo);
            preparedStatement.setString(3, String.valueOf(gameID));
            ResultSet resultSet = preparedStatement.executeQuery();
            int monthID = 0;
            double payout = 0;
            int jackpotCount = 0;

            while (resultSet.next()) {
                monthID = resultSet.getInt("MONTH_ID");
                payout = resultSet.getDouble("PAYOUT_AMN");
                jackpotCount = resultSet.getInt("JACKPOTS_COUNT");
                AggregatedDrawDataByMonth aggregatedDrawDataByMonth = new AggregatedDrawDataByMonth(monthID, payout, jackpotCount);
                aggregatedDrawDataByMonthList.add(aggregatedDrawDataByMonth);
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            throw (ex);
        }
        return aggregatedDrawDataByMonthList;

    }

    static public ArrayList<WinningColumn> getWinningColumnByDateRange(Date drawTimeFrom, Date drawTimeTo) {
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        //prepare query
        Query q = em.createNamedQuery("WinningColumn.findByDrawTimeRange", WinningColumn.class);
        q.setParameter("drawTimeFrom", drawTimeFrom);
        q.setParameter("drawTimeTo", drawTimeTo);
        ArrayList<WinningColumn> resultsList = new ArrayList<WinningColumn>(q.getResultList());

        em.close();
        emf.close();

        return resultsList;
    }
    
    static public ArrayList<ArrayList<GameDrawResults>> getDrawResultsByDrawId(ArrayList<Integer> drawIds, int gameID){
        ArrayList<ArrayList<GameDrawResults> > totalGameDrawResults = new ArrayList<ArrayList<GameDrawResults> >();
        //Create entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TzokerStatsPU");
        // Create EntityManager
        EntityManager em = emf.createEntityManager();
        for (Integer drawId : drawIds){
            //prepare query
            Query q = em.createNamedQuery("GameDrawResults.findByDrawId", GameDrawResults.class);
            q.setParameter("drawId",drawId);
            q.setParameter("gameId", gameID);
            ArrayList<GameDrawResults> singleGameDrawResults = new ArrayList<GameDrawResults>(q.getResultList());
            totalGameDrawResults.add(singleGameDrawResults);
        }
        em.close();
        emf.close();
        
        return totalGameDrawResults;
    }
    

    public enum Operation {
        FORCE_DELETE,
        DELETE,
        INSERT,
        FORCE_INSERT
    }

    public static class AggregatedDrawData {

        private int numberOfDraws;
        private int numberOfJackpots;
        private double payoutAmount;

        public AggregatedDrawData(int numberOfDraws, double payoutAmount, int numberOfJackpots) {
            this.numberOfDraws = numberOfDraws;
            this.numberOfJackpots = numberOfJackpots;
            this.payoutAmount = payoutAmount;
        }

        public int getNumberOfDraws() {
            return numberOfDraws;
        }

        public void setNumberOfDraws(int numberOfDraws) {
            this.numberOfDraws = numberOfDraws;
        }

        public int getNumberOfJackpots() {
            return numberOfJackpots;
        }

        public void setNumberOfJackpots(int numberOfJackpots) {
            this.numberOfJackpots = numberOfJackpots;
        }

        public double getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(double payoutAmount) {
            this.payoutAmount = payoutAmount;
        }
    }

    public static class AggregatedDrawDataByDraw {

        private int drawID;
        private int numberOfJackpots;
        private double payoutAmount;

        public AggregatedDrawDataByDraw(int drawID, double payoutAmount, int numberOfJackpots) {
            this.drawID = drawID;
            this.numberOfJackpots = numberOfJackpots;
            this.payoutAmount = payoutAmount;
        }

        public int getDrawID() {
            return drawID;
        }

        public void setDrawID(int drawID) {
            this.drawID = drawID;
        }

        public int getNumberOfJackpots() {
            return numberOfJackpots;
        }

        public void setNumberOfJackpots(int numberOfJackpots) {
            this.numberOfJackpots = numberOfJackpots;
        }

        public double getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(double payoutAmount) {
            this.payoutAmount = payoutAmount;
        }

    }

    public static class AggregatedDrawDataByMonth {

        private int monthID;
        private int numberOfJackpots;
        private double payoutAmount;

        public AggregatedDrawDataByMonth(int monthID, double payoutAmount, int numberOfJackpots) {
            this.monthID = monthID;
            this.numberOfJackpots = numberOfJackpots;
            this.payoutAmount = payoutAmount;
        }

        public int getMonthID() {
            return monthID;
        }

        public void setMonthID(int drawID) {
            this.monthID = drawID;
        }

        public int getNumberOfJackpots() {
            return numberOfJackpots;
        }

        public void setNumberOfJackpots(int numberOfJackpots) {
            this.numberOfJackpots = numberOfJackpots;
        }

        public double getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(double payoutAmount) {
            this.payoutAmount = payoutAmount;
        }

    }

    //Create and return a new connection to the L10DB
    private static Connection connect() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw (ex);
        }
        return conn;
    }
}
