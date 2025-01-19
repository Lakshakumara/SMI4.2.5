package sss.yml.smi.smi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sss.yml.smi.R;
import sss.yml.smi.room.School;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolHolder> {
    //public static final int VIEW_TYPE_EMPTY = 0;
    //public static final int VIEW_TYPE_NORMAL = 1;
    private List<School> schList = new ArrayList<>();
    private static SingleClickListener sClickListener;
    private static LongClickListener lClickListener;
    private static DownloadClickListener dClickListener;
    boolean sourceOffline = false;
    int row_index = -1;

    @NonNull
    @Override
    public SchoolAdapter.SchoolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SchoolHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_slectable_item, parent, false));
        /*switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new SchoolHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_selectable_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:

                /*return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.homeview, parent, false));
        }*/

    }

    /* @Override
     public int getItemViewType(int position) {
         if (schList != null && schList.size() > 0) {
             return VIEW_TYPE_NORMAL;
         } else {
             return VIEW_TYPE_EMPTY;
         }
     }*/
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull SchoolAdapter.SchoolHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        School ss = schList.get(position);
        if(sourceOffline){
            holder.name.setText(ss.getCensusNo()+"-"+ss.getName());
            if(ss.isSelected()) holder.tb.setImageResource(R.drawable.ic_delete);
            else holder.tb.setImageResource(R.drawable.ic_file_download);
        }else{
            holder.name.setText(ss.getName()+"\n@"+ss.getAddress());
            holder.line_2.setText("CensusNo:" + ss.getCensusNo()+" "+ss.getLevel()+" School");
            holder.line_3.setText("Grade:"+ss.getSpan()+" Male: "+ss.getMale()+" Female: "+ss.getFemale());
        }

        holder.mBackground.setOnClickListener( v-> {
            row_index = position;
            if (sClickListener != null && position != RecyclerView.NO_POSITION) {
                if(schList.size() != 0) sClickListener.onItemClick(schList.get(position));
            }
            notifyDataSetChanged();
        });
        holder.mBackground.setOnLongClickListener(v -> {
            row_index = position;
            if (lClickListener != null && position != RecyclerView.NO_POSITION) {
                if(schList.size() != 0) lClickListener.onItemLongClick(schList.get(position));
                notifyDataSetChanged();
            } else return false;
            return true;
        });
        //if(row_index == position) holder.mBackground.setBackgroundResource(R.drawable.rv_selected_bg);
       // else holder.mBackground.setBackgroundResource(R.drawable.rv_notselect_bg);
    }

    @Override
    public int getItemCount() {
        if (schList != null && schList.size() > 0)
            return schList.size();
        else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(boolean sourceOffline, List<School> schList) {
        this.sourceOffline = sourceOffline;
        this.schList = schList;
        row_index = -1;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(SingleClickListener myClickListener) {
        sClickListener = myClickListener;
    }
    void setOnItemLongClickListener(LongClickListener lClickListener) {
        SchoolAdapter.lClickListener = lClickListener;
    }
    void setOnDownloadClickListener(DownloadClickListener dClickListener) {
        SchoolAdapter.dClickListener = dClickListener;
    }
    class SchoolHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView line_2;
        TextView line_3;
        ImageButton tb;
        private final CardView mBackground;
        SchoolHolder(View itemView) {
            super(itemView);
            mBackground = itemView.findViewById(R.id.item_list_background);
            name = itemView.findViewById(R.id.show_mdate);
            line_2 = itemView.findViewById(R.id.show_wt);
            line_3 = itemView.findViewById(R.id.show_ht);
            tb = itemView.findViewById(R.id.toggleButton);
            if(sourceOffline) {
                tb.setVisibility(View.GONE);
                line_2.setVisibility(View.GONE);
                line_3.setVisibility(View.GONE);
            } else {
                tb.setVisibility(View.VISIBLE);
                line_2.setVisibility(View.VISIBLE);
                line_3.setVisibility(View.VISIBLE);
            }
            tb.setOnClickListener(v -> {
                School sch = schList.get(getAbsoluteAdapterPosition());
                sch.setSelected(true);
                int position = getAbsoluteAdapterPosition();
                if (dClickListener != null && position != RecyclerView.NO_POSITION) {
                    if(schList.size() != 0) dClickListener.onDownloadClick(schList.get(position));
                }
            });
        }
    }
    interface SingleClickListener {
        void onItemClick(School school);
    }
    interface LongClickListener {
        void onItemLongClick(School school);
    }

    interface DownloadClickListener {
        void onDownloadClick(School school);
    }
    /*public class EmptyViewHolder extends SchoolHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }*/
}