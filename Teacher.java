public class Teacher {
    int stuNumber;//Current number of students
    int stuMax;//Maximum number of students
    int meanStu;//Current average position
    int teacherNumber;//Teacher ID
    int stuSelected[];//Selected students
    String selected="without"
    void Teacher()
    {

    }
    Teacher(int stuMax,int stuNumber,int meanStu,int teacherNumber)
    {
        this.stuMax=stuMax;
        this.stuNumber=stuNumber;
        this.meanStu=meanStu;
        this.teacherNumber=teacherNumber;
    }
    Teacher(int stuMax,int stuNumber,int teacherNumber,int[] stuSelected)
    {
        this.stuMax=stuMax;
        this.stuNumber=stuNumber;
        this.teacherNumber=teacherNumber;
        this.stuSelected=stuSelected;
    }


    public int getMeanStu() {
        return meanStu;
    }

    public int getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(int stuNumber) {
        this.stuNumber = stuNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(int teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public void setMeanStu(int meanStu) {
        this.meanStu = meanStu;
    }

    public int getStuMax() {
        return stuMax;
    }

    public void setStuMax(int stuMax) {
        this.stuMax = stuMax;
    }

    @Override
    public String toString() {

        return "Teacher [Teacher ID =" + teacherNumber + ", 
Number of students that can be brought+ stuMax + ",Current number of students ="+stuNumber+",Selected students ="+selected+ ", Average student ranking=" + meanStu +"]";
    }
    //After selecting students, update the current number of students, average ranking and current students
    void updataTeacher(int ranking)
    {
        if (stuNumber==0||meanStu==0)
        {
            stuSelected[stuNumber]=ranking;
            stuNumber++;
            meanStu=ranking;
        }else{
            stuSelected[stuNumber]=ranking;
            meanStu=(stuNumber*meanStu+ranking)/(stuNumber+1);
            stuNumber++;
        }
        if (stuNumber==0){
            selected="æ— ";
        }else {

            if (stuNumber==1) {
                selected = ""+ranking;
            }else {
                selected = selected + "," + ranking;
            }

        }
        for(int i=0;i<stuNumber;i++){
            for(int j=i+1;j<stuNumber;j++)
                if(stuSelected[i]==stuSelected[j]){
                    break;
                }

        }
    }

}
