package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataReader {

    // list of tasks
    private ObservableList<Task> tasks;

    // file path
    private final String FILE_STORE = System.getProperty("user.dir")+"\\Tasks\\tasks.txt";

    public DataReader() {
        tasks = FXCollections.observableArrayList();
    }
    public ObservableList<Task> getTasks() {return tasks;}

    // add new task to the list
    public void add(Task task) {
        tasks.add(task);
        sortTasks();
    }
    public void add(List<Task> taskList) {
        if(!tasks.isEmpty()) {
            tasks.clear();
        }
        tasks.addAll(taskList);
        sortTasks();
    }

    // delete task
    public void remove(int id) {
        tasks.remove(id);
    }

    // sort tasks by date
    private void sortTasks() {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                String[] numbers1 = o1.getDate().split("\\.");
                String date1 = numbers1[2] + "-" + numbers1[1] + "-" + numbers1[0];
                String[] numbers2 = o2.getDate().split("\\.");
                String date2 = numbers2[2] + "-" + numbers2[1] + "-" + numbers2[0];
                if(date1!=date2) {
                    return date1.compareTo(date2);
                } else {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            }
        });
    }

    // save list of tasks to file
    public void write() {
        // check the file exists if no create new
        File f = new File(FILE_STORE);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // remake List<Taks> to List<String>
        List<String> writeList = new ArrayList<>();
        for (int i = 0; i < tasks.toArray().length; i++) {
            writeList.add(tasks.get(i).getTitle());
            writeList.add(tasks.get(i).getDate());
            writeList.add(tasks.get(i).getNote());
        }
        try (FileOutputStream fos = new FileOutputStream(f);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(writeList);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    // load tasks from file
    public void read() {
        File file = new File(FILE_STORE);
        if (!file.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<String> string = (ArrayList<String>) ois.readObject();
            List<Task> list = new ArrayList<>();
            for (int i = 0; i < string.size()-1; i+=3) {
                list.add(new Task(string.get(i), string.get(i+1), string.get(i+2)));
            }
            add(list);
        } catch (IOException e) {
            e.getStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
