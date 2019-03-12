package dashboard;

import javax.swing.JFrame;
import eu.hansolo.steelseries.extras.Altimeter;
import eu.hansolo.steelseries.extras.Led;
import eu.hansolo.steelseries.extras.TrafficLight;
import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.Radial1Square;
import eu.hansolo.steelseries.gauges.Radial2Top;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JPanel;


public final class Dashboard implements FrameSetup {

    public Dashboard()
    {
        createGUI();
    }
    
    @Override
    public void createGUI()
    {
        JFrame mainFrame = new JFrame("Plane Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        
        addComponentsToFrame(mainFrame.getContentPane());
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    @Override
    public void addComponentsToFrame(Container container)
    {
        JPanel mainPanel = new JPanel(new FlowLayout());
        
        WindRadial windGauge = new WindRadial("Wind Direction");
    
        Radial1Square radial1 = new Radial1Square();
        
        mainPanel.add(radial1);
        
        container.add(windGauge);
    }
    
    
    
   
    
    public static void main(String[] args) {
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                javax.swing.UnsupportedLookAndFeelException ex) {
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
