
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Function;

public class Fun_Declaration extends Declaration{
    //variable declarations
    public ReturnType returnType;
    public String name;
    public ArrayList<Parameter> params;
    public Compound_Statement compoundStatement;
    
    //constructor
    public Fun_Declaration(ReturnType r, String n, ArrayList<Parameter> p, Compound_Statement c){
        returnType = r;
        name = n;
        params = p;
        compoundStatement = c;
    }
    
    //getters
    public ReturnType getReturnType(){
        return returnType;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.print(tabLvl + returnType.name() + " " + name + "(");
        if(params != null){
            for(Parameter param : params){
                if(param != null) {
                    param.print(w);
                }
            }
        }
        w.println(")");
        //Increase the tab for the compound statement
        w.println(tabLvl + "{");
        compoundStatement.print(w, tabLvl + "    " );
        w.println(tabLvl + "}");
        w.println();
    }

    @Override
    CodeItem genLLCode() {
                //get return type and name from appropriate fields
                ReturnType returnType = myFunDecl.getReturnType();
                int myReturnNumber;
                if(returnType.equals(ReturnType.INT)){
                    myReturnNumber = 1;
                } else{
                    myReturnNumber = 0;
                }
                String name = myFunDecl.getName();
                //create a new Function
              Function newFunc = new Function(myReturnNumber, name);
        //          walk Params, doing 2 things:
        //              1. make FuncParams
        //              2. put Param name in lccal table
              //create new Block and make it currentBlock
              newFunc.createBlock0();
              BasicBlock newBlock = new BasicBlock(newFunc);
              newFunc.appendBlock(newBlock);
              newFunc.setCurrBlock(newBlock);
              //call genCode on Compound Statement
              (this.compoundStatement).genLLCode();           //need return type
    }
}
