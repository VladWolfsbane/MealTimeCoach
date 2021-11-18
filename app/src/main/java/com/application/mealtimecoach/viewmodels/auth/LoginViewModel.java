package com.application.mealtimecoach.viewmodels.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.mealtimecoach.models.User;
import com.application.mealtimecoach.utils.AuthResource;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<AuthResource<User>> authResourceMutableLiveData = new MutableLiveData<>();

    private final FirebaseAuth firebaseAuth;

    @Inject
    public LoginViewModel(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public MutableLiveData<AuthResource<User>> getAuthResourceMutableLiveData() {
        return authResourceMutableLiveData;
    }

    public void login(String email, String password) {
        authResourceMutableLiveData.setValue(AuthResource.loading(null));
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {

                        User user = new User();
                        user.setEmail(authResult.getUser().getEmail());
                        user.setId(authResult.getUser().getUid());
                        authResourceMutableLiveData.setValue(AuthResource.authenticated(user));

                    }

                })
                .addOnFailureListener(e -> {
                    if (e.getMessage() != null)
                        authResourceMutableLiveData.setValue(AuthResource.error(e.getMessage(), null));
                });
    }
}
