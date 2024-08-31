package com.example.easy_saletask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easy_saletask.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList<UserViewModel> usersList;
    private final MainActivity mainActivity;
    private final RecyclerViewInterface recyclerViewInterface;

    public RecyclerViewAdapter(Context context, ArrayList<UserViewModel> usersList,
                               MainActivity mainActivity, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.usersList = usersList;
        this.mainActivity = mainActivity;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.recycler_view_card, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface, usersList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to the view we created in the recycler_view_card layout file
        // based on the position of the recycler view

        holder.tvName.setText(String.format(
                "%s %s", usersList.get(position).getFirstName(), usersList.get(position).getLastName()));
        holder.tvEmail.setText(usersList.get(position).getEmail());
        holder.tvId.setText(String.valueOf(usersList.get(position).getId()));
        Glide.with(this.mainActivity)
                .load(usersList.get(position).getAvatar())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // the recycler view just wants to know the number of items you want displayed
        return usersList.size();
    }

    public void setUsersList(ArrayList<UserViewModel> list) {
        this.usersList = list;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recycler_view_card layout file

        ImageView imageView;
        TextView tvName;
        TextView tvEmail;
        Button editBtn;
        Button deleteBtn;
        TextView tvId;
        ArrayList<UserViewModel> usersList;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface,
                            ArrayList<UserViewModel> usersList) {
            super(itemView);

            imageView = itemView.findViewById(R.id.avatarImage);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvEmail = itemView.findViewById(R.id.emailTextView);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            tvId = itemView.findViewById(R.id.idTextView);
            this.usersList = usersList;

            deleteBtn.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onDeleteClick(pos);
                    }
                }
            });

            editBtn.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    String firstName = usersList.get(pos).getFirstName();
                    String lastName = usersList.get(pos).getLastName();
                    String avatar = usersList.get(pos).getAvatar();
                    String email = usersList.get(pos).getEmail();
                    int id = usersList.get(pos).getId();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onEditClick(view, pos, firstName, lastName, email, avatar, id);
                    }
                }
            });
        }
    }
}
