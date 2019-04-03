/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import eu.hansolo.steelseries.gauges.AbstractGauge;

/**
 *
 * @author cp3526m
 */
public class SlowValueThread implements Runnable {

    private AbstractGauge gauge;
    private double value;
    
    public SlowValueThread(AbstractGauge gauge, double value) {
        this.gauge = gauge;
        this.value = value;
    }
    
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
            } catch (Exception ex) {
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
            } catch (Exception ex) {
                System.out.println("thread blocked from sleep");
            }
        }
        }
        
    }
    
}
