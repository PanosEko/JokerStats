package gui;

import DataIngestion.WinningColumnStats;
import DataIngestion.WinningColumnStatsIntegrator;
import java.awt.Cursor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Panes
 */
public class StatsMainFrame extends javax.swing.JFrame {

    //Objectto manipulate the menu behaviour
    private MenuOverlay menuOverlay = new MenuOverlay();

    /**
     * Creates new form StatsDataFrame
     */
    public StatsMainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPnlMain = new javax.swing.JPanel();
        JPnlShowTable = new javax.swing.JPanel();
        JLblShowTableIcon = new javax.swing.JLabel();
        JLblShowTableText = new javax.swing.JLabel();
        JPnlShowStats = new javax.swing.JPanel();
        JLblShowStatsIcon = new javax.swing.JLabel();
        JLblShowStatsText = new javax.swing.JLabel();
        JPnlDisplay = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(190, 139, 6));

        JPnlMain.setBackground(new java.awt.Color(190, 139, 6));

        JPnlShowTable.setBackground(new java.awt.Color(190, 139, 6));
        JPnlShowTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JPnlShowTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPnlShowTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPnlShowTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPnlShowTableMouseExited(evt);
            }
        });

        JLblShowTableIcon.setBackground(new java.awt.Color(190, 139, 6));
        JLblShowTableIcon.setForeground(new java.awt.Color(190, 139, 6));
        JLblShowTableIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/table.png"))); // NOI18N

        JLblShowTableText.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JLblShowTableText.setForeground(new java.awt.Color(255, 255, 255));
        JLblShowTableText.setText("Προβολή σε Πίνακα");

        javax.swing.GroupLayout JPnlShowTableLayout = new javax.swing.GroupLayout(JPnlShowTable);
        JPnlShowTable.setLayout(JPnlShowTableLayout);
        JPnlShowTableLayout.setHorizontalGroup(
            JPnlShowTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlShowTableLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(JLblShowTableIcon)
                .addGap(18, 18, 18)
                .addComponent(JLblShowTableText, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JPnlShowTableLayout.setVerticalGroup(
            JPnlShowTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlShowTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JLblShowTableIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(JPnlShowTableLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(JLblShowTableText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        JPnlShowStats.setBackground(new java.awt.Color(190, 139, 6));
        JPnlShowStats.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JPnlShowStats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPnlShowStatsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPnlShowStatsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPnlShowStatsMouseExited(evt);
            }
        });

        JLblShowStatsIcon.setBackground(new java.awt.Color(190, 139, 6));
        JLblShowStatsIcon.setForeground(new java.awt.Color(190, 139, 6));
        JLblShowStatsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/stats-dots.png"))); // NOI18N

        JLblShowStatsText.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JLblShowStatsText.setForeground(new java.awt.Color(255, 255, 255));
        JLblShowStatsText.setText("Προβολή σε Γραφική Μορφή");

        javax.swing.GroupLayout JPnlShowStatsLayout = new javax.swing.GroupLayout(JPnlShowStats);
        JPnlShowStats.setLayout(JPnlShowStatsLayout);
        JPnlShowStatsLayout.setHorizontalGroup(
            JPnlShowStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlShowStatsLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(JLblShowStatsIcon)
                .addGap(18, 18, 18)
                .addComponent(JLblShowStatsText, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        JPnlShowStatsLayout.setVerticalGroup(
            JPnlShowStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlShowStatsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(JLblShowStatsText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(JLblShowStatsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JPnlDisplay.setBackground(new java.awt.Color(190, 139, 6));

        javax.swing.GroupLayout JPnlDisplayLayout = new javax.swing.GroupLayout(JPnlDisplay);
        JPnlDisplay.setLayout(JPnlDisplayLayout);
        JPnlDisplayLayout.setHorizontalGroup(
            JPnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        JPnlDisplayLayout.setVerticalGroup(
            JPnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout JPnlMainLayout = new javax.swing.GroupLayout(JPnlMain);
        JPnlMain.setLayout(JPnlMainLayout);
        JPnlMainLayout.setHorizontalGroup(
            JPnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlMainLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(JPnlShowTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(JPnlShowStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPnlDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPnlMainLayout.setVerticalGroup(
            JPnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPnlShowStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPnlShowTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPnlDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Displays Stats Table Frame, executes api call
    private void JPnlShowTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowTableMouseClicked

        if (!JPnlShowTable.isEnabled()) {
            return;
        }
        menuOverlay.setEnabled(JPnlShowTable, false);
        startWaiting((JPanel) evt.getSource());

        StatsTableFrame tableDataFrame = new StatsTableFrame();
        JPnlDisplay.removeAll();
        JPnlDisplay.repaint();
        JPnlDisplay.revalidate();

        try {
            WinningColumnStatsIntegrator winningColumnStatsIntegrator 
                    = new WinningColumnStatsIntegrator();
            // Setting api call parameters
            winningColumnStatsIntegrator.addAPIargument("gameID", "5104");
            WinningColumnStats winningColumnStats = new WinningColumnStats();
            // Execute api call to retrieve winning column statistics
            winningColumnStatsIntegrator.loadDataFromAPI();
            winningColumnStats = winningColumnStatsIntegrator.getTotalNumberStats();
            // Populate JTables with statistics data from response 
            tableDataFrame.fillTables(winningColumnStats);
            tableDataFrame.totaNumberStats = winningColumnStats;
            JPnlDisplay.add(tableDataFrame.getContentPane());
            JPnlDisplay.repaint();
            JPnlDisplay.revalidate();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την"
                    + " ανάγνωση των δεδομένων απο τo: "
                    + e.getMessage() + "\n          Ελέγξτε την σύνδεση σας.",
                    "Δεν Διαβάστηκαν Δεδομένα", 
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
            menuOverlay.setEnabled(JPnlShowTable, true);
        } finally {
            //Restore initial UI status
            stopWaiting();
            menuOverlay.setEnabled(JPnlShowStats, true);
        }
    }//GEN-LAST:event_JPnlShowTableMouseClicked

    private void JPnlShowTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowTableMouseEntered
        menuOverlay.setHilighted((JPanel) evt.getSource(), true);
    }//GEN-LAST:event_JPnlShowTableMouseEntered

    private void JPnlShowTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowTableMouseExited
        menuOverlay.setHilighted((JPanel) evt.getSource(), false);
    }//GEN-LAST:event_JPnlShowTableMouseExited

    private void JPnlShowStatsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowStatsMouseEntered
        menuOverlay.setHilighted((JPanel) evt.getSource(), true);
    }//GEN-LAST:event_JPnlShowStatsMouseEntered

    private void JPnlShowStatsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowStatsMouseExited
        menuOverlay.setHilighted((JPanel) evt.getSource(), false);
    }//GEN-LAST:event_JPnlShowStatsMouseExited
    // Displays Stats harts Frame
    private void JPnlShowStatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPnlShowStatsMouseClicked

        if (!JPnlShowStats.isEnabled()) {
            return;
        }
        menuOverlay.setEnabled(JPnlShowStats, false);
        startWaiting((JPanel) evt.getSource());
        StatsChartsFrame chartsDataFrame = new StatsChartsFrame();
        JPnlDisplay.removeAll();
        JPnlDisplay.repaint();
        JPnlDisplay.revalidate();

        JPnlDisplay.add(chartsDataFrame.getContentPane());
        JPnlDisplay.repaint();
        JPnlDisplay.revalidate();
        //Restore initial UI status
        stopWaiting();
        menuOverlay.setEnabled(JPnlShowTable, true);
    }//GEN-LAST:event_JPnlShowStatsMouseClicked

    private void startWaiting(JPanel panel) {
        JPnlMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        panel.grabFocus();
    }

    private void stopWaiting() {
        JPnlMain.setCursor(null); 
    }

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLblShowStatsIcon;
    private javax.swing.JLabel JLblShowStatsText;
    private javax.swing.JLabel JLblShowTableIcon;
    private javax.swing.JLabel JLblShowTableText;
    private javax.swing.JPanel JPnlDisplay;
    private javax.swing.JPanel JPnlMain;
    private javax.swing.JPanel JPnlShowStats;
    private javax.swing.JPanel JPnlShowTable;
    // End of variables declaration//GEN-END:variables
}
