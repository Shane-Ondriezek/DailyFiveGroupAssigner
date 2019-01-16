//accepts an arrayList of names
//takes each name and puts it into a randomly chosen group, and builds that
//into an arrayList
//passes that arrayList to model for saving to a file

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MakeGroups_Model {

    public static void makeGroups(ArrayList<String> stuList) {
        
        ArrayList<String> names = stuList;
        int numStus = names.size();
        
        //Activities; Read to self is done every day, and so is one 
        //additional activity, e.g. Monday is Read to self and Word Work,
        //Tuesday is Read to self and Read to someone, etc. 
        HashMap<Integer, String> readToSelf = new HashMap<>(); 
        HashMap<Integer, String> readToSomeone = new HashMap<>(); //Activity 0
        HashMap<Integer, String> workOnWriting = new HashMap<>(); //Activity 1
        HashMap<Integer, String> wordWork = new HashMap<>(); //Activity 2
        HashMap<Integer, String> listenToReading = new HashMap<>(); //Activity 3

        //random int in [0,24] for 25 students
        Random rand = new Random();
        int stuNum = rand.nextInt(numStus);

        for (int i = 0; i < numStus / 2; ) {
            //when a name is added to readToSelf, 
            //set it to null to prevent reuse
            if (names.get(stuNum) != null) {
                readToSelf.put(stuNum, names.get(stuNum));               
                names.set(stuNum, null);  
                i++;
            }
            stuNum = rand.nextInt(25);                         
        }

        int remStus;
        if (numStus % 2 == 0) {
            remStus = numStus/2;
        } else {
            remStus = numStus/2 + 1;
        }
        
        //readToSomeone is Activity 0
        //workOnWriting is Activity 1
        //wordWork is Activity 2
        //listenToReading is Activity 3
        //4 activities, so generate a random int in [0,3]
        int activityNum = rand.nextInt(4);
        String n = names.get(stuNum);
        int maxInGroup = 6; //maximum number of students allowed in a group
        
        for (int i = 0; i < remStus; ) {
            if (n != null) {
            switch (activityNum) {
                case 0:
                    if ((!readToSomeone.containsKey(stuNum)) &&
                            readToSomeone.size() < maxInGroup) {
                        readToSomeone.put(stuNum, n);
                        names.set(stuNum, null);
                        i++;                       
                    };
                    break;
                case 1:
                    if ((!workOnWriting.containsKey(stuNum)) &&
                            workOnWriting.size() < maxInGroup) {
                        workOnWriting.put(stuNum, n);
                        names.set(stuNum, null);
                        i++;
                    };
                    break;
                case 2:
                    if ((!wordWork.containsKey(stuNum)) &&
                            wordWork.size() < maxInGroup) {
                        wordWork.put(stuNum, n);
                        names.set(stuNum, null);
                        i++;
                    };
                    break;
                case 3:
                    if ((!listenToReading.containsKey(stuNum)) &&
                            listenToReading.size() < maxInGroup) {
                        listenToReading.put(stuNum, n);
                        names.set(stuNum, null);
                        i++;
                    };
                    break;
                }
                activityNum = rand.nextInt(4);
                stuNum = rand.nextInt(25);
                n = names.get(stuNum);
            } else {
                stuNum = rand.nextInt(25);
                n = names.get(stuNum);
            }

        }
        
        //arraylist to pass so that groups can be saved to a file
        ArrayList<HashMap> groups = new ArrayList<>();
        groups.add(readToSelf);
        groups.add(readToSomeone);
        groups.add(wordWork);
        groups.add(workOnWriting);
        groups.add(listenToReading);
        
        SaveGroups_Model.saveGroups(groups);
    }
    
}