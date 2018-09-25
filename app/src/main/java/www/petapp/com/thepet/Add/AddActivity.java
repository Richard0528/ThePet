package www.petapp.com.thepet.Add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import www.petapp.com.thepet.MainActivity;
import www.petapp.com.thepet.R;
import www.petapp.com.thepet.model.CustomViewPager;
import www.petapp.com.thepet.model.Photo;
import www.petapp.com.thepet.model.SectionPageAdapter;
import www.petapp.com.thepet.util.CompressImageAsyncTask;

public class AddActivity extends AppCompatActivity implements
        AddInfoFragment.OnButtonClickListener,
        AddImageFragment.OnButtonClickListener,
        AddCheckListFragment.OnButtonClickListener{

    private CustomViewPager viewPager;
    private String TAG = "AddActivity";

    // firebase fields
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private Photo mPhoto;

    // data from AddInfoFragment
    private String db_breeder;
    private String db_name;
    private int db_age;
    private double db_size;
    private double db_weight;
    private String db_description;

    private List<Bitmap> mCompressedBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // remove elevation below the action bar
        getSupportActionBar().setElevation(0);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mPhoto = new Photo();

        setupFirebaseAuth();
        setupViewPager();
    }

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
    private void setupFirebaseAuth() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //check if email is verified
                if(user.isEmailVerified()){
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                    Toast.makeText(AddActivity.this, "Signed in with" + user.getEmail(), Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(AddActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
                // ...
            }
        };

        // this will get called as soon as this activity started, every changes, automatically
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    /**
     * Responsible for adding the 3 tabs: 1.Media, 2.Info, 3.CheckLists
     */
    private void setupViewPager() {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddImageFragment());
        adapter.addFragment(new AddInfoFragment());
        adapter.addFragment(new AddCheckListFragment());
        viewPager = findViewById(R.id.container);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Media");
        tabLayout.getTabAt(1).setText("Info");
        tabLayout.getTabAt(2).setText("Checklists");
    }

    @Override
    public void onButtonClicked(View view){
        int currPos = viewPager.getCurrentItem();

        switch(view.getId()){

            case R.id.Info_button:
                viewPager.setCurrentItem(currPos+1);
                break;
            case R.id.Image_button:
                viewPager.setCurrentItem(currPos+1);
                break;
            case R.id.btn_submit:
                uploadImageData();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
                break;
        }
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }
    // gather data from all three fragments and upload to firebase
    // part 1) all the data will be related to the image, add new photo node
    // part 2) add image URL to User
    private void uploadImageData() {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        String newPhotoKey = myRef.child(AddActivity.this.getString(R.string.node_photos)).push().getKey();

        // create photo node
        mPhoto.setUserID(userId);
        mPhoto.setBreeder(db_breeder);
        mPhoto.setName(db_name);
        mPhoto.setAge(db_age);
        mPhoto.setSize(db_size);
        mPhoto.setWeight(db_weight);
        mPhoto.setDescription(db_description);
        mPhoto.setDate_created(getTimestamp());

        myRef.child(getString(R.string.node_photos))
                .child(newPhotoKey)
                .setValue(mPhoto);

        //upload pet images to fire storage

        for (int i = 0; i < mCompressedBitmaps.size(); i++) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(getString(R.string.node_images) + "/" + getString(R.string.node_users) +
                            "/" + userId + "/" + getString(R.string.node_pet) + "/picture" + i);

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            mCompressedBitmaps.get(i).compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Bitmap tempBitMap = mCompressedBitmaps.get(i);
            int bytes = tempBitMap.getByteCount();
            ByteBuffer buffer = ByteBuffer.allocate(bytes);
            tempBitMap.copyPixelsToBuffer(buffer);
            UploadTask uploadTask = storageReference.putBytes(buffer.array());
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Post Success", Toast.LENGTH_SHORT).show();

                    //insert the download url into the firebase database
                    Uri firebaseUri = taskSnapshot.getDownloadUrl();

                    Log.d(TAG, "onSuccess: firebase download url: " + firebaseUri.toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    //need to insert images url to database and fix rotation
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "could not upload photo", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(getString(R.string.node_images) + "/" + getString(R.string.node_users))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    @Override
    public void getImgPaths(List<String> imgPaths) {
        // compress images to the given width and height
        new CompressImageAsyncTask(new CompressImageAsyncTask.OnCompressImagePostExecuteDelegate() {
            @Override
            public void getCompressedBitmap(List<Bitmap> bitmaps) {
                mCompressedBitmaps = bitmaps;
            }
        }, imgPaths, 640, 320).execute();

    }

    /**
     * Get pet information from AddInfoFragment.
     * @param breeder
     * @param name
     * @param age
     * @param size
     * @param weight
     * @param description
     */
    @Override
    public void getPetInfo(String breeder, String name, int age, double size, double weight, String description) {
        Log.e(TAG, "Pet information: " + breeder + ", " + name + ", " + age + ", " +
                size + ", " + weight + ", " + description);
        db_breeder = breeder;
        db_name = name;
        db_age = age;
        db_size = size;
        db_weight = weight;
        db_description = description;
    }

    @Override
    public void getPetCheckLists() {

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
