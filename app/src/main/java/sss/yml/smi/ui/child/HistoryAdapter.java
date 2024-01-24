package sss.yml.smi.ui.child;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.ChildWpData;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.WPHolder> {
    Context ctx = HomePage.application;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<ChildWpData> childWpDataList;
    private static SingleClickListener listener;
    private static ConstraintLayout mBackground;

    public HistoryAdapter(List<ChildWpData> childWpDataList) {
        this.childWpDataList = childWpDataList;
    }

    @NonNull
    @Override
    public WPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_list_item, parent, false);

        /*switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new WPHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_spl2, parent, false));
        }*/

        return new WPHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_list_item, parent, false));
    }
    @Override
    public int getItemViewType(int position) {
        if (childWpDataList != null && childWpDataList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else return VIEW_TYPE_EMPTY;
    }
    @Override
    public void onBindViewHolder(@NonNull final WPHolder holder, final int position) {
        if(childWpDataList == null || childWpDataList.size() == 0) return;
        final ChildWpData currentItem = childWpDataList.get(position);
        @SuppressLint("SimpleDateFormat") String sqlDate = new SimpleDateFormat("yyyy-MM-dd").format(currentItem.getmDate());
        holder.madte.setText(String.format("%s  %s", sqlDate,
                getAgeText(currentItem.getAgeYY(), currentItem.getAgeMM(), currentItem.getAgeDD())));

        holder.show_wt.setText("Status   Wt - "+currentItem.getWt() + "  Ht - " + currentItem.getHt());
        holder.show_ht.setText(String.format("%s %s", ctx.getString(R.string.note), (currentItem.getNote() == null)?"":currentItem.getNote()));

    }
    private String getAgeText(int ageYY, int ageMM, int ageDD) {
        if (ageYY == 0 && ageMM == 0 && ageDD == 0) return ctx.getString(R.string.birth_weight);
        else if (ageYY == 0 && ageMM == 0) return ctx.getString(R.string.agelable)+": " + ageDD + ctx.getString(R.string.d);
        else if (ageYY == 0) return ctx.getString(R.string.agelable)+": " + ageMM + ctx.getString(R.string.m) + ageDD + ctx.getString(R.string.d);
        else return ctx.getString(R.string.agelable)+": " + ageYY + ctx.getString(R.string.y) + ageMM + ctx.getString(R.string.m) + ageDD + ctx.getString(R.string.d);
    }
    @Override
    public int getItemCount() {
        if (childWpDataList != null && childWpDataList.size() > 0) {
            return childWpDataList.size();
        } else return 1;
    }

    public ChildWpData getChildWpData(int index) {
        return childWpDataList.get(index);
    }

    public void setItem(ChildWpData item, int index) {
        childWpDataList.add(item);
        notifyItemInserted(index);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<ChildWpData> items) {
        this.childWpDataList = items;
        notifyDataSetChanged();
    }

    class WPHolder extends RecyclerView.ViewHolder {
        TextView madte;
        TextView show_wt;
        TextView show_ht;

        WPHolder(View itemView) {
            super(itemView);
            madte = itemView.findViewById(R.id.show_mdate);
            show_wt = itemView.findViewById(R.id.show_wt);
            show_ht = itemView.findViewById(R.id.show_ht);
            mBackground = itemView.findViewById(R.id.vertical_list_item_background);
            itemView.setOnClickListener(v -> {
                mBackground.setSelected(true);
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if(childWpDataList.size() != 0) listener.onItemClickListener(childWpDataList.get(position));
                }
            });
        }
    }

    public interface SingleClickListener {
        void onItemClickListener(ChildWpData childWpData);
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        listener = clickListener;
    }
}