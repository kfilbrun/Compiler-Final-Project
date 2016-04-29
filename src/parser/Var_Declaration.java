
package parser;

import compiler.CMinusCompiler;
import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.Function;

public class Var_Declaration extends Declaration{
    //variable declarations
    public String declID;
    public int  arraySize;
    
    //constructor
    public Var_Declaration(String s, int arrSize){
        declID = s;
        arraySize = arrSize;
    }
    
    public Var_Declaration(String s){
        declID = s;
    }
    
    //getters
    public String getName(){
        return declID;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.print(tabLvl + declID);
        if(arraySize > 0){
            w.println("[" + arraySize + "]");
        }
        else {
            w.println();
        }
    }
    
    //Global vardecl
    @Override
    CodeItem genLLCode() {
        CMinusCompiler.globalHash.put(declID, 0);
        return new Data(Data.TYPE_INT, declID);
    }
    
    //Function scoped vardecl
    void genLLCode(Function func)  throws CodeGenerationException{
        func.getTable().put(declID, func.getNewRegNum());
    }
}