package com.example.billviewer;

import java.util.ArrayList;
import java.util.Random;

public class Course {

    private static int courseID = 1;
    private String courseTitle;
    private int average;
    private ArrayList<Assignment> assignments;

    private Course(String title, ArrayList<Assignment> assign, int avg){
        courseTitle = title;
        assignments = assign;
        average = avg;
        courseID++;
    }

    static public Course generateRandomCourse(){
        Random rand = new Random();
        int assignmentNo = rand.nextInt(5);
        ArrayList<Assignment> tempAssign = new ArrayList<Assignment>();
        int tempAvg = 0;
        double avg = 0;
        for(int i = 0; i< assignmentNo; i++){
            tempAssign.add(Assignment.generateRandomAssignment());
        }
        //compute the average of assignments
        for(int i = 0; i< tempAssign.size(); i++){
            tempAvg += tempAssign.get(i).getAssignmentGrade();
        }
        if (assignmentNo != 0){
            avg = (tempAvg)/assignmentNo;
        }

        return new Course("Course " + courseID, tempAssign, (int)Math.round(avg));
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getAverage() {
        return average;
    }

    //Reset the course ID when activities ends
    static public void resetCourseID(){
        courseID = 1;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
}
