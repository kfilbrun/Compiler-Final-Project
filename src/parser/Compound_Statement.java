
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Map;
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

    @Override
    void genLLCode(Function function) {
        Map<String, Integer> localVars = function.getTable();
        for (Var_Declaration varDecl : this.varDecls) {
            localVars.put(varDecl.getName(), 0);
        }
        for (Statement statement : this.statements) {
            statement.genLLCode(function);                                      //Pass in local variables table instead ?
        }
    }
}
