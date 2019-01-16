//gets an arrayList of textfields (some of which have data and some that don't),
//eliminates any empty fields, compiles the data into an arrayList of strings,
//and passes that arrayList to the model for saving into a text file

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TextField;

public class BuildStuListForSave_Controller {

    static ArrayList<String> stuList = new ArrayList<>();

    public static void parseStuInfo(ArrayList<TextField> stuTFs)
            throws IOException {
        stuList.clear();

        for (int i = 0; i < stuTFs.size(); i++) {
            //eliminate empty textfields
            if (!stuTFs.get(i).getText().isEmpty()) {
                String name = stuTFs.get(i).getText();
                stuList.add(name);
            }
        }

        //pass all student names to model to either make groups or save data
        SaveNames_Model.saveNames(stuList);

    }
}
