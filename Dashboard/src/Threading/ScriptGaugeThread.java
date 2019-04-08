package Threading;

import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.AbstractGauge;

// synchronized thread that allows changing value on multiple gauges at the same time
public class ScriptGaugeThread extends Thread{
    
    private AbstractGauge gauge;
    private double value;
    
    public ScriptGaugeThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
    // on run check if the new value is bigger or smaller than the previous one
    @Override
    public void run() {
        double x = gauge.getValue();
        synchronized(this){
        if (x < value)
        {
            while (x < value) {
            x += 1.0;
            gauge.setValue(x);
            if (gauge instanceof WindDirection){
                        WindDirection gauge1 = (WindDirection) gauge;
                        gauge1.setLcdValue(x);
                    }
            try {
                Thread.sleep(50);
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        } else if (x > value)
        {
            while (x > value) {
            x -= 1.0;
            gauge.setValue(x);
            if (gauge instanceof WindDirection){
                        WindDirection gauge1 = (WindDirection) gauge;
                        gauge1.setLcdValue(x);
                    } 
            try {
                Thread.sleep(50);
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        }
        }
    }
}
