package com.hanson;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/*
    * MainActivity class which will be launched when application starts
 */
public class MainActivity extends AppCompatActivity {

    // Variable declaration
    TextView instructions; // for giving instruction and displaying name
    EditText input; // for accepting user input
    Button addB, displayB, sendB; // buttons for user actions
    String name; // name variable to store user data

    // Listeners are defined ouside as per the assignment requirements
    private View.OnClickListener listenerAddB = new View.OnClickListener(){
        @Override
        public void  onClick(View v){

            name = input.getText().toString(); // accepting input from user and storing in name field to send through intent
            if(!name.equals(""))
                toastMessage("Your Name is "+name);
            else if(name.equals(""))
                toastMessage("Nothing entered");
        }
    };
    private View.OnClickListener listenerDisplayB = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            displayMessage();
        }
    };

    private View.OnClickListener listenerSendB = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            sendMessage();
        }
    };

    // Default method which will execute when activity starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        addB.setOnClickListener(listenerAddB);
        displayB.setOnClickListener(listenerDisplayB);
        sendB.setOnClickListener(listenerSendB);
    }

    /*
        * Initializing the components to be used in this activity
     */
    public void initComponent(){
        instructions = (TextView) findViewById(R.id.instructions);
        input = (EditText) findViewById(R.id.input);
        addB = (Button) findViewById(R.id.addB);
        displayB = (Button) findViewById(R.id.displayB);
        sendB = (Button) findViewById(R.id.sendB);
    }

    /*
        * Method to display user input in the textview
     */
    public void displayMessage(){

        String s = input.getText().toString();
        if(!s.equals(""))
            instructions.setText(s);
        else if(s.equals(""))
            instructions.setText("Please enter student name below");
    }
    /*
        * Method to send user data through intent to other activity
     */
    public void sendMessage(){
        Intent intent  = new Intent(MainActivity.this,ViewActivity.class);
        name = input.getText().toString();
        intent.putExtra("message",name);
        startActivity(intent);
    }
    /*
        * Method will display the toast message when user will add the input entered to the name variable
     */
    public void toastMessage(String str){
        Toast toast = Toast.makeText(this,str,Toast.LENGTH_LONG);
        toast.show();
    }
}
