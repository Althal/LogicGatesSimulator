
package logicgatessimulator.gates;

import java.io.File;


public class AND2Gate extends ANDGate {
    
    public AND2Gate(){
        super(2);
    }

    @Override
    public String getImageSrc() {
        System.out.println(new File("a.txt").getAbsolutePath());
        return "lib\\img\\AND2.PNG";
    }
}
