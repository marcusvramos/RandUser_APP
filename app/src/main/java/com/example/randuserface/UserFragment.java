package com.example.randuserface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

import com.example.randuserface.api.RandomUserApi;
import com.example.randuserface.models.UserResponse;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFragment extends Fragment {

    private TextView userName;
    private ImageView userImage;
    private AutoCompleteTextView genderSpinner, nationalitySpinner;
    private MaterialButton btnFetchUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userName = view.findViewById(R.id.user_name);
        userImage = view.findViewById(R.id.user_image);
        genderSpinner = view.findViewById(R.id.spinner_gender);
        nationalitySpinner = view.findViewById(R.id.spinner_nationality);
        btnFetchUser = view.findViewById(R.id.btn_fetch_user);

        String[] genders = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, genders);
        genderSpinner.setAdapter(genderAdapter);

        String[] nationalities = getResources().getStringArray(R.array.nationality_array);
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, nationalities);
        nationalitySpinner.setAdapter(nationalityAdapter);

        btnFetchUser.setOnClickListener(v -> fetchUser());

        return view;
    }

    private void fetchUser() {
        String gender = genderSpinner.getText().toString().toLowerCase();
        String nationality = nationalitySpinner.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RandomUserApi api = retrofit.create(RandomUserApi.class);
        Call<UserResponse> call = api.getUser(gender, nationality);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().results.isEmpty()) {
                    UserResponse.User user = response.body().results.get(0);
                    userName.setText(user.name.first + " " + user.name.last);
                    Picasso.get().load(user.picture.large).into(userImage);

                    MainActivity.selectedUser = user;

                    ((MainActivity) getActivity()).enableLocationFeature();
                } else {
                    Toast.makeText(getContext(), "Nenhum usuário encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Falha ao buscar usuário.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
