//accepts an arrayList of names
//saves the names to the current directory in "studentNames.txt" file

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveNames_Model {

    static String fileName = "studentNames.txt";
    static File stuNames = new File(fileName);

    public static void saveNames(ArrayList<String> stuList) {
        try (FileWriter fWriter = new FileWriter(stuNames);
                BufferedWriter bWriter = new BufferedWriter(fWriter)) {

            for (String name : stuList) {
                bWriter.write(name);
                bWriter.newLine();                
            }
            bWriter.flush();
            bWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
