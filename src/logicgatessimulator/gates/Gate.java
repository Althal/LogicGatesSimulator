
package logicgatessimulator.gates;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public abstract class Gate extends JPanel{

    // Sta³e statyczne reprezentuj¹ce typy bramek
    public static int AND_GATE = 1;
    public static int NAND_GATE = 2;
    public static int OR_GATE = 3;
    public static int NOR_GATE = 4;
    public static int NOT_GATE = 5;
    public static int XOR_GATE = 6;
    
    
    // Sta³e wielkoœci
    public static int GATE_SIZE_X = 200;
    public static int GATE_SIZE_Y = 100;
    public static int TAB = 0;
    public static int DEFAULT_POSITION = 20;
    
    
    
    // Pola klasy: wejœcia wyjœcie oraz po³o¿enie
    protected ArrayList<InputSquare> inputSquares;
    OutputSquare outputSquare;
    private int x = DEFAULT_POSITION;
    private int y = DEFAULT_POSITION;
    private boolean selected = false;
    
    
    // Metoda zwracaj¹ca wyjœci z bramki logicznej
    public abstract ArrayList<int[]> getClausules();
    
    // Ikona bramki
    public abstract String getImageSrc();
    
    
    
    public Gate(int inputs, int gateType) {
        inputSquares = new ArrayList<>();
        outputSquare = new OutputSquare();
        
        switch (inputs) {
            case 1:
                inputSquares.add(new InputSquare(40));
                break;
            case 2:
                inputSquares.add(new InputSquare(15));
                inputSquares.add(new InputSquare(65));
                break;
            case 3:
                inputSquares.add(new InputSquare(15));
                inputSquares.add(new InputSquare(40));
                inputSquares.add(new InputSquare(65));
                break;
            case 4:
                inputSquares.add(new InputSquare(5));
                inputSquares.add(new InputSquare(29));
                inputSquares.add(new InputSquare(53));
                inputSquares.add(new InputSquare(77));
                break;
            default:
                break;
        }
        
        setInput();
        setOutput();
        
        setBounds(x, y, GATE_SIZE_X, GATE_SIZE_Y);
        setBackground(Color.red);
        setLayout(null);
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(getImageSrc()).getImage(), 0, 0, null);
    }
    // Zwraca wejœcia
    public ArrayList<InputSquare> getInputSquare() { return inputSquares; }
    
    // Zwraca wyjœcia
    public OutputSquare getOutputSquare() { return outputSquare; }
    
    // Czy zaznaczone
    public boolean isSelected() { return selected; }
    
    
    // Wstawienie prostok¹tów reprezentuj¹cych wejœcie
    private void setInput(){
        for(InputSquare is : inputSquares) add(is);
    }
    
    // Wstawienie prostok¹tów reprezentuj¹cych wyjœcie
    private void setOutput(){
        add(outputSquare);
    }
    
    
    // Naciœniêcie myszki
    public void mouseClick(int x, int y){
        if(checkSelection(x,y) && !checkSelectionOnSquare(x - this.x, y - this.y)){
            if(selected) unselect();
            else select();
        }        
    }
    
    
    // Metoda sprawdza czy zaznaczenie zawiera siê w bramce
    public boolean checkSelection(int x, int y){
        return (this.x <= x) && (this.x + GATE_SIZE_X >= x) && (this.y <= y) && (this.y + GATE_SIZE_Y >= y);
    }
    
    
    public void setPosition(int x, int y ){
         setBounds(x, y, Gate.GATE_SIZE_X, Gate.GATE_SIZE_Y);
         this.x = x;
         this.y = y;
    }
    
    
    // Sprawdzenie, czy klikniêcie jest na inpucie / outpucie, czy bramce
    private boolean checkSelectionOnSquare(int relX, int relY){
        for(InputSquare is : inputSquares) if(is.checkSelection(relX, relY)) return true;
        if(outputSquare.checkSelection(relX, relY)) return true;
        return false;
    }
    
    
    // Zaznacz
    public void select(){
        selected = true;
        setBorder(new LineBorder(Color.black, 5));
    }
    
    // Odznacz
    public void unselect(){
        selected = false;
        setBorder(null);
    }
}
