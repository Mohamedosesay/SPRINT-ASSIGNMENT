import java.util.Random;

import static java.lang.Math.round;

public class Main {
    static int stuAll;//Total number of students
    static int teacherAll;//Total number of teachers
    static  int rankingExpect;//Position preference
    static int expectDeviation;//Expectation deviation
    static int stuMax;//Maximum number of students
    static float vf;//
Volunteer weight factor
    static  int range;//Volunteer Scope
    static int stuSelected[];

    public static void main(String[] args)
    {
        init();

    }
    //初始化
    static void init(){
        initDate();
        Teacher teacherArrary[]=initTeacher();
        Student studentArrary[]=initStudent();
        Dto dto=new Dto(studentArrary,teacherArrary);
        System.out.println("Initialize students and teachers:");
        Common.showStudents(studentArrary);
        Common.showTeachers(teacherArrary);
        Common.firstAssignment(dto,4);
        System.out.println("Results of the first round of mutual elections:");
        Common.showStudents(studentArrary);
        Common.showTeachers(teacherArrary);
        Common.secondAssignment(dto,rankingExpect,range,teacherAll,stuAll);
        System.out.println("Results of the second round of mutual elections:");
        Common.showTeachers(teacherArrary);
        Common.showStudents(studentArrary);
        Common.matchRateAndVarianceMean(dto,rankingExpect);


    }
    //Initialize variable parameters
    static void initDate(){
        stuAll=80;
        teacherAll=8;
        rankingExpect=40;
        expectDeviation=10;
        vf=0.2f;
        range=round(stuAll*vf);
        stuMax=stuAll/teacherAll;
    }
    //Initialize the teacher array
    static Teacher[] initTeacher(){
        int stuNumber=0;
        stuSelected=new int[stuMax];
        Teacher teacherArrary[]=new Teacher[teacherAll];
        for(int i=0;i<teacherAll;i++){
            teacherArrary[i]=new Teacher(stuMax,stuNumber,i,stuSelected);
        }
        return teacherArrary;
    }
    //Initialize the student array
    static Student[] initStudent(){
        Student studentArrary[]=new Student[stuAll];
        boolean status=false;
        Random random=new Random();

        for(int j=0;j<stuAll;j++)
        {
            int volunNumber=random.nextInt(teacherAll);
            studentArrary[j]=new Student(volunNumber,j,status);
        }
        return studentArrary;
    }
}
