package sss.yml.smi.smi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sss.yml.smi.R;
import sss.yml.smi.room.Student;
import sss.yml.smi.util.SMIConstants;

public class SMIDataAdapter extends RecyclerView.Adapter<SMIDataAdapter.DataHolder> {
    //public static final int VIEW_TYPE_EMPTY = 0;
    //public static final int VIEW_TYPE_NORMAL = 1;
    private List<Student> studentList = new ArrayList<>();
    private static SingleClickListener sClickListener;
    private static LongClickListener lClickListener;
    private static CardPOPListener lCardPop;
    boolean sourceOffline = false;
    int row_index = -1;
    Context ctx;
    @NonNull
    @Override
    public SMIDataAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_data_view, parent, false));
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
    public void onBindViewHolder(@NonNull final SMIDataAdapter.DataHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        Student ss = studentList.get(position);
        holder.menuIcon.setOnClickListener(v -> lCardPop.onPopupMenuClick(v, ss));
        boolean isMale = ss.getSex().equalsIgnoreCase("Male");
        holder.gender.setImageResource((isMale) ? R.drawable.ic_male : R.drawable.ic_female);
        holder.name.setText(ss.getUserName());
        holder.mDate.setText(SMIConstants.d.format(ss.getMdate()));
        holder.ht.setText(""+ss.getHeight());
        holder.htStatus.setBackgroundColor(ss.getHtColor());
        holder.htStatus.setText(ss.getHtStatus());
        holder.wt.setText(""+ss.getWeight());
        holder.wtStatus.setBackgroundColor(ss.getWtColor());
        holder.wtStatus.setText(""+ss.getWtStatus());
        holder.bmi.setText(""+ss.getBmi());
        holder.dob.setText("DOB:"+SMIConstants.d.format(ss.getDob())+"\tAGE:"+ss.getAgeY() +"y" + ss.getAgeM() + "m" + ss.getAgeD() + "d");
           /* holder.cardView.setOnLongClickListener(v -> {
                holder.cardView.setChecked(!holder.cardView.isChecked());
                row_index = position;
                Logger.setMsg("setOnLongClickListener");
                if (lClickListener != null && position != RecyclerView.NO_POSITION) {
                    if(studentList.size() != 0) lClickListener.onItemLongClick(studentList.get(position));
                    notifyDataSetChanged();
                } else return false;
                return true;
            });*/
        holder.cardView.setTag(ss);
        holder.cardView.setOnClickListener(v-> {
            //holder.cardView.setChecked(!holder.cardView.isChecked());
            row_index = (row_index == -1)?position:-1;
            if (sClickListener != null && position != RecyclerView.NO_POSITION) {
                if(studentList.size() != 0) sClickListener.onItemClick(studentList.get(position));
            }
            notifyDataSetChanged();
        });
        }
    @Override
    public int getItemCount() {
        if (studentList != null && studentList.size() > 0)
            return studentList.size();
        else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(boolean sourceOffline, List<Student> studentList, Context ctx) {
        this.sourceOffline = sourceOffline;
        this.studentList = studentList;
        this.ctx = ctx;
        row_index = -1;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(SingleClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }
    void setOnPopupMenuClick(CardPOPListener p){
        this.lCardPop = p;
    }
   /* void setOnItemLongClickListener(LongClickListener lClickListener) {
        this.lClickListener = lClickListener;
    }*/
   static class DataHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView mDate;
        private final TextView wt;
        private final TextView wtStatus;
        private final TextView ht;
        private final TextView htStatus;
        private final TextView bmi;
        private final TextView dob;
        private final ImageView gender;
       ImageView menuIcon;
        private final CardView cardView;
        DataHolder(View itemView) {
            super(itemView);
            menuIcon = itemView.findViewById(R.id.menuIcon);
            cardView = itemView.findViewById(R.id.item_list_background);
            /*cardView.setOnLongClickListener(v -> {
                //cardView.setChecked(!cardView.isChecked());
                return true;
            });
            cardView.setOnCheckedChangeListener((card, isChecked) -> {
                Logger.setMsg(""+isChecked);
                Logger.setMsg(card.getTag().toString());
            });
            cardView.setOnClickListener(v-> {
                cardView.setChecked(!cardView.isChecked());
                //if(cardView.isChecked()) cardView.setChecked(false);
            });*/

            name = itemView.findViewById(R.id.sdv_sName);
            name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            name.setSingleLine(true);
            name.setSelected(true);
            name.setMarqueeRepeatLimit(-1);

            mDate = itemView.findViewById(R.id.sdv_mdate);
            ht = itemView.findViewById(R.id.sdv_ht);
            htStatus = itemView.findViewById(R.id.sdv_htStatus);
            htStatus.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            htStatus.setSingleLine(true);
            htStatus.setSelected(true);
            htStatus.setMarqueeRepeatLimit(-1);
            wt = itemView.findViewById(R.id.sdv_wt);
            wtStatus = itemView.findViewById(R.id.sdv_wtStatus);
            wtStatus.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            wtStatus.setSingleLine(true);
            wtStatus.setSelected(true);
            wtStatus.setMarqueeRepeatLimit(-1);
            bmi = itemView.findViewById(R.id.sdv_bmi);
            dob = itemView.findViewById(R.id.sdv_dob);
            gender = itemView.findViewById(R.id.sdv_sex_image);
        }
    }
    interface SingleClickListener {
        void onItemClick(Student student);
    }
    interface LongClickListener {
        void onItemLongClick(Student student);
    }
    public interface CardPOPListener {
        void onPopupMenuClick(View view, Student student);
    }
    /*public class EmptyViewHolder extends SchoolHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }*/
    }