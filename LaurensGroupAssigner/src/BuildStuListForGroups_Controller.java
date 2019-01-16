//accepts an arrayList of textFields
//removes data from textFields and compiles it into an arrayList of names
//passes that arrayList to the model for making groups

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TextField;

public class BuildStuListForGroups_Controller {
    
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

        MakeGroups_Model.makeGroups(stuList);
    }
}
