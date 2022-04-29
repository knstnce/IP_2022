package pack.View;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pack.View.Customs.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import pack.Controller.Controller2;
import pack.Model.Model2for3x3;
import pack.Model.ModelForJSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static pack.View.Customs.Custom.p;

public class View2 extends Pane implements iView {

    //TODO fix when clicking "Save matrix" two times, stack overflow
    // TODO FIX THE BUTTON GETTING NOT DISABLED WHEN THERES SITLL A WRONG VALUE BUT THE NEXT ONE IS RIGHT

    private CustomTextField[][] fieldListRb1, fieldListRb2;
    private HBox fieldsPane;
    private CustomRadioButton rb1;
    private CustomRadioButton rb2;
    private CustomButton btnStart, btnReset, btnSave;
    private ToggleGroup group = new ToggleGroup();
    private ComboBox cb;
    private CustomButton butt;
    private Label ll;
    private VBox vbUi;
    private VBox vbPo;
    private ModelForJSON JASONDERULO;
    private VBox vbLeft;
    private VBox vbRight;
    private static CustomTextField t1, t2, t3, t4, t5, t6, t7, t8, t9;
    private static double a1, a2, a3, b1, b2, b3, c1, c2, c3;
    static FileWriter file;
    private static ArrayList<Integer> other = new ArrayList<>();
    private JSONObject jsonObject;
    private JSONObject names;
    private Stage newWindow;
    private HBox emptyBox;
    private CustomButton invisibleButton;
    private VBox vbBackground;

    public View2() {
        jsonObject = getThatObject();
        this.rb1 = new CustomRadioButton("2 x 2");
        this.rb2 = new CustomRadioButton("3 x 3");
        this.rb1.setToggleGroup(group);
        this.rb2.setToggleGroup(group);
        this.btnStart = new CustomButton("START\nTHE\nMAGIK");
        this.btnStart.setDisable(true);
        this.btnReset = new CustomButton("RESET\nTHE\nMAGIK");
        this.cb = new ComboBox();
        this.cb.setDisable(true);
        this.btnSave = new CustomButton("Save Matrix");
        this.btnSave.setDisable(true);
        this.btnSave.setPrefSize(200, 20);
        JASONDERULO = new ModelForJSON();
        names = getThemNames();
        UpdateLeCombobox();
        //comboBox on action
        cb.setOnAction(event -> {
            //Call a method to determine which item in the list the user has selected
            jsonObject = getThatObject();
            if (cb.getValue() != "Saved Matrices") {
                setMatrix(YesImAGummyBear((JSONArray) jsonObject.get(cb.getValue())));
            }
        });
//Save Button on action
        btnSave.setOnAction(event -> {
            //Call a method to determine which item in the list the user has selected
            DaVoid(); //Send the selected item to the method
            cb.getSelectionModel().selectFirst();

        });

        fieldListRb1 = new CustomTextField[2][2];
        fieldListRb2 = new CustomTextField[3][3];
        fieldsPane = new HBox();

        this.invisibleButton = new CustomButton("test");
        this.invisibleButton.setVisible(false);

        vbBackground = new VBox();
        vbUi = new VBox();  // user input
        vbPo = new VBox();  // program output
        vbLeft = new VBox();
        vbRight = new VBox();

        this.emptyBox = new HBox();
        setVbUi(setHbRadios(this.rb1, this.rb2), setHbComboBox());
        setVbPo("Eigenvalues and Eigenvectors");

        setVbLeft(setLeft(this.vbUi, null));
        setVbRight(setRight(this.vbPo, setHbBottom(this.btnStart, this.btnReset)));

        setView2();
        setActions();

    }

    //update the comboBox when then save button is pressed
    private void UpdateLeCombobox() {

        cb.setValue(null);
        cb.getItems().clear();
        cb.getItems().add("Saved Matrices");
        JSONArray list = (JSONArray) names.get("names");
        Iterator<JSONObject> iterator = list.iterator();
        while (iterator.hasNext()) {
            cb.getItems().add(String.valueOf(iterator.next()));
        }
        cb.getSelectionModel().selectFirst();
    }

    //get all the numbers for the matrix chosen in the combobox and then making an arrayList with it
    private ArrayList<Double> YesImAGummyBear(JSONArray js) {


        ArrayList<String> objs = new ArrayList<>();
        ArrayList<Double> ints = new ArrayList<>();
        JSONArray list = js;
        Iterator<JSONObject> iterator;
        try {
            iterator = js.iterator();

            while (iterator.hasNext()) {
                objs.add(String.valueOf(iterator.next()));
            }
            for (int i = 0; i < objs.size(); i++) {
                ints.add(Double.valueOf(objs.get(i)));
            }
        } catch (Exception e) {
            System.out.println("we've run into a problem");
        }
        return ints;
    }

    //Set the matrix we got from the combobox into the textfields
    private void setMatrix(ArrayList<Double> ints) {
        a1 = ints.get(0);
        a2 = ints.get(1);
        a3 = ints.get(2);
        b1 = ints.get(3);
        b2 = ints.get(4);
        b3 = ints.get(5);
        c1 = ints.get(6);
        c2 = ints.get(7);
        c3 = ints.get(8);

        if (rb1.isSelected()) {
            fieldListRb1[0][0].setText(String.valueOf(a1));
            fieldListRb1[0][1].setText(String.valueOf(a2));
            fieldListRb1[1][0].setText(String.valueOf(b1));
            fieldListRb1[1][1].setText(String.valueOf(b2));
        }
        if (rb2.isSelected()) {
            fieldListRb2[0][0].setText(String.valueOf(a1));
            fieldListRb2[0][1].setText(String.valueOf(a2));
            fieldListRb2[0][2].setText(String.valueOf(a3));
            fieldListRb2[1][0].setText(String.valueOf(b1));
            fieldListRb2[1][1].setText(String.valueOf(b2));
            fieldListRb2[1][2].setText(String.valueOf(b3));
            fieldListRb2[2][0].setText(String.valueOf(c1));
            fieldListRb2[2][1].setText(String.valueOf(c2));
            fieldListRb2[2][2].setText(String.valueOf(c3));
        }
    }

    //Will make an array list for the matrix we want to get from the combobox
    public static ArrayList<Integer> makeTheArrayList(JSONArray js) {
        ArrayList<String> objs = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        JSONArray list = js;
        Iterator<JSONObject> iterator = js.iterator();
        while (iterator.hasNext()) {
            objs.add(String.valueOf(iterator.next()));
        }

        for (int i = 0; i < objs.size(); i++) {
            ints.add(Integer.valueOf(objs.get(i)));
        }
        return ints;
    }

    // the actual window for when we press save
    private void DaVoid() {
        StackPane secondaryLayout = new StackPane();

        secondaryLayout.getChildren().add(PANCAKES());
        Scene secondScene = new Scene(secondaryLayout, 300, 300);

        // New window (Stage)
        newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    // everything inside the new window that open when save is pressed
    private VBox PANCAKES() {
        VBox payne = new VBox();
        payne.setPadding(new Insets(10));
        this.ll = new Label("Choose a name for your matrix");
        ll.setTextFill(Color.BLUE);
        Text txt = new Text("YO SWITCH IT UP");


        TextField ctf = new CustomTextField();
        butt = new CustomButton("SEND IT");
        payne.getChildren().add(ll);
        payne.getChildren().add(ctf);
        payne.getChildren().add(butt);
        butt.setOnAction(event -> {

            try { // THE NAME OF THE MATRIX IS ALREADY USE
                if (jsonObject.containsKey(ctf.getText()) == true) {
                    if (!payne.getChildren().contains(txt)) {
                        payne.getChildren().add(txt);

                    }
                } else { // CLOSE THE SCENE + MATRIX NAME NOT USED
                    invisibleButton.setText("Matrix " + ctf.getText() + " has been saved.");
                    invisibleButton.setStyle("-fx-background-color: #1985A1; -fx-text-fill: E7EBEE");
                    invisibleButton.setVisible(true);

                    FadeTransition ft = new FadeTransition(Duration.millis(3000), invisibleButton);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.play();

                    humptyDumptyFellOffAWall(ctf.getText());
                    payne.getChildren().remove(txt);
                    newWindow.close();

                }
            } catch (NullPointerException e) {
                System.out.println("not another problem");

            }


        });

        return payne;
    } //sent the saved matrix to both of the JSONfiles TO SAVE

    private void humptyDumptyFellOffAWall(String name) {
        JSONArray newMatrix = new JSONArray();
        if (rb1.isSelected()) {
            newMatrix.add(View2.getT1());//
            newMatrix.add(View2.getT2());//
            newMatrix.add("0");
            newMatrix.add(View2.getT4());//
            newMatrix.add(View2.getT5());//
            newMatrix.add("0");
            newMatrix.add("0");
            newMatrix.add("0");
            newMatrix.add("0");
        }

        if (this.getRb2().isSelected()) {
            for (int i = 0; i < 9; i++) {
                newMatrix.add(this.getFieldListRb2().get(i).getText());
            }
        }
        jsonObject.put(name, newMatrix);
        JSONArray getNames = (JSONArray) names.get("names");
        getNames.add(name);
        names.put("names", getNames);

        try {
            file = new FileWriter("Resources/JsonFile.json");
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            file = new FileWriter("Resources/JsonNames.json");
            file.write(names.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        names = getThemNames();
        UpdateLeCombobox();
    }

    // get all the name from the JSONArray in the JSONFILE
    private JSONObject getThemNames() {
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader("Resources/JsonNames.json"));
            this.names = (JSONObject) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return names;
    }

    // get the JSONObject of the JSONFILE
    private JSONObject getThatObject() {
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader("Resources/JsonFile.json"));
            this.jsonObject = (JSONObject) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // HBox which will contain the save button and the combobox for the JSON
    private HBox setHbComboBox() {
        HBox hbComboBox = new HBox(100);
        hbComboBox.setPadding(new Insets(15));
        hbComboBox.getChildren().addAll(this.cb, this.btnSave);
        return hbComboBox;
    }

    //The base VBox on the left
    @Override
    public VBox setLeft(VBox vbUi, Pane graphPane) {
        VBox vbLeft = new VBox();
        vbLeft.setSpacing(10);
        vbLeft.setPrefSize(500, 695);
        vbLeft.setLayoutX(10);
        vbLeft.setLayoutY(14);
        vbLeft.getChildren().addAll(vbUi);

        return vbLeft;
    }

    //set the whole output  with all the eigenvalues and vectors
    public GridPane showDaRight(Controller2 controller2) {
        GridPane gpt = new GridPane();
        gpt.setVgap(20);
        gpt.setPrefSize(500, 595);

        if (rb2.isSelected()) {
            CustomText text = new CustomText("λ = " + controller2.getEigenValues()[0] + "\nTHE EIGEN VECTOR IS: ");
            CustomText text1 = new CustomText("λ = " + controller2.getEigenValues()[1] + "\nTHE EIGEN VECTOR IS: ");
            CustomText text2 = new CustomText("λ = " + controller2.getEigenValues()[2] + "\nTHE EIGEN VECTOR IS: ");
            gpt.add(text, 0, 0);
            if (controller2.getEigenVectors()[0].size() == 3) {
                gpt.add(newVector(1, controller2, 0), 0, 1);
            } else if (controller2.getEigenVectors()[0].size() == 6) {
                gpt.add(newVector(1, controller2, 0), 0, 1);
                gpt.add(newVector(2, controller2, 0), 1, 1);
            } else {
                gpt.add(newVector(1, controller2, 0), 0, 1);
                gpt.add(newVector(2, controller2, 0), 1, 1);
                gpt.add(newVector(3, controller2, 0), 2, 1);
            }
            gpt.add(text1, 0, 2);
            if (controller2.getEigenVectors()[1].size() == 3) {
                gpt.add(newVector(1, controller2, 1), 0, 3);
            } else if (Model2for3x3.getS2().size() == 6) {
                gpt.add(newVector(1, controller2, 1), 0, 3);
                gpt.add(newVector(2, controller2, 1), 1, 3);
            } else {
                gpt.add(newVector(1, controller2, 1), 0, 3);
                gpt.add(newVector(2, controller2, 1), 1, 3);
                gpt.add(newVector(3, controller2, 1), 2, 3);
            }
            gpt.add(text2, 0, 4);
            if (controller2.getEigenVectors()[2].size() == 3) {
                gpt.add(newVector(1, controller2, 2), 0, 5);
            } else if (Model2for3x3.getS3().size() == 6) {
                gpt.add(newVector(1, controller2, 2), 1, 5);
                gpt.add(newVector(2, controller2, 2), 2, 5);
            } else {
                gpt.add(newVector(1, controller2, 2), 0, 5);
                gpt.add(newVector(2, controller2, 2), 1, 5);
                gpt.add(newVector(3, controller2, 2), 2, 5);
            }
        }
        if (rb1.isSelected()) {
            CustomText text = new CustomText("λ = " + controller2.getEigenValues()[0] + "\nTHE EIGEN VECTOR IS: ");
            CustomText text1 = new CustomText("λ = " + controller2.getEigenValues()[1] + "\nTHE EIGEN VECTOR IS: ");
            gpt.add(text, 0, 0);
            if (controller2.getEigenVectors()[0].size() == 2) {
                gpt.add(newVector(1, controller2, 0), 0, 1);
            } else {
                gpt.add(newVector(1, controller2, 0), 0, 1);
                gpt.add(newVector(2, controller2, 0), 1, 1);
            }
            gpt.add(text1, 0, 2);
            if (controller2.getEigenVectors()[1].size() == 2) {
                gpt.add(newVector(1, controller2, 1), 0, 3);
            } else {
                gpt.add(newVector(1, controller2, 1), 0, 3);
                gpt.add(newVector(2, controller2, 1), 1, 3);
            }
        }
        return gpt;
    }

    // create a new vector with the bracket images and the numbers
    public HBox newVector(int counter, Controller2 controller2, int i) { // counter is the vector if size=6 there is counter 1 and 2 possible
        HBox hbx = new HBox(10);
        VBox vbx1 = new VBox();
        ImageView imL = new ImageView(new Image(p + "Right.png"));
        imL.setFitWidth(10);
        imL.setFitHeight(75);
        ImageView imR = new ImageView(new Image(p + "Left.png"));
        imR.setFitWidth(10);
        imR.setFitHeight(75);
        if (rb1.isSelected()) {
            vbx1 = putVertical2x2(counter, controller2, i);
            vbx1.setPrefHeight(75);
        }
        if (rb2.isSelected()) {
            vbx1 = putVertical3x3(counter, controller2, i);
            vbx1.setPrefHeight(75);
        }
        hbx.getChildren().addAll(imL, vbx1, imR);
        hbx.setAlignment(Pos.CENTER);
        return hbx;
    }

    // put the eigenvector in vertical for a 3x3 matrix to then be put in between the two brackets for the output
    public VBox putVertical3x3(int counter, Controller2 controller2, int i) {
        VBox vbx1 = new VBox();
        vbx1.setAlignment(Pos.CENTER);
        Double numba1, numba2, numba3;

        if (counter == 1) {
            numba1 = controller2.getEigenVectors()[i].get(0);
            numba2 = controller2.getEigenVectors()[i].get(1);
            numba3 = controller2.getEigenVectors()[i].get(2);
        } else if (counter == 2) {
            numba1 = controller2.getEigenVectors()[i].get(3);
            numba2 = controller2.getEigenVectors()[i].get(4);
            numba3 = controller2.getEigenVectors()[i].get(5);
        } else {
            numba1 = controller2.getEigenVectors()[i].get(6);
            numba2 = controller2.getEigenVectors()[i].get(7);
            numba3 = controller2.getEigenVectors()[i].get(8);
        }

        CustomText nb1 = new CustomText(Double.toString(numba1));
        CustomText nb2 = new CustomText(Double.toString(numba2));
        CustomText nb3 = new CustomText(Double.toString(numba3));
        vbx1.getChildren().add(nb1);
        vbx1.getChildren().add(nb2);
        vbx1.getChildren().add(nb3);

        return vbx1;
    }

    // put the eigenvector in vertical for a 2x2 matrix to then be put in between the two brackets for the output
    public VBox putVertical2x2(int counter, Controller2 controller2, int i) {
        VBox vbx1 = new VBox(15);
        vbx1.setAlignment(Pos.CENTER);
        Double numba1, numba2;

        if (counter == 1) {
            numba1 = controller2.getEigenVectors()[i].get(0);
            numba2 = controller2.getEigenVectors()[i].get(1);
        } else {  // if counter == 2
            numba1 = controller2.getEigenVectors()[i].get(2);   // changed 1 to i
            numba2 = controller2.getEigenVectors()[i].get(3);   // changed 1 to i
        }
        CustomText nb1 = new CustomText(Double.toString(numba1));
        CustomText nb2 = new CustomText(Double.toString(numba2));
        vbx1.getChildren().addAll(nb1, nb2);
        return vbx1;
    }

    //set a VBox on the left which will contain everything on the left
    private void setVbUi(HBox hbRadios, HBox hbComboBox) {
        this.vbUi.setSpacing(5);
        this.vbUi.setPrefSize(500, 695);
        this.vbUi.setStyle("-fx-background-color: #333335");
        this.emptyBox.setAlignment(Pos.CENTER);
        this.emptyBox.getChildren().add(invisibleButton);
        this.vbUi.getChildren().addAll(hbRadios, hbComboBox, this.emptyBox);
    }

    //set a VBox on the right which will contain everything on the right
    private void setVbPo(String title) {
        this.vbBackground.setPrefSize(500, 580);
        BackgroundImage myBI = new BackgroundImage(new Image(p + "View2.png", 520, 580, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.vbBackground.setBackground(new Background(myBI));

        this.vbPo.setPrefSize(500, 595);
        //this.vbPo.setPadding(new Insets(15));
        this.vbPo.setSpacing(15);
        this.vbPo.setAlignment(Pos.TOP_CENTER);
        this.vbPo.setStyle("-fx-background-color: #333335");
        this.vbPo.getChildren().addAll(Custom.setTitle(title), this.vbBackground);
    }

    //set the whole view
    public void setView2() {
        this.setPrefSize(1050, 750);
        this.setStyle("-fx-background-color: #6F6F77;");    // Blue Grey
        this.getChildren().addAll(this.vbLeft, this.vbRight);
    }

    //set actiong for radio buttont
    public void setActions() {
        rb1.setOnAction(event -> { //2x2 radio button
            this.btnStart.setDisable(false);
            this.btnSave.setDisable(false);
            this.cb.setDisable(false);

            fieldsPane = setFields(fieldListRb1);
            this.vbUi.getChildren().clear();
            this.vbUi.getChildren().addAll(setHbRadios(rb1, rb2), setHbComboBox(), emptyBox, fieldsPane);
        });

        rb2.setOnAction(event -> { // 3x3 radio button
            this.btnStart.setDisable(false);
            this.btnSave.setDisable(false);
            this.cb.setDisable(false);
            fieldsPane = setFields(fieldListRb2);
            this.vbUi.getChildren().clear();
            this.vbUi.getChildren().addAll(setHbRadios(rb1, rb2), setHbComboBox(), emptyBox, fieldsPane);
        });
        this.btnStart.setOnAction(event -> {
            handleStart(rb1.isSelected());
        });
        this.btnReset.setOnAction(event -> {
            handleReset();
        });
    }

    //format the output for each eigenvalues
    public HBox setFields(CustomTextField[][] textFields) {
        ImageView iv1 = new ImageView(new Image(p + "Right.png")); // right vector bracket
        iv1.setFitWidth(54);
        iv1.setFitHeight(270);
        ImageView iv2 = new ImageView(new Image(p + "Left.png")); //left vector bracket
        iv2.setFitWidth(54);
        iv2.setFitHeight(270);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);

        HBox hbText = new HBox();
        hbText.setPadding(new Insets(15));
        hbText.setAlignment(Pos.CENTER);
        CustomText stringEqualZero = new CustomText("= 0");
        stringEqualZero.setStyle("-fx-fill: #E7EBEE");
        stringEqualZero.changeSize(30);
        hbText.getChildren().add(stringEqualZero);

        int rows = textFields.length;
        int cols = textFields[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rows == 2) {
                    iv1.setFitHeight(200);
                    iv2.setFitHeight(200);
                    iv1.setFitWidth(40);
                    iv2.setFitWidth(40);
                }
                textFields[i][j] = new CustomTextField();
                textFields[i][j].setPrefSize(50, 40);
                int finalI = i;
                int finalJ = j;

                textFields[i][j].textProperty().addListener((observable, oldValue, newValue) -> {
                    btnStart.setDisable(textFields[finalI][finalJ].checkField());
                });
                gridPane.add(textFields[i][j], j, i);
            }
        }
        HBox hbFields = new HBox();
        hbFields.setPadding(new Insets(20, 0, 0, 0));
        hbFields.getChildren().addAll(iv1, gridPane, iv2, hbText);
        hbFields.setAlignment(Pos.CENTER);
        return hbFields;
    }

    //start buttont
    public void handleStart(boolean isRb1Selected) {
        if (isRb1Selected) {
            for (int i = 0; i < fieldListRb1.length; i++) {
                for (int j = 0; j < fieldListRb1[0].length; j++) {
                    if (fieldListRb1[i][j].getText().equals("")) {
                        fieldListRb1[i][j].setText("0");
                    }
                }
            }
        } else {
            for (int i = 0; i < fieldListRb2.length; i++) {
                for (int j = 0; j < fieldListRb2[0].length; j++) {
                    if (fieldListRb2[i][j].getText().equals("")) {
                        fieldListRb2[i][j].setText("0");
                    }
                }
            }
        }
        Controller2 controller2 = new Controller2(this);
        addOutput(controller2);
    }

    //reset button
    public void handleReset() {
        this.getChildren().clear();
        btnStart.setDisable(true);
        btnSave.setDisable(true);
        cb.setDisable(true);
        rb1.setSelected(false);
        rb2.setSelected(false);
        this.vbUi.getChildren().removeAll(fieldsPane, invisibleButton);
        this.vbPo.getChildren().clear();
        this.getChildren().addAll(this.vbLeft, this.vbRight);
    }

    // the output reset
    public void addOutput(Controller2 controller2) {
        this.vbBackground.getChildren().clear();
        this.vbBackground.getChildren().add(showDaRight(controller2));
    }

    // get the textfield for the 2x2 button
    public ArrayList<CustomTextField> getFieldListRb1() {
        ArrayList<CustomTextField> fieldList = new ArrayList<>();
        for (CustomTextField[] tfArray : this.fieldListRb1) {
            for (CustomTextField tf : tfArray) {
                fieldList.add(tf);
            }
        }
        return fieldList;
    }

    // get the textfield for the 3x3 button
    public ArrayList<CustomTextField> getFieldListRb2() {
        ArrayList<CustomTextField> fieldList = new ArrayList<>();
        for (CustomTextField[] tfArray : this.fieldListRb2) {
            for (CustomTextField tf : tfArray) {
                fieldList.add(tf);
            }
        }
        return fieldList;
    }

    public CustomRadioButton getRb1() {
        return rb1;
    }

    public CustomRadioButton getRb2() {
        return rb2;
    }

    public ComboBox getCb() {
        return cb;
    }

    public static CustomTextField getT1() {
        return t1;
    }

    public static CustomTextField getT2() {
        return t2;
    }

    public static CustomTextField getT4() {
        return t4;
    }

    public static CustomTextField getT5() {
        return t5;
    }

    private void setVbRight(VBox vbRight) {
        this.vbRight = vbRight;
    }

    private void setVbLeft(VBox vbLeft) {
        this.vbLeft = vbLeft;
    }

    public ArrayList<Integer> getOther() {
        return other;
    }

}