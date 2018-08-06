package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

import android.app.Activity;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;



import java.util.List;

import id.co.megadepo.fppb_sidoarjo.R;
import id.co.megadepo.fppb_sidoarjo.Util.DataTemp;

public class AdapterData extends RecyclerView.Adapter<AdapterData.AdapterBarangViewHolder> {

    private List<fppb_model> list;
    private Activity activity;

    sqLite db;

    public AdapterData(Activity activity, List<fppb_model> list) {
        this.activity=activity;
        this.list = list;
    }

    @Override
    public AdapterBarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_riwayatfppb, parent, false);
        AdapterBarangViewHolder adapterTeamViewHolder = new AdapterBarangViewHolder(view);
        return adapterTeamViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterBarangViewHolder holder, int position) {
        //db = new SQLiteHelpers(activity);
        fppb_model dataBarang = list.get(position);
        holder.lnotemp.setText(dataBarang.getNotemp());
        holder.nama.setText(dataBarang.getNama());
        holder.ltgl.setText(dataBarang.getTgl());

        final String notemp = dataBarang.getNotemp();
        final String tgl = dataBarang.getTgl();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), detail_fppb.class);
                intent1.putExtra("notemp", notemp);
                intent1.putExtra("tgl", tgl);
                intent1.putExtra("gerai", DataTemp.getGerai());
                ((view.getContext())).startActivity(intent1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterBarangViewHolder extends RecyclerView.ViewHolder {
        TextView lnotemp;
        TextView ltgl;
        CardView card;
        TextView nama;
        public AdapterBarangViewHolder(View itemView) {
            super(itemView);

           lnotemp = (TextView) itemView.findViewById(R.id.lnotem);
           ltgl = (TextView) itemView.findViewById(R.id.ltgl);
           card = (CardView) itemView.findViewById(R.id.card_view);
           nama = (TextView) itemView.findViewById(R.id.lnama);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
