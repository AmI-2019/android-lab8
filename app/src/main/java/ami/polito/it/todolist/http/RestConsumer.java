package ami.polito.it.todolist.http;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ami.polito.it.todolist.entities.Task;
public class RestConsumer {

    public static String baseUrl = "http://172.22.89.77:8080/api/v1.0";

    public static List<Task> getTasks(){
        RestTemplate restTemplate = new RestTemplate();
        Task[] tasks = restTemplate.getForObject(baseUrl + "/tasks", Task[].class);
        return Arrays.asList(tasks);
    }

    public static void deleteTask(int taskId){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(baseUrl + "/tasks/" + taskId);
    }

    public static void updateTask(Task task){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(baseUrl + "/tasks/" + task.getId(), task);
    }

    public static void addTask(Task task){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(baseUrl + "/tasks", task, Task.class);
    }

}
