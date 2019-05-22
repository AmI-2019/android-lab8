package ami.polito.it.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ami.polito.it.todolist.entities.Task;
import ami.polito.it.todolist.http.RestConsumer;

public class MainActivity extends AppCompatActivity {

    ListView tasksListView;
    FloatingActionButton addButton;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get a references to UI widgets
        tasksListView = findViewById(R.id.listview_tasks);
        addButton = findViewById(R.id.button_add);
        pb = findViewById(R.id.progress_bar);

        pb.setVisibility(View.VISIBLE);
        //fill the list view with the task list
        new AsyncTask<Void, Void, List<Task>>() {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                try {
                    return RestConsumer.getTasks();
                }
                catch (Throwable t){
                    t.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Task> tasks){
                if(tasks != null) {
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, tasks);
                    tasksListView.setAdapter(adapter);
                    pb.setVisibility(View.GONE);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "OOOOPS, something goes wrong!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }.execute();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open a new activity for inserting a new task
                Intent openAddActivity = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(openAddActivity);
            }
        });

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                //open a new activity for visualizing the task details
                Intent detailActivityIntent = new Intent(getApplicationContext(), DetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("task", task);
                detailActivityIntent.putExtras(b);
                startActivity(detailActivityIntent);
            }
        });

    }
}
