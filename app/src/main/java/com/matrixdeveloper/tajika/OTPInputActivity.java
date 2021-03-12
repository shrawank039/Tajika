package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.helpers.GenericTextWatcher;

public class OTPInputActivity extends AppCompatActivity {

    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input);

        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        otp_textbox_five = findViewById(R.id.otp_edit_box5);
        otp_textbox_six = findViewById(R.id.otp_edit_box6);

        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six};

        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));
        otp_textbox_five.addTextChangedListener(new GenericTextWatcher(otp_textbox_five, edit));
        otp_textbox_six.addTextChangedListener(new GenericTextWatcher(otp_textbox_six, edit));

    }

    public void onLoginCLick(View view) {
        startActivity(new Intent(
                this, HomeActivity.class
        ));
    }
}