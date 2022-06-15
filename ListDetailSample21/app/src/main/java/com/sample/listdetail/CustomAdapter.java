package com.sample.listdetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<UserData> localUserDataSet;
    private ItemClickHandler itemClickHandler;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView txtNum;
        private final ImageView imgTest;
        private final TextView txtTitle;
        private final TextView txtName;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            txtNum = (TextView)v.findViewById(R.id.item_txt_num);
            imgTest = (ImageView)v.findViewById(R.id.item_img_test);
            txtTitle = (TextView)v.findViewById(R.id.item_txt_title);
            txtName = (TextView)v.findViewById(R.id.item_txt_name);

        }

        public View getRootView() {
            return rootView;
        }

        public TextView getNumTextView() {
            return txtNum;

        }

        public ImageView getTestImageView() {
            return imgTest;

        }

        public TextView getTitleTextView() {
            return txtTitle;

        }

        public TextView getNameTextView() {
            return txtName;

        }

    }

    public CustomAdapter(List<UserData> userDataSet, ItemClickHandler handler) {
        localUserDataSet = userDataSet;
        itemClickHandler = handler;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_listitem, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData currentData = localUserDataSet.get(position);
        holder.getNumTextView().setText(String.valueOf(position));
        int colorValue = Integer.parseInt(currentData.color);
        holder.getTestImageView().setBackgroundColor(colorValue);
        holder.getTitleTextView().setText(currentData.title);
        holder.getNameTextView().setText(currentData.name);

        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickHandler.itemClickEvent(position, "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return localUserDataSet != null ? localUserDataSet.size() : 0;

    }

}
