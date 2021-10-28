import java.util.*;

import static java.lang.Math.round;

public class Common {
    void Common() {
    }

    static Random random = new Random();//Use random numbers after instantiating random objects
    static int count = 0;

    void update() {


    }

    /**
     * Print the current status of all students
     * @param students
     */
    static void showStudents(Student[] students) {

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }

    static void showTeachers(Teacher[] teachers) {
        for (int j = 0; j < teachers.length; j++) {
            System.out.println(teachers[j]);
        }
    }

    /**
     * In the first round, the tutor randomly assigns volunteer students
     * @param dto The container of teacher class and student class collection
     * @param assignmengNumber Number of students assigned
     * @return
     */
    static Dto firstAssignment(Dto dto,int assignmengNumber) {
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        int arrary[][] = new int[teachers.length][80];
        List<Integer> integerArrayList = new ArrayList<>();
        int group;
        int mid;

        for (int i = 0; i < teachers.length; i++)//Take turns to choose for each teacher
        {
            //Traverse the collection of students and add the students who have volunteered to fill in the current tutor into the list collection
            for (int j = 0; j < students.length; j++) {
                if (students[j].volunNumber == i) {
                    integerArrayList.add(students[j].ranking);
                }
            }
            //If the number of volunteers selected for this student is less than the number that will be allocated, then the number of volunteers will be allocated
            if (integerArrayList.size() < assignmengNumber) {
                mid = integerArrayList.size();
            } else {
                mid = assignmengNumber;
            }
            //
Use nextInt to randomly select students in the list set to fill in the teacher's selected list
            for (int m = 0; m < mid; m++) {
                int index = random.nextInt(integerArrayList.size());
                int ranking = integerArrayList.get(index);
                teachers[i].updataTeacher(ranking+1);//Use nextInt to randomly select students in the list set to fill in the teacher's selected list
                students[ranking].updateStudent(teachers[i].teacherNumber);//
After the student is selected, update the status of the student
                integerArrayList.remove(index);//List The selected students in the set are removed
            }
            integerArrayList.clear();
        }
        return dto;
    }

    /**
     * Allocate students for the second time, taking the uniformity of the average ranking and the probability of voluntary conformity as the optimization goal
     * @param dto Container for student collection and teacher collection
     * @param rankingExpect rankingExpect The average expected ranking of the class
     * @param Volunteer search range
     * @param teacherAll 
Total number of teachers
     * @param stuAll   Total number of students
     * @return
     */
    static Dto secondAssignment(Dto dto, int rankingExpect, int range, int teacherAll, int stuAll) {
        int op;//
The most suitable ranking based on the average ranking and average expectation of current students
        int opUp, opDown;//Search in the upper and lower ranges of the most suitable rankings, reducing the complexity by half and increasing the volunteer rate
        int stuRest = 0;
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        //Traverse the collection of students and count the number of students who have not yet been selected
        for (int i = 0; i < students.length; i++) {
            if (!students[i].status) {
                stuRest++;
            }
        }
        System.out.println("Remaining number" + stuRest);
        //The outermost loop, to ensure that all students are selected before jumping out of the loop
        while (true) {
            //The second level of loop, let the teacher poll the selection
            for (int i = 0; i < teacherAll; i++) {
                //
Jump out if the number of tutors is full
                if (teachers[i].stuNumber < teachers[i].stuMax) {
                    //The third level of loop, to ensure that the teacher re-searches the best position after selecting middle school students
                    for (int k = count; k == count; ) {
                        int midRange = round(range / 2);
                        {
                            Queue<Integer> queue = new LinkedList<Integer>();
                            op = (teachers[i].stuNumber + 1) * rankingExpect - (teachers[i].stuNumber * teachers[i].meanStu);
                            if (op + range > stuAll || op > stuAll) {
                                op = stuAll - midRange;
                            } else if (op < range || op - range < 0) {
                                op = 0 + midRange;
                            } else {
                            }
                            opUp = op + 1;
                            opDown = op;
                            //
The fourth level of looping ensures that the current round of searching for teachers can select students
                            while (true) {
                                //In the following range, if the student happens to choose the current teacher as a volunteer, it will directly choose to end the cycle
                                if (!students[opDown].status && students[opDown].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opDown);
                                    students[opDown].updateStudent(teachers[i].teacherNumber);
                                    count++;
                                    break;
                                    //In the above range, if the student happens to choose the current teacher as a volunteer, he will directly choose to end the cycle
                                } else if (!students[opUp].status && students[opUp].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opUp);
                                    students[opUp].updateStudent(teachers[i].teacherNumber);
                                    count++;
                                    break;
                                } else {
                                    //If the volunteer of the student in the following range is not the current teacher, but has not been selected yet, then join the preparatory queue
                                    if (!students[opDown].status) {
                                        queue.offer(opDown);
                                    }
                                    //If the volunteers of the students in the above range are not the current teachers, but have not been selected, they will be added to the preparatory queue
                                    if (!students[opUp].status) {
                                        queue.offer(opUp);
                                    }
                                    //
Every time a range cycle has elapsed, pop the queue
                                    if (istrue(op, opDown, opUp, queue, midRange)) {
                                        //
Pop up students who are not in line with their volunteers but who are closest to the best position
                                        int rank = queue.poll();
                                        teachers[i].updataTeacher(rank);
                                        students[rank].updateStudent(teachers[i].teacherNumber);
                                        count++;
                                        queue.clear();
                                        break;
                                    }
                                    //Ensure that the array subscript does not cross the boundary and can be searched
                                    if (opUp < stuAll-1) {
                                        opUp++;
                                    }
                                    //Ensure that the array subscript does not cross the boundary and can be searched
                                    if (opDown > 0)
                                        opDown--;
                                }
                            }
                        }

                    }
                }
            }
            if (count == stuRest)//use countit Calculate the number of selected students and compare them with the remaining students to ensure that each student is selected
                break;
        }
        return dto;
    }
    //Judge that when a range period has elapsed, if the queue is not empty, the student at the end of the queue will be ejected
    static boolean istrue(int op, int opDown, int opUp, Queue queue, int midrange) {
        if (op == opDown || op == opUp) {
            return false;
        } else if (queue.size() == 0) {
            return false;
        } else if (((op - opDown) % midrange) == 0) {
            return true;
        } else if (((opUp - op) % midrange) == 0) {
            return true;
        } else {
            return false;
        }
    }
    static void matchRateAndVarianceMean (Dto dto,int rankingExpect)
    {
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        int stusAll=students.length;
        int teachersAll=teachers.length;
        double matchNumber=0;
        double variance=0;
        for (int i=0;i<stusAll;i++)
        {
            int teacherNumber= Integer.valueOf(students[i].teacherNumber);
            if (teacherNumber==students[i].volunNumber)
            {
                matchNumber++;
            }
        }
        for (int j=0;j<teachersAll;j++)
        {
            double meanStu=teachers[j].meanStu;
            if (teachers[j].meanStu>=rankingExpect)
                variance=variance+(meanStu-rankingExpect);
            else {
                variance=variance+(rankingExpect-meanStu);
            }
        }
        double matchRate=round(matchNumber/stusAll*100);
        double varianceMean=variance/teachersAll;
        System.out.println("Average variance of student rankings:"+varianceMean);
        System.out.println("
Student Volunteer Compliance Rate:"+matchRate+"%");
    }

}
