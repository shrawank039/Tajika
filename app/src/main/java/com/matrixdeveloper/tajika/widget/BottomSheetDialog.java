package com.matrixdeveloper.tajika.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.utils.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    EditText firstName,flat,fullAdd,city,zip,state,country;
    String Phone,Flat,FullAdd,Zip,State,City,Country,address,message;
    double lat,lng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_address, container, false);

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

       //  setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);

        firstName=v.findViewById(R.id.txtFirstName);
        flat=v.findViewById(R.id.txt_flat_no);
        fullAdd=v.findViewById(R.id.txtAddress1);
        city=v.findViewById(R.id.txtCity);
        zip=v.findViewById(R.id.txtzip);
        state=v.findViewById(R.id.txtstate);
        country=v.findViewById(R.id.txtcountry);
        v.findViewById(R.id.edt).setFocusable(false);

        firstName.setText("AppConstants.phone");
        fullAdd.setText("AppConstants.address");
        city.setText("AppConstants.city");
        zip.setText("AppConstants.zip");
        state.setText("AppConstants.state");
        country.setText("AppConstants.country");

        Button button1 = v.findViewById(R.id.btn_add_address);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 clicked");
                Phone=firstName.getText().toString().trim();
                Flat=flat.getText().toString();
                FullAdd=fullAdd.getText().toString();
                City=city.getText().toString();
                Zip=zip.getText().toString();
                State=state.getText().toString();
                Country=country.getText().toString();

                address= "https://www.google.com/maps/search/?api=1&query=Google&query_place_id="+"AppConstants.locationId";

                message = "PHONE -" +Phone + " \nADDRESS -" + FullAdd + " \nSERVICES -" + "AppConstants.serviceInstant"
                        + " \nMAP_URL -" + address;

                if (TextUtils.isEmpty(Phone) || Phone.length() < 10) {
                    firstName.setError("Valid number is required");
                    firstName.requestFocus();
                }
                else if (TextUtils.isEmpty(Flat)) {
                    flat.setError("Please enter House/Flat No");
                    flat.requestFocus();
                }
                else if (TextUtils.isEmpty(FullAdd)) {
                    fullAdd.setError("Address cannot be empty");
                    fullAdd.requestFocus();
                }
                else if (TextUtils.isEmpty(City)) {
                    city.setError("City Name cannot be empty");
                    city.requestFocus();
                }
                else if (TextUtils.isEmpty(Country)) {
                    country.setError("Country cannot be empty");
                    country.requestFocus();
                }
                else {
                    Toast.makeText(getContext(), "AppConstants.locationId", Toast.LENGTH_SHORT).show();
                 //   sendEmail();
//                    Intent intent = new Intent(getContext(), ThankYou.class);
//                  //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                }
            }});
        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View touchOutsideView = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .getDecorView()
                .findViewById(com.google.android.material.R.id.touch_outside);
        touchOutsideView.setOnClickListener(null);
    }
//    private void sendEmail() {
//        SendMail sm = new SendMail(getContext(), "sales.waschen@gmail.com", "Instant Pickup", message);
//        sm.execute();
//    }
}
