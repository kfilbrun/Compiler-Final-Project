
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;

public class Call extends Expression{
    //variable declaration
    public String name;
    public ArrayList<Expression> expressions;
    
    //constructor
    public Call(String n, ArrayList<Expression> e){
        name = n;
        expressions = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        if(expressions == null){
            w.print(tabLvl + name + "(");
        }
        else {
            w.println(tabLvl + name + "\n" + tabLvl + "(");
        }
        
        if(expressions != null){
            for(Expression exp : expressions){
                if(exp != null) {
                    exp.print(w, tabLvl + "    ");
                }
            }
        }
        if(expressions == null){
                w.println(")");
        }
        else {
            w.println(tabLvl + ")");
        }
        
    }

    @Override
    void genLLCode() {
    }
}
