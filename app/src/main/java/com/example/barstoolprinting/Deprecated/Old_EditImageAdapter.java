package com.example.barstoolprinting.Deprecated;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barstoolprinting.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Old_EditImageAdapter extends RecyclerView.Adapter<Old_EditImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Old_Upload> mUploads;
    private OnItemClickListener mListener;

    public Old_EditImageAdapter(Context context, List<Old_Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Old_Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewDescription.setText(uploadCurrent.getDescription());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public TextView textViewDescription;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem editName = menu.add(Menu.NONE, 1, 1, "Edit Name");
            MenuItem editDescription = menu.add(Menu.NONE, 2, 2, "Edit Description");
            MenuItem delete = menu.add(Menu.NONE, 3, 3, "Delete");

            editName.setOnMenuItemClickListener(this);
            editDescription.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditNameClick(position);
                            return true;
                        case 2:
                            mListener.onEditDescriptionClick(position);
                            return true;
                        case 3:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditNameClick(int position);

        void onEditDescriptionClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}