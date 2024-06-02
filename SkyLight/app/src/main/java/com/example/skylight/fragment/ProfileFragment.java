package com.example.skylight.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skylight.MainActivity;
import com.example.skylight.R;

public class ProfileFragment extends Fragment {

    TextView tvUsername, tvPassword;
    Button btnLogout;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);

        btnLogout = view.findViewById(R.id.btnLogout);
        tvUsername = view.findViewById(R.id.tv_username);
        tvPassword = view.findViewById(R.id.tv_password);
        imageView = view.findViewById(R.id.iv_userProfil);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Intent.ACTION_PICK);
                open.setType("image/*");
                launcherIntentGallery.launch(open);
            }
        });

        String username = sharedPreferences.getString("Username", "");
        String password = sharedPreferences.getString("Password", "");
        tvUsername.setText("Username: " + username);
        tvPassword.setText("Password: " + password);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv_search = view.findViewById(R.id.menu_explore);
        ImageView iv_simpan = view.findViewById(R.id.menu_simpan);
        ImageView iv_home = view.findViewById(R.id.menu_home);

        iv_search.setOnClickListener(v -> loadFragment(new SearchFragment()));
        iv_simpan.setOnClickListener(v -> loadFragment(new SimpanFragment()));
        iv_home.setOnClickListener(v -> loadFragment(new HomeFragment()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    ActivityResultLauncher<Intent> launcherIntentGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        imageUri = data.getData();
                        imageView.setImageURI(imageUri);
                    }
                }
            });
}
