package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    //Added two variable name category and status ...updated constructer and created getters and setters of both new variable
    private String description;
    private String dueDate;
    private String category;
    private String status;

    public ToDoItem(String description, String dueDate, String category, String status) {
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
