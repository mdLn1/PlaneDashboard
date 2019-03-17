/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import eu.hansolo.steelseries.gauges.AbstractRadial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

/**
 *
 * @author mp7857c
 */
public class GaugesListener extends MouseAdapter {
    
    AbstractRadial gauge;
    JLabel label;
    int iVal =0;
    public GaugesListener(JLabel j)
    {
        //gauge = gag;
        label = j;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       label.setText("fdasfdas" + iVal);
       iVal++;
    }
    
}
