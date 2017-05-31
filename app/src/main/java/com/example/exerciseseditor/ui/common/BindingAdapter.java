package com.example.exerciseseditor.ui.common;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Радик on 31.05.2017.
 */

public abstract class BindingAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<V>> {

    @Nullable
    private List<T> items;

    @Override
    public BindingViewHolder<V> onCreateViewHolder(ViewGroup parent, int i) {
        V binding = createBinding(parent);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<V> holder, int i) {
        if (items == null) {
            throw new NullPointerException("Items list is pointing to null");
        }

        bind(holder.binding, items.get(i));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(@Nullable List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected abstract V createBinding(ViewGroup parent);
    protected abstract void bind(V binding, T item);
}
