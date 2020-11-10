package abhinay.firstapp.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ed1,ed2,ed3;
    Button b;
    ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ed1 = findViewById(R.id.prevpassword);
        ed2 = findViewById(R.id.newpassword);
        loadingBar = new ProgressDialog(this);
        ed3 = findViewById(R.id.confnewpassword);
        b = findViewById(R.id.submit);
        b.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (ed1.getText().toString().isEmpty() == false && ed2.getText().toString().isEmpty() == false && ed3.getText().toString().isEmpty() == false) {
            loadingBar.setTitle("Change Password");
            loadingBar.setMessage("Please wait while we are changing password for you");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            final DatabaseReference Rootref;
            Rootref = FirebaseDatabase.getInstance().getReference();
            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (ed1.getText().toString().isEmpty() == false && ed2.getText().toString().isEmpty() == false && ed3.getText().toString().isEmpty() == false) {
                        if (ed1.getText().toString().isEmpty() == false && snapshot.child("Users").child(Prevalent.currentOnlineUser.getPhone()).exists()) {
                            if (ed1.getText().toString().matches(Prevalent.currentOnlineUser.getPassword())) {
                                String newp = ed2.getText().toString();
                                String new2 = ed3.getText().toString();
                                HashMap<String, Object> h = new HashMap<>();
                                h.put("password", newp);
                                if (newp.matches(new2)) {
                                    Rootref.child("Users").child(Prevalent.currentOnlineUser.getPhone()).updateChildren(h).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if ((task.isSuccessful())) {
                                                Toast.makeText(ChangePasswordActivity.this, "Your Password has been changed successfully!", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(ChangePasswordActivity.this, HomeNavigationActivity.class);
                                                startActivity(i);
                                                finish();
                                                loadingBar.dismiss();
                                            } else {
                                                Toast.makeText(ChangePasswordActivity.this, "Error please retry", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Passwords not matched", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Wrong previous password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Enter all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}