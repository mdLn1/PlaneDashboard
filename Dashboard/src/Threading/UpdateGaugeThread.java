package Threading;

import eu.hansolo.steelseries.gauges.AbstractGauge;

// thread which does not allow updating different dials at the same time
// mainly used when user edits the dial value
public class UpdateGaugeThread extends Thread {

    
    private AbstractGauge gauge;
    private double value;
    
    public UpdateGaugeThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
    // on run check if the new value is bigger or smaller than the previous one
    @Override
    public void run() {
        synchronized (AbstractGauge.class) {
        double x = gauge.getValue();
        if (x < value)
        {
            while (x < value) {
            x += 1.0;
            gauge.setValue(x);
            try {
                Thread.sleep(70);
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        } else if (x > value)
        {
            while (x > value) {
            x -= 1.0;
            gauge.setValue(x);
            try {
                Thread.sleep(70);
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        }
        }
    }
    
}
