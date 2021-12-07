package com.example.appquanlyhanghoa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Obj_adapter extends RecyclerView.Adapter<Obj_adapter.UserViewHolder> {
    private IClickListener mIClickListener;
    private List<Obj> mListObj;

    public interface IClickListener{
        void OnClickUpdateItem(Obj obj);
        void OnClickDeleteItem(Obj obj);
    }
    public Obj_adapter(List<Obj> mListObj,IClickListener listener){
        this.mListObj = mListObj;
        this.mIClickListener = listener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obj_adapter, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Obj obj = mListObj.get(position);
        if(obj == null){
            return;
        }
        holder.tvid.setText(""+ obj.getID());
        holder.tvname.setText("   Tên: "+ obj.getName());
        holder.tvtype.setText("   Loại: "+ obj.getType());
        holder.tvquantity.setText("   Số lượng : "+ obj.getQuantity());
        holder.tvunit.setText("   Đơn vị: "+ obj.getUnit());
        holder.tvdscribe.setText("   Mô tả: "+ obj.getDscribe());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.OnClickUpdateItem(obj);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.OnClickDeleteItem(obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListObj != null){
            return mListObj.size();
        }
        return 0;
    }

    public  class UserViewHolder extends RecyclerView.ViewHolder{
        private Button btnUpdate;
        private FloatingActionButton btnDelete;
        private TextView tvid, tvname,tvquantity, tvtype, tvunit, tvdscribe;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid_objadapter);
            tvquantity = itemView.findViewById(R.id.tvquantity_objadapter);
            tvname = itemView.findViewById(R.id.tvname_objadapter);
            tvtype = itemView.findViewById(R.id.tvtype_objadapter);
            tvunit = itemView.findViewById(R.id.tvunit_objadapter);
            tvdscribe = itemView.findViewById(R.id.tvdscribe_objadapter);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
