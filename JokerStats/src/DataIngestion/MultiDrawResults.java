package DataIngestion;

import java.util.ArrayList;

public class MultiDrawResults {

    public ArrayList<Content> content;
    public int totalPages;
    public int totalElements;
    public boolean last;
    public int numberOfElements;
    public ArrayList<Sort> sort;
    public boolean first;
    public int size;
    public int number;

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

    public class Content {

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
    }

    public class Sort {

        public String direction;
        public String property;
        public boolean ignoreCase;
        public String nullHandling;
        public boolean descending;
        public boolean ascending;
    }

}
