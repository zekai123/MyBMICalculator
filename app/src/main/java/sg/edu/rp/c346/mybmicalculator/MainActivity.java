package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
EditText weight , height;
Button cal , rest;
TextView cal_date , cal_bmi , result_bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight=findViewById(R.id.editTextWeight);
        height=findViewById(R.id.editTextHeight);
        cal=findViewById(R.id.buttonCal);
        rest=findViewById(R.id.buttonReset);
        cal_date=findViewById(R.id.textViewCalDate);
        cal_bmi=findViewById(R.id.textViewCalBMI);
        result_bmi=findViewById(R.id.textViewRange);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                cal_date.setText("Last Calculated Date:" + datetime);
                String heightStr = height.getText().toString();
                String weightStr = weight.getText().toString();
               float weight = Float.parseFloat(weightStr);
               float height = Float.parseFloat(heightStr)/100;
               float bmi = weight/(height*height);
               cal_bmi.setText("Calculated BMI:"+bmi);
               if(bmi<18.5){
                   result_bmi.setText("You are underweight");
               }
               else if (bmi>=18.5 && bmi<=24.9){
                   result_bmi.setText("Your BMI is normal");
               }
               else if (bmi>=25 && bmi<=29.9){
                   result_bmi.setText("You are overweight");
               }
               else{
                   result_bmi.setText("You are obese");
               }




            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight.setText("");
                height.setText("");
                cal_date.setText(R.string.cal_date);
                cal_bmi.setText(R.string.cal_bmi);
                result_bmi.setText(R.string.result_bmi);


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        String date =cal_date.getText().toString();
        String bmiP = cal_bmi.getText().toString();
        String final_result = result_bmi.getText().toString();
        SharedPreferences prefN = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefN.edit();
        prefEdit.putString("date", date);
        prefEdit.putString("bmi", bmiP);
        prefEdit.putString("result", final_result );
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefN = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String msg = prefN.getString("date", String.valueOf(R.string.cal_date));
        String msg2= prefN.getString("bmi", String.valueOf(R.string.cal_bmi));
        String msg3 = prefN.getString("result",String.valueOf(R.string.result_bmi));
        cal_bmi.setText(msg2);
        cal_date.setText(msg);
        result_bmi.setText(msg3);
    }
}
