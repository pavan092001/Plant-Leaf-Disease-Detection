package com.example.plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<PlantModel> list;
    Context context;
    ProgressBar progressBar;

    public MyAdapter(List<PlantModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlantModel plantModel = list.get(position);
        holder.nameofPLANT.setText(plantModel.nameOfPlant);
        holder.nameOfDiseases.setText(plantModel.nameOfDiseases);
        String imgUri = null;
        imgUri=plantModel.img;
        Picasso.get().load(imgUri).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameofPLANT,nameOfDiseases;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofPLANT = itemView.findViewById(R.id.plant_name);
            nameOfDiseases = itemView.findViewById(R.id.nameofDis);
            img = itemView.findViewById(R.id.item_img);

        }
    }


//    public void onDataChanged()
//    {
//        if(progressBar!=null)
//        {
//            progressBar.setVisibility(View.GONE);
//        }
//
//    }
}
