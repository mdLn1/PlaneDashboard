package Threading;

import eu.hansolo.steelseries.gauges.AbstractGauge;

public class UpdateGaugeThread extends Thread {

    
    private AbstractGauge gauge;
    private double value;
    
    public UpdateGaugeThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
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
