package com.matrixdeveloper.tajika.SPbusiness;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiHomeActivity;
import com.matrixdeveloper.tajika.location.LiveGpsTracker;
import com.matrixdeveloper.tajika.model.Register;
import com.matrixdeveloper.tajika.model.SubCategory;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.AppConstants;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpbRegisterActivity extends AppCompatActivity {

    private ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, submit;
    private static final String TAG = "SpbRegisterAct";
    private String name, phone, email, pass, Cpass, service_area, business_categories, service_description, year_of_experience,
            bussiness_link, minimum_charge, bussiness_logo, service_photo, latitude, longitude;
    private EditText edtName, edtPhone, edtEmail, edtPass, edtCPass, edtLogo, edtServicePhoto, edtServiceArea, edt_description,
            edtYearExperience, edtBusinessLink, edtMinCharges;
    private ImageView showPass, showCPass;
    private LinearLayout ll_logo_upload, ll_service_photo_upload;
    private int type = AppConstants.DOCUMENT_TYPE_ID;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private int MY_CAMERA_REQUEST_CODE = 100;
    private Bitmap bitmap;
    private File destination;
    private InputStream inputStreamImg;
    private String imgPath = null;

    List<String> spinnerArray = new ArrayList<>();
    String[] simpleArray = {"Select Business Category"};
    Spinner spinner;
    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_register);

        prf = new PrefManager(this);

        regViewFlipper = findViewById(R.id.vf_regBViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);
        edtName = regViewFlipper.findViewById(R.id.edt_name);
        edtPhone = regViewFlipper.findViewById(R.id.edt_phone);
        edtEmail = regViewFlipper.findViewById(R.id.edt_email);
        edtPass = regViewFlipper.findViewById(R.id.edt_pass);
        edtCPass = regViewFlipper.findViewById(R.id.edt_cpass);
        edtLogo = regViewFlipper.findViewById(R.id.edt_logo);
        edtServicePhoto = regViewFlipper.findViewById(R.id.edt_service_photo);
        edtServiceArea = regViewFlipper.findViewById(R.id.edt_areaOfService);
        edt_description = regViewFlipper.findViewById(R.id.edt_description);
        edtYearExperience = regViewFlipper.findViewById(R.id.edt_year_of_experience);
        edtBusinessLink = regViewFlipper.findViewById(R.id.edt_business_link);
        edtMinCharges = regViewFlipper.findViewById(R.id.edt_min_charges);
        showPass = regViewFlipper.findViewById(R.id.showPass);
        showCPass = regViewFlipper.findViewById(R.id.showCPass);

        ll_logo_upload = regViewFlipper.findViewById(R.id.ll_logo_upload);
        ll_service_photo_upload = regViewFlipper.findViewById(R.id.ll_service_photo_upload);

        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        submit.setOnClickListener(view -> initLocation());

        ll_logo_upload.setOnClickListener(view -> {
            type = 1;
            initiatePhotoSelection();
        });
        ll_service_photo_upload.setOnClickListener(view -> {
            type = 2;
            initiatePhotoSelection();
        });

        getServiceList();

        spinner = (Spinner) regViewFlipper.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                business_categories = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initiatePhotoSelection() {
        try {
            final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        }
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                byte[] b = bytes.toByteArray();
                String encImage = Base64.encodeToString(b, Base64.DEFAULT);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();

                if (type == 1) {
                    bussiness_logo = encImage;
                    edtLogo.setText("img_" + timeStamp);
                } else {
                    service_photo = encImage;
                    edtServicePhoto.setText("img_" + timeStamp);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                byte[] b = bytes.toByteArray();
                String encImage = Base64.encodeToString(b, Base64.DEFAULT);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath);

                //ivPassDocument.setImageBitmap(bitmap);

                if (type == 1) {
                    bussiness_logo = encImage;
                    edtLogo.setText("img_" + timeStamp);
                } else {
                    service_photo = encImage;
                    edtServicePhoto.setText("img_" + timeStamp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initLocation() {
        LiveGpsTracker.getInstance(SpbRegisterActivity.this, new LiveGpsTracker.LocationUpdate() {
            @Override
            public void onLocationFound(double latitide, double longi) {
                latitude = String.valueOf(latitide);
                longitude = String.valueOf(longi);
                registerSubmit();
            }

            @Override
            public void OnLocationSettingNotFound() {
                Toast.makeText(SpbRegisterActivity.this, "Please enable location setting and retry", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopupPermission() {
                Toast.makeText(SpbRegisterActivity.this, "Please enable location permissions", Toast.LENGTH_SHORT).show();
            }
        }).initLocationUpdate();
    }

    private void registerSubmit() {

        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        Cpass = edtCPass.getText().toString();
        service_area = edtServiceArea.getText().toString();
        service_description = edt_description.getText().toString();
        year_of_experience = edtYearExperience.getText().toString();
        bussiness_link = edtBusinessLink.getText().toString();
        minimum_charge = edtMinCharges.getText().toString();

        if (pass.equals(Cpass)) {
            JSONObject data = new JSONObject();
            try {
                data.put("name", name);
                data.put("phone", phone);
                data.put("email", email);
                data.put("password", pass);
                data.put("password_confirmation", pass);
                data.put("role", "4");
                data.put("business_name", name);
                data.put("location_address", service_area);
                data.put("location", service_area);
                data.put("business_categories", business_categories);
                data.put("service_description", service_description);
                data.put("year_of_experience", year_of_experience);
                data.put("bussiness_link", bussiness_link);
                data.put("minimum_charge", minimum_charge);
                data.put("latitude", latitude);
                data.put("longitude", longitude);
                data.put("service_offerd_videolink", "");
                data.put("business_logo", bussiness_logo);
                data.put("service_offerd_image", service_photo);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiCall.postMethod(this, ServiceNames.REGISTER_SERVICE_PROVIDER_BUSI, data, response -> {

                Utils.log(TAG, response.toString());
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

                try {

                    Register register = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), Register.class);

                    prf.setString(Global.user_id, register.getId().toString());
                    prf.setString(Global.token, register.getToken());
                    prf.setString(Global.role, register.getRoles().toString());
                    prf.setString(Global.email, register.getEmail());

                    startActivity(new Intent(this, SpiHomeActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        } else {
            Toast.makeText(this, "Password did't match", Toast.LENGTH_SHORT).show();
        }
    }

    private void getServiceList() {

        ApiCall.getMethod(this, ServiceNames.SERVICE_LIST, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonarray = null;
            try {

                jsonarray = response.getJSONArray("data");

                for (int i = 0; i < jsonarray.length(); i++) {

                    try {

                        SubCategory subCategory = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), SubCategory.class);
                        spinnerArray.add(subCategory.getServiceName());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                simpleArray = new String[ spinnerArray.size() ];
                spinnerArray.toArray( simpleArray );
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, simpleArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    @Override
    public void onBackPressed() {
        int displayedChild = regViewFlipper.getDisplayedChild();
        if (displayedChild > 0) {
            regViewFlipper.setDisplayedChild(displayedChild - 1);
        } else {
            super.onBackPressed();
        }
    }

    public void onHideClick(View view) {
        if (view.getId() == R.id.showPass) {
            if (edtPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Passsword
                edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

        if (view.getId() == R.id.showCPass) {
            if (edtCPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                edtCPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Passsword
                edtCPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}