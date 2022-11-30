package com.utotech.iper.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Model.Sentence;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Dialog;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.databinding.MessageListRowBinding;

public class RecyclerSentenceListAdapter extends RecyclerView.Adapter<RecyclerSentenceListAdapter.DiffVh> {
    Activity activity;
    DatabaseAccess access;

    public RecyclerSentenceListAdapter(Activity activity) {
        this.activity = activity;
        access = DatabaseAccess.getInstance(activity);
    }

    private MessageListRowBinding binding;

    @NonNull
    @Override
    public DiffVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = MessageListRowBinding.inflate(inflater);
        return new DiffVh(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull DiffVh holder, int position) {
        int where = differ.getCurrentList().size() - 1 - position;
        Sentence sentence = (Sentence) differ.getCurrentList().get(where);
        int fontSize = Integer.parseInt(Shared.readAppSetting(activity.getApplicationContext(), "fontSize"));
        if (position == 0) {
            binding.cardview.setCardBackgroundColor(activity.getResources().getColor(R.color.aqua));
            binding.txtMessage.setTextColor(activity.getResources().getColor(R.color.white));
        }
        if (sentence.getBeShowen() == 1) {
            binding.txtRepeat.setChecked(true);
        }
        setFont(binding.txtMessage);
        binding.txtMessage.setText(sentence.getSentence());
        binding.txtMessage.setTextSize(fontSize);
        binding.txtRepeat.setText(String.valueOf(sentence.getRepeatNum()).concat(" دفعه"));

        binding.txtMessage.setText(sentence.getSentence());

        binding.txtRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    access.updateSentence(String.valueOf(sentence.getId()), 1, sentence.getRepeatNum(), sentence.getSentence(), 0, false);

                } else {
                    access.updateSentence(String.valueOf(sentence.getId()), 0, sentence.getRepeatNum(), sentence.getSentence(), 1, false);
                }
            }
        });


        binding.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(activity);
                dialog.editSentenceDialog(sentence.getId(), sentence.getSentence(), differ);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    class DiffVh extends RecyclerView.ViewHolder {
        public DiffVh(@NonNull View itemView) {
            super(itemView);
        }
    }

    private DiffUtil.ItemCallback<Sentence> differCallBak = new DiffUtil.ItemCallback<Sentence>() {

        @Override
        public boolean areItemsTheSame(@NonNull Sentence oldItem, @NonNull Sentence newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Sentence oldItem, @NonNull Sentence newItem) {
            return oldItem == newItem;
        }
    };

    public AsyncListDiffer differ = new AsyncListDiffer(this, differCallBak);

    private void setFont(TextView txtMessage) {
        String fontName = Shared.readAppSetting(activity, "fontName");
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "font/" + fontName);
        txtMessage.setTypeface(typeface);
    }
}
