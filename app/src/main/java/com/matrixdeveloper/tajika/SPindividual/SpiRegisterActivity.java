package com.matrixdeveloper.tajika.SPindividual;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.config.Config;
import com.matrixdeveloper.tajika.model.Register;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.AppConstants;
import com.matrixdeveloper.tajika.utils.FileOp;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.ImageFilePath;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SpiRegisterActivity extends AppCompatActivity {

    ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, nextToDocumentUpload, submit;
    private LinearLayout ll_id_upload, ll_certificate_upload, galleryLayout;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;
    int needDate = 0;
    private static final String TAG = "SpiRegisterAct";
    private int type = AppConstants.DOCUMENT_TYPE_ID;
    private String imagePath = "";
    private String documentPath;
    private TextView txtTitle, toDate, fromDate;
    ImageView ivDocumentPreview;
    Button btnRetake;
    Button btnSave;
    private int PICK_IMAGE_REQUEST = 2;
    private Uri filePath;
    String picturePath = "";
    String dateSelected;
    protected boolean hasReadWritePermissions;
    protected static final int REQUEST_PERMISSIONS_READ_WRITE = 4;
    private String name, phone, email, pass, Cpass,service_area,business_categories,service_description,year_of_experience,
            bussiness_link,minimum_charge,education_level,passportnumber,upload_passportid,professional_qualification,
            qualification_certification,latitude,longitude;
    private EditText edtName, edtPhone, edtEmail, edtPass, edtCPass;
    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_register);

        prf = new PrefManager(this);

        regViewFlipper = findViewById(R.id.vf_regViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        nextToDocumentUpload = regViewFlipper.findViewById(R.id.btn_nexttoDocumentDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);
        ll_id_upload = regViewFlipper.findViewById(R.id.ll_id_upload);
        galleryLayout = regViewFlipper.findViewById(R.id.galleryLayout);
        ll_certificate_upload = regViewFlipper.findViewById(R.id.ll_certificate_upload);
        ivDocumentPreview = regViewFlipper.findViewById(R.id.iv_preview);
        btnRetake = regViewFlipper.findViewById(R.id.document_retake);
        btnSave = regViewFlipper.findViewById(R.id.document_save);

        initListeners();
    }

    private void initListeners() {
        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        nextToDocumentUpload.setOnClickListener(view -> regViewFlipper.showNext());

        btnRetake.setOnClickListener(view -> onDocumentUploadChoosePhoto());
        btnSave.setOnClickListener(view -> getFileList());
        ll_id_upload.setOnClickListener(view -> {
            type = 1;
            onDocumentUploadChoosePhoto();
        });
        ll_certificate_upload.setOnClickListener(view -> {
            type = 2;
            onDocumentUploadChoosePhoto();
        });
        submit.setOnClickListener(view -> {
            startActivity(new Intent(this, SpiHomeActivity.class));
           // registerSubmit();
        });
    }

    private void registerSubmit() {

        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        Cpass = edtCPass.getText().toString();

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
                data.put("business_categories", business_categories);
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

                    startActivity(new Intent(this, SpiHomeActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        } else {
            Toast.makeText(this, "Password did't match", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            picturePath = ImageFilePath.getPath(SpiRegisterActivity.this, data.getData());
            String tempPath = FileOp.getDocumentPhotoPath(getDocumentTitle(type));
            FileOp.writeBitmapToFile(picturePath, tempPath);

            regViewFlipper.showNext();
            galleryLayout.setVisibility(View.VISIBLE);
            setDocumentImage(tempPath);
            Log.i(TAG, "onActivityResult: IMAGE GALLERY PATH : " + tempPath);

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data == null) {
            documentPath = imagePath;
            galleryLayout.setVisibility(View.GONE);
            regViewFlipper.showNext();
            setDocumentImage(imagePath);
            Log.i(TAG, "onActivityResult: IMAGE CAMERA PATH : " + imagePath);

        }
    }

    private String getDocumentTitle(int type) {

        switch (type) {

            case AppConstants.DOCUMENT_TYPE_ID:
                return "1";

            case AppConstants.DOCUMENT_TYPE_PROFESSIONAL_CERTIFICATE:
                return "2";

            default:
                return "0";
        }

    }


    private void setDocumentImage(String imagePath) {

        Glide.with(SpiRegisterActivity.this)
                .load(imagePath)
                .apply(new RequestOptions()
                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                        .fitCenter())
                .into(ivDocumentPreview);
    }

    protected boolean checkForReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                /*String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_WRITE);*/
                return hasReadWritePermissions = false;
            } else {
                return hasReadWritePermissions = true;
            }
        } else {
            return hasReadWritePermissions = true;
        }
    }

    protected void getReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_WRITE);
            }
        }
    }

    public void onDocumentUploadTakePhotoClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        }
//        else {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // Ensure that there's a camera activity to handle the intent
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                // Create the File where the photo should go
//                File photoFile = null;
//                try {
////                    imagePath = image.getAbsolutePath();
//                    photoFile = App.createImageFile(App.getFileName(type)).getAbsoluteFile();
//                    imagePath = photoFile.getAbsolutePath();
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                }
//                if (photoFile != null) {
//                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
//                    } else {
//                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
//                                getApplicationContext().getPackageName() + ".provider", photoFile);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    }
//
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        }
    }


    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (picturePath != null && !picturePath.equals("")) {
            String tempPath = FileOp.getDocumentPhotoPath(getDocumentTitle(type));
            FileOp.writeBitmapToFile(picturePath, tempPath);
            fileList.add(tempPath);
            Log.i(TAG, "getFileList: Temp : " + tempPath);
            Log.i(TAG, "getFileList: Original " + documentPath);
        }

        return fileList;
    }

    public void onDocumentUploadChoosePhoto() {
        Log.i(TAG, "onDocumentUploadChoosePhoto: : " + "clicked");

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
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
}