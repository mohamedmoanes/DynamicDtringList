package com.moanes.dynamicstringlist;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> {
    private List<String> list;
    private String hint;
    private int input_type;

    public StringListAdapter(List<String> list, String hint, int input_type) {
        this.list = list;
        this.hint = hint;
        this.input_type = input_type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.phone_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String text = list.get(position);
        holder.phone.setText(text);
        holder.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.remove(position);
                list.add(position, s.toString());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == list)
            return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputLayout inputLayout;
        private AppCompatImageButton delete;
        private TextInputEditText phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inputLayout = itemView.findViewById(R.id.input_layout);
            delete = itemView.findViewById(R.id.delete);
            phone = itemView.findViewById(R.id.input);
            phone.setHint(hint);
            phone.setInputType(input_type);
        }
    }
}
