
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
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
    public CodeItem genLLCode() {
        //get return type and name from appropriate fields
        ReturnType returnType = this.getReturnType();
        int myReturnNumber;
        
        if(returnType.equals(ReturnType.INT)){
            myReturnNumber = Data.TYPE_INT;
        } else{
            myReturnNumber = Data.TYPE_VOID;
        }
        
        Function newFunc = new Function(myReturnNumber, name);
        
        FuncParam curParam = null;
        for(Parameter param : params){
            if(curParam == null){
                curParam = param.genLLCode(newFunc);
                newFunc.setFirstParam(curParam);
            }
            else{
                curParam.setNextParam(param.genLLCode(newFunc));
            }
        }
        
        newFunc.createBlock0();
        BasicBlock newBlock = new BasicBlock(newFunc);
        newFunc.appendBlock(newBlock);
        newFunc.setCurrBlock(newBlock);
        //call genCode on Compound Statement
        (this.compoundStatement).genLLCode(newFunc);                      //Void Return Type - Passing in reference to self instead???
        return newFunc;
    }
}
