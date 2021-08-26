
package simplex;

import javax.swing.JOptionPane;
import javax.swing.*;



class Simplex
{
    class LPP
    {
        double OBJECTIVE_FX[];
        double CONSTRAINTS_EQS[][];
        int NO_OF_TERMS;
        int NO_OF_CONSTRAINTS;
        int NO_CONSTRAINT_TERMS[];
        boolean Max;
        boolean Min;
        //Feed in the whole LPP
        LPP()
        {       
            NO_OF_TERMS = 3;
            NO_OF_CONSTRAINTS = 2;
            int NO_CONSTRAINT_TERMS[] = { 2 , 3 };   
            Max = true;
            Min = false;
            double OBJECTIVE_FX[] = { 2, 3, 1 };
            NO_CONSTRAINT_TERMS = new int[NO_OF_CONSTRAINTS];
            OBJECTIVE_FX = new double[NO_OF_CONSTRAINTS];
            int i;
            for(i = 0; i < NO_OF_TERMS; i++)
               CONSTRAINTS_EQS[i] = new double[NO_CONSTRAINT_TERMS[i]];       
            double CONSTRAINTS_EQS[][] = {
                { 2, 0, 3 },
                { 1, 2, 3 }
            };
        }      
    }
    LPP prob;
    double TABLEAU[][];
    Simplex()
    {
        prob = new LPP();
        int i;
        for (i = 0; i < prob.NO_OF_TERMS; i++) {
            TABLEAU[i] = new double[prob.NO_OF_CONSTRAINTS + prob.NO_OF_TERMS + 2];
            for (j = 0; j < prob.NO_OF_TERMS; j++) {
                TABLEAU[i][j] = prob.CONSTRAINTS_EQS[i][j];
            }
            for (j = prob.NO_OF_TERMS; j < prob.NO_OF_CONSTRAINTS + prob.NO_OF_TERMS; j++) {
                TABLEAU[i][j] = (i == j - prob.NO_OF_TERMS ? 1 : 0);
            }
            for(j = prob.NO_OF_TERMS + prob.NO_OF_CONSTRAINTS; j < prob.NO_OF_TERMS + prob.NO_OF_CONSTRAINTS + 2; j++)
               TABLEAU[i][j] =  
                
    }
    
}