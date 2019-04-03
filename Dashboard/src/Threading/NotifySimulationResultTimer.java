/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import eu.hansolo.steelseries.extras.TrafficLight;
import java.util.TimerTask;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author cp3526m
 */
public class NotifySimulationResultTimer extends TimerTask{
    private JFrame container;
    private Object message;
    private String title;
    private int messageType;
    
    public NotifySimulationResultTimer(JFrame container, Object message, String title, int messageType){
        this.container = container;
        this.message = message;
        this.title = title;
        this.messageType = messageType;
    }
    
    @Override
    public void run() {
        JOptionPane.showMessageDialog(container, message, title, messageType);
    }
}
