package ami.polito.it.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ami.polito.it.todolist.entities.Task;
import ami.polito.it.todolist.http.RestConsumer;

public class AddActivity extends AppCompatActivity {
    EditText descriptionText;
    CheckBox urgentCheckbox;
    Button addButton;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //get references to the UI widgets
        descriptionText = findViewById(R.id.text_description);
        urgentCheckbox = findViewById(R.id.checkbox_urgent);
        addButton = findViewById(R.id.button_add);
        pb = findViewById(R.id.progress_bar);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a new task to be added
                final Task task = new Task();
                task.setDescription(descriptionText.getText().toString());
                if(urgentCheckbox.isChecked())
                    task.setUrgent(1);
                else
                    task.setUrgent(0);

                pb.setVisibility(View.VISIBLE);
                //call the REST API to add a task
                new AsyncTask<Void,Void,Boolean>(){

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try{
                            RestConsumer.addTask(task);
                        }
                        catch (Throwable t){
                            return false;
                        }
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean ret) {
                        pb.setVisibility(View.GONE);
                        if(ret){
                            Intent openMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(openMainIntent);
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "OOOOPS, something goes wrong!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }.execute();

            }
        });
    }
}
