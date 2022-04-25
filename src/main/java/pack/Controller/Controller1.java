package pack.Controller;



import javafx.geometry.Point3D;
import pack.Model.Model1;
import pack.View.Customs.CustomTextField;
import pack.View.View1;


import java.util.ArrayList;

public class Controller1 {

    View1 view;
    private ArrayList<CustomTextField> fieldList;
    private ArrayList<Double> matrixCoefficients;
    private boolean is2by2;
    private Model1 model;

    public Controller1(View1 view) {
        this.view = view;
        is2by2 = view.getRb1().isSelected();
        fieldList = new ArrayList<>();
        matrixCoefficients = new ArrayList<>();
        transform();
    }

    // Include 2x2 as well later
    public void transform() {
        if (is2by2) {
            fieldList = view.getFieldListRb1();
            transform2x2();
        } else {
            fieldList = view.getFieldListRb2();
            transform3x3();
        }
    }

    public void transform2x2() { // always 6
        for (int i = 0; i < this.fieldList.size(); i++) {
            if (i == 2 || i == 5 ) {
                continue;
            }
            this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(i).getText()));
        }
        this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(2).getText()));
        this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(5).getText()));

    }

    public void transform3x3() {   //always 12d
        for (int i = 0; i < this.fieldList.size(); i++) {
            if (i == 3 || i == 7 || i == 11) {
                continue;
            }
            this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(i).getText()));
        }
        this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(3).getText()));
        this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(7).getText()));
        this.matrixCoefficients.add(Double.parseDouble(this.fieldList.get(11).getText()));
    }


    String rank;
    String boop;
    public String[] getOutput() {
        model = new Model1(this.matrixCoefficients, is2by2);
        if (is2by2) {
            double[][] A = Model1.getMatrixA_2x2();
            double[] b = Model1.getMatrixB_2x2();
            double[] x = model.SLEsolve(A, b);
            boop = model.printRowEchelonForm(A,b);
            String[] sol = new String[x.length];
            if (x[2] == 0) {
                for (int i = 0; i < x.length - 1; i++) {
                    sol[i] = " No solution";
                }
                rank = "0";
            }
            else if (x[2] == 1) {
                for (int i = 0; i < x.length - 1; i++)
                    sol[i] = " " + x[i];

                rank = "" + b.length;
            }
                else if (x[2] == 2){
                    sol[0] = " " + x[0] + " + (" + x[1] + ")t";
                    sol[1] = " t";
                rank = "1";
            }

            return sol;
        } else {
            double[][] A = Model1.getMatrixA_3x3();
            double[] b = Model1.getMatrixB_3x3();
            double[] x = model.SLEsolve(A, b);
            String[] sol = new String[x.length];
            if (x[4] == 0) {
                for (int i = 0; i < x.length - 1; i++)
                    sol[i] = " No solution";
                rank = "0";
            }
            else if (x[4] == 1){
                for (int i = 0; i < x.length - 1; i++) {
                    sol[i] = " " + x[i];
                }
                rank = ""+b.length;
                }
            else if (x[4] == 2) {
                sol[0] = " " + x[0] + " + (" + x[1] + ")t";
                sol[1] = " " + x[2] + " + (" + x[3] + ")t";
                sol[2] = "t";

                rank = "2";
            } else {
                sol[0] = " " + x[0] + " + (" + x[1] + ")s" + " + (" + x[2] + ")t";
                sol[1] = "s";
                sol[2] = "t";

                rank = "1";
            }
            sol[sol.length - 1] = boop;
            return sol;
        }
    }

    public String getRank() {
        return rank;
    }

    public int getArraySize() {
        model = new Model1(this.matrixCoefficients, is2by2);
        if (is2by2) {
           return 2;
        } else {
           return 3;
        }
    }


}
