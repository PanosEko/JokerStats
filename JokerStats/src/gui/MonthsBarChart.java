package gui;

import DataIngestion.DBIntegrator;
import DataIngestion.DBIntegrator.AggregatedDrawDataByMonth;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Dimension;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleInsets;

public class MonthsBarChart extends javax.swing.JPanel {

    private String fromDate;
    private String toDate;
    private String monthName;
    private int gameID;
    private String year;
    private HashMap<Integer, String> monthTable;
    JPanel sourcePanel;

    public MonthsBarChart(int gameID, String fromDate, String toDate, String monthName, JPanel sourcePanel) throws SQLException {
        this.gameID = gameID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.monthName = monthName;
        this.year = fromDate.substring(0, 4);
        monthTable = new HashMap<>();
        this.sourcePanel = sourcePanel;
        initMonthMap();
        initUI();
    }

    private void initUI() throws SQLException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<AggregatedDrawDataByMonth> aggregatedDrawDataByMonthList = new ArrayList<>();
        //Prepare data set        
        aggregatedDrawDataByMonthList = DBIntegrator.getAggregatedDrawDataByMonth(gameID, fromDate, toDate);
        for (AggregatedDrawDataByMonth aggregatedDrawDataByMonth : aggregatedDrawDataByMonthList) {
            dataset.addValue(aggregatedDrawDataByMonth.getPayoutAmount(),
                    "Μήνας",
                    String.valueOf(aggregatedDrawDataByMonth.getMonthID()));
        }

        //Create chart  
        JFreeChart chart = ChartFactory.createBarChart(
                "Μήνας", //Chart Title  
                "",//Κληρώσεις", // Category axis  
                "",//Κέρδη", // Value axis  
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        //Trim chart
        chart.removeLegend();
        chart.setTitle("");
        
         StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
        theme.setRangeGridlinePaint( new java.awt.Color(192, 192, 192));
        theme.setPlotBackgroundPaint( Color.white );
        theme.setChartBackgroundPaint( Color.white );
        theme.setGridBandPaint( Color.red );
        theme.setAxisOffset( new RectangleInsets(0,0,0,0) );
        theme.setBarPainter(new StandardBarPainter());
        theme.apply( chart );
        
        chart.getCategoryPlot().setOutlineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
        chart.getCategoryPlot().setRangeGridlineStroke( new BasicStroke() );
        chart.setTextAntiAlias( true );
        chart.setAntiAlias( true );
        chart.getCategoryPlot().getRenderer().setSeriesPaint( 0, new java.awt.Color(50, 122, 194));
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setShadowVisible( true );
        renderer.setShadowXOffset( 2 );
        renderer.setShadowYOffset( 0 );
        renderer.setShadowPaint( new java.awt.Color(192, 192, 192) );
        System.out.println(aggregatedDrawDataByMonthList.size());
        if (aggregatedDrawDataByMonthList.size()<4){
            renderer.setMaximumBarWidth(0.2);
        }
        else if(aggregatedDrawDataByMonthList.size()<7){
            renderer.setMaximumBarWidth(0.1);
        }
        else{
            renderer.setMaximumBarWidth(0.06);
        }

        ChartPanel cp = new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(650, 150);
            }
        };
        cp.setMouseWheelEnabled(true);

        //Add on click listener to perform drill down
        cp.addChartMouseListener(new ChartMouseListener() {
            public void chartMouseClicked(ChartMouseEvent e) {
                //Drill Down to Month
                DiplayDataByDrawFrame diplayDataFrame = new DiplayDataByDrawFrame();
                diplayDataFrame.setYear(year);
                diplayDataFrame.setMonth(getSelectedMonth(e.getEntity().getToolTipText()));
                diplayDataFrame.execForm();
                final JDialog frame = new JDialog(diplayDataFrame, "Προβολή Δεδομένων Τζόκερ ανα μήνα", true);
                frame.setUndecorated(true); // <-- the title bar is removed here
                frame.getContentPane().add(diplayDataFrame.getContentPane());
                frame.setResizable(false);
                frame.pack();     
                Point point = sourcePanel.getLocationOnScreen();
                frame.setLocation(new Point(point.x, point.y));
                frame.setVisible(true);
            }

            public void chartMouseMoved(ChartMouseEvent e) {
            }
        });
        add(cp);
    }

    //Inditify Selected month and return it to client
    private int getSelectedMonth(String tooltipText) {
        //Get Month
        String[] month = tooltipText.split(",");
        int index = month[1].indexOf(')');
        String res = month[1].substring(1, index);
        int ss = Integer.parseInt(res.trim());
        return ss;
    }

    private void initMonthMap() {
        monthTable.put(1, "Ιανουάριος");
        monthTable.put(2, "Φεβρουάριος");
        monthTable.put(3, "Μάρτιος");
        monthTable.put(4, "Απρίλιος");
        monthTable.put(5, "Μάιος");
        monthTable.put(6, "Ιούνιος");
        monthTable.put(7, "Ιούλιος");
        monthTable.put(8, "Αύγουστος");
        monthTable.put(9, "Σεπτέμβριος");
        monthTable.put(10, "Οκτώβριος");
        monthTable.put(11, "Νοέμβριος");
        monthTable.put(12, "Δεκέμβριος");
    }
}
