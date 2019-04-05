package Timers;

import eu.hansolo.steelseries.extras.TrafficLight;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreenColorTimerTask extends TimerTask{

    private final TrafficLight trafficLight;
    
    public GreenColorTimerTask(TrafficLight trafficLight){
        this.trafficLight = trafficLight;
    }
    
    @Override
    public void run() {
        // do the simulation for the traffic light on take off
        try {
        trafficLight.setRedOn(false);
        trafficLight.setGreenOn(true);
        trafficLight.setYellowBlinking(false);
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GreenColorTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            trafficLight.setGreenOn(false);
        }
        
    }
    
}
