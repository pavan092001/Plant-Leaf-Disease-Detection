package com.example.plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<PlantModel> list;
    Context context;

    SelectListner selectListner;

    public MyAdapter(List<PlantModel> list, Context context,SelectListner selectListner) {
        this.list = list;
        this.context = context;
        this.selectListner=selectListner;
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

        String finalImgUri = imgUri;
        holder.history_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectListner.onItemClicked(list.get(position), finalImgUri);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameofPLANT,nameOfDiseases;
        LinearLayout history_item;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofPLANT = itemView.findViewById(R.id.plant_name);
            nameOfDiseases = itemView.findViewById(R.id.nameofDis);
            img = itemView.findViewById(R.id.item_img);
            history_item= itemView.findViewById(R.id.main_card);

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
