package com.hanson.myadapters;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
* Details activity will take input from ListActivity or MainActivity to display student details
 */
public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = "MY_ADAPTER_DETAIL";
    TextView textView; // TextView Will display user name and id
    ImageView imageView; // imageView to display default image to user according to gender
    Button detailViewListB,detailAddMoreB,detailEditB; // buttons for user actions to go to ListActivity or MainActivity
    String name; // to store student name
    int id; // to store student id
    int semester;
    String campus;
    boolean gender; // gender of student

    DatabaseHelper myDatabaseObject;
    /*
    * take user to MainActivity to add new student when add new button is clicked
     */
    private View.OnClickListener addBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };
    /*
    * take user to the list activity when list button is clicked and also add item to the array
     */
    private View.OnClickListener listBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DetailsActivity.this,ListActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener editBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DetailsActivity.this,EditActivity.class);
            //intent.putExtra("name",name);
            intent.putExtra("id",id);
            //intent.putExtra("gender",gender);
            //intent.putExtra("sem",semester);
            //intent.putExtra("campus",campus);
            startActivity(intent);
        }
    };

    /*
    * Starting point for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initComponents();

        final Intent intent = getIntent(); // will accept input from the parent activity
        id = intent.getIntExtra("id",0);
        try {
            name = myDatabaseObject.getName(id);
            semester = myDatabaseObject.getSemester(id);
            campus = myDatabaseObject.getCampus(id);
            gender = myDatabaseObject.getGender(id);

        }catch(CursorIndexOutOfBoundsException e){
            toastMessage("Error");
            Log.e(TAG,"ID is :"+id+"\nError: "+e.toString());
        }

        if(gender)
            imageView.setImageResource(R.drawable.male); // image will be male if gender is true
        else
            imageView.setImageResource(R.drawable.female); // image will be female if gender is false

        textView.setText(" Student name is: "+name+"\n Student Id is: "+id+"" +
                "\n Semester: "+semester+"\n Campus: "+campus);
    }
    /*
    * initialize the components of the activity
     */
    public void initComponents(){

        myDatabaseObject = new DatabaseHelper(this);

        textView = (TextView) findViewById(R.id.detailTextView);
        imageView = (ImageView) findViewById(R.id.imageView);


        detailAddMoreB = (Button) findViewById(R.id.detailAddMoreB);
        detailViewListB = (Button) findViewById(R.id.detailViewListB);
        detailEditB = (Button) findViewById(R.id.detailEditB);


        detailViewListB.setOnClickListener(listBListener);
        detailAddMoreB.setOnClickListener(addBListener);
        detailEditB.setOnClickListener(editBListener);

        name = "";
        id = 0;
        gender = true;
        semester = 0;
        campus = "";
    }
    public void toastMessage(String str){
        Toast toast = Toast.makeText(this,str,Toast.LENGTH_LONG);
        toast.show();
    }
}
