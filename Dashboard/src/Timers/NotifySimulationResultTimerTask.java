package Timers;

import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// timer used for displaying message dialog box to let the user know the outcome
// of the simulated flight
public class NotifySimulationResultTimerTask extends TimerTask{
    
    private final JFrame container;
    private final Object message;
    private final String title;
    private final int messageType;
    
    public NotifySimulationResultTimerTask(JFrame container, Object message, String title, int messageType){
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
