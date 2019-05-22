package ami.polito.it.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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

public class DetailActivity extends AppCompatActivity {

    EditText descriptionText;
    CheckBox urgentCheckBox;
    FloatingActionButton updateButton;
    Button deleteButton;
    Button saveButton;
    Task task;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get the references to the UI widgets
        descriptionText = findViewById(R.id.text_description);
        urgentCheckBox = findViewById(R.id.checkbox_urgent);
        updateButton = findViewById(R.id.button_update);
        deleteButton = findViewById(R.id.button_delete);
        saveButton = findViewById(R.id.button_save);
        pb = findViewById(R.id.progress_bar);

        //get the selected task
        Bundle b = getIntent().getExtras();
        task = (Task) b.getSerializable("task");

        //fill the UI widgets with the task details
        descriptionText.setText(task.getDescription());
        if(task.getUrgent() == 1)
            urgentCheckBox.setChecked(true);
        else
            urgentCheckBox.setChecked(false);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionText.setEnabled(true);
                urgentCheckBox.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the REST API to delete a task
                pb.setVisibility(View.VISIBLE);
                new AsyncTask<Void, Void, Boolean>(){

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try{
                            RestConsumer.deleteTask(task.getId());
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                String newDescription = descriptionText.getText().toString();
                int newUrgent;
                Boolean checked = urgentCheckBox.isChecked();
                if(checked)
                    newUrgent = 1;
                else
                    newUrgent = 0;

                task.setDescription(newDescription);
                task.setUrgent(newUrgent);

                //call the REST API to update a task
                new AsyncTask<Void,Void, Boolean>(){

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try{
                            RestConsumer.updateTask(task);
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
