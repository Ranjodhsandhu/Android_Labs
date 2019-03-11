package hanson.listenintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends AppCompatActivity {

    String input; // input string to receive user input from previous activity
    String name; // name string to receive extra value added to the intent in previous activity
    boolean odd; // odd boolean to get boolean value added in intent
    int id; // id integer to receive the id from last activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);

        final Intent intent = getIntent(); // this will receive the whole intent created in the main activity
        input = intent.getStringExtra("message"); // getting string value from intent
        name = intent.getStringExtra("name"); // getting string name from intent
        odd = intent.getBooleanExtra("odd",true); // getting boolean value from the intent
        id = intent.getIntExtra("id",0); // getting integer value from the intent

        final TextView displayView = (TextView) findViewById(R.id.DisplayView); // initializing the text view variable
        Button show = (Button)findViewById(R.id.Show); // initiating the show button
        Button action = (Button)findViewById(R.id.Action); // initiating the action button

        displayView.setText(input); // setting the text view value received from main activity user input

        /*
            this will set the click listener on the show button to display the HID
         */
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView.setText("Your Hid is: H00018073");
                }
        });

        /*
            this will set the click listener on the action button to display the
         */
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Your Name is: "+name+"\nYour H ID is:  H000"+id+"\nYour ID is ODD: "+odd);

            }
        });

    }

    /*
       This method will display the toast containing the message from the edit text user input
    */
    private void toastMessage(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);

        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(18);// changing the size of text in toast display
        toast.show();
    }
}
