package UIClasses;

import dashboard.GaugeFactory;
import dashboard.Helpers;
import Interfaces.SetPanel;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GaugeSetup extends JPanel implements SetPanel{

    private String title;
    private int id;
    private JLabel gaugeName;
    
    AbstractGauge gauge;

    public GaugeSetup(String name, String type) {
        
        if (!name.isEmpty() && !type.isEmpty()) {
            name = name.trim();
            type = type.trim();
            buildPanel(name, type);
            
        }
        else
        {
            initialiseEmptyPanel();
        }
        
    }
    
    public GaugeSetup() {
        initialiseEmptyPanel();
    }
    
    //create gauge and label defined by name and type
    @Override
    public void buildPanel(String name, String type)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gaugeName = Helpers.createLabel(name);
        
        gauge = (AbstractGauge) GaugeFactory.createRadialGauge(type);
        if (gauge == null)
        {
            initialiseEmptyPanel();
            return;
        }

        if (gauge instanceof LinearBargraph)
        {
            gauge.setPreferredSize(new Dimension(140,300));
        } else
        {
            gauge.setPreferredSize(new Dimension(300,300));
        }
        setTitle(name);
        setGaugeUnit("unit");
        
        add(gaugeName);
        add(gauge);
    }
    
    
    //create gauge and label if no parameters defined
    public void initialiseEmptyPanel()
    {
        gaugeName = Helpers.createLabel("Default");
        
        gauge = new Radial();
        
        gauge.setPreferredSize(new Dimension(300,300));
        setTitle("Default");
        add(gaugeName);
        add(gauge);
        
    }
    
    public void setGaugeUnit(String unit) {
        gauge.setUnitString(unit);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        gaugeName.setText(title);
        gauge.setTitle(title);
    }
    
    @Override
    public AbstractGauge getGauge()
    {
        return gauge;
    }
   

}
