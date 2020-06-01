
package logicgatessimulator.gates;

import lombok.Data;

@Data
public class InputSquare extends SignalSquare{

    public InputSquare(int yAxis){
        super(Gate.TAB, yAxis);
    }

}
