package DataIngestion;

import java.util.ArrayList;

public class WinningColumnStats{
    public Header header;
    public ArrayList<Number> numbers;
    public ArrayList<BonusNumber> bonusNumbers;

    public class Header{
        public int dateFrom;
        public int dateTo;
        public int drawCount;
    }

    public class Number{
        public int occurrences;
        public int delays;
        public int number;
    }

    public class BonusNumber{
        public int occurrences;
        public int delays;
        public int number;
    }
}
