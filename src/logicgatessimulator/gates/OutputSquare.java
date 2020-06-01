
package logicgatessimulator.gates;

import lombok.Data;

@Data
public class OutputSquare extends SignalSquare{

    public OutputSquare(){
        super(Gate.GATE_SIZE_X - Gate.TAB - SignalSquare.SQUARE_SIZE, (Gate.GATE_SIZE_Y - SignalSquare.SQUARE_SIZE) / 2);
    }
}
