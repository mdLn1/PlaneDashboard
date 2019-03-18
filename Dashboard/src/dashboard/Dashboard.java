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
import javax.swing.BoxLayout;
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
    JPanel editPanel;
    
    JLabel dangerLabel;
    JTextField dangerMinText;
    JTextField dangerMaxText;
    JLabel dangerMinLabel;
    JLabel dangerMaxLabel;
    
    JLabel unitLabel;
    JButton unitButton;
    JTextField unitTextField;
    
    JLabel titleLabel;
    JButton titleButton;
    JTextField titleTextField;

    public Dashboard() {
        createGUI();
    }

    //method that designs mainFrame
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

    //design main container for frame
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

        //add mouse listener for every gauge + add every gauge to the main container
        for (Map.Entry<Object, PairHeads> g : context.getGauges().entrySet()) {
            GaugeSetup tempGauge = (GaugeSetup) g.getKey(); 
            tempGauge.getGauge().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (g.getKey() instanceof SpecialisedGauge) {
                        selectedGaugeLabel.setText(tempGauge.getTitle());
                        adjustRightContainer((SpecialisedGauge) g.getKey());
                        
                    } else if (g.getKey() instanceof RegularGauge) {
                        selectedGaugeLabel.setText(tempGauge.getTitle());
                        adjustRightContainer((RegularGauge) g.getKey());
                    } else {
                        System.out.println("Got clicked");
                    }

                }

            });
            PairHeads positions = context.getConstraints(tempGauge.getTitle());
            c.gridx = positions.getStart();
            c.gridy = positions.getEnd();
            container.add(tempGauge, c);

        }

    }
    
    //adjust the right container in the frame
    public void adjustRightContainer(Object gauge)
    {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        //boxedPanel.setPreferredSize(new Dimension(250,400));
        editPanel.setAlignmentX(Component.TOP_ALIGNMENT);
        if (gauge instanceof SpecialisedGauge)
        {   
            
            createRegularFormInput((RegularGauge) gauge, editPanel);
        }else if (gauge instanceof RegularGauge)
        {
            createRegularFormInput((RegularGauge) gauge, editPanel);
        }
        
    }
    
    //create an input form for editing gauges
    public void createRegularFormInput(RegularGauge gauge, Container container)
    {
        String selected = selectedGaugeLabel.getText();
        GridBagConstraints c = new GridBagConstraints();
        
        // <editor-fold desc="setting up title components to edit">
        titleLabel = Helpers.createLabel("Set title for " + gauge.getTitle());
        titleLabel.setText("Set title for " + gauge.getTitle());
        titleLabel.setPreferredSize(new Dimension(280, 40));
//        c = Helpers.addConstraints(0, 0,1.0,0.8);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        container.add(titleLabel, c);
        container.add(titleLabel);

        titleTextField = Helpers.createTextField(gauge.getTitle());
//        c = Helpers.addConstraints(0, 1);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        container.add(titleTextField, c);
        container.add(titleTextField);

        titleButton = Helpers.createButton("Save title");
//        c = Helpers.addConstraints(0, 2);
//        c.fill = GridBagConstraints.NONE;
//        container.add(titleButton, c);
        container.add(titleButton);
        // </editor-fold>
        
        // <editor-fold desc="setting up unit of the components for editing">
        unitLabel = Helpers.createLabel("Set unit for " + gauge.getTitle());
        unitLabel.setPreferredSize(new Dimension(280, 40));
//        c = Helpers.addConstraints(0, 3,1.0,0.8);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        container.add(titleLabel, c);
        container.add(unitLabel);

        unitTextField = Helpers.createTextField(gauge.getUnit());
//        c = Helpers.addConstraints(0, 4);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        container.add(titleTextField, c);
        container.add(unitTextField);

        unitButton = Helpers.createButton("Save unit");
//        c = Helpers.addConstraints(0, 5);
//        c.fill = GridBagConstraints.NONE;
//        container.add(titleButton, c);
        container.add(unitButton);

        // </editor-fold>
        
        
        
        
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        
        rightContainer.revalidate();
        rightContainer.repaint();
        rightContainer.add(container, c);
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
