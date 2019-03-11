package com.hanson.myadapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
* The following MainActivity let the user introduce to the application
* This activity will ask the user to provide details such as name and gender
* This activity will take user to details activity and list activity on corresponding button selection
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MY_ADAPTER_MAIN";

    TextView instructions;  // instructions will give instructions to user what kind of input need to be entered
    EditText input;         // input let user enter according to the instructions
    Button list,create;     // list and create action buttons for user to go to another activity
    int trackCreateClicks;  // trackCreateClicks is a counter to see how many times user have clicked on the create button
    String name;            // name to store user entered value name
    boolean gender;         // gender to store wether user is a male or female

    DatabaseHelper myDatabaseObject;
    /*
    * Following listener will take the user to ListActivity when user press on List button
     */
    private View.OnClickListener listListener= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent listIntent = new Intent(MainActivity.this,ListActivity.class);
            startActivity(listIntent);
        }
    };

    /*
    * this listener will take user to DetailsActivity when create button is clicked
     */
    private View.OnClickListener createListener= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(trackCreateClicks == 0) { //if user pressed the create button 1st time
                trackCreateClicks++;
                name = input.getText().toString(); // take value of name from input field
                input.setText("");
                instructions.setText("Please Enter Gender below " + "\n" + "M for male" + "\n" + "F for Female");
            }else if(trackCreateClicks == 1){ // if user pressed create button 2nd time
                trackCreateClicks++;
                String g = input.getText().toString(); // take user gender value that user entered in input field

                if(g.equals("m")||g.equals("M")){
                    gender = true;
                    toastMessage("Gender is Male"); // if user entered m or M
                }else if(g.equals("f")||g.equals("F")){
                    gender = false;
                    toastMessage("Gender is Female"); // if user entered f or F
                }else{
                    toastMessage("Please Enter Correct gender"); // if user entered something else
                    trackCreateClicks--;
                }

            }else if(trackCreateClicks == 2){ // if user pressed the create button 3rd time

                new AlertDialog.Builder(MainActivity.this)  // Alert dialog for the user to take permission from user to
                        .setTitle("Show Details")                   // go to Details activity
                        .setMessage("Are you sure you want to go to Details?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() { //
                            public void onClick(DialogInterface dialog, int which) {
                                String g="";
                                if(gender)
                                    g = "M";
                                else
                                    g= "F";

                                boolean r = myDatabaseObject.addPrimaryDetails(name,g);
                                Log.e(TAG,"Gender is: "+gender);
                                if(r){
                                    Log.d(TAG,"Data added successfully");
                                } else{
                                  Log.e(TAG,"Data was not added");
                                }
                                Intent detailsIntent = new Intent(MainActivity.this,EditActivity.class);

                                int i = -1;
                                try {
                                    Log.d(TAG,"Before Checking id for: "+ name);
                                    i = myDatabaseObject.getStudentID(name);
                                   // Log.d(TAG,"After Checking id for: "+ name);
                                    //gets the id associated with the name
                                    toastMessage("Value of i: "+ i);
                                    if(i > -1){
                                        Log.d(TAG, "onItemClick: The ID is " + i);
                                        detailsIntent.putExtra("id",i);
                                        startActivity(detailsIntent); // if user pressed yes will take data from Main to Details Activity
                                    } else {
                                        toastMessage("No ID associated with that name");
                                    }
                                }catch (CursorIndexOutOfBoundsException e) {
                                    toastMessage("Error");
                                    Log.e(TAG,"ID is: " + i+"\nError: "+e.toString());
                                }
                            }
                        })
                        .setNegativeButton("No",  new DialogInterface.OnClickListener() { //if user pressed No
                            public void onClick(DialogInterface dialog, int which) {            // Main activity will stay active
                                trackCreateClicks = 0;
                                instructions.setText("Please Enter Name in below field");
                                input.setText("");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }
    };


    /*
    * At Start of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        list.setOnClickListener(listListener);
        create.setOnClickListener(createListener);
    }
    /*
    * Will initialize all the components
     */
    public void initComponents(){
        myDatabaseObject = new DatabaseHelper(this);

        trackCreateClicks = 0;
        gender = true;
        name = "";

        instructions = (TextView) findViewById(R.id.instructions);
        input = (EditText) findViewById(R.id.input);
        input.setText("");

        list = (Button) findViewById(R.id.listB);
        create = (Button) findViewById(R.id.createB);
    }

    /*
    * Toast message to display messages to user
     */
    public void toastMessage(String str){
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }
}
