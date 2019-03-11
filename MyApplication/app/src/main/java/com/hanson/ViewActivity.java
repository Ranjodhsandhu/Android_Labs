package com.hanson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
    * ViewActivity is second activity, this is started by the main activity
    * user data is sent through intents
 */
public class ViewActivity extends AppCompatActivity {

    // variable declaration
    String name; // name to store user entered name
    TextView Display; // Textview to display the user entered details
    EditText Input; // Input to be accepted from user
    Button add, show, next; // Button for user actions
    int count, max, currentPosition; // variable to help add and get info from student array
    Boolean maxReached = false; // flag to help tracking and fetching info from current index
    Student []studentArray; // student arrray to store student name entered by user

    // listener initialized outside for convenience as per assignment requirement
    private View.OnClickListener listenerAddStudent = new View.OnClickListener(){

                /*
                    * This listener will add a new student to the array when add button is pressed on activity
                 */
                @Override
                public void onClick(View v){
                String tmpName = Input.getText().toString();
                if(!tmpName.equals("")) {
                    if(count == max-1){
                        maxReached = true;
                    }
                    if(count == -1 || count == max-1){
                        count = 0;
                        studentArray[count] = new Student(tmpName, count+1);
                    }
                    else if(count < max-1){
                        count ++;
                        studentArray[count] = new Student(tmpName, count+1);
                    }
                    Toast.makeText(ViewActivity.this,"Added new student to index: "+(count),Toast.LENGTH_LONG).show();
                 }
                else {
                    Toast.makeText(ViewActivity.this,"Nothing to add to index: "+count,Toast.LENGTH_LONG).show();
                }
        }
     };

    private View.OnClickListener listenerShowStudent = new View.OnClickListener(){

                /*
                * This listener method will get the student data from the student data at the current position
                * if this location is empty it will show nothing
                * */
                @Override
                public void onClick(View v){

                String msgToDisplay;
                toastMessage("Fetching student data from index: "+currentPosition);
                if(!maxReached) {
                    if (currentPosition >= 0 && currentPosition < count + 1) {
                            msgToDisplay = "Student id is: " + (studentArray[currentPosition].id) + "\n" +
                                "Your name is: " + studentArray[currentPosition].name + "\n" +
                                "Enrolled: " + studentArray[currentPosition].enrolled;
                            Display.setText(msgToDisplay);
                    } else {
                            Display.setText("Nothing to show");
                            toastMessage("Nothing To show");
                }
                }else if(maxReached){
                            msgToDisplay = "Student id is: " + studentArray[currentPosition].id + "\n" +
                                    "Your name is: " + studentArray[currentPosition].name + "\n" +
                                    "Enrolled: " + studentArray[currentPosition].enrolled;
                            Display.setText(msgToDisplay);
                }
                }
    };
    private View.OnClickListener listenerNextIndex = new View.OnClickListener(){

                /*
                *  This listener will implement the working of the next button to increment the index of the
                *  student array, also helps to keep track of which item to show from the student array
                * */
                @Override
                public void onClick(View v){
                    if(currentPosition == -1 || currentPosition == max-1){
                        currentPosition = 0;
                    }else if(currentPosition < max-1){
                        currentPosition++;
                    }
                toastMessage("Current Index Position is: "+currentPosition);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initComponents();



        final Intent intent = getIntent(); // receiving the intent from the previous activity
        name = intent.getStringExtra("message"); // receiving the values from user entered in the main activity

        if(!name.equals("")){
            count = 0;
            studentArray[count] = new Student(name, count+1);
            toastMessage("Added new Student: " + name);
            Display.setText("Name to show is: "+name);
        }else if (name.equals("")){
            toastMessage("Nothing from previous activity");
            Display.setText("Nothing to show from previous activity");
        }


        add.setOnClickListener(listenerAddStudent);
        show.setOnClickListener(listenerShowStudent);
        next.setOnClickListener(listenerNextIndex);

    }
    /*
    *   Initializing the components and variable of the activity
    * */
    public void initComponents(){
        count = -1;
        currentPosition = count;
        max = 10; //maximum length of the array
        name = "";
        studentArray = new Student[max];

        Display = (TextView) findViewById(R.id.studentInfo);
        Input = (EditText) findViewById(R.id.enterNameView);
        add = (Button) findViewById(R.id.addNameView);
        show = (Button) findViewById(R.id.showB);
        next = (Button) findViewById(R.id.nextB);
    }

    /*
    * This method will show the toast message
    * */
    public void toastMessage(String str){
        Toast toast = Toast.makeText(this,str,Toast.LENGTH_LONG);
        toast.show();
    }


}
