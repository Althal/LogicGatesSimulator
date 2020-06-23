
package logicgatessimulator.gates;

import java.util.ArrayList;

public class XOR2Gate extends Gate {
    
    public XOR2Gate(){
        super(2, Gate.XOR_GATE);
    }

    @Override
    public ArrayList<int[]> getClausules() {
        ArrayList<int[]> ret = new ArrayList<>();
        int i1 = inputSquares.get(0).getVarId();
        int i2 = inputSquares.get(1).getVarId();
        int i3 = outputSquare.getVarId();
        
        ret.add(new int[]{-i1,-i2,-i3});
        ret.add(new int[]{-i1,i2,i3});
        ret.add(new int[]{i1,-i2,i3});
        ret.add(new int[]{i1,i2,-i3});
                    
        return ret;
    }

    @Override
    public String getImageSrc() {
        return "lib\\img\\XOR2.PNG";
    }
    
}
