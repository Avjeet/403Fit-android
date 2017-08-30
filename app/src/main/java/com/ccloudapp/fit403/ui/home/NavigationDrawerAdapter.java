package com.ccloudapp.fit403.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccloudapp.fit403.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 24/8/17.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<NavDrawerItem> mNavDrawerItems;

    public static final int TYPE_ITEM = 10;
    public static final int TYPE_HEADER = 11;
    public static final int TYPE_DIVIDER = 12;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> list){
        this.mContext = context;
        this.mNavDrawerItems = list;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ITEM:
                return createItemViewHolder(parent);
            case TYPE_HEADER:
                return createHeaderViewHolder(parent);
            case TYPE_DIVIDER:
                return createDividerViewHolder(parent);
            default: return null;
        }
    }

    private DividerViewHolder createDividerViewHolder(ViewGroup parent) {
        return new DividerViewHolder(mInflater.inflate(R.layout.divider, parent, false));
    }

    private HeaderViewHolder createHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(mInflater.inflate(R.layout.header_navigation_drawer, parent, false));
    }

    private ItemViewHolder createItemViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_navigation_drawer, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.itemView.setOnClickListener(v -> {
            if(mContext instanceof NavigationHomeActivity){
                ((NavigationHomeActivity) mContext).onClick(getItem(itemViewHolder.getAdapterPosition()));
            }
        });
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_ITEM:
                bindItem(getItem(position), (ItemViewHolder)holder);
                break;
            case TYPE_HEADER:
                bindHeader(getItem(position), (HeaderViewHolder)holder);
                break;
            case TYPE_DIVIDER:
                bindDivider((DividerViewHolder)holder);
                break;
        }
    }

    private void bindDivider(DividerViewHolder holder) {

    }

    private void bindHeader(NavDrawerItem item, HeaderViewHolder holder) {
        holder.mUsernameTextView.setText(item.title);
        holder.mEmailTextView.setText(item.email);
    }

    private void bindItem(NavDrawerItem item, ItemViewHolder holder) {
        holder.itemView.setSelected(item.isSelected);
        holder.mTextView.setText(item.title);
        holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(item.drawableId, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return mNavDrawerItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).itemId == -3)
            return TYPE_HEADER;
        else if(getItem(position).itemId == -2)
            return TYPE_DIVIDER;
        else return TYPE_ITEM;
    }

    private NavDrawerItem getItem(int position){
        return mNavDrawerItems.get(position);
    }

    public void updateList(List<NavDrawerItem> list) {
        this.mNavDrawerItems = list;
        notifyDataSetChanged();
    }

    public void setSelected(int selfNavDrawerItem) {
        for (NavDrawerItem navDrawerItem : mNavDrawerItems) {
            navDrawerItem.isSelected = (selfNavDrawerItem == navDrawerItem.itemId);
        }
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title_textview)
        public TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.profile_image)
        public ImageView mImageView;
        @BindView(R.id.nav_user_name_tv)
        public TextView mUsernameTextView;
        @BindView(R.id.nav_user_email_tv)
        public TextView mEmailTextView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class DividerViewHolder extends RecyclerView.ViewHolder{

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnNavDrawerItemClickListener{
        void onClick(NavDrawerItem navDrawerItem);
    }
}
