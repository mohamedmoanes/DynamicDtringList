package com.moanes.dynamicstringlist;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class DynamicStringList extends ConstraintLayout {
    private StringListAdapter stringListAdapter;
    private List<String> phones;
    private int max;
    private IDynamicStringListCallback iDynamicStringListCallback;

    public DynamicStringList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.phone_attrs,
                0, 0);
        max = attributes.getInteger(R.styleable.phone_attrs_max, 100);
        String hint = attributes.getString(R.styleable.phone_attrs_hint);
        int input_type = attributes.getInt(R.styleable.phone_attrs_inputType, 0x00000000);

        int btnColor = attributes.getColor(R.styleable.phone_attrs_addButtonColor, 0);
        int btnResColor = attributes.getColor(R.styleable.phone_attrs_addButtonResColor, 0);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.add_phones_list_view, this, true);


        initRecyclerView(hint, input_type);
        initAddBTN(btnColor, btnResColor);
    }

    private void initRecyclerView(String hint, int input_type) {
        RecyclerView recyclerView = findViewById(R.id.list);
        phones = new ArrayList<>();
        stringListAdapter = new StringListAdapter(phones, hint, input_type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(stringListAdapter);
    }

    private void initAddBTN(int btnColor, int btnResColor) {
        FloatingActionButton add_btn = findViewById(R.id.add);
        add_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (max > phones.size()) {
                    phones.add("");
                    stringListAdapter.notifyItemInserted(phones.size() - 1);
                } else if (null != iDynamicStringListCallback) {
                    iDynamicStringListCallback.onMaxReached(max);
                }
            }
        });
        if (btnColor != 0) {
            add_btn.setBackgroundTintList(ColorStateList.valueOf(btnColor));
        }

        if (0 != btnResColor) {
            add_btn.setSupportImageTintList(ColorStateList.valueOf(btnResColor));
        }
    }

    public void setDynamicStringListCallback(IDynamicStringListCallback iDynamicStringListCallback) {
        this.iDynamicStringListCallback = iDynamicStringListCallback;
    }

    public List<String> getList() {
        return phones;
    }

    public void setList(List<String> phones) {
        this.phones.addAll(phones);
        stringListAdapter.notifyDataSetChanged();
    }
}
