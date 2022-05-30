package DataIngestion;

import java.util.ArrayList;


public class SingleDrawResults {

    public int gameId;
    public int drawId;
    public long drawTime;
    public String status;
    public int drawBreak;
    public int visualDraw;
    public PricePoints pricePoints;
    public WinningNumbers winningNumbers;
    public ArrayList<PrizeCategory> prizeCategories;
    public WagerStatistics wagerStatistics;

    public class PricePoints {

        public double amount;
    }

    public class WinningNumbers {

        public ArrayList<Integer> list;
        public ArrayList<Integer> bonus;
    }

    public class PrizeCategory {

        public int id;
        public double divident;
        public int winners;
        public double distributed;
        public double jackpot;
        public double fixed;
        public int categoryType;
        public String gameType;
        public double minimumDistributed;
    }

    public class WagerStatistics {

        public int columns;
        public int wagers;
        public ArrayList<Object> addOn;
    }

}
