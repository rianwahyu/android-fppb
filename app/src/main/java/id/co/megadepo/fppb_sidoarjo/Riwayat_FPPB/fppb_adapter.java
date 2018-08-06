package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.megadepo.fppb_sidoarjo.R;

/**
 * Created by addin on 31/10/17.
 */

public class fppb_adapter extends ArrayAdapter<fppb_model> implements View.OnClickListener {

    private ArrayList<fppb_model> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView lnotemp;
        TextView ltgl;
    }

    public fppb_adapter (ArrayList<fppb_model> data, Context context) {
        super(context, R.layout.activity_row_riwayatfppb, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        fppb_model dataModel = (fppb_model) object;

//        switch (v.getId()) {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " + dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        fppb_model dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_row_riwayatfppb, parent, false);
            viewHolder.lnotemp = (TextView) convertView.findViewById(R.id.lnotem);
            viewHolder.ltgl = (TextView) convertView.findViewById(R.id.ltgl);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.lnotemp.setText(dataModel.getNotemp());
        viewHolder.ltgl.setText(dataModel.getTgl());
        // Return the completed view to render on screen
        return convertView;
    }
}