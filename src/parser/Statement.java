
package parser;

import java.io.PrintWriter;

public abstract class Statement {
    abstract void print(PrintWriter p, String tabLvl);
}
