package com.homenas.netdrive.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.homenas.netdrive.Constants;
import com.homenas.netdrive.Data.FilesData;
import com.homenas.netdrive.R;

import java.util.List;

/**
 * Created by engss on 24/10/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    public interface CustomAdapterListener { // create an interface
        void onItemClick(int position); // create callback function
    }

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private CustomAdapterListener mListener;
    private List<FilesData> mDataSet;
    private boolean mViewGrid;
    private boolean LOG = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    mListener.onItemClick(getAdapterPosition());
                }
            });
            textView = v.findViewById(R.id.textView);
            checkBox = v.findViewById(R.id.checkBox);
        }

        public TextView getTextView() {
            return textView;
        }
        public CheckBox getCheckBox() { return checkBox; }
    }

    public RecyclerviewAdapter(Context context, List<FilesData> dataSet, CustomAdapterListener listener) {
        mContext = context;
        mDataSet = dataSet;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(mViewGrid ? R.layout.card_item : R.layout.row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if(LOG) Log.i(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet.get(position).fileName);

        // Get CheckBox properties
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        ColorStateList colorStateList = new ColorStateList(
                new int[][] {
                        new int[]{-android.R.attr.state_checked}, // disable
                        new int[]{android.R.attr.state_checked} // enable
                },
                new int[] {
                        Color.argb(50,128,128,128),
                        Color.parseColor("#ffa726")
                }
        );
        if(Constants.starListSet.contains(mDataSet.get(position).file.getUri().getPath())){
            viewHolder.checkBox.setChecked(true);
            CompoundButtonCompat.setButtonTintList(viewHolder.checkBox, colorStateList);
        }else{
            viewHolder.checkBox.setChecked(false);
            CompoundButtonCompat.setButtonTintList(viewHolder.checkBox, colorStateList);
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Constants.starListSet.add(mDataSet.get(position).file.getUri().getPath());
                }else{
                    Constants.starListSet.remove(mDataSet.get(position).file.getUri().getPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setView(boolean viewGrid) {
        mViewGrid = viewGrid;
    }

}
