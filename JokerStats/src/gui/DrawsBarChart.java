package gui;

import DataIngestion.DBIntegrator;
import DataIngestion.DBIntegrator.AggregatedDrawDataByDraw;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleInsets;

public class DrawsBarChart extends javax.swing.JPanel {

    private String fromDate;
    private String toDate;
    private String monthName;
    private int gameID;

    public DrawsBarChart(int gameID, String fromDate, String toDate, String monthName) throws SQLException {
        this.gameID = gameID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.monthName = monthName;
        initUI();
    }

    private void initUI() throws SQLException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<AggregatedDrawDataByDraw> aggregatedDrawDataByDrawList = new ArrayList<>();
        //Prepare data set        
        aggregatedDrawDataByDrawList = DBIntegrator.getAggregatedDrawDataByDraw(gameID, fromDate, toDate);
        for (AggregatedDrawDataByDraw aggregatedDrawDataByDraw : aggregatedDrawDataByDrawList) {
            dataset.addValue(aggregatedDrawDataByDraw.getPayoutAmount(),
                    monthName,
                    String.valueOf(aggregatedDrawDataByDraw.getDrawID()));
        }

        //Create chart  
        JFreeChart chart = ChartFactory.createBarChart(
                "Κέρδη ανα Κλήρωση", //Chart Title  
                "",//Κληρώσεις", // Category axis  
                "",//Κέρδη", // Value axis  
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

         StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
        theme.setRangeGridlinePaint( new java.awt.Color(192, 192, 192));
        theme.setPlotBackgroundPaint( Color.white );
        theme.setChartBackgroundPaint( Color.white );
        theme.setGridBandPaint( Color.red );
        theme.setAxisOffset( new RectangleInsets(0,0,0,0) );
        theme.setBarPainter(new StandardBarPainter());
//        theme.setAxisLabelPaint( new java.awt.Color(50, 122, 194) );
        theme.apply( chart );
        
        chart.getCategoryPlot().setOutlineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
        chart.getCategoryPlot().setRangeGridlineStroke( new BasicStroke() );
//        chart.getCategoryPlot().getRangeAxis().setTickLabelPaint( new java.awt.Color(50, 122, 194) );
//        chart.getCategoryPlot().getDomainAxis().setTickLabelPaint( new java.awt.Color(50, 122, 194) );
        
        chart.setTextAntiAlias( true );
        chart.setAntiAlias( true );
        chart.getCategoryPlot().getRenderer().setSeriesPaint( 0, new java.awt.Color(50, 122, 194));
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setShadowVisible( true );
        renderer.setShadowXOffset( 2 );
        renderer.setShadowYOffset( 0 );
        renderer.setShadowPaint( new java.awt.Color(192, 192, 192) );
        renderer.setMaximumBarWidth(0.06);
        
        chart.removeLegend();
        chart.setTitle("");

        ChartPanel cp = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(650, 150);
            }
        };
        cp.setMouseWheelEnabled(true);

        cp.addChartMouseListener(new ChartMouseListener() {

            public void chartMouseClicked(ChartMouseEvent e) {
                System.out.println(e.getEntity().getToolTipText());
            }

            public void chartMouseMoved(ChartMouseEvent e) {
            }

        });

        add(cp);
    }
}
