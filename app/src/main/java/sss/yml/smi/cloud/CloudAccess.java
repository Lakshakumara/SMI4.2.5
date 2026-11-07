package sss.yml.smi.cloud;

import androidx.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import sss.yml.smi.Home.App;
import sss.yml.smi.room.Child;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;

public class CloudAccess {

    public enum TO {
        PARENT, MOH, AUTHOR
    }
    public static void upload(Child child){
        if(child == null) return;
        FirebaseFirestore.getInstance().collection("Data")
                .document("child").set(child)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("xyz", "Uploaded");
                        //msg.setText("Uploaded");
                        //hideProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // msg.setText("Error");hideProgressDialog();
                    }
                });
    }
    public static void upload(ChildWpData childWpData){
        if(childWpData == null) return;
        FirebaseFirestore.getInstance().collection("Data")
                .document("child").set(childWpData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("xyz", "Uploaded ");
                        //msg.setText("Uploaded");
                        //hideProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // msg.setText("Error");hideProgressDialog();
                        Log.e("xyz", "Sorry "+e);
                    }
                });
    }
    public static void upload(ChildData childData){
        if(childData == null) return;
        String uid = PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext()).getString("uid", "");
        if(uid.isEmpty()) return;
        CompleteChild cc = new CompleteChild(childData.child, childData.childWpData);
        FirebaseFirestore.getInstance().collection("Data")
                .document(uid).set(cc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("xyz", "Uploaded ");
                        //msg.setText("Uploaded");
                        //hideProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // msg.setText("Error");hideProgressDialog();
                        Log.e("xyz", "Sorry "+e);
                    }
                });
    }

    public static  void uploadTo(final ChildData childData, TO to){
        if(to == TO.PARENT){
            Log.e("xyz", "Reading: ");
            FirebaseFirestore.getInstance()
                    .collection("users").whereEqualTo("ptp", childData.child.getTp())
            .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    CompleteChild cc = new CompleteChild(childData.child, childData.childWpData);

                                   /* FirebaseFirestore.getInstance()
                                            .collection("share/"+document.getId()+"/"+String.valueOf(childData.child.getId()))
                                            .set(cc)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.e("xyz", "Uploaded ");
                                                    //msg.setText("Uploaded");
                                                    //hideProgressDialog();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // msg.setText("Error");hideProgressDialog();
                                                    Log.e("xyz", "Sorry "+e);
                                                }
                                            });*/

                                    FirebaseFirestore.getInstance()
                                            .collection("share")
                                            .document(document.getId())
                                            .collection("BMI")
                                            .document(String.valueOf(childData.child.getId())).set(cc)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.e("xyz", "Uploaded ");
                                                    //msg.setText("Uploaded");
                                                    //hideProgressDialog();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // msg.setText("Error");hideProgressDialog();
                                                    Log.e("xyz", "Sorry "+e);
                                                }
                                            });
                                }
                            } else {
                                Log.e("xyz", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }

}
