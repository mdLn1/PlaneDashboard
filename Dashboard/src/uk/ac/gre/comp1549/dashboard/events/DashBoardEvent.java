package uk.ac.gre.comp1549.dashboard.events;

// DashboardDemoProject provided code


/**
 *Holds information about DashBoardEvents that can be
 * passed to DashBoardEventListeners.
 * @author COMP1549
 */
public class DashBoardEvent {

    private String type; // type of event e.g "speed"
    private String value; // value of the event e.g. "30"

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "type:" + type + " value:" + value;
        
    }
    
}
