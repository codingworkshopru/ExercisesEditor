package com.example.exerciseseditor.ui.common;

import androidx.databinding.ViewDataBinding;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Радик on 31.05.2017.
 */

public abstract class BindingListAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<BindingListViewHolder<V>> {

    @Nullable
    private List<T> items;

    @Override
    public BindingListViewHolder<V> onCreateViewHolder(ViewGroup parent, int i) {
        V binding = createBinding(parent);
        return new BindingListViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingListViewHolder<V> holder, int i) {
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

    protected void setItems(@Nullable List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected abstract V createBinding(ViewGroup parent);
    protected abstract void bind(V binding, T item);
}
