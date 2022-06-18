package com.example.drrbnicompany.ViewModels;

import static com.example.drrbnicompany.Constant.ACTIVATED;
import static com.example.drrbnicompany.Constant.ADDRESS;
import static com.example.drrbnicompany.Constant.ADS_DESCRIPTION;
import static com.example.drrbnicompany.Constant.ADS_ID;
import static com.example.drrbnicompany.Constant.ADS_REQUIREMENTS;
import static com.example.drrbnicompany.Constant.ADS_TITLE;
import static com.example.drrbnicompany.Constant.COLLECTION_ADS;
import static com.example.drrbnicompany.Constant.COLLECTION_JOBS;
import static com.example.drrbnicompany.Constant.COLLECTION_MAJORS;
import static com.example.drrbnicompany.Constant.COLLECTION_STUDENT_PROFILES;
import static com.example.drrbnicompany.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbnicompany.Constant.EMAIL;
import static com.example.drrbnicompany.Constant.GOVERNORATE;
import static com.example.drrbnicompany.Constant.IMG;
import static com.example.drrbnicompany.Constant.CATEGORY;
import static com.example.drrbnicompany.Constant.MAJOR;
import static com.example.drrbnicompany.Constant.NAME;
import static com.example.drrbnicompany.Constant.COMPANY_TYPE;
import static com.example.drrbnicompany.Constant.TYPE_USER;
import static com.example.drrbnicompany.Constant.UID;
import static com.example.drrbnicompany.Constant.USER_ID;
import static com.example.drrbnicompany.Constant.VERIFIED;
import static com.example.drrbnicompany.Constant.WHATSAPP;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Major;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.Models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Repository {

    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private MutableLiveData<Company> profileInfo;
    private MutableLiveData<List<Ads>> adsData;

    public Repository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        profileInfo = new MutableLiveData<>();
        adsData = new MutableLiveData<>();
    }


    public void signUp(String email, String password,
                       MyListener<FirebaseUser> isSuccessful, MyListener<String> isFailure) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isSuccessful.onValuePosted(firebaseAuth.getCurrentUser());
                        } else {
                            isFailure.onValuePosted(task.getException().toString());
                        }
                    }
                });
    }

    public void storeSignUpData(FirebaseUser firebaseUser, String name, String category
            , MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(UID, firebaseUser.getUid());
        data.put(NAME, name);
        data.put(EMAIL, firebaseUser.getEmail());
        data.put(TYPE_USER, COMPANY_TYPE);
        data.put(ACTIVATED, false);
        data.put(VERIFIED, false);
        data.put(CATEGORY, category);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).document(firebaseUser.getUid())
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);
                else
                    Log.d("kkkkk" , task.getException().toString());
            }
        });
    }

    public void storeAddressData(String governorate, String address, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(GOVERNORATE, governorate);
        data.put(ADDRESS, address);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).
                document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);

                    }
                });
    }

    public void storeContactData(String whatsapp, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(WHATSAPP, whatsapp);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                    }
                });
    }

    public void storeImg(Uri image, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseStorage.getReference().child("Images/").putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap<String, Object> data = new HashMap<>();
                        data.put(IMG, uri.toString());

                        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                                .document(firebaseAuth.getCurrentUser().getUid())
                                .update(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            isSuccessful.onValuePosted(true);
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void signIn(String email, String password, MyListener<Boolean> isSuccessful, MyListener<String> isFailure) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);
                else
                    isFailure.onValuePosted(task.getException().toString());
            }
        });
    }

    public void checkSignInData(String email, MyListener<Company> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .whereEqualTo(EMAIL, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company company = document.toObject(Company.class);
                                isSuccessful.onValuePosted(company);
                            }
                        } else {
                            isFailure.onValuePosted(true);
                        }
                    }
                });
    }

    public void resetPassword(String email, MyListener<Boolean> isSuccessful, MyListener<String> isFailure) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    isSuccessful.onValuePosted(true);
                } else {
                    isFailure.onValuePosted(task.getException().getMessage());
                }

            }
        });
    }

    public void requestProfileInfo(String uid) {
        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .whereEqualTo(UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company company = document.toObject(Company.class);
                                profileInfo.postValue(company);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<Company> getProfileInfo() {
        return profileInfo;
    }

    public void storeAdsData(String uid, Uri image, String adsName, String major, String adsRequirements,
                             String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        firebaseStorage.getReference().child("AdsImages/").putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        DocumentReference docRef = firebaseFirestore.collection(COLLECTION_ADS).document();
                        Ads ads = new Ads(docRef.getId(), uid, adsName, major, adsRequirements, adsDescription
                                , uri.toString() , new Timestamp(new Date()));
                        docRef.set(ads);

                        isSuccessful.onValuePosted(true);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void requestGetAds(String uid) {
        firebaseFirestore.collection(COLLECTION_ADS)
                .whereEqualTo(USER_ID, uid)
                .orderBy("timestamp" , Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    return;
                }

                List<Ads> adsList = new ArrayList<>();
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
                    Ads ads = doc.toObject(Ads.class);
                    adsList.add(ads);
                    adsData.postValue(adsList);
                }

            }
        });
    }

    public MutableLiveData<List<Ads>> getAdsData() {
        return adsData;
    }

    public void getAdsById(String adsId ,MyListener<Ads> isSuccessful
            , MyListener<Boolean> isFailure){
        firebaseFirestore.collection(COLLECTION_ADS)
                .document(adsId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                           DocumentSnapshot document = task.getResult();
                            Ads ads = document.toObject(Ads.class);
                            isSuccessful.onValuePosted(ads);
                        }else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void getMajors(MyListener<List<Major>> isSuccessful) {
        firebaseFirestore.collection(COLLECTION_MAJORS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Major> majorList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Major major = document.toObject(Major.class);
                                majorList.add(major);
                            }
                            isSuccessful.onValuePosted(majorList);
                        }
                    }
                });
    }

    public void editProfileData(Uri image, String companyName, String email, String category
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        firebaseStorage.getReference().child("Images/").putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put(NAME, companyName);
                        data.put(EMAIL, email);
                        data.put(IMG, uri.toString());
                        data.put(CATEGORY, category);


                        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                                            .document(firebaseUser.getUid())
                                            .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                                isSuccessful.onValuePosted(true);
                                        }
                                    });

                                }else
                                    Log.d("ddddddd" , task.getException().toString());
                            }
                        });

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void editProfileDataWithoutImage(String companyName, String email, String category
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(NAME, companyName);
        data.put(EMAIL, email);
        data.put(CATEGORY, category);

        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("ddddddddd" , "sucss");
                    firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                            .document(firebaseUser.getUid())
                            .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                isSuccessful.onValuePosted(true);
                            else
                                isFailure.onValuePosted(true);
                        }
                    });

                }else
                    Log.d("ddddddddd" , task.getException().toString());

            }
        });
    }

    public void editContactInformation(String whatsapp, MyListener<Boolean> isSuccessful
            , MyListener<Boolean> isFailure) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(WHATSAPP, whatsapp);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseUser.getUid())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                        else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void editAddress(String governorate, String address
            , MyListener<Boolean> isSuccessful , MyListener<Boolean> isFailure) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(GOVERNORATE, governorate);
        data.put(ADDRESS, address);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).
                document(firebaseUser.getUid())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                        else
                            isFailure.onValuePosted(true);
                    }
                });
    }


    public void changePassword(String currentPassword,String newPassword
            ,MyListener<Boolean > isSuccessful , MyListener<String> isFailure){

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail() ,currentPassword);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

               if (task.isSuccessful()){

                   firebaseUser.updatePassword(newPassword)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   isSuccessful.onValuePosted(true);
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           isFailure.onValuePosted(e.toString());
                       }
                   });
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }

    public void getCompanyImgAndName(String uid , MyListener<Company> company){
        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .whereEqualTo(UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company com = document.toObject(Company.class);
                                company.onValuePosted(com);
                            }
                        }
                    }
                });
    }

    public void editAdsData(String adsId, Uri image, String adsTitle, String major, String adsRequirements,
                             String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseStorage.getReference().child("AdsImages/").putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap<String, Object> ads = new HashMap<>();
                        ads.put(ADS_ID, adsId);
                        ads.put(IMG, uri.toString());
                        ads.put(MAJOR, major);
                        ads.put(ADS_TITLE, adsTitle);
                        ads.put(ADS_REQUIREMENTS, adsRequirements);
                        ads.put(ADS_DESCRIPTION, adsDescription);


                        firebaseFirestore.collection(COLLECTION_ADS)
                                .document(adsId)
                                .update(ads).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    isSuccessful.onValuePosted(true);
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void editAdsDataWithoutImage(String adsId, String adsTitle, String major, String adsRequirements,
                            String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        HashMap<String, Object> ads = new HashMap<>();
        ads.put(ADS_ID, adsId);
        ads.put(MAJOR, major);
        ads.put(ADS_TITLE, adsTitle);
        ads.put(ADS_REQUIREMENTS, adsRequirements);
        ads.put(ADS_DESCRIPTION, adsDescription);


        firebaseFirestore.collection(COLLECTION_ADS)
                .document(adsId)
                .update(ads).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    isSuccessful.onValuePosted(true);
                }else
                    isFailure.onValuePosted(true);
            }
        });

    }

    public void deleteAds(String adsId, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseFirestore.collection(COLLECTION_ADS)
                .document(adsId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            isSuccessful.onValuePosted(true);
                        }else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void getAllStudentByMajor(String majorName , MyListener<List<Student>> isSuccessful
            , MyListener<Boolean> isFailure){
        firebaseFirestore.collection(COLLECTION_STUDENT_PROFILES)
                .whereEqualTo(MAJOR , majorName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Student> studentList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Student student = document.toObject(Student.class);
                        studentList.add(student);
                    }
                    isSuccessful.onValuePosted(studentList);
                }else{
                    isFailure.onValuePosted(true);
                }

            }
        });
    }

    public void getStudentById(String studentId ,MyListener<Student> isSuccessful
            , MyListener<Boolean> isFailure){
        firebaseFirestore.collection(COLLECTION_STUDENT_PROFILES)
                .document(studentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Student student = document.toObject(Student.class);
                            isSuccessful.onValuePosted(student);
                        }else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void getStudentJobsById(String uid , MyListener<List<Job>> isSuccessful
            , MyListener<Boolean> isFailure){

        firebaseFirestore.collection(COLLECTION_JOBS)
                .whereEqualTo(USER_ID , uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            List<Job> jobList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Job job = document.toObject(Job.class);
                                jobList.add(job);
                            }
                            isSuccessful.onValuePosted(jobList);
                        }else
                            isFailure.onValuePosted(true);
                    }
                });
    }
}
