package Interfaces;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

// interface for setting up a JFrame if needed to use the same style
public interface FrameSetup {
    
    public default void createGUI()
    {
        JFrame mainFrame = new JFrame("Default");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(2000, 1000);
        mainFrame.setLayout(new BorderLayout());
        
        //add code here
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    public abstract void addComponentToRightContainer(Container container);
    public abstract void addComponentsToMainContainer(Container container);
    public abstract void addComponentsToFrame(Container container);
    
    
}
