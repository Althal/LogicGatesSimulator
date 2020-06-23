
package logicgatessimulator.lib;

import java.util.ArrayList;
import java.util.Arrays;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class SatSolver {

    
    
    private ArrayList<int[]> clausules = new ArrayList<>();
    
    
    
    public void addClausules(ArrayList<int[]> clausule){
        clausules.addAll(clausule);
    }
    
    private int getMaxVar(){
        int max = 1;
        for(int[] i : clausules){
            for(int j : i){
                int k = Math.abs(j);
                if(k>max) max = k;
            }
        }
        return max;
    }
    
    public int[] solve() throws TimeoutException, ContradictionException{
        final int MAXVAR = getMaxVar();
        final int NBCLAUSES = clausules.size();

        ISolver solver = SolverFactory.newDefault();
        Reader reader = new DimacsReader(solver);
        // prepare the solver to accept MAXVAR variables. MANDATORY for MAXSAT solving
        solver.newVar(MAXVAR);
        solver.setExpectedNumberOfClauses(NBCLAUSES);

        System.out.println("CLAUSULES");
        for(int[] i : clausules){
            System.out.println(Arrays.toString(i));
            try{
                if(i.length != 0) solver.addClause(new VecInt(i));
                else return new int[] {0};
            }
            catch(Exception e){
                return new int[] {0};
            }
        }

        // we are done. Working now on the IProblem interface
        IProblem problem = solver;
        int[] ret = new int[MAXVAR];
        if (problem.isSatisfiable()) {
            System.out.println("Satisfiable !");
            System.out.println(reader.decode(problem.model()));
            ret = problem.findModel();            
            System.out.println(Arrays.toString(ret));
        } else {
            return new int[] {0};
        }
        
        return ret;
    }

    public void filterValues(ArrayList<Integer> setted) {
        System.out.println("BEFORE");
        for(int[] i : clausules){   
            System.out.println(Arrays.toString(i));
        }
        
        // Usuwanie pe³nych klauzul
        ArrayList<int[]> newClausules = new ArrayList<>();
        for(int i=0; i<clausules.size(); i++){
            int[] c = clausules.get(i);
            boolean contains = false;
            for(int j : c) {
                for(int k : setted) if(k == j) contains = true;
            }
            if(!contains) newClausules.add(c);
        }
        
        // Usuwanie z klauzul
        ArrayList<int[]> newClausules2 = new ArrayList<>();
        for(int i=0; i<newClausules.size(); i++){
            ArrayList<Integer> cl = new ArrayList<>();
            for(int j : newClausules.get(i)){
                if(!setted.contains(-j) && !setted.contains(j)){
                    cl.add(j);
                }
            }
            newClausules2.add(cl.stream().mapToInt(k -> k).toArray());
        }
        clausules = newClausules2;
        System.out.println("AFTER");
        for(int[] i : clausules){
            System.out.println(Arrays.toString(i));
        }
    }
}
