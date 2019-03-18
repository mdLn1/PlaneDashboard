package dashboard;

import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.AbstractRadial;

public class UpdateGaugeThread extends Thread {

    
    private AbstractGauge gauge;
    private double value;
    
    public UpdateGaugeThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
    @Override
    public void run() {
        double x = gauge.getMinValue();
        while (x < value) {
            x += 0.5;
            gauge.setValue(x);
            try {
                Thread.sleep(50);
            } catch (Exception ex) {

            }
        }
    }
    
}
