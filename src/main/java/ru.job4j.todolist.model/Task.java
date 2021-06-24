package ru.job4j.todolist.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String head;
    private String description;
    private Timestamp created;
    private boolean isDone;

    public Task(String head, String description) {
        this.head = head;
        this.description = description;
        this.isDone = false;
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public Task() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return isDone == task.isDone
                && Objects.equals(id, task.id)
                && Objects.equals(head, task.head)
                && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
