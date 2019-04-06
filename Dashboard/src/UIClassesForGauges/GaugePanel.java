package UIClassesForGauges;

import DesignPatterns.GaugeFactory;
import DesignPatterns.Helpers;
import Interfaces.SetPanel;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

// super class for RegularGauge and SpecialisedGauge classes
public class GaugePanel extends JPanel implements SetPanel{

    // title property
    private String title;
    
    //GUI components of the panel
    private JLabel gaugeName;
    AbstractGauge gauge;

    // constructor with two parameters
    public GaugePanel(String name, String type) {
        // if nothing is passed in the constructor then do the same as the no-parameter constructor
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
    
    // no parameters constructor
    public GaugePanel() {
        initialiseEmptyPanel();
    }
    
    //create dial and label defined by name and type
    @Override
    public void buildPanel(String name, String type)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gaugeName = Helpers.createLabel(name);
        
        gauge = (AbstractGauge) GaugeFactory.createDial(type);
        
        // if the mentioned type is not found, call initialise empty panel
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
    
    
    //create default gauge and label if no parameters defined
    @Override
    public void initialiseEmptyPanel()
    {
        gaugeName = Helpers.createLabel("Default");
        
        gauge = new Radial();
        
        gauge.setPreferredSize(new Dimension(300,300));
        setTitle("Default");
        add(gaugeName);
        add(gauge);
        
    }
    
    // set the unit for dial
    public void setGaugeUnit(String unit) {
        gauge.setUnitString(unit);
    }


    // return the title of the panel
    @Override
    public String getTitle() {
        return title;
    }

    // set the tile of the panel, also change the one of the gauge
    @Override
    public void setTitle(String title) {
        this.title = title;
        gaugeName.setText(title);
        gauge.setTitle(title);
    }
    
    // return the gauge
    @Override
    public AbstractGauge getGauge()
    {
        return gauge;
    }
   

}
