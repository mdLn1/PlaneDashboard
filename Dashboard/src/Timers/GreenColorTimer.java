package Timers;

import eu.hansolo.steelseries.extras.TrafficLight;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreenColorTimer extends TimerTask{

    private final TrafficLight trafficLight;
    
    public GreenColorTimer(TrafficLight trafficLight){
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
            Logger.getLogger(GreenColorTimer.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            trafficLight.setGreenOn(false);
        }
        
    }
    
}
