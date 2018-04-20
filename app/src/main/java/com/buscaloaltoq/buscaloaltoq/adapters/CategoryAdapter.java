package com.buscaloaltoq.buscaloaltoq.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.Categories;

import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategory> implements View.OnClickListener {

    ArrayList<Categories> categoriasArrayList;
    private View.OnClickListener listener;

    public CategoryAdapter(ArrayList<Categories> categoriasArrayList) {
        this.categoriasArrayList = categoriasArrayList;
    }

    @Override
    public CategoryAdapter.ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return  new ViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolderCategory holder, int position) {
        holder.txtItemNombreC.setText(categoriasArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriasArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        TextView txtItemNombreC;
        public ViewHolderCategory(View itemView) {
            super(itemView);
            txtItemNombreC = (TextView) itemView.findViewById(R.id.txtItemNombreC);
        }
    }
}
