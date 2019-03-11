package com.hanson.myadapters;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This activity will add two more fields to the previous field entered by the user
 * if selected will be updated into the Student Details
 */
public class EditActivity extends AppCompatActivity {

    public static final String TAG = "MY_ADAPTER_EDIT";

    Spinner campusSpinner; //Spinner for choosing campus
    Button backB,updateB; // button to go back and update
    RadioGroup semesterRB; // Radio group to select semester from the given radio button

    String campus;// Fields to temporary save student data
    int id,semester;

    DatabaseHelper myDatabaseObject;
    /**
     * This method will start at the time of activity call
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initComponents();

        final Intent intent = getIntent(); // Get values from the intent sent to this activity
         id = intent.getIntExtra("id",0);
        try {
            semester = myDatabaseObject.getSemester(id);
            campus = myDatabaseObject.getCampus(id);
        }catch (CursorIndexOutOfBoundsException e){
            toastMessage("Error");
            Log.e(TAG,"ID is: "+id+"\nError is: "+e);
        }

        // switch statement to preset the radio button if semester was previously set
        switch(semester){
            case 1:
                semesterRB.check(R.id.radioButton1);
                break;
            case 2:
                semesterRB.check(R.id.radioButton2);
                break;
            case 3:
                semesterRB.check(R.id.radioButton3);
                break;
            case 4:
                semesterRB.check(R.id.radioButton4);
                break;
            default:
                semesterRB.check(0);
        }

        // Adapter array to populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.campusSpinnerEntries, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        campusSpinner.setAdapter(adapter);
        if (campus != null) { // Preset the campus spinner to the value already choosen
            int spinnerPosition = adapter.getPosition(campus);
            campusSpinner.setSelection(spinnerPosition);
        }
    }

    /**
     * Initialize the class components
     */
    public void initComponents(){
        myDatabaseObject = new DatabaseHelper(this);

        //name = "";
        campus = "";
        //gender = true;
        id = 0;
        semester = 0;

        semesterRB = (RadioGroup) findViewById(R.id.semesterRDG);
        campusSpinner = (Spinner) findViewById(R.id.campusSpinner);

        backB = (Button) findViewById(R.id.backButton);
        updateB = (Button) findViewById(R.id.updateButton);

        // This listener will select the user choice and store into the semester variable
        semesterRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("Checked Id is: ",checkedId+" ");
                if(checkedId == R.id.radioButton1){
                    semester = 1;
                }else if(checkedId == R.id.radioButton2){
                    semester = 2;
                } else if(checkedId == R.id.radioButton3){
                    semester = 3;
                }else if(checkedId == R.id.radioButton4){
                    semester = 4;
                }
            }
        });

        // This listener will select the user choice and store into the campus variable
        campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                campus = campusSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                campus = "";
            }
        });

        // Back button listener to sent user to details activity without updating data in student array
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Without updation");
                Intent intent = new Intent(EditActivity.this,DetailsActivity.class);
                //intent.putExtra("name",name);
                intent.putExtra("id",id);
                //intent.putExtra("gender",gender);
                //intent.putExtra("sem",semOnBackPress);
                //intent.putExtra("campus",campusOnBackPress);
                startActivity(intent);
            }
        });

        // Update button will send the user to detail activity by updating the data
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean r = myDatabaseObject.updateDetails(id,campus,semester);
                if(r) {
                    toastMessage("Updated");
                    Intent intent = new Intent(EditActivity.this, DetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }else{
                    Log.e(TAG,"Error updating for id: "+ id);
                }
            }
        });
    }
    public void toastMessage(String str){
        Toast.makeText(EditActivity.this,str,Toast.LENGTH_LONG).show();
    }
}
