package hanson.listenintent;

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

public class MainActivity extends AppCompatActivity {

    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView view = (TextView)findViewById(R.id.textView); // text view in main activity to display the user input value
        final EditText input = (EditText) findViewById(R.id.editText); // edit text in main activity to receive user input
        Button send = (Button) findViewById(R.id.sendB); // send button to call next activity using intent
        Button display = (Button) findViewById(R.id.displayB); // display button to display the value from edit text to text view

        /*
            this will set the click listener on the send button to make it work on clicking
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = input.getText().toString();
                sendMessage(text);
            }
        });

        /*
            this code snippet will set the click listener on the display button to display the value in text view
         */
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(input.getText().toString());
                //toastMessage(input.getText().toString());
            }
        });
    }
    /*
        This method is to create a new intent and to start the display activity
     */
    public void sendMessage(String str){
        Intent intent = new Intent(MainActivity.this, Display.class);
        int x = 18073;
        intent.putExtra("message", str);        // put extra is used to send the extra values with the intent call
        intent.putExtra("id",x); //
        intent.putExtra("name","Ranjodh Singh");
        intent.putExtra("odd",true);

        startActivity(intent); // stating the activity
    }

    /*
        This method will display the toast containing the message from the edit text user input
     */
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }

}
