/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import eu.hansolo.steelseries.extras.TrafficLight;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cp3526m
 */
public class GreenColorTimer extends TimerTask{

    private TrafficLight trafficLight;
    
    public GreenColorTimer(TrafficLight trafficLight){
        this.trafficLight = trafficLight;
    }
    
    @Override
    public void run() {
        
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
