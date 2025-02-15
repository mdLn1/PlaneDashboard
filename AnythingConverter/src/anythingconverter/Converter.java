package anythingconverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Converter extends javax.swing.JPanel {

    private double SCALE = 1.0;
    NumberFormat formatter = new DecimalFormat("#0.##");
    
    public Converter() {
        initComponents();
    }
    
    public void setScale(double scale)
    {
        SCALE = scale;
    }
    
    public double getScale()
    {
        return SCALE;
    }
    
    public String getConverterName()
    {
        return converterNameLabel.getText();
    }
    
    public void setConverterName(String name)
    {
        converterNameLabel.setText(name);
    }
    
    
    public String getUnit1()
    {
        return unit1.getText();
    }
    
    public String getUnit2()
    {
        return unit2.getText();
    }
    
    
    public void setUnit1(String unitOne)
    {
        unit1.setText(unitOne);
    }
    
    public void setUnit2(String unitTwo)
    {
        unit2.setText(unitTwo);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        value1 = new javax.swing.JTextField();
        converterNameLabel = new javax.swing.JLabel();
        converterLabel = new javax.swing.JLabel();
        value2 = new javax.swing.JTextField();
        unit1 = new javax.swing.JLabel();
        unit2 = new javax.swing.JLabel();

        value1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        value1.setText("value1");
        value1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                value1ActionPerformed(evt);
            }
        });

        converterNameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        converterNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        converterNameLabel.setText("converterLabel");

        converterLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        converterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        converterLabel.setText("Converter");

        value2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        value2.setText("value2");
        value2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                value2ActionPerformed(evt);
            }
        });

        unit1.setText("unit1");

        unit2.setText("unit2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(converterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(converterNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(value2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(unit2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(value1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(unit1)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(converterNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(converterLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unit1)
                    .addComponent(value1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(value2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unit2))
                .addGap(0, 16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void value1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_value1ActionPerformed
        try {
        value2.setText(String.valueOf(formatter.format(Double.parseDouble(value1.getText()) * SCALE)));
        } catch(Exception ex)
        {
            value2.setText("Invalid data");
        }
    }//GEN-LAST:event_value1ActionPerformed

    private void value2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_value2ActionPerformed
        try{
        value1.setText(String.valueOf(formatter.format(Double.parseDouble(value1.getText()) / SCALE)));
        } catch(Exception ex)
        {
            value2.setText("Invalid data");
        }
    }//GEN-LAST:event_value2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel converterLabel;
    private javax.swing.JLabel converterNameLabel;
    private javax.swing.JLabel unit1;
    private javax.swing.JLabel unit2;
    private javax.swing.JTextField value1;
    private javax.swing.JTextField value2;
    // End of variables declaration//GEN-END:variables
}
