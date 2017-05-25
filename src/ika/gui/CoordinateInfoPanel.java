/*
 * CoordinateInfoPanel.java
 *
 * Created on July 1, 2005, 3:05 PM
 */
package ika.gui;

import ika.map.tools.*;
import ika.mapanalyst.Manager;
import ika.utils.CoordinateFormatter;
import java.text.*;

/**
 *
 * @author jenny
 */
public class CoordinateInfoPanel extends javax.swing.JPanel
        implements MeasureToolListener, MapToolMouseMotionListener {

    private static final DecimalFormat angleFormatter
            = new DecimalFormat("###,##0.0");

    private Manager manager = null;

    /**
     * Creates new form CoordinateInfoPanel
     */
    public CoordinateInfoPanel() {
        initComponents();
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        xCoordLabel = new javax.swing.JLabel();
        yCoordLabel = new javax.swing.JLabel();
        distTextLabel = new javax.swing.JLabel();
        distLabel = new javax.swing.JLabel();
        angleTextLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        xCoordLabel.setFont(xCoordLabel.getFont().deriveFont(xCoordLabel.getFont().getSize()-2f));
        xCoordLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        xCoordLabel.setToolTipText("Horizontal coordinate of the mouse pointer.");
        xCoordLabel.setPreferredSize(new java.awt.Dimension(130, 13));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(xCoordLabel, gridBagConstraints);

        yCoordLabel.setFont(yCoordLabel.getFont().deriveFont(yCoordLabel.getFont().getSize()-2f));
        yCoordLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        yCoordLabel.setToolTipText("Vertical coordinate of the mouse pointer.");
        yCoordLabel.setPreferredSize(new java.awt.Dimension(130, 13));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(yCoordLabel, gridBagConstraints);

        distTextLabel.setFont(distTextLabel.getFont().deriveFont(distTextLabel.getFont().getSize()-2f));
        distTextLabel.setText("Distance : ");
        distTextLabel.setToolTipText("The last distance measured with the Measure Distance and Angle tool.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        add(distTextLabel, gridBagConstraints);

        distLabel.setFont(distLabel.getFont().deriveFont(distLabel.getFont().getSize()-2f));
        distLabel.setText("-");
        distLabel.setToolTipText("The last distance measured with the Measure Distance and Angle tool.");
        distLabel.setPreferredSize(new java.awt.Dimension(120, 13));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        add(distLabel, gridBagConstraints);

        angleTextLabel.setFont(angleTextLabel.getFont().deriveFont(angleTextLabel.getFont().getSize()-2f));
        angleTextLabel.setText("Angle : ");
        angleTextLabel.setToolTipText("The last angle measured with the Measure Distance and Angle tool.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        add(angleTextLabel, gridBagConstraints);

        angleLabel.setFont(angleLabel.getFont().deriveFont(angleLabel.getFont().getSize()-2f));
        angleLabel.setText("-");
        angleLabel.setToolTipText("The last angle measured with the Measure Distance and Angle tool.");
        angleLabel.setPreferredSize(new java.awt.Dimension(120, 13));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        add(angleLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void clearDistance() {
        this.distLabel.setText("-");
        this.angleLabel.setText("-");
    }

    @Override
    public void distanceChanged(double distance, double angle,
            ika.gui.MapComponent mapComponent) {

        // distance
        CoordinateFormatter coordFormatter = mapComponent.getCoordinateFormatter();
        this.distLabel.setText(coordFormatter.format(distance));

        // angle
        double azimuth = -Math.toDegrees(angle) + 90.;
        if (azimuth < 0) {
            azimuth += 360;
        }
        String angleStr = angleFormatter.format(azimuth);
        this.angleLabel.setText("<HTML>" + angleStr + "&#176</HTML>");
    }

    @Override
    public void positionChanged(double x1, double y1, double x2, double y2, MapComponent mapComponent) {
        final double dx = x2 - x1;
        final double dy = y2 - y1;
        final double d = Math.hypot(dx, dy);
        final double angle = Math.atan2(dy, dx);

        /*double[][] localOSMPoints = null;
        if (true) {
            localOSMPoints = linkManager.getLinkedPoints()[1];
            OSMCoordinateConverter.fromOSM(localOSMPoints, localOSMPoints, null);
        }

        System.out.println("distanceChanged");*/
    }

    private void updateCoordinates(java.awt.geom.Point2D.Double point,
            ika.gui.MapComponent mapComponent) {
        if (point != null) {
            boolean osm = "new".equals(mapComponent.getName()) 
                    && manager != null && manager.isUsingOpenStreetMap();
            String[] str = mapComponent.coordinatesStrings(point, osm);
            xCoordLabel.setText(str[0]);
            yCoordLabel.setText(str[1]);
        } else {
            this.xCoordLabel.setText("-");
            this.yCoordLabel.setText("-");
        }
    }

    @Override
    public void mouseMoved(java.awt.geom.Point2D.Double point,
            ika.gui.MapComponent mapComponent) {
        updateCoordinates(point, mapComponent);
    }

    public void registerWithMapComponent(MapComponent mapComponent) {
        mapComponent.addMouseMotionListener(this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel angleTextLabel;
    private javax.swing.JLabel distLabel;
    private javax.swing.JLabel distTextLabel;
    private javax.swing.JLabel xCoordLabel;
    private javax.swing.JLabel yCoordLabel;
    // End of variables declaration//GEN-END:variables

}
