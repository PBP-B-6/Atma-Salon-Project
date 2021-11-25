package com.example.atmasalon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atmasalon.databinding.ActivityContainerBinding;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;

public class ContainerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ActivityContainerBinding binding;
    private UserPreference userPref;
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        binding.bottomNavigation.setOnItemSelectedListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("sample_notification");

        userPref = new UserPreference(this);

        changeFragment(new FragmentDashboard());

        //TODO: kalo ada bug gambar, otomatis ini dibenarkan
        if(userPref.GetURLProfilePic() != null)
        {
            Bitmap img = null;
            img = Base64ToBitmap(userPref.GetURLProfilePic());
            if(img != null)
            {
                binding.profileImage.setImageBitmap(img);
            }
        }

        CheckLogin();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_beranda){
            changeFragment(new FragmentDashboard());
        }else if(item.getItemId() == R.id.menu_riwayat) {
            changeFragment(new FragmentRiwayat());
        }else if(item.getItemId() == R.id.menu_reservasi) {
            changeFragment(new FragmentReservation2());
        }else{
            changeFragment(new FragmentProfil());
        }

        return true;
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }

    private void CheckLogin()
    {
        if(!userPref.CheckLogin())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private Bitmap Base64ToBitmap(String encodedImage)
    {
        if(encodedImage.isEmpty() || encodedImage == null){return null;}
        try
        {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}