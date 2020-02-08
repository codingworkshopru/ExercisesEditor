package com.example.exerciseseditor.ui.common;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Радик on 31.05.2017.
 */

public class BindingListViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T binding;

    public BindingListViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
