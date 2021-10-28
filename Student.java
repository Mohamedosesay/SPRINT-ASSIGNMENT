public class Student {
    int volunNumber;//Volunteer Numbering Group
    int ranking;//grades ranking
    boolean status;//The flag determines whether the tutor has been selected, 1 is selected, 0 is not selected;
    String teacherNumber="without";

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    Student(){}
    Student(int volunNumber,int ranking,boolean status)
    {
        this.ranking=ranking;
        this.status=status;
        this.volunNumber=volunNumber;
    }

    public int getVolunNumber() {
        return volunNumber;
    }

    public void setVolunNumber(int volunNumber) {
        this.volunNumber = volunNumber;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean statue) {
        this.status = statue;
    }

    @Override
    public String toString() {
        String sta;
        if(status){
            sta="Has been selected";
        }else {
            sta="Not selected";
        }
        return "Student [Rank=" + ranking + Volunteer Number =" + volunNumber Selected teacher =" + teacherNumber+ ",Current state="+sta+"]";
    }
    //Update status after student is selected
    void updateStudent(int teacherNumber){
        this.teacherNumber=String.valueOf(teacherNumber);
        this.status=true;
    }
}
