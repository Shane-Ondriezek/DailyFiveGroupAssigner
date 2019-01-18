//accepts an arrayList<String> of names
//creates two sets of groups; first set puts half the names in a specific
//group and spreads the other half among remaining groups; second set swaps
//the names in the specific group to a different group, and puts the names from
//the "remaining groups" (mentioned in the first set) into the specific group
//each set is assembled into an individual arrayList<HashMap>, and each 
//arrayList is passed to another class individually for saving to a file

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

        ArrayList<HashMap> firstRotation = createFirstRotation(
                names, readToSelf, readToSomeone, workOnWriting, wordWork,
                listenToReading);
        
        SaveGroups_Model.saveGroups(firstRotation);
        
        ArrayList<HashMap> secondRotation = createSecondRotation(
                names, readToSelf, readToSomeone, workOnWriting, wordWork,
                listenToReading);

        SaveGroups_Model.saveGroups(secondRotation);
    }

    private static ArrayList<HashMap> createFirstRotation(
            ArrayList<String> names,
            HashMap<Integer, String> readToSelf,
            HashMap<Integer, String> readToSomeone,
            HashMap<Integer, String> workOnWriting,
            HashMap<Integer, String> wordWork,
            HashMap<Integer, String> listenToReading) {

        int numStus = names.size();

        //random int in [0,24] for 25 students
        Random rand = new Random();
        //each student gets a number as a key for inserting into hashmap
        //so for 25 students, i.e. numStus==25, stuNum is in [0,24]
        int stuNum = rand.nextInt(numStus);

        //create the readToSelf group with half ( or half-1) of the students
        for (int i = 0; i < numStus / 2;) {
            //when a name is added to readToSelf, 
            //set it to null to prevent reuse
            if (names.get(stuNum) != null) {
                readToSelf.put(stuNum, names.get(stuNum));
                names.set(stuNum, null);
                i++;
            }
            stuNum = rand.nextInt(25);
        }

        ArrayList<HashMap> groupsFirstRotation = new ArrayList<>();
        groupsFirstRotation.add(readToSelf);

        //now create the remaining groups (excluding readToSelf)
        ArrayList<HashMap> remainingGroups = createRemainingGroups(names,
                readToSelf, readToSomeone, workOnWriting, wordWork,
                listenToReading, 1);
        groupsFirstRotation.addAll(remainingGroups);
        return groupsFirstRotation;

    }

    //the second rotation switches the students in readToSelf to the other
    //groups, and moves all the students from the other groups to readToSelf
    private static ArrayList<HashMap> createSecondRotation(
            ArrayList<String> names,
            HashMap<Integer, String> readToSelf,
            HashMap<Integer, String> readToSomeone,
            HashMap<Integer, String> workOnWriting,
            HashMap<Integer, String> wordWork,
            HashMap<Integer, String> listenToReading) {

        //move students from all other groups to newReadToSelf
        HashMap<Integer, String> newReadToSelf = new HashMap<>();
        newReadToSelf.putAll(readToSomeone);
        newReadToSelf.putAll(workOnWriting);
        newReadToSelf.putAll(wordWork);
        newReadToSelf.putAll(listenToReading);

        ArrayList<HashMap> groupsSecondRotation = new ArrayList<>();
        groupsSecondRotation.add(newReadToSelf);

        //move the students in the old readToSelf to their new groups
        //arrayList is needed for createRemainingGroups method
        ArrayList<String> namesForRemainingGroups = new ArrayList<>();
        Iterator itr = readToSelf.values().iterator();
        while (itr.hasNext()) {
            namesForRemainingGroups.add((String) itr.next());
        }

        ArrayList<HashMap> remainingGroups = createRemainingGroups(
                namesForRemainingGroups, readToSelf, readToSomeone,
                workOnWriting, wordWork, listenToReading, 2);

        groupsSecondRotation.addAll(remainingGroups);
        return groupsSecondRotation;
    }

    private static ArrayList<HashMap> createRemainingGroups(
            ArrayList<String> names,
            HashMap<Integer, String> readToSelf, //only used for second rotation
            HashMap<Integer, String> readToSomeone,
            HashMap<Integer, String> workOnWriting,
            HashMap<Integer, String> wordWork,
            HashMap<Integer, String> listenToReading,
            int rotationNumber) {

        readToSomeone.clear();
        workOnWriting.clear();
        wordWork.clear();
        listenToReading.clear();;
       
        Random rand = new Random();
        int numStus;
        int remStus;
        int stuNum;
        Iterator itr = null;
        if (rotationNumber == 1) {
            numStus = names.size();
            if (numStus % 2 == 0) {
                remStus = numStus / 2;
            } else {
                remStus = numStus / 2 + 1;
            }
            itr = names.iterator();
            stuNum = rand.nextInt(numStus);
        } else {
            numStus = readToSelf.size();
            remStus = numStus;
            //get any key from the hashmap as the initial stuNum
            itr = readToSelf.keySet().iterator();
            stuNum = (int) itr.next();
        }

        //readToSomeone is Activity 0
        //workOnWriting is Activity 1
        //wordWork is Activity 2
        //listenToReading is Activity 3
        //4 activities, so generate a random int in [0,3]
        int activityNum = rand.nextInt(4);
        String n;
        if (rotationNumber == 1) {
            n = names.get(stuNum);
        } else {
            n = readToSelf.get(stuNum);
        }
        int maxInGroup = 6; //maximum number of students allowed in a group

        //create the remaining groups (excluding readToSelf)
        for (int i = 0; i < remStus && 
                (itr.hasNext() || n.equals(names.get(names.size()-1))); ) {
            //if the name hasn't already been used, add it to a group
            if (n != null) {
                //System.out.println("stuNum: " + stuNum + " I: " + i);
                switch (activityNum) {
                    case 0:
                        if ((!readToSomeone.containsKey(stuNum))
                                && readToSomeone.size() < maxInGroup) {
                            readToSomeone.put(stuNum, n);
                            if (rotationNumber == 1) {
                                names.set(stuNum, null);
                            } else {
                                readToSelf.put(stuNum, null);
                            }
                            i++;
                        }
                        break;
                    case 1:
                        if ((!workOnWriting.containsKey(stuNum))
                                && workOnWriting.size() < maxInGroup) {
                            workOnWriting.put(stuNum, n);
                            if (rotationNumber == 1) {
                                names.set(stuNum, null);
                            } else {
                                readToSelf.put(stuNum, null);
                            }
                            i++;
                        }
                        break;
                    case 2:
                        if ((!wordWork.containsKey(stuNum))
                                && wordWork.size() < maxInGroup) {
                            wordWork.put(stuNum, n);
                            if (rotationNumber == 1) {
                                names.set(stuNum, null);
                            } else {
                                readToSelf.put(stuNum, null);
                            }
                            i++;
                        }
                        break;
                    case 3:
                        if ((!listenToReading.containsKey(stuNum))
                                && listenToReading.size() < maxInGroup) {
                            listenToReading.put(stuNum, n);
                            if (rotationNumber == 1) {
                                names.set(stuNum, null);
                            } else {
                                readToSelf.put(stuNum, null);
                            }
                            i++;
                        }
                        break;
                }

                activityNum = rand.nextInt(4);
                if (rotationNumber == 1) {
                    stuNum = rand.nextInt(numStus);
                    n = names.get(stuNum);
                } else {
                    if (!itr.hasNext()) {
                        break;
                    }
                    stuNum = (int) itr.next();
                    n = readToSelf.get(stuNum);
                }

            } else {
                if (rotationNumber == 1) {
                    stuNum = rand.nextInt(numStus);
                    n = names.get(stuNum);
                } else {
                    if (!itr.hasNext()) {
                        break;
                    }
                    stuNum = (int) itr.next();
                    n = readToSelf.get(stuNum);
                }

            }
        }

        ArrayList<HashMap> remainingGroups = new ArrayList<>();
        remainingGroups.add(readToSomeone);
        remainingGroups.add(workOnWriting);
        remainingGroups.add(wordWork);
        remainingGroups.add(listenToReading);

        return remainingGroups;
    }

}
