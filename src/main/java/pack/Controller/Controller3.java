package pack.Controller;

import javafx.scene.text.Text;
import pack.Model.Model3;
import pack.View.Customs.CustomText;
import pack.View.Customs.CustomTextField;
import pack.View.View3;

import java.util.ArrayList;

public class Controller3 {

    View3 view;

    //Take the graph and do it here bb
    private Model3 model= new Model3();


    public Controller3(View3 view) {
        this.view = view;
    }


    private ArrayList<CustomTextField> fieldList;
    private ArrayList<Double> matrixCoefficients;
    private boolean is2by2;

    //Lines
    public void getValues() {
        if(view.getRb1().isSelected()) {
        model.bringT(view.getFieldListRb1());
        model.setThingies(); }

        if(view.getRb2().isSelected()) { {model.transform(view.getFieldListRb2());}}
    }


    public String identifyLines(){

        if(model.parallel()==true) {return "parallel";}

        if(model.det()==true) { return "intersecting"; }

        if(model.det()==false) {return "skew";}

        return null;}


    public void addElementsGraph() {
        if(view.getRb1().isSelected()) {
        //First line
        view.getGraph().addLine(  model.linesPoints(1,0), model.linesPoints(1,2),model.dirVector(1));
        //Second line
        view.getGraph().addLine(model.linesPoints(2,0),model.linesPoints(2,2),model.dirVector(2));
     if(identifyLines()=="intersecting"){view.getGraph().addPoint(model.intersectionLines());}}

      else {
            model.crossProduct(model.n1,model.n2);
            model.solutionPoints(5);
            view.getGraph().addPlane(model.n1[0]/-model.n1[3],model.n1[1]/-model.n1[3],model.n1[2]/-model.n1[3],"Plane1");
            view.getGraph().addPlane(model.n2[0]/-model.n2[3],model.n2[1]/-model.n2[3],model.n2[2]/-model.n2[3],"Plane1");
            view.getGraph().addLine(model.solutionPoints(95), model.solutionPoints(-10),model.crossProduct(model.n1,model.n2));}
    }


    public CustomText[] GenericTexts() {
        if(view.getRb1().isSelected()) {

            Text l = (Text) view.getGraph().labelsList.get(2);
            CustomText c = new CustomText(l.getText());
            Text l2 = (Text) view.getGraph().labelsList.get(5);
            CustomText c2 = new CustomText(l2.getText());
            CustomText[] custom = {c, c2};
            return custom;

        }
        else {
            CustomText c= new CustomText(model.planeEq(1));
            CustomText c2= new CustomText(model.planeEq(2));
            CustomText [] custom= {c,c2};
            return custom;
        }

    }

    public CustomText[] SolutionTexts() {
        if(view.getRb1().isSelected()) {
        if(identifyLines()=="intersecting"){
            CustomText l= new CustomText("S="+model.x[0]+"\n"+
                                   "T="+model.x[1]+"\n");

            CustomText l2= new CustomText("Point: <"+model.intersectionLines().getX()+", "+model.intersectionLines().getY()
           +", "+model.intersectionLines().getZ()+">");

            CustomText [] custom= {l,l2};

            return custom;

        }

        if(identifyLines()=="skew"){
            CustomText l= new CustomText("These two lines are skew"+"\n"
                                          +"The closest distance between+" +"\n"+
                                          "them is: "+model.distanceSkew());
            CustomText [] custom= {l};

            return custom;}

        if(identifyLines()=="parallel"){
            CustomText l= new CustomText("These two lines are parallel"+"\n"
                                          +"No intersection point");
            CustomText [] custom= {l};

            return custom;

        }  }

        else {
            CustomText c=new CustomText("Line of intersection:");
            CustomText c1=new CustomText("Direction vector: <"+model.getCrossProduct()[0]+", "+model.getCrossProduct()[1]+", "+model.getCrossProduct()[2]+">");
            CustomText c2=new CustomText("Point: <"+model.solutionPoints(95).getX()+", "+model.solutionPoints(95).getY()+", "+model.solutionPoints(95).getZ()+">");
            CustomText [] custom= {c,c1,c2};

            return custom;
        }

        return null;

    }








}