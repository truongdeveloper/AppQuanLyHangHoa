package com.example.appquanlyhanghoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private EditText edtname, edtID, edttype,edtquantity,edtunit,edtdscribe;
    private Button btokaddlist;
    private FloatingActionButton btaddadapter;
    private RecyclerView rcvObj;
    private Obj_adapter mObjAdapter;
    private List<Obj> mlistObj;
    private SearchView search;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Anhxa();
        dieukhienNut();
        getdata("");
        int mamathang = getIntent().getIntExtra("maQr",0);
        if(mamathang!=0){
            FirebaseDatabase database   = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("list_object");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mlistObj.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Obj obj = dataSnapshot.getValue(Obj.class);
                        if(obj != null){
                            if( obj.getID() == mamathang){
                                mlistObj.add(obj);
                            }
                        }
                    }
                    if(mlistObj.isEmpty()){
                        Toast.makeText(ListActivity.this, "không có mặt hàng nào có mã này", Toast.LENGTH_SHORT).show();
                        clickAdd(Gravity.CENTER);
                    }
                    mObjAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ListActivity.this, "LẤY DỮ LIỆU THẤT BẠI", Toast.LENGTH_SHORT).show();
                }
            });
        }
        BottomNavigationView mNavView = findViewById(R.id.botom_nav);

        mNavView.setSelectedItemId(R.id.list);

        mNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.list:
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), ScanKit.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.info:
                        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        //TÌM KIẾM
//        search = findViewById(R.id.seachView);
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                getdata(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                getdata(newText);
//                return false;
//            }
//        });

    }
    private void dieukhienNut() {
        btaddadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAdd(Gravity.CENTER);
            }
        });
    }

    private void clickAdd(int gravity){
        //Toast.makeText(ListActivity.this, gravity, Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addlist);

        Window window = dialog.getWindow();
        if(window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = gravity;
        window.setAttributes(windowAtributes);

        if(Gravity.CENTER == gravity)
            dialog.setCancelable(true);
        else
            dialog.setCancelable(false);

        edtname = dialog.findViewById(R.id.edittextname);
        edtID = dialog.findViewById(R.id.edittextID);
        edttype = dialog.findViewById(R.id.edittexttype);
        edtunit = dialog.findViewById(R.id.edittextunit);
        edtquantity = dialog.findViewById(R.id.edittextquantity);
        edtdscribe = dialog.findViewById(R.id.edittextdscribe);
        btokaddlist = dialog.findViewById(R.id.buttonokaddlist);
        Button btthoat = dialog.findViewById(R.id.buttonthoat);

        btokaddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id;
                int quantity;
                String name = edtname.getText().toString().trim();
                String type = edttype.getText().toString().trim();
                String unit = edtunit.getText().toString().trim();
                String dscribe= edtdscribe.getText().toString().trim();

                if(edtID.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long dien id", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    id = Integer.parseInt(edtID.getText().toString().trim());
                }

                if(edtquantity.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long dien so luong", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    quantity = Integer.parseInt(edtquantity.getText().toString().trim());
                }

                if(name.equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long dien ten", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(type.equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long nhap loai", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(unit.equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long nhap don vi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dscribe.equalsIgnoreCase("")){
                    Toast.makeText(ListActivity.this, "Vui long nhap mo ta", Toast.LENGTH_SHORT).show();
                    return;
                }
        // cau 1 check trung
//                for(Obj value: mlistObj) {
//                    if (name.equalsIgnoreCase(value.getName())) {
//                        Toast.makeText(ListActivity.this, "TRÙNG TÊN", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }



               int dem =0;
            // cau 2 check trung cong so luong
//            for(Obj value: mlistObj){
//                if(name.equalsIgnoreCase(value.getName())){
//                    dem = 1;
//                    // thong bao
//                    new AlertDialog.Builder(ListActivity.this).setTitle(getString(R.string.app_name)).setMessage("SẢN PHẨM ĐÃ CÓ bạn có muốn cộng thêm vào sản phẩm?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            int soluong = quantity;
//                            soluong += value.getQuantity();
//                            Obj obj = new Obj(value.getID(),name,soluong,type,unit,dscribe);
//                            clickPush(obj);
//                            dialog.cancel();
//                        }
//                    })
//                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(ListActivity.this, "Vui lòng đổi tên", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                            }).show();
//                }
//            }

                if(dem==0){
                    Obj obj = new Obj(id,name,quantity,type,unit,dscribe);
                    clickPush(obj);
                    dialog.cancel();
                }
            }
        });
        btthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void clickPush(Obj obj) {
        FirebaseDatabase database   = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_object");

        String pathObject = String.valueOf(obj.getID());
        myRef.child(pathObject).setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ListActivity.this, "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getdata(String keyword){
        FirebaseDatabase database   = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_object");

        //SẮP XẾP
//        Button sapxep = findViewById(R.id.sapxep);
//        sapxep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Query query = myRef.orderByChild("name"); // thay doi cach sap xep
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        mlistObj.clear();
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            Obj obj = dataSnapshot.getValue(Obj.class);
//                            if(obj != null){
//                                //String s = String.valueOf(obj.getID());
//                                if( obj.getName().contains(keyword)){
//                                    mlistObj.add(obj);
//                                }
//                            }
//                        }
//                        mObjAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ListActivity.this, "LẤY DỮ LIỆU THẤT BẠI", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        Button sxthg = findViewById(R.id.sxthg);
//        sxthg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Query query = myRef.orderByChild("id"); // thay doi cach sap xep
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        mlistObj.clear();
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            Obj obj = dataSnapshot.getValue(Obj.class);
//                            if(obj != null){
//                                //String s = String.valueOf(obj.getID());
//                                if( obj.getName().contains(keyword)){
//                                    mlistObj.add(obj);
//                                }
//                            }
//                        }
//                        mObjAdapter.notifyDataSetChanged();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ListActivity.this, "LẤY DỮ LIỆU THẤT BẠI", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlistObj.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Obj obj = dataSnapshot.getValue(Obj.class);
                    if(obj != null){
                        String s = String.valueOf(obj.getName()); // THAY ĐỔI TÌM KIẾM
                        if( s.contains(keyword)){
                            mlistObj.add(obj);
                        }
                    }
                }
                mObjAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, "LẤY DỮ LIỆU THẤT BẠI", Toast.LENGTH_SHORT).show();
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Obj obj = snapshot.getValue(Obj.class);
                if(obj == null ){
                    mlistObj.add(obj);
                    mObjAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Obj obj = snapshot.getValue(Obj.class);
                if(obj == null || mlistObj == null || mlistObj.isEmpty()){
                    return;
                }
                for (int i=0; i<mlistObj.size(); i++){
                    if(obj.getID()==mlistObj.get(i).getID()){
                        mlistObj.set(i,obj);
                        break;
                    }
                }
                mObjAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Obj obj = snapshot.getValue(Obj.class);
                if(obj == null || mlistObj == null || mlistObj.isEmpty()){
                    return;
                }
                for (int i=0; i<mlistObj.size(); i++){
                    if(obj.getID()==mlistObj.get(i).getID()){
                        mlistObj.remove(mlistObj.get(i));
                        break;
                    }
                }
                mObjAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Anhxa() {

//         tìm sl theo khoang
//        EditText edtu = findViewById(R.id.edtu);
//        EditText edden = findViewById(R.id.edden);
//        Button timsl = findViewById(R.id.timsl);
//
//        timsl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayList<Obj> listsl = new ArrayList<>();
//                listsl.clear();
//                for(Obj value: mlistObj){
//                    if(edtu.getText().toString().equalsIgnoreCase("") || edden.getText().toString().equalsIgnoreCase("")){
//                        listsl.add(value);
//                    }else{
//                        int tua = Integer.parseInt(edtu.getText().toString());
//                        int denb = Integer.parseInt(edden.getText().toString());
//                        if(tua<=value.getQuantity() && denb>=value.getQuantity()){
//                            listsl.add(value);
//                        }
//                    }
//                }
//                mObjAdapter = new Obj_adapter(listsl, new Obj_adapter.IClickListener() {
//                    @Override
//                    public void OnClickUpdateItem(Obj obj) {
//                        openDialogUpdateItem(obj);
//                    }
//
//                    @Override
//                    public void OnClickDeleteItem(Obj obj) {
//                        onClickDeleteData(obj);
//                    }
//                });
//                rcvObj.setAdapter(mObjAdapter);
//            }
//        });

        btaddadapter = findViewById(R.id.buttonadd);
        //Nút tìm kiếm

        rcvObj = findViewById(R.id.rcvObj);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvObj.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvObj.addItemDecoration(dividerItemDecoration);

        mlistObj = new ArrayList<>();
        mObjAdapter = new Obj_adapter(mlistObj, new Obj_adapter.IClickListener() {
            @Override
            public void OnClickUpdateItem(Obj obj) {
                openDialogUpdateItem(obj);
            }

            @Override
            public void OnClickDeleteItem(Obj obj) {
                onClickDeleteData(obj);
            }
        });
        rcvObj.setAdapter(mObjAdapter);
    }

    private void openDialogUpdateItem (Obj obj){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diaglog_updatelist);

        Window window = dialog.getWindow();
        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        window.setAttributes(windowAtributes);
        dialog.setCancelable(false);

        EditText edtUpdateName = dialog.findViewById(R.id.edt_name);
        EditText edtUpdateType= dialog.findViewById(R.id.edt_type);
        EditText edtUpdateQuantity = dialog.findViewById(R.id.edt_quantity);
        EditText edtUpdateUnit = dialog.findViewById(R.id.edt_unit);
        EditText edtUpdateDscribe = dialog.findViewById(R.id.edt_dscribe);
        Button btnCancel = dialog.findViewById(R.id.btn_Cancel);
        Button btnUp = dialog.findViewById(R.id.btn_Update);

        edtUpdateName.setText(obj.getName());
        edtUpdateType.setText(obj.getType());
        edtUpdateQuantity.setText(""+obj.getQuantity());
        edtUpdateUnit.setText(obj.getUnit());
        edtUpdateDscribe.setText(obj.getDscribe());


       btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialog.dismiss();
           }
       });
        btnUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FirebaseDatabase database   = FirebaseDatabase.getInstance();
               DatabaseReference myRef = database.getReference("list_object");
               String newName = edtUpdateName.getText().toString().trim();
               obj.setName(newName);
               String newType= edtUpdateType.getText().toString().trim();
               obj.setType(newType);
               int newQuantity = Integer.parseInt(edtUpdateQuantity.getText().toString().trim());
               obj.setQuantity(newQuantity);
               String newUnit = edtUpdateUnit.getText().toString().trim();
               obj.setUnit(newUnit);
               String newDscribe= edtUpdateDscribe.getText().toString().trim();
               obj.setDscribe(newDscribe);
               myRef.child(String.valueOf(obj.getID())).updateChildren(obj.toMap(), new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                       Toast.makeText(ListActivity.this, "Chỉnh sửa thành công ", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
               });
           }
       });

       dialog.show();
    }
    private void onClickDeleteData(Obj obj){
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name)).setMessage("Bạn có chắc muốn xóa bản ghi này?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase database   = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("list_object");
                myRef.child(String.valueOf(obj.getID())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ListActivity.this, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })
        .setNegativeButton("Cancel",null).show();
    }

}