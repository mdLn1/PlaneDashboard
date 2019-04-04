package Threading;

import eu.hansolo.steelseries.gauges.AbstractGauge;

// do a slow updating, designed for fuel dial
public class SlowValueThread implements Runnable {

    private AbstractGauge gauge;
    private double value;
    
    public SlowValueThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
    // on run check if the new value is bigger or smaller than the previous one
    @Override
    public void run() {
        double x = gauge.getValue();
        if (x < value)
        {
            while (x < value) {
            x += 0.6;
            gauge.setValue(x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        } else if (x > value)
        {
            while (x > value) {
            x -= 0.6;
            gauge.setValue(x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        }
        
    }
    
}
