package com.slizzz.android.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slizzz.android.R;
import com.slizzz.android.ui.fragment.ChooseCameraDialogue;
import com.slizzz.android.ui.fragment.DashboardFragment;
import com.slizzz.android.util.AddFragments;
import com.slizzz.android.util.FileUtil;
import com.slizzz.android.util.UiConstants;
import com.slizzz.android.util.UiUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by sjena on 25/08/18.
 */

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{


    private Toolbar mToolbar;
    private Spinner feeTypeSpinner, publicTypeSpinner;
    private LinearLayout spinnerLayout;
    private TextView selectedType, selectedPublicType;
    private ImageView uploadImage;
    private static boolean IS_READ_STORAGE = false;
    private static boolean IS_WRITE_STORAGE = false;
    private static boolean IS_CAMERA_PERMISSION = false;

    private static final int PHOTO_REQUEST_CODE = 102;

    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 223;
    private String path;
    private Uri file;
    private Uri uri, fileUri, downloadUri = null;
    private FancyButton create_event;
    private EditText feeTypeEdtTV, eventTittle, dateEDtTV, timeEDtTV;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar cal = Calendar.getInstance();
    Bitmap bitmap;
    File compressedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_createevent);

        setToolBar();
        inItUi();



    }

    private void inItUi() {

        feeTypeSpinner = (Spinner)findViewById(R.id.feeTypeSpinner);

        List<String> categories = new ArrayList<String>();
        categories.add("PREPAID");
        categories.add("PAY AT DOOR");
        categories.add("FREE");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        feeTypeSpinner.setAdapter(dataAdapter);
        spinnerLayout = (LinearLayout)findViewById(R.id.spinnerLayout);
        spinnerLayout.setOnClickListener(this);

        selectedType = (TextView) findViewById(R.id.selectedType);
        selectedPublicType = (TextView) findViewById(R.id.selectedPublicType);
        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                String item = (String)adapterView.getItemAtPosition(position);
                selectedType.setText(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });



        publicTypeSpinner = (Spinner)findViewById(R.id.publicTypeSpinner);
        List<String> categories1 = new ArrayList<String>();
        categories1.add("PUBLIC");
        categories1.add("PRIVATE");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        publicTypeSpinner.setAdapter(dataAdapter1);

        publicTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                String item = (String)adapterView.getItemAtPosition(position);
                selectedPublicType.setText(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        create_event = (FancyButton) findViewById(R.id.create_event);
        create_event.setOnClickListener(this);

        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(this);

        feeTypeEdtTV = (EditText) findViewById(R.id.feeTypeEdtTV);
        eventTittle = (EditText) findViewById(R.id.eventTittle);
        dateEDtTV = (EditText) findViewById(R.id.dateEDtTV);
        timeEDtTV = (EditText) findViewById(R.id.timeEDtTV);
        dateEDtTV.setOnClickListener(this);
        timeEDtTV.setOnClickListener(this);
    }


    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.spinnerLayout:
                feeTypeSpinner.performClick();
                break;

            case R.id.uploadImage:

                FragmentManager manager3 =  getSupportFragmentManager();
                ChooseCameraDialogue dialogue3 = new ChooseCameraDialogue();
                Bundle bundle3 = new Bundle();
                //bundle3.putInt(UiConstants.DIALOGUE_TYPE, 4);
                dialogue3.setArguments(bundle3);
                dialogue3.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_NoTitle);
                //typeId = 9;


                dialogue3.show(manager3, "ShareDialogueFragment");

                break;

            case R.id.create_event:
                //UiUtil.showToast(this, "Event created succefully");
                //finish();

                ParseObject profilePicture = ParseObject.create("Event");
                UiUtil.showProgressDialogue(this, "","Creating Event");
                profilePicture.put("coverImage", getfile(mBitmap));

                profilePicture.put("createdBy", ParseUser.getCurrentUser());
                profilePicture.put("feeType", selectedType.getText().toString());
                profilePicture.put("fee", Integer.parseInt(feeTypeEdtTV.getText().toString()));
                profilePicture.put("descriptionText", eventTittle.getText().toString());
                profilePicture.put("name", eventTittle.getText().toString());
                profilePicture.put("date", cal.getTime());
                ParseACL acl=new ParseACL();
                acl.setWriteAccess(ParseUser.getCurrentUser(), true);

                acl.setPublicReadAccess(true);
                acl.setPublicWriteAccess(true);
                if(selectedPublicType.getText().toString().equalsIgnoreCase("PUBLIC"))
                {
                    profilePicture.put("isPublic", true);
                    UiUtil.showToast(CreateEventActivity.this, "Event created succefully");
                }else
                {
                    profilePicture.put("isPublic", false);
                }

                profilePicture.saveInBackground(new SaveCallback()
                {
                    @Override
                    public void done(ParseException e)
                    {
                        if (e == null)
                        {
                            Log.i("Parse", "saved successfully");
                            UiUtil.showToast(CreateEventActivity.this,"Event create successfully");

                            finish();

                        }
                        else
                        {
                            Log.i("Parse", "error while saving");
                        }

                        UiUtil.cancelProgressDialogue();
                    }
                });

                break;


            case R.id.dateEDtTV:

                showDatePicker();



                break;


            case R.id.timeEDtTV:
                
                showTime();


                break;
        }
    }

    private void showTime() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        timeEDtTV.setText(hourOfDay + ":" + minute);
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void showDatePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                       dateEDtTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

       /* if (v == btnTimePicker) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();*/
    }

    public void clickedLayout(String type, int typei) {

        if (type.equals("gallery")) {

            if (IS_WRITE_STORAGE && IS_READ_STORAGE) {

                launchPhotoGallery();
            } else {
                checkPermissions();

            }
        } else {
            if (IS_WRITE_STORAGE && IS_READ_STORAGE) {

                //launchCamera();
                //launchCamera2();
                takePicture();
            } else {
                checkPermissions();

            }
        }

    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Uri photoURI = null;
            try {
                File photoFile = createImageFileWith();
                path = photoFile.getAbsolutePath();
                photoURI = FileProvider.getUriForFile(CreateEventActivity.this,
                        getString(R.string.file_provider_authority),
                        photoFile);

            } catch (IOException ex) {
                Log.e("TakePicture", ex.getMessage());
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
        }
    }

    private static BitmapFactory.Options provideCompressionBitmapFactoryOptions() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = false;
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        return opt;
    }

    public File createImageFileWith() throws IOException {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "JPEG_" + timestamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "pics");
        storageDir.mkdirs();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "chat.png"));
        }
        return outputFileUri;
    }



    public Intent getPickImageChooserIntent() {


        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        String fileName = "fkatPic";

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/example";

        File file = new File(path,fileName+".jpg");

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            fileUri = FileProvider.getUriForFile(this,"com.agentapp.android.provider", file);
//            if (fileTemp != null) {
//            fileUri = Uri.fromFile(fileTemp);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


            intent.setPackage(res.activityInfo.packageName);
           /* if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }*/
            allIntents.add(intent);
        }




        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");


        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }



    private void launchPhotoGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                UiConstants.PHOTO_GALLERY_REQUEST);
    }





    private void checkPermissions() {

        int hasLocationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);

        int hasWritePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<String>();


        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.CAMERA);
        } else {
            IS_CAMERA_PERMISSION = true;
        }

        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            IS_WRITE_STORAGE = true;
        }

        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            IS_READ_STORAGE = true;
        }




        if (!permissions.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(android.Manifest.permission.CAMERA)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            IS_CAMERA_PERMISSION = true;
                            //UiUtil.showLog("Permissions", "Permission Granted: " + permissions[i]);
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            // UiUtil.showLog("Permissions", "Permission Denied: " + permissions[i]);
                            IS_CAMERA_PERMISSION = false;
                        }
                    } else if (permissions[i].equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            IS_READ_STORAGE = true;
                            Log.d("Permissions", "Permission Granted: " + permissions[i]);
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Log.d("Permissions", "Permission Denied: " + permissions[i]);
                            IS_READ_STORAGE = false;
                        }
                    } else if (permissions[i].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            IS_WRITE_STORAGE = true;
                            Log.d("Permissions", "Permission Granted: " + permissions[i]);
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Log.d("Permissions", "Permission Denied: " + permissions[i]);
                            IS_WRITE_STORAGE = false;
                        }
                    }
                }



            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

    }

    Bitmap  mBitmap;
    File compressedFile, actualFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                Uri imageUri = getPickImageResultUri(data);

                actualFile = new File(path);

                //mBitmap  = BitmapFactory.decodeFile(path);

                //uploadImage.setImageBitmap(mBitmap);
                setImage();






            }


        }
        else{

            if (resultCode == RESULT_OK) {
                fileUri = data.getData();


                final Uri selectedUri = data.getData();
                //actualFile = new File(getPath(this, selectedUri));

                /*try {
                    mBitmap  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedUri);
                    uploadImage.setImageBitmap(mBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                try {
                    actualFile = FileUtil.from(this, data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setImage();
                /*try {
                    File file = FileUtil.from(this, data.getData());
                    BitmapFactory.decodeFile(file.getAbsolutePath());
                    uploadImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            }


        }

    }

    public static String getPath(Context context,Uri uri ) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }
    private void setImage() {

        new Compressor(this)
                .compressToFileAsFlowable(actualFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        compressedImage = file;
                        //uploadImage.setImageBitmap(mBitmap);

                        mBitmap  = BitmapFactory.decodeFile(compressedImage.getAbsolutePath());
                        uploadImage.setImageBitmap(mBitmap);

                        //uploadImage.setImageBitmap(mBitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        //showError(throwable.getMessage());
                    }
                });
    }

    public ParseFile getfile(Bitmap bitmap)
    {
        //Bitmap bitmap = BitmapFactory.decodeFile("Your File Path");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        final byte[] data = stream.toByteArray();
        ParseFile file = new ParseFile("coverimage.png", data);
        return file;
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public void askPermission()
    {

    }
}
