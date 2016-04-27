
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Data;

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

    @Override
    CodeItem genLLCode() {
        //add new Data object
        return new Data(1, this.getName());
    }
}
