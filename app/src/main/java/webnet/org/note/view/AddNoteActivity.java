package webnet.org.note.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.theartofdev.edmodo.cropper.CropImage;

import webnet.org.note.R;
import webnet.org.note.database.NOTE_TABLE;
import webnet.org.note.databaserepo.NoteTableDetailsRepo;
import webnet.org.note.utils.Utility;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout activityTextnotediteail;
    private ImageView imageView;
    private int flag;
    private long id;
    private String encodedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        activityTextnotediteail = (LinearLayout) findViewById(R.id.activity_textnotediteail);
        findViewById(R.id.saveid).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.pickImage);

        flag = getIntent().getIntExtra("FLAG", 0);
        id = getIntent().getLongExtra("ID", 0);
        if (flag == 1) {
            getTitleid().setText(NoteTableDetailsRepo.getProfileDetailForId(getApplicationContext(), id).getTITLE());
            getDeatailid().setText(NoteTableDetailsRepo.getProfileDetailForId(getApplicationContext(), id).getDESCRIPTION());
            imageView.setImageBitmap(Utility.decodeBase64(NoteTableDetailsRepo.getProfileDetailForId(getApplicationContext(), id).getIMAGE()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveid:
                String title = getTitleid().getText().toString().trim();
                String details = getDeatailid().getText().toString().trim();
                if (flag == 1) {
                    NOTE_TABLE note_table = new NOTE_TABLE();
                    note_table.setId(id);
                    note_table.setTITLE(title);
                    note_table.setDESCRIPTION(details);
                    note_table.setIMAGE(encodedString);
                    note_table.setDATE(String.valueOf(System.currentTimeMillis()));
                    NoteTableDetailsRepo.insertOrUpdate(getApplicationContext(), note_table);
                } else {
                    NOTE_TABLE note_table = new NOTE_TABLE();
                    note_table.setTITLE(title);
                    note_table.setDESCRIPTION(details);
                    note_table.setIMAGE(encodedString);
                    note_table.setDATE(String.valueOf(System.currentTimeMillis()));
                    NoteTableDetailsRepo.insertOrUpdate(getApplicationContext(), note_table);
                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pickimage, menu);
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pic:
                if (CropImage.isExplicitCameraPermissionRequired(this)) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                } else {
                    CropImage.startPickImageActivity(this);
                }
                break;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imageUri)
                    .start(this);
            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
//                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {

//                mCurrentFragment.setImageUri(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Glide.with(this)
                        .load(resultUri)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageView.setImageBitmap(resource);
                                encodedString = Utility.encodeTobase64(resource);
                            }
                        });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private EditText getTitleid() {
        return (EditText) findViewById(R.id.titleid);
    }

    private EditText getDeatailid() {
        return (EditText) findViewById(R.id.deatailid);
    }
}
