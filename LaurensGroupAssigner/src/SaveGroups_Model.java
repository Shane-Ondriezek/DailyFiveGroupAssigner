//accepts an arrayList<HashMap>, where each hashmap is a group
//saves every activity name and that activity's students to the current
//directory in the "activityGroups.txt" file

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SaveGroups_Model {

    final static String FILENAME = "activityGroups.txt";
    final static File GROUPFILE = new File(FILENAME);
    static boolean isFirstRotation;

    public static void saveGroups(ArrayList<HashMap> groupMaps,
            boolean isFirstRotation) {
        //groups in groupMaps are in this order:
        //readToSelf
        //readToSomeone
        //wordWork
        //workOnWriting
        //listenToReading

        SaveGroups_Model.isFirstRotation = isFirstRotation;
        System.out.println(isFirstRotation);
        ArrayList<String> readToSelfList;
        ArrayList<String> readToSomeoneList;
        ArrayList<String> wordWorkList;
        ArrayList<String> workOnWritingList;
        ArrayList<String> listenToReadingList;

        FileWriter fWriter = null;
        try {
            if (isFirstRotation) {
                //overwrite for the first rotation
                fWriter = new FileWriter(GROUPFILE);
            } else {
                //append to the file after first rotation
                fWriter = new FileWriter(GROUPFILE, true);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        BufferedWriter bWriter = new BufferedWriter(fWriter);
        if (isFirstRotation) {
            try {
                bWriter.write("FIRST ROTATION\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                bWriter.write("\n\n\n\nNEXT ROTATION\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        for (int i = 0; i < groupMaps.size(); i++) {
            switch (i) {
                case 0:
                    readToSelfList
                            = new ArrayList<>(groupMaps.get(0).values());
                    writeNamesForGroup(readToSelfList, "Read to Self",
                            bWriter);
                    break;
                case 1:
                    readToSomeoneList
                            = new ArrayList<>(groupMaps.get(1).values());
                    writeNamesForGroup(readToSomeoneList, "Read to Someone",
                            bWriter);
                    break;
                case 2:
                    wordWorkList
                            = new ArrayList<>(groupMaps.get(2).values());
                    writeNamesForGroup(wordWorkList, "Word Work",
                            bWriter);
                    break;
                case 3:
                    workOnWritingList
                            = new ArrayList<>(groupMaps.get(3).values());
                    writeNamesForGroup(workOnWritingList, "Work On Writing",
                            bWriter);
                    break;
                case 4:
                    listenToReadingList
                            = new ArrayList<>(groupMaps.get(4).values());
                    writeNamesForGroup(listenToReadingList, "Listen to Reading",
                            bWriter);
                    break;
            }

        }
        try {
            bWriter.flush();
            bWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeNamesForGroup(ArrayList<String> group,
            String activityName, BufferedWriter bWriter) {
        try {
            bWriter.write(activityName);
            bWriter.newLine();
            for (String name : group) {
                bWriter.write(name);
                bWriter.newLine();
            }
            bWriter.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
