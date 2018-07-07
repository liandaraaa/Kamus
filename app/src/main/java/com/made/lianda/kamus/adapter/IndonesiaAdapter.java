package com.made.lianda.kamus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.made.lianda.kamus.R;
import com.made.lianda.kamus.db.KamusHelper;
import com.made.lianda.kamus.model.Indonesia;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IndonesiaAdapter extends RecyclerView.Adapter<IndonesiaAdapter.IndonesiaHolder>{

    private ArrayList<Indonesia> mData;
    private Context context;
    private LayoutInflater mInflater;
    private OnKataClickCallback onKataClickCallback;

    public OnKataClickCallback getOnKataClickCallback() {
        return onKataClickCallback;
    }

    public void setOnKataClickCallback(OnKataClickCallback onKataClickCallback) {
        this.onKataClickCallback = onKataClickCallback;
    }

    public ArrayList<Indonesia> getmData() {
        return mData;
    }

    public IndonesiaAdapter(Context context, ArrayList<Indonesia> list) {
        this.context = context;
        this.mData = list;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public IndonesiaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View  view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_indonesia_row, viewGroup, false);
        return new IndonesiaHolder(view);
    }

    public void addItem(ArrayList<Indonesia> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(IndonesiaHolder indonesiaHolder, int i) {
        indonesiaHolder.bind(getmData().get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class IndonesiaHolder extends RecyclerView.ViewHolder{

        TextView tvKata;

        public IndonesiaHolder(View itemView) {
            super(itemView);

            tvKata = itemView.findViewById(R.id.tv_kata);
        }

        public void bind (Indonesia indonesia){
            tvKata.setText(indonesia.getKata());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnKataClickCallback().onKataClicked(getmData().get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnKataClickCallback{
        void onKataClicked(Indonesia indonesia);
    }
}
