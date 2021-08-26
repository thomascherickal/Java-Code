
package parser;
import java.util.Stack;
import java.util.StringTokenizer;
/**
 *
 * @author Thomas
 */
public class ExprEvaluator {
    
    private String infix;
    private String postfix;
    private enum Priorities
    { NA(-1) , MIN, LOW, MED, HIGH, MAX };
    
    private StringTokenizer st;
    private Stack s;
    
    final double UNKNOWN_OP = -1;
    
    /** Creates a new instance of ExprEvaluator */
    public ExprEvaluator(String infix) {
        s = new Stack<String>();
        this.infix = infix;
        st = new StringTokenizer(infix);
    }
    
    /**Priority Manager
    */
    Priorities Priority(String operator)
    {
        if (operator.equals("!") || operator.equals("--")) // -- is unary negation
        {
            return Priorities.MAX;
        }
        else if (operator.equals("*") || operator.equals("/") || operator.equals("%"))
        {
            return Priorities.HIGH;
        }
        else if (operator.equals("+") || operator.equals("-"))
        {
            return Priorities.MED;
        }
        else if (operator.equals("&&") || operator.equals("||"))
        {
            return Priorities.LOW;
        }
        else if (operator.equals("="))
        {
            return Priorities.MIN;
        }
        else return Priorities.NA;
        
    }
    
    /**Convert an input string to postfix form*/
    /*Method used
     */
    String Convert2Postfix()
    {
       return new String(); 
    }
    double PerformOperation(String op, String ... tk)
    {
        if (op.equals("--")) return -Double.parseDouble(tk[0]);
        else if (op.equals("/")) return Double.parseDouble(tk[0]) / Double.parseDouble(tk[1]);
        else if (op.equals("*")) return Double.parseDouble(tk[0]) * Double.parseDouble(tk[1]);
        else if (op.equals("+")) return Double.parseDouble(tk[0]) + Double.parseDouble(tk[1]);
        else if (op.equals("-")) return Double.parseDouble(tk[0]) - Double.parseDouble(tk[1]);
        else 
            return UNKNOWN_OP;
        
    }
    String EvaluateExpression()
    {
       return new String();  
    }
    

    
}
