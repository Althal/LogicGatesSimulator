
package logicgatessimulator.gates;

import java.util.ArrayList;
public class NOT1Gate extends Gate {
    
    public NOT1Gate(){
        super(1, Gate.NOT_GATE);
    }

    @Override
    public ArrayList<int[]> getClausules() {
        ArrayList<int[]> ret = new ArrayList<>();
        int i1 = inputSquares.get(0).getVarId();
        int i2 = outputSquare.getVarId();
        
        ret.add(new int[]{i1,i2});
        ret.add(new int[]{-i1,-i2});

        return ret;
    }

    @Override
    public String getImageSrc() {
        return "C:\\Users\\user\\Desktop\\LogicGatesSimulator\\src\\logicgatessimulator\\img\\NOT.PNG";
    }
    
}
