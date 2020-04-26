package aAdapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barazeli.codelab.MessageActivity;
import com.barazeli.codelab.R;
import com.bumptech.glide.Glide;

import java.util.List;

import aModel.User;
import de.hdodenhof.circleimageview.CircleImageView;
public class RecyclerFriendsAdapter extends RecyclerView.Adapter<RecyclerFriendsAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    public RecyclerFriendsAdapter(Context context,List<User> users) {
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_row,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name_friend.setText(users.get(position).getName());
        if (users.get(position).getImg_url()=="default"){
            Glide.with(context).load(users.get(position).getImg_url()).into(holder.img_friend);
        }else {
            holder.img_friend.setImageResource(R.drawable.a1);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("id",users.get(position).getId());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_friend;
        TextView name_friend;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_friend=itemView.findViewById(R.id.img_friend_row);
            name_friend=itemView.findViewById(R.id.name_friend_row);
        }
    }
}
