package com.application.mealtimecoach.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.mealtimecoach.models.Recipe;
import com.application.mealtimecoach.utils.Constants;
import com.application.mealtimecoach.utils.Resource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Resource<ArrayList<Recipe>>> resourceMutableLiveData = new MutableLiveData<>();

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseReference;

    @Inject
    public MainViewModel(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
        fetchRecipes();
    }

    public MutableLiveData<Resource<ArrayList<Recipe>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }

    private void fetchRecipes() {
        if (firebaseAuth.getCurrentUser() != null) {
            resourceMutableLiveData.setValue(Resource.loading(null));

            databaseReference
                    .child(Constants.USERS)
                    .child(firebaseAuth.getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(Constants.DIET)) {
                                String diet = snapshot.child(Constants.DIET).getValue(String.class);

                                if (diet != null) {
                                    databaseReference
                                            .child(Constants.RECIPES)
                                            .child(diet)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    ArrayList<Recipe> arrayList = new ArrayList<>();

                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        if (dataSnapshot.hasChild(Constants.TITLE) &&
                                                                dataSnapshot.hasChild(Constants.DESCRIPTION) &&
                                                                dataSnapshot.hasChild(Constants.IMAGE_URL)
                                                        ) {
                                                            String title = dataSnapshot.child(Constants.TITLE).getValue(String.class);
                                                            String description = dataSnapshot.child(Constants.DESCRIPTION).getValue(String.class);
                                                            String imageUrl = dataSnapshot.child(Constants.IMAGE_URL).getValue(String.class);

                                                            Recipe exercise = new Recipe();
                                                            exercise.setTitle(title);
                                                            exercise.setDescription(description);
                                                            exercise.setImageUrl(imageUrl);

                                                            arrayList.add(exercise);
                                                        }
                                                    }

                                                    resourceMutableLiveData.setValue(Resource.success(arrayList));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    resourceMutableLiveData.setValue(Resource.error(error.getMessage(), null));

                                                }
                                            });
                                }

                            } else {
                                resourceMutableLiveData.setValue(Resource.error("Database does not have the diet child", null));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            resourceMutableLiveData.setValue(Resource.error(error.getMessage(), null));
                        }
                    });


        }
    }
}
