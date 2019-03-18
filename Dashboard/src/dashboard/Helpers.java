package dashboard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public final class Helpers {
    
    public static final String SIMPLER_RADIAL = "Simple Radial";
    public static final String HALF_DIAL = "Half Dial";
    public static final String QUARTER_DIAL = "Quarter Dial";
    public static final String LINEAR_BAR = "VerticalBar";
    public static final String DIRECTION_DIAL = "Direction Radial";
    private static final Font FONT_BUTTON = new Font("Calibri", Font.PLAIN, 16);
    private static final Font FONT_LABEL = new Font("Arial", Font.BOLD, 20);
    
    public static JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(FONT_BUTTON);
        
        return button;
    }

    public static JLabel createLabel(String name) {
        JLabel label = new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(FONT_LABEL);
        
        return label;
    }

    public static JTextField createTextField(String name) {
        JTextField textField = new JTextField(name);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setColumns(4);
        
        return textField;
    }
    
     public static JLabel createSeparatorYaxis() {
        JLabel separator = new JLabel();
        separator.setPreferredSize(new Dimension(150, 20));
        separator.setOpaque(true);
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        return separator;
    }
     
      public static GridBagConstraints addConstraints(int x, int y, double x1, double y1)
    {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = x;
        c1.gridy = y;
        c1.weightx = x1;
        c1.weighty = y1;
        
        return c1;
    }
    
    public static GridBagConstraints addConstraints(int x, int y)
    {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = x;
        c1.gridy = y;
        c1.weightx = 1.0;
        c1.weighty = 1.0;
        
        return c1;
    }
    
    
}
