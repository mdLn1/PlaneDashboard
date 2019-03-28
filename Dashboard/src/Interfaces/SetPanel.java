
package Interfaces;

public interface SetPanel {
    
    void buildPanel(String name, String type);
    void initialiseEmptyPanel();
    void setTitle(String title);
    String getTitle();
    Object getGauge();
}
