import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Carl-Johan on 2016-02-24.
 */
public class Schedule {

    private int end = 5;
    ArrayList<Integer> deadlines = new ArrayList<Integer>();
    ArrayList<Integer> length = new ArrayList<Integer>();
    ArrayList<Task> tasks = new ArrayList<Task>();

    private void createParameters() {
        for(int it=0;it<end;it++){
            this.deadlines.add((int) Math.floor(Math.random() * 6));
            this.length.add((int) Math.ceil(Math.random() * 12));
        }
    }

    private void makeTask(){
        for(int it=0;it<deadlines.size();it++) {
            Task a = new Task(this.deadlines.get(it),this.length.get(it));
            tasks.add(a);
        }
    }

    private void sortTasks(){
        Collections.sort(tasks);
    }

    private void planSchedule(int days, int amountOfTimePerDay) {


        int amountOfSubjects = this.tasks.size();
        int taskIterator = 0;
        int time = 0;
        Task task;
        Task lastTask = null;

        labelday:
        for (int day = 0; day < days; day++) {
            task = tasks.get(taskIterator);
            while (task.getLength() <= amountOfTimePerDay - time) {
                if (lastTask != task)
                    task.setStart(day, time);
                time += task.getLength();
                task.setFinish(day, time);
                taskIterator++;

                if (taskIterator>=amountOfSubjects)
                    break labelday;

                lastTask = task;
                task = tasks.get(taskIterator);

            }
            task.setStart(day, time);
            task.setLength(amountOfTimePerDay - time);
            time = 0;
            lastTask = task;

        }


    }




    public static void main(String[] args) {
        Schedule m = new Schedule();

        m.createParameters();
        m.makeTask();
        m.sortTasks();
        m.planSchedule(6,7);
        for(Task a:m.tasks){

            System.out.println("Start: " + a.getStart());
            System.out.println("Finish: " + a.getFinish());
            System.out.println("Deadline: \nDay: "+a.getDeadline());
        }



    }

}

class Task implements Comparable<Task>{

    int deadline;
    int length;
    Tuple start;
    Tuple finish;

    public Task(int dl, int t) {
        deadline = dl; //en dag
        length = t;
        start = new Tuple(0,0);
        finish = new Tuple(0,0);
    }

    public int getDeadline(){
        return this.deadline;
    }
    public int getLength(){
        return this.length;
    }

    public void setLength(int i){
        this.length -=i;

    }

    public void setStart(int day, int time){
        start.day = day;
        start.time = time;
    }

    public void setFinish(int day, int time){
        finish.day = day;
        finish.time = time;
    }

    public String getFinish(){
        String str2 = ((String)"\nDay: "+this.finish.day +" Time: "+ this.finish.time);
        return str2;
    }

    public String getStart(){
        String str1 = ((String)"\n Day: "+this.start.day +" Time: "+ this.start.time);
        return str1;
    }

    @Override
    public int compareTo(Task compareT) {
        int compareDeadline = ((Task) compareT).getDeadline();
        return this.deadline - compareDeadline;

    }
}

class Tuple{

    int day;
    int time;

        public Tuple(int y, int x){

            day   = y;
            time = x;
        }
        }
