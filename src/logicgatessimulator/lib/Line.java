
package logicgatessimulator.lib;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import logicgatessimulator.MainController;
import logicgatessimulator.gates.Gate;
import logicgatessimulator.gates.SignalSquare;

public class Line extends JPanel{

    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(10));
        if(x2 > x1) {
            if(y2 > y1) g2.drawLine(0, 0, x2-x1, y2-y1);
            else g2.drawLine(0, y1-y2, x2-x1, 0);
        }
        else {
            if(y1 > y2) g2.drawLine(x1-x2, y1-y2, 0, 0);
            else g2.drawLine(0, y2-y1, x1-x2, 0);
        }
    }
    
    private final int gateIdInput;
    private final int inputId;
    
    private final int gateIdOutput;
    
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    

    public Line(int gateIdInput, int inputId, int gateIdOutput) {
        this.gateIdInput = gateIdInput;
        this.inputId = inputId;
        this.gateIdOutput = gateIdOutput;
        calculateXY();
        setSize(Math.abs(x2-x1),Math.abs(y2-y1));
        setBackground( new Color(255, 255, 255, 100) );
        setForeground( Color.black );
        setLocation(new Point(Math.min(x2,x1)+SignalSquare.SQUARE_SIZE/2,Math.min(y2,y1)+SignalSquare.SQUARE_SIZE/2));
    }
    
    private void calculateXY(){
        Gate g1 = MainController.gates.get(gateIdInput);
        Gate g2 = MainController.gates.get(gateIdOutput);
        SignalSquare s1 = g1.getInputSquare().get(inputId);
        SignalSquare s2 = g2.getOutputSquare();
        if(g1.getX() > g2.getX()){
            x1 = g1.getX() + s1.getX();
            y1 = g1.getY() + s1.getY();
            x2 = g2.getX() + s2.getX();
            y2 = g2.getY() + s2.getY();
        }
        else {
            x2 = g1.getX() + s1.getX();
            y2 = g1.getY() + s1.getY();
            x1 = g2.getX() + s2.getX();
            y1 = g2.getY() + s2.getY();
        }
    }

    public int getGateIdInput() {
        return gateIdInput;
    }

    public int getInputId() {
        return inputId;
    }

    public int getOutputId() {
        return gateIdOutput;
    }

    @Override
    public String toString() {
        return "Line{" + "gateIdInput=" + gateIdInput + ", inputId=" + inputId + ", gateIdOutput=" + gateIdOutput + ", x1=" + x1 + ", x2=" + x2 + ", y1=" + y1 + ", y2=" + y2 + '}';
    }
    
}
