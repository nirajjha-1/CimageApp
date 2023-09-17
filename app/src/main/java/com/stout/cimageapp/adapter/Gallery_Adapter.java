package com.stout.cimageapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stout.cimageapp.R;
import com.stout.cimageapp.models.Event_Model;
import com.stout.cimageapp.models.Gallery_Model;

import java.util.List;

public class Gallery_Adapter extends BaseAdapter {

    private Context context;
    List<Gallery_Model> gallery_list;

    public Gallery_Adapter(Context context, List<Gallery_Model> gallery_list) {
        this.context = context;
        this.gallery_list = gallery_list;
    }

    @Override
    public int getCount() {
        return gallery_list.size();
    }

    @Override
    public Gallery_Model getItem(int i) {
        return gallery_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class GalleryViewHolder{
        TextView gallery_title, gallery_dscr;
        ImageView gallery_img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GalleryViewHolder gallery_holder;
        if(view == null){
            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflate.inflate(R.layout.gallery_items,null);

            gallery_holder = new GalleryViewHolder();
            gallery_holder.gallery_title = (TextView) view.findViewById(R.id.gallery_item_title);
            gallery_holder.gallery_dscr = (TextView) view.findViewById(R.id.gallery_item_dscr);
            gallery_holder.gallery_img = (ImageView) view.findViewById(R.id.gallery_item_img);

            view.setTag(gallery_holder);
        }
        else {
            gallery_holder = (GalleryViewHolder) view.getTag();
        }

        Gallery_Model gallery = gallery_list.get(i);

        gallery_holder.gallery_title.setText(gallery.getGallery_title());
        gallery_holder.gallery_dscr.setText(gallery.getGallery_dscr());
        Glide.with(context).load(gallery.getGallery_img_url()).into(gallery_holder.gallery_img);
        return view;
    }
}
