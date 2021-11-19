package com.example.appquanlyhanghoa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Obj_adapter extends RecyclerView.Adapter<Obj_adapter.UserViewHolder> {

    private List<Obj> mListObj;
    public Obj_adapter(List<Obj> mListObj){
        this.mListObj = mListObj;
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
        holder.tvid.setText("ID: "+ obj.getID());
        holder.tvname.setText("Name: "+ obj.getName());
        holder.tvtype.setText("Type: "+ obj.getType());
        holder.tvquantity.setText("Quantity: "+ obj.getQuantity());
        holder.tvunit.setText("Unit: "+ obj.getUnit());
        holder.tvdscribe.setText("Description: "+ obj.getDscribe());
    }

    @Override
    public int getItemCount() {
        if(mListObj != null){
            return mListObj.size();
        }
        return 0;
    }
// tim kiem ten san pham
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String searchview = charSequence.toString();
//                if(searchview.isEmpty()){
//                    mListObj = mListOld;
//                }else{
//                    List<Obj> list = new ArrayList<>();
//                    for(Obj obj : mListOld){
//                        if (obj.getName().toLowerCase().contains(searchview.toLowerCase())){
//                                list.add(obj);
//                        }
//                    }
//                    mListObj = list;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mListObj;
//                return null;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults results) {
//                mListObj = (List<Obj>)results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public  class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvid, tvname,tvquantity, tvtype, tvunit, tvdscribe;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid_objadapter);
            tvquantity = itemView.findViewById(R.id.tvquantity_objadapter);
            tvname = itemView.findViewById(R.id.tvname_objadapter);
            tvtype = itemView.findViewById(R.id.tvtype_objadapter);
            tvunit = itemView.findViewById(R.id.tvunit_objadapter);
            tvdscribe = itemView.findViewById(R.id.tvdscribe_objadapter);

        }
    }
}
