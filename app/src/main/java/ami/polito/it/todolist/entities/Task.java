package ami.polito.it.todolist.entities;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String description;
    private int urgent;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
