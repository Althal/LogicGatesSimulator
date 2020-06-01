
package logicgatessimulator;


import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;


public class Main {
    
    public static MainFrame mainFrame;
    
    public static void main(String... args) throws ContradictionException, TimeoutException{
        mainFrame = new MainFrame();	
    }

}
