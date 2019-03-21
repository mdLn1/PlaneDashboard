/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

/**
 *
 * @author acer
 */
public class InvalidLimitsException extends Exception {
        
    double min;
    double max;
    double maxLimit;
    double minLimit;
    
    
    public InvalidLimitsException(double min, double max, double minLimit, double maxLimit) {
        this.max = max;
        this.min = min;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }
    
    @Override
    public String toString() {
        if (max < min)
        {
            return "Invalid arguments passed, maximum cannot be less than "
                    + "minimum value.";
            
        } else if (max - min < 1.0)
        {
            return "Invalid arguments passed, minimum value must be less than"
                    + " maximum with at least 1 difference.";
        } else if (min < minLimit || min >= maxLimit) {
            return "Invalid arguments passed, minimum value that can be set is "
                    + this.minLimit + ".";
        } else if (max > maxLimit || max <= minLimit) {
             return "Invalid arguments passed, maximum value that can be set is "
                    + this.maxLimit + ".";
        }
            return "Inavalid arguments";
        
        
    }
    
}
