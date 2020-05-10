package com.example.finalproject.UI.Activities;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.example.finalproject.ServerRequests.jsonPlaceHolderApi;
import com.example.finalproject.UI.FirebaseConnection.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Register extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    jsonPlaceHolderApi apiService;
    Uri picUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputFullname;
    private TextInputLayout textInputPassword;
    private TextInputLayout text_input_phone;
    private TextInputLayout text_input_grade;
    private TextInputLayout text_input_university;
    private TextInputLayout text_input_facebookUrl;
    private Button chooseImage;
    private Button confirmRegister;
    private ImageView mImageView;
    private ArrayList<String> schools;
    private boolean imageChoos = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputFullname = findViewById(R.id.text_input_fullname);
        textInputPassword = findViewById(R.id.text_input_password);
        text_input_phone = findViewById(R.id.text_input_phone);
        text_input_grade = findViewById(R.id.text_input_grade);
        text_input_university = findViewById(R.id.text_input_university);
        text_input_facebookUrl = findViewById(R.id.text_input_facebookUrl);

        chooseImage = findViewById(R.id.chooseImage);
        confirmRegister = findViewById(R.id.confirmRegister);
        mImageView = findViewById(R.id.imageViewRegister);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        schools = new ArrayList<>();
        schools.add("BGU");
        schools.add("Sapir");
        schools.add("Tel Aviv");

        askPermissions();
        initRetrofitClient();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
                openFileChooser();

            }

        });

        confirmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Register.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    if(valid() && imageChoos) {
                        String username = textInputUsername.getEditText().getText().toString();
                        String password = textInputPassword.getEditText().getText().toString();
                        String fullName = textInputFullname.getEditText().getText().toString();
                        String email = textInputEmail.getEditText().getText().toString();
                        String phone = text_input_phone.getEditText().getText().toString();
                        String grade = text_input_grade.getEditText().getText().toString();
                        String school = text_input_university.getEditText().getText().toString();
                        String facebook = text_input_facebookUrl.getEditText().getText().toString();

                        if(ViewModel.getInstance().register(username, password, fullName, email, phone, grade, school, facebook)){
                            uploadFile();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Register.this, "Connection Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(Register.this, "Fill All Fields.\nPassword need to include 1 digit, at least 1 upper case char and at least 1 special char ", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }



    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }


    private boolean validateFullName() {
        String fullNameInput = textInputFullname.getEditText().getText().toString().trim();

        if (fullNameInput.isEmpty()) {
            textInputFullname.setError("Field can't be empty");
            return false;
        } else {
            textInputFullname.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String phone = text_input_phone.getEditText().getText().toString().trim();
        if (phone.isEmpty()) {
            text_input_phone.setError("Field can't be empty");
            return false;
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            text_input_phone.setError("Not a phone number");
            return false;
        } else {
            text_input_phone.setError(null);
            return true;
        }
    }

    private boolean validateGPA() {
        String gpa = text_input_grade.getEditText().getText().toString().trim();
        try {
            if (gpa.isEmpty()) {
                text_input_grade.setError("Field can't be empty");
                return false;
            } else if (Integer.parseInt(gpa) > 100 || Integer.parseInt(gpa) < 0) {
                text_input_grade.setError("Not a grade");
                return false;
            } else {
                text_input_grade.setError(null);
                return true;
            }
        }
        catch (Exception e){
            text_input_grade.setError("Not a grade");
        }
        return false;
    }

    private boolean validateSchool() {
        String school = text_input_university.getEditText().getText().toString().trim();

        if (school.isEmpty()) {
            text_input_university.setError("Field can't be empty");
            return false;
        } else if (!schools.contains(school)) {
            text_input_university.setError("You can choose - BGU/Sapir/Tel Aviv");
            return false;
        } else {
            text_input_university.setError(null);
            return true;
        }
    }

    private boolean validateFacebook() {
        String facebook = text_input_facebookUrl.getEditText().getText().toString().trim();

        if (facebook.isEmpty()) {
            text_input_facebookUrl.setError("Field can't be empty");
            return false;
        } else if (!Patterns.WEB_URL.matcher(facebook).matches()) {
            text_input_facebookUrl.setError("Not a URL");
            return false;
        } else {
            text_input_facebookUrl.setError(null);
            return true;
        }
    }

    public boolean valid() {
       if(!validateEmail() || !validateUsername() || !validatePassword()|| !validateFullName() ||!validatePhone() ||!validateGPA() ||!validateSchool() ||!validateFacebook())
           return false;
       return true;
    }

    //ask from user permittions
    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    //init request json place holder
    private void initRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        apiService = new Retrofit.Builder().baseUrl("http://192.168.1.13:3000/").client(client).build().create(jsonPlaceHolderApi.class);
    }


    //choose image from gallary
    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
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

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    //return uri of image
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//
//            ImageView imageView = findViewById(R.id.imageView);
//
//            if (requestCode == IMAGE_RESULT) {
//
//
//                String filePath = getImageFilePath(data);
//                if (filePath != null) {
//                    //try ( InputStream is = new URL( filePath ).openStream() ) {
//                        //mBitmap = BitmapFactory.decodeStream( new URL( filePath ).openStream() );
//                    //} catch (MalformedURLException e) {
//                    //    e.printStackTrace();
//                    //} catch (IOException e) {
//                    //    e.printStackTrace();
//                    //}
//                    mBitmap = BitmapFactory.decodeFile(filePath);
//                    imageView.setImageBitmap(mBitmap);
//                }
//            }
//
//        }
//
//    }

    //return image path
    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }


    //find permittion that havent asked yet
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    //check if this permition is givven
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //version checks
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    ///////////////////////////////////////firebase////////////////////////////////


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        imageChoos = true;
    }


    private void uploadFile() {
        if(!validateUsername()){
            Toast.makeText(Register.this, "Please enter valid user name first", Toast.LENGTH_LONG).show();
            return;
        }
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println(uri.toString());
                                    Toast.makeText(Register.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    Upload upload = new Upload(textInputUsername.getEditText().getText().toString(),uri.toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    upload.setKey(uploadId);
                                    mDatabaseRef.child(uploadId).setValue(upload);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    System.out.println("errrooorrrrrrrrrrrrrrrrrrrrr");
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }




}