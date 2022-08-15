package com.flatcode.simplemultiapps.MultipleDelete;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.databinding.ItemMultiDeleteBinding;

import java.util.ArrayList;

public class MultiDeleteAdapter extends RecyclerView.Adapter<MultiDeleteAdapter.ViewHolder> {

    private ItemMultiDeleteBinding binding;
    private final Context context;
    Activity activity;

    ArrayList<String> arrayList;
    TextView tvEmpty;
    MultiDelete mainViewModel;

    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<String> selectList = new ArrayList<>();

    //Create Constructor
    public MultiDeleteAdapter(Context context, Activity activity, ArrayList<String> arrayList, TextView tvEmpty) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.tvEmpty = tvEmpty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        binding = ItemMultiDeleteBinding.inflate(LayoutInflater.from(context), parent, false);
        //Initialize view model
        mainViewModel = ViewModelProviders.of((FragmentActivity) activity).get(MultiDelete.class);
        //Return view
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Set text on text view
        holder.textView.setText(arrayList.get(position));

        holder.itemView.setOnLongClickListener(v -> {
            //Check condition
            if (!isEnable) {
                //When action mode is not enable
                //Initialize action mode
                ActionMode.Callback callback = new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        //Initialize menu inflate
                        MenuInflater menuInflater = actionMode.getMenuInflater();
                        //Inflate menu
                        menuInflater.inflate(R.menu.multi_delete_menu, menu);
                        //Return true
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(final ActionMode actionMode, Menu menu) {
                        //When action mode is prepare
                        //Set isEnable true
                        isEnable = true;
                        //Create method
                        ClickItem(holder);

                        //Set observer on get text method
                        mainViewModel.getText().observe((LifecycleOwner) activity, s -> {
                            //When text change
                            //Set text on action mode title
                            actionMode.setTitle(String.format("%s Selected", s));
                        });
                        //Return true
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        //When click on action mode item
                        //Get item id
                        int id = menuItem.getItemId();
                        //Use switch condition
                        switch (id) {
                            case R.id.menu_delete:
                                //When click on delete
                                //User for loop
                                for (String s : selectList) {
                                    //Remove selected item from array list
                                    arrayList.remove(s);
                                }
                                //Check condition
                                if (arrayList.size() == 0) {
                                    //When array list is empty
                                    //Visible text view
                                    tvEmpty.setVisibility(View.VISIBLE);
                                }
                                //Finish action mode
                                actionMode.finish();
                                break;
                            case R.id.menu_select_all:
                                //When click on select all
                                //Check condition
                                if (selectList.size() == arrayList.size()) {
                                    //When all item selected
                                    //Set isSelectedAll false
                                    isSelectAll = false;
                                    //Clear select array list
                                    selectList.clear();
                                } else {
                                    //When all item unselected
                                    //Set isSelectedAll true
                                    isSelectAll = true;
                                    //Clear select array list
                                    selectList.clear();
                                    //Add all value in select array list
                                    selectList.addAll(arrayList);
                                }
                                //Set text on view model
                                mainViewModel.setText(String.valueOf(selectList.size()));
                                //Notify adapter
                                notifyDataSetChanged();
                                break;
                        }
                        //Return true
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {
                        //When action mode is destroy
                        //Set isEnable false
                        isEnable = false;
                        //Set isSelectAll false
                        isSelectAll = false;
                        //Clear select array list
                        selectList.clear();
                        //Notify adapter
                        notifyDataSetChanged();
                    }
                };
                //Start action mode
                ((AppCompatActivity) v.getContext()).startActionMode(callback);
            } else {
                //When action mode is already enable
                //Call method
                ClickItem(holder);

            }
            //Return true
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            //Check condition
            if (isEnable) {
                //When action mode is enable
                //Call method
                ClickItem(holder);
            } else {
                //When action mode is not enable
                //Display Toast
                Toast.makeText(activity, "You Clicked " + arrayList.get(holder.getAdapterPosition()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Check condition
        if (isSelectAll) {
            //When all value selected
            //Visible all check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            //Set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            //When all value unselected
            //hide all check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            //Set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void ClickItem(ViewHolder holder) {
        //Get selected item value
        String s = arrayList.get(holder.getAdapterPosition());
        //Check condition
        if (holder.ivCheckBox.getVisibility() == View.GONE) {
            //When item not selected
            //Visible check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            //Set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            //Add value in select array list
            selectList.add(s);
        } else {
            //When item selected
            //Hide check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            //Set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //Remove value in select array list
            selectList.remove(s);
        }
        //Set text on view model
        mainViewModel.setText(String.valueOf(selectList.size()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView ivCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = binding.text;
            ivCheckBox = binding.checkBox;
        }
    }
}