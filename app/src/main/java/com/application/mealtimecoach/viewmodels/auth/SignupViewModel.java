package com.application.mealtimecoach.viewmodels.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.mealtimecoach.models.SignupModel;
import com.application.mealtimecoach.models.User;
import com.application.mealtimecoach.utils.AuthResource;
import com.application.mealtimecoach.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import javax.inject.Inject;

public class SignupViewModel extends ViewModel {

    private final MutableLiveData<AuthResource<User>> authResourceMutableLiveData = new MutableLiveData<>();

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseReference;

    @Inject
    public SignupViewModel(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
    }

    public MutableLiveData<AuthResource<User>> getAuthResourceMutableLiveData() {
        return authResourceMutableLiveData;
    }

    public void signup(String email, String password, SignupModel signupModel) {
        authResourceMutableLiveData.setValue(AuthResource.loading(null));
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Constants.EMAIL, email);
                        hashMap.put(Constants.NAME, signupModel.getName());
                        hashMap.put(Constants.DIET, signupModel.getDiet());
                        hashMap.put(Constants.AGE_GROUP, signupModel.getAgeGroup());
                        hashMap.put(Constants.GOAL, signupModel.getGoal());

                        databaseReference
                                .child(Constants.USERS)
                                .child(authResult.getUser().getUid())
                                .setValue(hashMap)
                                .addOnSuccessListener(unused -> {
                                    User user = new User();
                                    user.setEmail(authResult.getUser().getEmail());
                                    user.setId(authResult.getUser().getUid());
                                    authResourceMutableLiveData.setValue(AuthResource.authenticated(user));
                                })
                                .addOnFailureListener(e -> {
                                    if (e.getMessage() != null)
                                        authResourceMutableLiveData.setValue(AuthResource.error(e.getMessage(), null));
                                });
                    }

                })
                .addOnFailureListener(e -> {
                    if (e.getMessage() != null)
                        authResourceMutableLiveData.setValue(AuthResource.error(e.getMessage(), null));
                });
    }
}
