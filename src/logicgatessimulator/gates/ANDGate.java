
package logicgatessimulator.gates;

import java.util.ArrayList;

public abstract class ANDGate extends Gate {
    
    public ANDGate(int inputs){
        super(inputs, Gate.AND_GATE);
    }

    @Override
    public ArrayList<int[]> getClausules() {
        ArrayList<int[]> ret = new ArrayList<>();
        int i1 = inputSquares.get(0).getVarId();
        int i2 = inputSquares.get(1).getVarId();
        
        switch (inputSquares.size()) {
            case 2:
                {
                    int i3 = outputSquare.getVarId();
                    ret.add(new int[]{-i1,-i2,i3});
                    ret.add(new int[]{i1,-i3});
                    ret.add(new int[]{i2,-i3});
                    break;
                }
            case 3:
                {
                    int i3 = inputSquares.get(2).getVarId();
                    int i4 = outputSquare.getVarId();
                    ret.add(new int[]{-i1,-i2,-i3,i4});
                    ret.add(new int[]{i1,-i4});
                    ret.add(new int[]{i2,-i4});
                    ret.add(new int[]{i3,-i4});
                    break;
                }
            case 4:
                {
                    int i3 = inputSquares.get(2).getVarId();
                    int i4 = inputSquares.get(3).getVarId();
                    int i5 = outputSquare.getVarId();
                    ret.add(new int[]{-i1,-i2,-i3,-i4,i5});
                    ret.add(new int[]{i1,-i5});
                    ret.add(new int[]{i2,-i5});
                    ret.add(new int[]{i3,-i5});
                    ret.add(new int[]{i4,-i5});
                    break;
                }
            default:
                break;
        }
        
        return ret;
    }
    
}
