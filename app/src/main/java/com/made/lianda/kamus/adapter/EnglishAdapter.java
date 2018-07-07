package com.made.lianda.kamus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.made.lianda.kamus.R;
import com.made.lianda.kamus.model.English;

import java.util.ArrayList;

public class EnglishAdapter extends RecyclerView.Adapter<EnglishAdapter.EnglishHolder>{

    private ArrayList<English> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private OnEnglishClikcCallback onEnglishClikcCallback;

    public ArrayList<English> getmData() {
        return mData;
    }

    public OnEnglishClikcCallback getOnEnglishClikcCallback() {
        return onEnglishClikcCallback;
    }

    public void setOnEnglishClikcCallback(OnEnglishClikcCallback onEnglishClikcCallback) {
        this.onEnglishClikcCallback = onEnglishClikcCallback;
    }

    public EnglishAdapter(Context context, ArrayList<English> list) {
        this.context = context;
        this.mData = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public EnglishHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View  view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_english_row, viewGroup, false);
        return new EnglishHolder(view);
    }

    public void addItem(ArrayList<English> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EnglishHolder englishHolder, int i) {
        englishHolder.bind(getmData().get(i));
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

    public class EnglishHolder extends RecyclerView.ViewHolder{

        TextView tvWord;

        public EnglishHolder(View itemView) {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tv_word);
        }

        public void bind (English english){
            tvWord.setText(english.getWord());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnEnglishClikcCallback().onEnglishClicked(getmData().get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnEnglishClikcCallback{
        void onEnglishClicked(English english);
    }
}
