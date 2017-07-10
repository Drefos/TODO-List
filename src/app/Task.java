package app;

public class Task {
    private String title;
    private String date;
    private String note;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public Task(String title, String date, String note) {
        this.title = title;
        this.date = date;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
