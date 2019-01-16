//accepts an arrayList of HashMaps, where hashmap is a group
//saves every activity name and that activitie's students to the current
//directory in the "activityGroups.txt" file

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveGroups_Model {
    final static String FILENAME = "activityGroups.txt";
    final static File GROUPFILE = new File(FILENAME);
    
    public static void saveGroups(ArrayList<HashMap> groupMaps) {
            //groups in groupMaps are in this order:
            //readToSelf
            //readToSomeone
            //wordWork
            //workOnWriting
            //listenToReading
            
            ArrayList<String> readToSelfList;
            ArrayList<String> readToSomeoneList;
            ArrayList<String> wordWorkList;
            ArrayList<String> workOnWritingList;
            ArrayList<String> listenToReadingList;
            
            FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(GROUPFILE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            
            for (int i = 0; i < groupMaps.size(); i++) {
                switch (i) {
                    case 0:
                        readToSelfList = 
                                new ArrayList<>(groupMaps.get(0).values());
                        writeNamesForGroup(readToSelfList, "Read to Self",
                                bWriter);
                        break;
                    case 1:
                        readToSomeoneList = 
                                new ArrayList<>(groupMaps.get(1).values());
                        writeNamesForGroup(readToSomeoneList, "Read to Someone",
                                bWriter);
                        break;
                    case 2:                      
                        wordWorkList = 
                                new ArrayList<>(groupMaps.get(2).values());
                        writeNamesForGroup(wordWorkList, "Word Work",
                                bWriter);
                        break;
                    case 3:
                        workOnWritingList = 
                                new ArrayList<>(groupMaps.get(3).values());
                        writeNamesForGroup(workOnWritingList, "Work On Writing",
                                bWriter);
                        break;
                    case 4:
                        listenToReadingList = 
                                new ArrayList<>(groupMaps.get(4).values());
                        writeNamesForGroup(listenToReadingList, "Listen to Reading",
                                bWriter);
                        break;
                }

            }  
        try {
            bWriter.flush();
            bWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveGroups_Model.class.getName()).log(Level.SEVERE, null, ex);
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