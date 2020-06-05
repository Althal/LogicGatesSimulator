
package logicgatessimulator.gates;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import logicgatessimulator.MainController;
import logicgatessimulator.lib.Signal;
import lombok.Data;

@Data
public abstract class SignalSquare extends JPanel{
    
    public static int SQUARE_SIZE = 20;
    public static int DEFAULT_ID = 0;
    
    private Signal signal = Signal.UNDEFINDED;
    private int x;
    private int y;    
    private boolean selected = false;
    
    private int varId = DEFAULT_ID;
    
    public SignalSquare(int xAxis, int yAxis){
        this.x = xAxis;
        this.y = yAxis;
        setBounds(x, y, SQUARE_SIZE, SQUARE_SIZE);
        setBackground(Color.gray);
    }
    
    
    public boolean checkSelection(int x, int y){
        if((this.x < x) && (this.x + SQUARE_SIZE > x) && (this.y < y) && (this.y + SQUARE_SIZE > y)){
            if(MainController.isLineDrawing) {
                selectToDraw();                
                return true;
            }
            if(selected) unselect();
            else select();
            return true;
        }
        return false;
    }
    
    public void setSignal(Signal s) {
        signal = s;

        JLabel l = new JLabel();
        l.setFont(new Font("Monospaced", Font.BOLD, 10));
        
        if(s == Signal.ONE) {
            //l.setText("1");
            setBackground(Color.GREEN);
        }
        else {
            //l.setText("0");
            setBackground(Color.RED);
        }
        
        add(l);
    }
    
    public void setVar(){
        if(varId == DEFAULT_ID){
            removeAll();
            setBackground(new JPanel().getBackground());
        }
        JLabel l = new JLabel();
        l.setFont(new Font("Monospaced", Font.BOLD, 10));
        l.setText(String.valueOf(varId));
        setBackground(Color.LIGHT_GRAY);
        add(l);
    }

    
    public void unregisterSignal(){
        removeAll();
        setSignal(Signal.UNDEFINDED);
        varId = 0;
    }
    
    public Signal getSignal() {
        return signal;
    }
    
    // Zaznacz
    public void select(){
        selected = true;
        setBorder(new LineBorder(Color.blue, 3));
    }
    
    // Odznacz
    public void unselect(){
        selected = false;
        setBorder(null);
    }
        
    // Zaznacz do rysowania linii
    public void selectToDraw(){
        selected = true;
        setBorder(new LineBorder(Color.yellow, 3));
    }
}
