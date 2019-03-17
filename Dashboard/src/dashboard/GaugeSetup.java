package dashboard;

import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GaugeSetup extends JPanel{

    private String title;
    private JLabel gaugeName;
    
    private AbstractGauge gauge;

    public GaugeSetup(String name, String type) {
        if (!name.isEmpty() && !type.isEmpty()) {
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
    private void buildPanel(String name, String type)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setTitle(name);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gaugeName = Helpers.createLabel(name);
        
        gauge = GaugeFactory.createRadialGauge(type);
        gauge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                gaugeMouseClicked(evt);
            }
        });
        if (gauge instanceof LinearBargraph)
        {
            gauge.setPreferredSize(new Dimension(150,350));
        } else
        {
            gauge.setPreferredSize(new Dimension(350,350));
        }
        setGaugeTitle(title);
        add(gaugeName);
        add(gauge);
    }
    
    private void gaugeMouseClicked(MouseEvent evt) {                                         
        gaugeName.setText(gaugeName.getText().toUpperCase());
    } 
    
    //create gauge and label if no parameters defined
    private void initialiseEmptyPanel()
    {
        gaugeName = Helpers.createLabel("Default");
        setTitle("Default");
        
        gauge = GaugeFactory.createRadialGauge("");
        gauge.setPreferredSize(new Dimension(350,350));
        setGaugeTitle(title);
        
        add(gaugeName);
        add(gauge);
        
    }
    
    public void setGaugeUnit(String unit) {
        gauge.setUnitString(unit);
    }

    public void setGaugeTitle(String title) {
        gauge.setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public AbstractGauge getGauge()
    {
        return gauge;
    }

}
