package dashboard;

import eu.hansolo.steelseries.extras.TrafficLight;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class Dashboard implements FrameSetup {

    ContextStorage context;
    JFrame mainFrame;
    Container rightContainer;
    Container centerContainer;
    JLabel selectedGaugeLabel;
    
    RegularGauge windDir;
    
    JLabel unitLabel;
    JButton unitButton;
    JTextField unitTextField;
    
    JLabel titleLabel;
    JButton titleButton;
    JTextField titleTextField;

    public Dashboard() {
        createGUI();
    }

    @Override
    public void createGUI() {
        mainFrame = new JFrame("Plane Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(2000, 1000);
        mainFrame.setPreferredSize(mainFrame.getSize());
        mainFrame.setLayout(new BorderLayout());

        context = ContextStorage.getInstance();

        addComponentsToFrame(mainFrame.getContentPane());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void addComponentsToMainContainer(Container container) {

        windDir = new RegularGauge("Wind Direction", Helpers.DIRECTION_DIAL);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
//        c.gridx = 0;
//        c.gridy = 0;
//        container.add(windDir, c);
        context.addGauge(windDir, new PairHeads(0, 0));

        RegularGauge halfdial = new RegularGauge("Air Pressure", Helpers.HALF_DIAL);
//        c.gridx = 1;
//        c.gridy = 0;
//        container.add(halfdial, c);
        context.addGauge(halfdial, new PairHeads(1, 0));

        SpecialisedGauge radial = new SpecialisedGauge("Speed", Helpers.SIMPLER_RADIAL);
//        c.gridx = 2;
//        c.gridy = 0;
//        container.add(radial, c);
        context.addGauge(radial, new PairHeads(2, 0));

        SpecialisedGauge temperature = new SpecialisedGauge("Temperature", Helpers.LINEAR_BAR);
//        c.gridx = 0;
//        c.gridy = 1;
//        container.add(temperature, c);
        context.addGauge(temperature, new PairHeads(0, 1));

        RegularGauge quarterDial = new RegularGauge("Fuel", Helpers.QUARTER_DIAL);
//        c.gridx = 1;
//        c.gridy = 1;
//        container.add(quarterDial, c);
        context.addGauge(quarterDial, new PairHeads(1, 1));

        TrafficLight trafficLight = new TrafficLight();
        trafficLight.setPreferredSize(new Dimension(300, 300));
        c.gridx = 2;
        c.gridy = 1;

        container.add(trafficLight, c);

        for (Map.Entry<GaugeSetup, PairHeads> g : context.getGauges().entrySet()) {
            g.getKey().getGauge().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (g.getKey() instanceof SpecialisedGauge) {
                        selectedGaugeLabel.setText("SpecialisedGauge");
                        adjustRightContainer(g.getKey());
                        
                    } else if (g.getKey() instanceof RegularGauge) {
                        selectedGaugeLabel.setText("RegularGauge");
                    } else {
                        System.out.println("Got clicked");
                    }

                }

            });
            PairHeads positions = context.getConstraints(g.getKey().getTitle());
            c.gridx = positions.getStart();
            c.gridy = positions.getEnd();
            container.add(g.getKey(), c);

        }

    }
    
    public void adjustRightContainer(GaugeSetup gauge)
    {
        createRegularFormInput(gauge);
    }
    
    public void createRegularFormInput(GaugeSetup gauge)
    {
        String selected = selectedGaugeLabel.getText();
        JPanel boxedPanel = new JPanel();
        boxedPanel.setLayout(new GridBagLayout());
        //boxedPanel.setPreferredSize(new Dimension(250,400));
        boxedPanel.setAlignmentX(Component.TOP_ALIGNMENT);
        GridBagConstraints c = new GridBagConstraints();

        titleLabel = Helpers.createLabel("Set title for " + selected);
        titleLabel.setPreferredSize(new Dimension(240, 40));
        c = Helpers.addConstraints(0, 0,1.0,0.8);
        c.fill = GridBagConstraints.HORIZONTAL;
        boxedPanel.add(titleLabel, c);

        JTextField titleTextField = Helpers.createTextField("");
        c = Helpers.addConstraints(0, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        boxedPanel.add(titleTextField, c);

        JButton saveDetailsGauge = Helpers.createButton("Save title");
        
        c = Helpers.addConstraints(0, 2);
        c.fill = GridBagConstraints.NONE;
        boxedPanel.add(saveDetailsGauge, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        rightContainer.add(boxedPanel, c);
    }

    @Override
    public void addComponentsToFrame(Container container) {
        //edit container on the right
        rightContainer = new Container();
        rightContainer.setLayout(new GridBagLayout());
        addComponentToRightContainer(rightContainer);
        
        centerContainer = new Container();
        centerContainer.setLayout(new GridBagLayout());
        addComponentsToMainContainer(centerContainer);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(rightContainer, BorderLayout.EAST);

    }

    @Override
    public void addComponentToRightContainer(Container container) {
        JPanel boxedPanel = new JPanel();
        boxedPanel.setLayout(new GridBagLayout());
        //boxedPanel.setPreferredSize(new Dimension(250,400));
        boxedPanel.setAlignmentX(Component.TOP_ALIGNMENT);
        GridBagConstraints c = new GridBagConstraints();

        selectedGaugeLabel = Helpers.createLabel("Selected Gauge");
        selectedGaugeLabel.setPreferredSize(new Dimension(240, 40));
        c = Helpers.addConstraints(0, 0,1.0,0.8);
        c.fill = GridBagConstraints.HORIZONTAL;
        boxedPanel.add(selectedGaugeLabel, c);

        JTextField selectedGaugeText = Helpers.createTextField("");
        c = Helpers.addConstraints(0, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        boxedPanel.add(selectedGaugeText, c);

        JButton saveDetailsGauge = Helpers.createButton("Save");
        
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        boxedPanel.add(saveDetailsGauge, c);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        container.add(boxedPanel, c);

    }
    
    
   
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dashboard dashboard = new Dashboard();
            }
        });
    }

}
