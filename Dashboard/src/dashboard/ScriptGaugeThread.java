package dashboard;

import eu.hansolo.steelseries.gauges.AbstractGauge;

public class ScriptGaugeThread extends Thread{
    
    private AbstractGauge gauge;
    private double value;
    
    public ScriptGaugeThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
    @Override
    public void run() {
        double x = gauge.getValue();
        if (x < value)
        {
            while (x < value) {
            x += 1.0;
            gauge.setValue(x);
            try {
                Thread.sleep(100);
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
                Thread.sleep(100);
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        }
        
    }
}
