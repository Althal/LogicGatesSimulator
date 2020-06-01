
package logicgatessimulator.lib;

public enum Signal {    
    ONE,
    ZERO,
    UNDEFINDED;
    
    public static boolean getBoolean(Signal s){
        return s.equals(ONE);
    }
    
    public static Signal getSignal(boolean s){
        if(s) return ONE;
        else return ZERO;
    }
}
