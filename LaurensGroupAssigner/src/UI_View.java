/*
This code controls the UI for "Lauren's Group-Maker"

Currently: 
    The user can add/remove students to a master list by selecting "Add/Remove Student(s)" from a combobox. This list is saved in the current directory to the text file "studentNames.txt" when the user clicks the "Save" button. 
    The user can make groups with the list of students by selecting "Use This List" from the combobox (this is the default selection). This changes the text on the button from "Save" to "Make Groups". When the user clicks "Make Groups", the text file "activityGroups.txt" is saved to the current directory. This file lists an activity name, followed by one or more student names. Once all the students for a particular group have been listed, there is an empty line, followed by the next activity name, then the students for that activity, etc., until all the activities are listed with their respective students.

TODO: 
    generate groups for multiple days 
    display the group in the UI after they have been generated

Optional:
    allow the user to select the number of days for which they want to generate
        groups; e.g. if a week is shortened due to a holiday, the user should be        able to select 4 days
    allow the user to add a student directly to a group within the UI. E.g. if a        new student arrives on Wednesday, the user should not have to remake            groups for the entire week, rather they should be able to add the new           student to whatever groups they want, and the student lists (i.e. the           one that is displayed as well as the one in the studentNames.txt file)          should update automatically
    allow the user to enter the maximum number of total students (currently              hard-coded at 25)
    allow the user to enter the maximum number of students for a single group           (currently hard-coded at 6)
    allow the user to add/remove activities
    
*/
import java.io.IOException;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane root = new ScrollPane();
        root.setFitToHeight(true);
        root.setFitToWidth(true);
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(true);

        int numdays = 5;
        for (int i = 0; i < numdays + 1; i++) {
            double prefWidth;
            if (i == 0) {
                prefWidth = 160;
            } else {
                prefWidth = 50;
            }
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            ColumnConstraints cc = new ColumnConstraints(150,
                    prefWidth, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER,
                    true);
            gp.getColumnConstraints().add(cc);
            if (i < 2) {
                gp.getRowConstraints().add(rc);
            }
        }

        String addRmStus = "Add/Remove Student(s)";
        String[] stuCbOptions = {"Use This List", addRmStus};
        ComboBox addRmCb = new ComboBox(
                FXCollections.observableArrayList(stuCbOptions));
        addRmCb.setMaxWidth(Double.MAX_VALUE);
        addRmCb.getSelectionModel().selectFirst();
        gp.add(addRmCb, 0, 0);

        /*ChangeListener<Boolean> txtFldCL = new ChangeListener<Boolean>() {
            @Override
            public void changed(
                    ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if (!newValue && 
                        addRmCb.getValue().equals(addRmStus)) {
                    System.out.println();
                    System.out.println("pass to controller ");
                    //BuildStuList_Controller.parseStuInfo();
                }
            }
        };*/
 /*ChangeListener<String> txtFldCL = new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!(newValue.equalsIgnoreCase(oldValue)) && 
                        addRmCb.getValue().equals(addRmStus)) {
                    System.out.println();
                    System.out.println(newValue);
                    //BuildStuList_Controller.parseStuInfo();
                }
            }
        };*/
        //check to see if studentNames.txt already exists; 
        //if yes, poplulate the textfields with those names
        ArrayList<String> extantNames = 
                CheckForStuList_Model.checkForStuList();
        
        int numStus = 25;
        ArrayList<TextField> stuTFs = new ArrayList<>(numStus);
        for (int i = 0; i < numStus; i++) {
            TextField tf = new TextField("");
            String nameOfTf = "stuTf" + i;
            tf.setId(nameOfTf);
            tf.setDisable(true);
            tf.setTooltip(new Tooltip("Enter Student Name"));
            stuTFs.add(tf);
        }
        
        if (!extantNames.isEmpty()) {
            for (int i = 0; i < extantNames.size(); i++) {
                stuTFs.get(i).setText(extantNames.get(i));
            }
        }

        VBox stuVb = new VBox();
        Button mkSvBt = new Button("Make Groups");
        //mkSvBt.setDisable(true);
        mkSvBt.setMaxWidth(Double.MAX_VALUE);
        stuVb.getChildren().add(mkSvBt);                       
        
        //passes student names to the controller
        EventHandler<ActionEvent> mkSvOnClick = (ActionEvent e) -> {
            if (mkSvBt.getText().equals("Save")) {
                try {
                    BuildStuListForSave_Controller.parseStuInfo(stuTFs);
                } catch (IOException ex) {
                    Logger.getLogger(UI_View.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } else {
                //send to method to make groups
                try {
                    BuildStuListForGroups_Controller.parseStuInfo(stuTFs);
                } catch (IOException ex) {
                    Logger.getLogger(UI_View.class.getName()).
                            log(Level.SEVERE, null, ex);
                }                
            }
        };
        mkSvBt.setOnAction(mkSvOnClick);

        ChangeListener<String> cbCL = (ObservableValue<? extends String> 
                observable, String oldValue, String newValue) -> {
            if (addRmCb.getValue().equals(addRmStus)) {
                for (TextField tf : stuTFs) {
                    tf.setDisable(false);
                }
                mkSvBt.setText("Save");
            } else {
                for (TextField tf : stuTFs) {
                    tf.setDisable(true);
                }
                mkSvBt.setText("Make Groups");
            }
        };

        addRmCb.valueProperty().addListener(cbCL);

        stuVb.getChildren().addAll(stuTFs);
        gp.add(stuVb, 0, 1);

        for (int i = 1; i < numdays + 1; i++) {
            Label dayLabel = new Label("Day " + i);
            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setAlignment(Pos.CENTER);
            gp.add(dayLabel, i, 0);
        }

        root.setContent(gp);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
