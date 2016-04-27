
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import lowlevel.Function;

public class Compound_Statement extends Statement {
    //variable declarations
    public ArrayList<Var_Declaration> varDecls;
    public ArrayList<Statement> statements;
    
    //constructor
    public Compound_Statement(ArrayList<Var_Declaration> v, ArrayList<Statement> s){
        varDecls = v;
        statements = s;
    }
    
    public void print(PrintWriter w, String tabLvl){
        for(Var_Declaration var : varDecls) {
            if(var != null) {
                var.print(w, tabLvl);
            }
        }
        for(Statement stmt : statements) {
            if(stmt != null) {
                stmt.print(w, tabLvl);
            }
        }
    }
    
    public void genLLCode(Function parentFunction){
        
    }
}
