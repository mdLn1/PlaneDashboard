package dashboard;

import java.awt.Container;
import javax.swing.JFrame;

public interface FrameSetup {
    
    public default void createGUI()
    {
        JFrame mainFrame = new JFrame("Default");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //add code here
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public abstract void addComponentsToFrame(Container container);
    
    
}
