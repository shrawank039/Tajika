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
import com.matrixdeveloper.tajika.utils.AppConstants;
import com.matrixdeveloper.tajika.utils.FileOp;
import com.matrixdeveloper.tajika.utils.ImageFilePath;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SpiRegisterActivity extends AppCompatActivity {

    ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, nextToDocumentUpload, submit;
    LinearLayout ll_id_upload, ll_certificate_upload, cameraLayout, galleryLayout;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;
    int needDate = 0;
    private static final String TAG = "DocUpA";
    private int type = AppConstants.DOCUMENT_TYPE_ID;
    private String imagePath = "";
    private String documentPath;
    private TextView txtTitle, toDate, fromDate;
    LinearLayout dateLinear;
    private ImageView ivDocumentPreview;
    private Button btnRetake;
    private Button btnSave;
    private int PICK_IMAGE_REQUEST = 2;
    private Uri filePath;
    String picturePath = "";
    String dateSelected;
    protected boolean hasReadWritePermissions;
    protected static final int REQUEST_PERMISSIONS_READ_WRITE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_register);

        regViewFlipper = findViewById(R.id.vf_regViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        nextToDocumentUpload = regViewFlipper.findViewById(R.id.btn_nexttoDocumentDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);
        ll_id_upload = regViewFlipper.findViewById(R.id.ll_id_upload);
        cameraLayout = regViewFlipper.findViewById(R.id.cameraLayout);
        galleryLayout = regViewFlipper.findViewById(R.id.galleryLayout);
        ll_certificate_upload = regViewFlipper.findViewById(R.id.ll_certificate_upload);
        ivDocumentPreview = regViewFlipper.findViewById(R.id.iv_document_upload_preview);
        btnRetake = regViewFlipper.findViewById(R.id.btn_document_upload_retake);
        btnSave = regViewFlipper.findViewById(R.id.btn_document_upload_save);

        initListeners();
    }

    private void initListeners() {
        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        nextToDocumentUpload.setOnClickListener(view -> regViewFlipper.showNext());

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
        });
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
            cameraLayout.setVisibility(View.GONE);
            galleryLayout.setVisibility(View.VISIBLE);
            setDocumentImage(tempPath);
            Log.i(TAG, "onActivityResult: IMAGE GALLERY PATH : " + tempPath);

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data == null) {
            documentPath = imagePath;
            galleryLayout.setVisibility(View.GONE);
            cameraLayout.setVisibility(View.VISIBLE);
            regViewFlipper.showNext();
            setDocumentImage(imagePath);
            Log.i(TAG, "onActivityResult: IMAGE CAMERA PATH : " + imagePath);

        }
    }

    private String getDocumentTitle(int type) {

        dateLinear = regViewFlipper.findViewById(R.id.dateLayout);
        dateLinear.setVisibility(View.GONE);
        needDate = 0;

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

        Glide.with(getApplicationContext())
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


    public void onDocumentUploadRetakeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        onDocumentUploadTakePhotoClick(view);
    }

    public void onDocumentUploadSaveClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (needDate == 0) {
            performDocumentUploadSave();
        } else {
            if (fromDate.getText().equals("click to choose") && toDate.getText().equals("click to choose")) {
                Toast.makeText(this, "Need to select Date", Toast.LENGTH_SHORT).show();
            } else {
                performDocumentUploadSave();
            }
        }
    }

    public void performDocumentUploadSave() {

        Log.i(TAG, "performDocumentUploadSave: : " + "clicked");

        //  swipeView.setRefreshing(true);
        JSONObject postData = getDocumentUploadSaveJSObj();

        ArrayList<String> fileList = getFileList();


    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (documentPath != null && !documentPath.equals("")) {
            String tempPath = FileOp.getDocumentPhotoPath(getDocumentTitle(type));
            FileOp.writeBitmapToFile(documentPath, tempPath);
            fileList.add(tempPath);
            Log.i(TAG, "getFileList: Temp : " + tempPath);
            Log.i(TAG, "getFileList: Original " + documentPath);
        }

        return fileList;
    }

    private JSONObject getDocumentUploadSaveJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("auth_token", Config.getInstance().getAuthToken());
            postData.put("type", type);
            if (!fromDate.getText().equals("click to choose") && !toDate.getText().equals("click to choose")) {
                postData.put("to", fromDate.getText().toString().trim());
                postData.put("from", toDate.getText().toString().trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
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

    public void onDocumentRetakeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Log.i(TAG, "onDocumentRetakeClick: : " + "clicked");
        onDocumentUploadChoosePhoto();
    }

    public void onDocumentSaveClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Log.i(TAG, "onDocumentSaveClick: : " + "clicked");

        //  swipeView.setRefreshing(true);
        JSONObject postData = getDocumentUploadSaveJSObj();

        ArrayList<String> fileList = new ArrayList<>();
        String tempPath = FileOp.getDocumentPhotoPath(getDocumentTitle(type));
        FileOp.writeBitmapToFile(picturePath, tempPath);
        fileList.add(tempPath);
        Log.i(TAG, "getFileList: Temp : " + tempPath);
        Log.i(TAG, "getFileList: Original " + picturePath);
        // fileList.add(picturePath);
        //  Toast.makeText(this, fileList.toString(), Toast.LENGTH_SHORT).show();

//        DataManager.performDocumentUpload(postData, fileList, new BasicListener() {
//
//            @Override
//            public void onLoadCompleted(BasicBean basicBean) {
//                swipeView.setRefreshing(false);
////              App.saveToken(getApplicationContext(), driverDetailsBean);
//                Intent intent = new Intent();
//                intent.putExtra("type", type);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//
//            @Override
//            public void onLoadFailed(String error) {
//                swipeView.setRefreshing(false);
//                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
//                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
//
//                /* To Be Removed....*/
//                if (App.getInstance().isDemo()) {
//                    Intent intent = new Intent();
//                    intent.putExtra("type", type);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }
//            }
//        });
    }

    public void fromDate(View view) {
        datePicker(1);
    }

    public void toDate(View view) {
        datePicker(2);
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
}