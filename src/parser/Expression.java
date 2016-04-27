
package parser;
import java.io.PrintWriter;

public abstract class Expression {
    abstract void print(PrintWriter p, String tabLvl);
}
