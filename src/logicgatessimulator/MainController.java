
package logicgatessimulator;

import logicgatessimulator.lib.Line;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logicgatessimulator.gates.*;
import logicgatessimulator.lib.SatSolver;
import logicgatessimulator.lib.Signal;

public class MainController {
    
    // Czy w³¹czono rysowanie po³¹czeñ
    public static boolean isLineDrawing = false;
    
    // Liczba wybranych po³¹czeñ (0 lub 1)
    public static boolean anchor = false;
    
    // Wszystkie dodane bramki i linie
    public static ArrayList<Gate> gates = new ArrayList<>();
    public static ArrayList<Line> lines = new ArrayList<>();
    
    
    // Dane wymagane w przesuniêciu;
    private static int xSel;
    private static int ySel;
    
    
    
    
    
    public static void addAnd2Gate(){
        if(checkAddPosition()) addGate(new AND2Gate());
    }
    
    public static void addAnd3Gate(){
        if(checkAddPosition()) addGate(new AND3Gate());
    }
       
    public static void addAnd4Gate(){
        if(checkAddPosition()) addGate(new AND4Gate());
    }
    
    public static void addNand2Gate(){
        if(checkAddPosition()) addGate(new NAND2Gate());
    }
    
    public static void addNand3Gate(){
        if(checkAddPosition()) addGate(new NAND3Gate());
    }
    
    public static void addNand4Gate(){
        if(checkAddPosition()) addGate(new NAND4Gate());
    }
    
    public static void addOr2Gate(){
        if(checkAddPosition()) addGate(new OR2Gate());
    }
    
    public static void addOr3Gate(){
        if(checkAddPosition()) addGate(new OR3Gate());
    }
    
    public static void addOr4Gate(){
        if(checkAddPosition()) addGate(new OR4Gate());
    }
    
    public static void addNor2Gate(){
        if(checkAddPosition()) addGate(new NOR2Gate());
    }
    
    public static void addNor3Gate(){
        if(checkAddPosition()) addGate(new NOR3Gate());
    }
    
    public static void addNor4Gate(){
        if(checkAddPosition()) addGate(new NOR4Gate());
    }
    
    public static void addNot1Gate(){
        if(checkAddPosition()) addGate(new NOT1Gate());
    }
    
    public static void addXor2Gate(){
        if(checkAddPosition()) addGate(new XOR2Gate());
    }
    
    
    
    
    public static void mouseClick(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        if(isSelectedGate()){
            int selectedGate = getSelectedGate();
            int xR = x - xSel;
            int yR = y - ySel;
            gates.get(selectedGate).setPosition(xR, yR);
            
            int linesSize = lines.size();
            ArrayList<Line> newLines = new ArrayList<>();
            for(int i=0; i<linesSize; i++){
                Line line = lines.get(i);
                newLines.add(new Line(line.getGateIdInput(),line.getInputId(),line.getOutputId()));
            }
            lines = newLines;
            refresh();
            unselectAll();
            Main.mainFrame.validate();
            Main.mainFrame.repaint();
        }
        else if(isBusy(x,y)) {
            for(Gate g : gates) g.mouseClick(x,y);
            if(getSelectedGate() != -1){
                // Zaznaczenie bramki
                xSel = x - gates.get(getSelectedGate()).getX();
                ySel = y - gates.get(getSelectedGate()).getY();
            }
            else {
                // Rysowanie linii (drugi zaznaczony element)
                if(isLineDrawing && anchor){
                    int g1 = -1;
                    int s1 = -1;
                    int g2 = -1;
                    for(int i=0; i<gates.size(); i++){
                        for(int j=0; j<gates.get(i).getInputSquare().size(); j++){
                            if(gates.get(i).getInputSquare().get(j).isSelected()){
                                g1 = i;
                                s1 = j;
                            }
                            if(gates.get(i).getOutputSquare().isSelected()){
                                g2 = i;
                            }
                        }
                    }
                    if(g1 == -1 || s1 == -1 || g2 == -1) {
                        JOptionPane.showMessageDialog(null, "Nie mo¿esz po³¹czyæ dwóch elementów tego samego typu: wejœcie/wyjœcie");
                    }
                    else if(g1 == g2){
                        JOptionPane.showMessageDialog(null, "Nie mo¿esz po³¹czyæ ze sob¹ tej samej bramki");
                    }
                    else if(isLineForInputSquare(g1,s1)){
                        JOptionPane.showMessageDialog(null, "Nie mo¿esz dodaæ dwóch po³¹czeñ do wejœcia");
                    }
                    else addLine(new Line(g1,s1,g2));
                    unselectAll();
                    anchor = false;
                }
                else if(isBusy(x,y)){
                    // Zaznaczenie pierwszej kotwy
                    anchor = true;
                }
            }
        }
        checkSignalButtonEnablity();
    } 
    
    public static void draw(){
        isLineDrawing = !isLineDrawing;
        if(isLineDrawing) disableAllAddGateButtons();
        else enableAllAddGateButtons();
        anchor = false;
        unselectAll();
    }
    
    public static void run(){
        calculate();
    }
    
    public static void clearAll(){
        Main.mainFrame.getArea().removeAll();
        gates = new ArrayList<>();
        lines = new ArrayList<>();
        
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
    }
    
    public static void refresh(){
        Main.mainFrame.getArea().removeAll();
        for(Gate g : gates) Main.mainFrame.getArea().add(g);
        for(Line l : lines) Main.mainFrame.getArea().add(l);
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
    }
    
    public static void setOne(){
        if(isLineDrawing) return;
        for(Gate g : gates){
            for(InputSquare is : g.getInputSquare()){
                if(is.isSelected() && !isLineDrawing){
                    is.setSignal(Signal.ONE);
                }
            }
            if(g.getOutputSquare().isSelected() && !isLineDrawing){
                g.getOutputSquare().setSignal(Signal.ONE);
            }
        }
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
        unselectAll();
    }
    
    public static void setZero(){
        if(isLineDrawing) return;
        for(Gate g : gates){
            for(InputSquare is : g.getInputSquare()){
                if(is.isSelected() && !isLineDrawing){
                    is.setSignal(Signal.ZERO);
                }
            }
            if(g.getOutputSquare().isSelected() && !isLineDrawing){
                g.getOutputSquare().setSignal(Signal.ZERO);
            }
        }
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
        unselectAll();
    }
    
    
    
    
    
    
    
    
    
    
    private static void disableAllAddGateButtons(){
        for(JButton b : getButtonList()) b.setEnabled(false);
    }
    
    private static void enableAllAddGateButtons(){
        for(JButton b : getButtonList()) b.setEnabled(true);
    }
    
    private static ArrayList<JButton> getButtonList(){
        ArrayList<JButton> ret = new ArrayList<>();
        
        ret.add(Main.mainFrame.getAND2());
        ret.add(Main.mainFrame.getAND3());
        ret.add(Main.mainFrame.getAND4());
        ret.add(Main.mainFrame.getNAND2());
        ret.add(Main.mainFrame.getNAND3());
        ret.add(Main.mainFrame.getNAND4());
        ret.add(Main.mainFrame.getOR2());
        ret.add(Main.mainFrame.getOR3());
        ret.add(Main.mainFrame.getOR4());
        ret.add(Main.mainFrame.getNOR2());
        ret.add(Main.mainFrame.getNOR3());
        ret.add(Main.mainFrame.getNOR4());
        ret.add(Main.mainFrame.getXOR2());
        ret.add(Main.mainFrame.getNOT1());
        
        return ret;
    }
    
    private static boolean checkAddPosition(){
        if(isBusy(Gate.DEFAULT_POSITION, Gate.DEFAULT_POSITION)) {
            JOptionPane.showMessageDialog(null, "Miejsce dodania nowej bramki (lewy górny róg) jest zajête. Przesuñ bramkê.");
            return false;
        }
        return true;
    }
    
    private static int getSelectedGate(){
        for(int i=0; i<gates.size(); i++) if(gates.get(i).isSelected()) return i;
        return -1;
    }
    
    private static boolean isSelectedGate(){
        for(Gate g : gates) if(g.isSelected()) return true;
        return false;
    }
    
    private static boolean isBusy(int x, int y){
        for(Gate g : gates) if(g.checkSelection(x,y)) return true;
        return false;
    }
    
    private static boolean isLineForInputSquare(int g, int is){
        for(Line l : lines){
            if(l.getGateIdInput() == g && l.getInputId() == is) return true;
        }
        return false;
    }
    
    private static ArrayList<OutputSquare> getOutputs(){
        ArrayList<OutputSquare> ret = new ArrayList<>();
        for(int i=0; i<gates.size(); i++) if(!isLineOutput(i)) ret.add(gates.get(i).getOutputSquare());
        return ret;
    }
    
    private static boolean isLineOutput(int gate){
        for(Line l : lines) if(l.getOutputId() == gate) return true;
        return false;
    }
    
    private static void addGate(Gate g){
        gates.add(g);
        Main.mainFrame.getArea().add(g);
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
    }
    
    private static void addLine(Line l){
        lines.add(l);
        JPanel p = new JPanel();
        p.add(l);
        l.setVisible(true);
        Main.mainFrame.getArea().add(l);
        
        System.out.println("Dodano liniê: " + l.toString());
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
    }

    private static void unselectAll(){
        for(Gate g : gates){
            g.unselect();
            for(SignalSquare ss : g.getInputSquare()) ss.unselect();
            g.getOutputSquare().unselect();
        }
    }

    private static void checkSignalButtonEnablity() {
        if(!isLineDrawing){
            for(Gate g : gates){
                for(InputSquare is : g.getInputSquare()){
                    if(is.isSelected() && !isLineDrawing){
                        Main.mainFrame.getONE().setEnabled(true);
                        Main.mainFrame.getZERO().setEnabled(true);
                        return;
                    }
                }
                if(g.getOutputSquare().isSelected() && !isLineDrawing){
                    Main.mainFrame.getONE().setEnabled(true);
                    Main.mainFrame.getZERO().setEnabled(true);
                    return;
                }
            }
        }
        Main.mainFrame.getONE().setEnabled(false);
        Main.mainFrame.getZERO().setEnabled(false);
    }
    
    
    
    
    
    
    
    //////////////////////////////////////////////
    //            CALULATING METHODS            //
    //////////////////////////////////////////////
    private static void calculate(){
        try {
            setVar();
            displayVars();

            SatSolver s = new SatSolver();
            for(Gate g : gates){
                s.addClausules(g.getClausules());
            }

            ArrayList<Integer> setted = getSettedValues();
            s.filterValues(setted);

            int[] allSolves = s.solve();
            int[] solve = filterSolve(allSolves);
            
            JOptionPane.showMessageDialog(null, "<html>ROZWI¥ZANIE:<BR>" + Arrays.toString(solve));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private static void setVar(){
        int var = 1;
        for(int i=0; i<gates.size(); i++){
            Gate g = gates.get(i);
            for(int j=0; j<g.getInputSquare().size(); j++){
                if(g.getInputSquare().get(j).getVarId() != SignalSquare.DEFAULT_ID) continue;
                boolean setted = false;
                // SprawdŸ po³¹czenie z innym wyjœciem
                for(Line l : lines){
                    if(l.getInputId() == j && l.getGateIdInput() == i){
                        // Je¿eli var id wyjœcia po³¹czonego z wejsciem jest -1, to nadaj obu
                        OutputSquare os = gates.get(l.getOutputId()).getOutputSquare();
                        if(os.getVarId() == SignalSquare.DEFAULT_ID) {
                            os.setVarId(var);
                            g.getInputSquare().get(j).setVarId(var++);
                        }
                        // Je¿eli ma numer, to nadaj ten co ma
                        else {
                            g.getInputSquare().get(j).setVarId(os.getVarId());
                        }
                        
                        setted = true;
                        break;
                    }                    
                }
                if(!setted) g.getInputSquare().get(j).setVarId(var++);
            }
            
            boolean setted = false;
            OutputSquare os = g.getOutputSquare();
            if(os.getVarId() != SignalSquare.DEFAULT_ID) continue;
            for(Line l : lines){
                if(l.getOutputId() == i){
                    // Je¿eli var id wejœcia po³¹czonego z wyjsciem jest -1, to nadaj obu
                    InputSquare is = gates.get(l.getGateIdInput()).getInputSquare().get(l.getInputId());
                    if(is.getVarId() == SignalSquare.DEFAULT_ID) {
                        is.setVarId(var);
                        os.setVarId(var++);
                    }
                    // Je¿eli ma numer, to nadaj ten co ma
                    else os.setVarId(is.getVarId());
                    
                    setted = true;
                    break;
                }
            }            
            if(!setted) os.setVarId(var++);
        }
    }
    
    private static void displayVars(){
        for(int i=0; i<gates.size(); i++){
            Gate g = gates.get(i);
            for(int j=0; j<g.getInputSquare().size(); j++){
                if(g.getInputSquare().get(j).getSignal() == Signal.UNDEFINDED) g.getInputSquare().get(j).setVar();
            }
            if(g.getOutputSquare().getSignal() == Signal.UNDEFINDED) g.getOutputSquare().setVar();
        }
        
        Main.mainFrame.validate();
        Main.mainFrame.repaint();
    }
    
    private static ArrayList<Integer> getSettedValues(){
        ArrayList<Integer> ret = new ArrayList<>();
        for(Gate g : gates){
            for(InputSquare is : g.getInputSquare()){
                if(is.getSignal() == Signal.ONE) ret.add(is.getVarId());
                else if(is.getSignal() == Signal.ZERO) ret.add(is.getVarId() * -1);
            }
            OutputSquare os = g.getOutputSquare();
            if(os.getSignal() == Signal.ONE) ret.add(os.getVarId());
            else if(os.getSignal() == Signal.ZERO) ret.add(os.getVarId() * -1);
        }
        return ret;
    }

    private static int[] filterSolve(int[] allSolves) {
        ArrayList<Integer> vars = new ArrayList<>();
        
        int[] allSolvesAbs = new int[allSolves.length];
        int index = 0;
        for(int i : allSolves) allSolvesAbs[index] = Math.abs(allSolves[index++]);
        
        for(index = 0; index < allSolvesAbs.length; index++){
            int i = allSolvesAbs[index];
            boolean contains = false;
            for(Line l : lines){
                int id1 = gates.get(l.getGateIdInput()).getInputSquare().get(l.getInputId()).getVarId();
                int id2 = gates.get(l.getGateIdInput()).getOutputSquare().getVarId();
                if(id1 == i || id2 == i) {
                    contains = true;
                }
            }
            if(!contains) vars.add(allSolves[index]);
        }
        
        return vars.stream().mapToInt(i -> i).toArray();
    }
}
