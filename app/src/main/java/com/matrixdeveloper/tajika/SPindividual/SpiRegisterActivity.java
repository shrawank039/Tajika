package com.matrixdeveloper.tajika.SPindividual;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.location.LiveGpsTracker;
import com.matrixdeveloper.tajika.model.Category;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SpiRegisterActivity extends AppCompatActivity {

    ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, nextToDocumentUpload, submit;
    EditText idOrPassword, professionalCertificate;
    private LinearLayout ll_id_upload, ll_certificate_upload, galleryLayout;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;
    int needDate = 0;
    private static final String TAG = "SpiRegisterAct";
    private int type = AppConstants.DOCUMENT_TYPE_ID;
    private String imagePath = "";
    private String documentPath;
    private TextView toDate, fromDate;
    Button btnRetake;
    Button btnSave;
    private int PICK_IMAGE_REQUEST = 2;
    private Uri filePath;
    String picturePath = "";
    String dateSelected;
    int serviceCatID;
    protected boolean hasReadWritePermissions;
    protected static final int REQUEST_PERMISSIONS_READ_WRITE = 4;
    private String name, phone, email, pass, Cpass, service_area, business_name, business_categories, service_description, year_of_experience,
            bussiness_link, minimum_charge, education_level, passportnumber, upload_passportid = "", professional_qualification,
            qualification_certification, latitude, longitude;
    private EditText edtName, edtPhone, edtEmail, edtPass, edtCPass, edtServiceArea, edtYourExperience,
            edtBusinessLink, edtServiceCharge, edtSkillDescription, edtHighestEducation, edtPassportNumber, edtProQualification, edtBusinessName;
    private ImageView showPass, showCPass;
    private static PrefManager prf;

    List<String> spinnerArray = new ArrayList<>();
    String[] simpleArrayEducation = {"None", "Primary", "Secondary/High School", "College/Polytechnic", "University"};
    Spinner spinnerServiceCategory, spinnerHighestEducation;

    private Bitmap bitmap;
    private File destination;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private int MY_CAMERA_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;


    private List<String> categoryName = new ArrayList<>();
    private List<Integer> categoryID = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_register);

        prf = new PrefManager(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        regViewFlipper = findViewById(R.id.vf_regViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        nextToDocumentUpload = regViewFlipper.findViewById(R.id.btn_nexttoDocumentDetails);
        submit = regViewFlipper.findViewById(R.id.btn_spiSubmit);
        ll_id_upload = regViewFlipper.findViewById(R.id.ll_id_upload);
        galleryLayout = regViewFlipper.findViewById(R.id.galleryLayout);
        ll_certificate_upload = regViewFlipper.findViewById(R.id.ll_certificate_upload);
        idOrPassword = regViewFlipper.findViewById(R.id.edt_IDorPassword);
        professionalCertificate = regViewFlipper.findViewById(R.id.edt_professionalCertificate);

        edtName = regViewFlipper.findViewById(R.id.edt_name);
        edtPhone = regViewFlipper.findViewById(R.id.edt_phone);
        edtEmail = regViewFlipper.findViewById(R.id.edt_email);
        edtPass = regViewFlipper.findViewById(R.id.edt_pass);
        edtCPass = regViewFlipper.findViewById(R.id.edt_cpass);
        showPass = regViewFlipper.findViewById(R.id.showPass);
        showCPass = regViewFlipper.findViewById(R.id.showCPass);

        edtServiceArea = regViewFlipper.findViewById(R.id.edt_serviceArea);
        edtBusinessName = regViewFlipper.findViewById(R.id.edt_businessName);

        //edtBusinessCategories = regViewFlipper.findViewById(R.id.edt_businessCategory);

        edtBusinessLink = regViewFlipper.findViewById(R.id.edt_businessLink);
        edtYourExperience = regViewFlipper.findViewById(R.id.edt_yourExperience);
        edtServiceCharge = regViewFlipper.findViewById(R.id.edt_serviceCharge);
        edtSkillDescription = regViewFlipper.findViewById(R.id.edt_skillDescription);

        //edtHighestEducation = regViewFlipper.findViewById(R.id.edt_highestEducation);
        edtPassportNumber = regViewFlipper.findViewById(R.id.edt_idPassNumber);
        edtProQualification = regViewFlipper.findViewById(R.id.edt_proQualification);

        btnRetake = regViewFlipper.findViewById(R.id.document_retake);
        btnSave = regViewFlipper.findViewById(R.id.document_save);

        initListeners();
        getCatSubcat("service");

        spinnerServiceCategory = regViewFlipper.findViewById(R.id.spinner);
        spinnerHighestEducation = regViewFlipper.findViewById(R.id.spinnerEducation);

        categoryName.add("Choose Service Type");
        categoryID.add(0);
        spinnerServiceCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //business_categories = parent.getItemAtPosition(position).toString();
                serviceCatID = categoryID.get(spinnerServiceCategory.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerHighestEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                education_level = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter aa = new ArrayAdapter(SpiRegisterActivity.this, android.R.layout.simple_spinner_item, simpleArrayEducation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHighestEducation.setAdapter(aa);

        ArrayAdapter aaa = new ArrayAdapter(SpiRegisterActivity.this, android.R.layout.simple_spinner_item, categoryName);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceCategory.setAdapter(aaa);

    }

    private void getCatSubcat(String type) {

        JSONObject data = new JSONObject();
        try {
            data.put("service_type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ALL_CAT_SUBCAT, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray serviceArray = null;
            if (Objects.equals(response.opt("status"), 200)) {
                try {
                    serviceArray = response.getJSONArray("data");

                    for (int i = 0; i < serviceArray.length(); i++) {
                        try {
                            Category category = MySingleton.getGson().fromJson(serviceArray.getJSONObject(i).toString(), Category.class);

                            categoryID.add(category.getId());
                            categoryName.add(category.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {
        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        nextToDocumentUpload.setOnClickListener(view -> regViewFlipper.showNext());

        ll_id_upload.setOnClickListener(view -> {
            type = 1;
            initiatePhotoSelection();
        });
        ll_certificate_upload.setOnClickListener(view -> {
            type = 2;
            initiatePhotoSelection();
        });
        submit.setOnClickListener(view -> {
            initLocation();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat #requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void initLocation() {
        LiveGpsTracker.getInstance(SpiRegisterActivity.this, new LiveGpsTracker.LocationUpdate() {
            @Override
            public void onLocationFound(double latitide, double longi) {
                latitude = String.valueOf(latitide);
                longitude = String.valueOf(longi);
                if (serviceCatID != 0) {
                    registerSubmit();
                } else {
                    Toast.makeText(SpiRegisterActivity.this, "Please choose service type", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnLocationSettingNotFound() {
                Toast.makeText(SpiRegisterActivity.this, "Please enable location setting and retry", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopupPermission() {
                Toast.makeText(SpiRegisterActivity.this, "Please enable location permissions", Toast.LENGTH_SHORT).show();
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
        business_name = edtBusinessName.getText().toString();
        year_of_experience = edtYourExperience.getText().toString();
        bussiness_link = edtBusinessLink.getText().toString();
        minimum_charge = edtServiceCharge.getText().toString();
        service_description = edtSkillDescription.getText().toString();
        passportnumber = edtPassportNumber.getText().toString();
        professional_qualification = edtProQualification.getText().toString();

        if (upload_passportid.equals(""))
            upload_passportid = idOrPassword.getText().toString();

        if (pass.equals(Cpass)) {
            JSONObject data = new JSONObject();
            try {
                data.put("name", name);
                data.put("phone", phone);
                data.put("email", email);
                data.put("password", pass);
                data.put("password_confirmation", pass);
                data.put("role", "3");
                data.put("service_area", service_area);
                data.put("business_name", business_name);
                data.put("business_categories", String.valueOf(serviceCatID));
                data.put("service_description", service_description);
                data.put("year_of_experience", year_of_experience);
                data.put("bussiness_link", bussiness_link);
                data.put("minimum_charge", minimum_charge);
                data.put("education_level", education_level);
                data.put("passportnumber", passportnumber);
                data.put("upload_passportid", upload_passportid);
                data.put("professional_qualification", professional_qualification);
                data.put("qualification_certification", qualification_certification);
                data.put("latitude", latitude);
                data.put("longitude", longitude);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiCall.postMethod(this, ServiceNames.REGISTER_SERVICE_PROVIDER_INDI, data, response -> {

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
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        } else {
            Toast.makeText(this, "Password did't match", Toast.LENGTH_SHORT).show();
        }

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
                    upload_passportid = encImage;
                    idOrPassword.setText("img_" + timeStamp);
                } else {
                    qualification_certification = encImage;
                    professionalCertificate.setText("img_" + timeStamp);
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
                    upload_passportid = encImage;
                    idOrPassword.setText("img_" + timeStamp);
                } else {
                    qualification_certification = encImage;
                    professionalCertificate.setText("img_" + timeStamp);
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

    public void datePicker(final int a) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month + 1;
                        //  Log.d("datePicker","date : "+ String.valueOf(month));
                        dateSelected = String.valueOf(year + "-" + month + "-" + day);
                        if (a == 1)
                            fromDate.setText(dateSelected);
                        else
                            toDate.setText(dateSelected);
                    }
                }, year, month, day);
        datePickerDialog.show();
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