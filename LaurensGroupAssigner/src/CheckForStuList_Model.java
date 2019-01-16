//checks for the file "studentNames.txt"
//if the file exists, the names are stored in an arrayList and returned
//so that the view can populate the textFields with the names

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckForStuList_Model {

    static public ArrayList<String> checkForStuList() {
        ArrayList<String> extantNames = new ArrayList<>();
        File stuList = new File("studentNames.txt");
        if (stuList.isFile()) {
            try (FileReader fReader = new FileReader(stuList);
                    BufferedReader bReader = new BufferedReader(fReader)) {
                String name = null;
                while ((name = bReader.readLine()) != null) {
                    extantNames.add(name);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CheckForStuList_Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CheckForStuList_Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return extantNames;
    }
}
