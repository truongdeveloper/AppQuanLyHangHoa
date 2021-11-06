package com.example.appquanlyhanghoa.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appquanlyhanghoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    View mView;
    private String email;

    private EditText edtName, edtNumber;
    private TextView txtvMail,txtvName,txtvNumber;
    private Button btSave;
    private static final String USERS = "users";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_profile,container,false);
        AnhXa();
        initListener();
        read();
        setUser();
        return mView;
    }




    private void AnhXa() {
        edtName = mView.findViewById(R.id.edittextName);
        edtNumber = mView.findViewById(R.id.edittextNumber);
        txtvMail = mView.findViewById(R.id.textviewEmail);
        txtvName = mView.findViewById(R.id.textviewName);
        txtvNumber = mView.findViewById(R.id.textviewNumber);
        btSave = mView.findViewById(R.id.buttonSave);
    }

    private void initListener() {
        //Lưu thông tin
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference strName = database.getReference("name");
                strName.setValue(edtName.getText().toString().trim());

                DatabaseReference strNumber = database.getReference("number");
                strNumber.setValue(edtNumber.getText().toString().trim());
            }
        });
    }
    private void read() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference(USERS);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child(user.getEmail()).getValue().equals(user.getEmail())){
                        txtvName.setText(ds.child("name").getValue(String.class));
                        txtvNumber.setText(ds.child("number").getValue(String.class));
                    }
                }
                String value = dataSnapshot.getValue(String.class);
                txtvName.setText(value);

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

   private void setUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        txtvMail.setText(user.getEmail());
        //txtvName.setText(user.getDisplayName());
        txtvNumber.setText(user.getPhoneNumber());
    }
}
