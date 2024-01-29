package pl.coderslab;

import java.time.LocalDate;

public class Task {
    private String title;
    private LocalDate dueDate;
    private boolean isDone;

    public Task(String title, LocalDate dueDate, boolean isDone) {
        this.title = title;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

}
