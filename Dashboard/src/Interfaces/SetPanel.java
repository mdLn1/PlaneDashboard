package Interfaces;

// setting up panels that contain a gauge or some 2D graphic element
public interface SetPanel {
    
    void buildPanel(String name, String type);
    void initialiseEmptyPanel();
    void setTitle(String title);
    String getTitle();
    Object getGauge();
}
