package id.co.megadepo.fppb_sidoarjo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import id.co.megadepo.fppb_sidoarjo.Util.ModelRiwayat;

public class AdapterRiwayat extends BaseAdapter {
    Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelRiwayat> item;

    public AdapterRiwayat(Activity activity, List<ModelRiwayat> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View cview, ViewGroup parent) {
        if (inflater == null)
            inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (cview == null)
            cview = inflater.inflate(R.layout.adapter_cari,null);

        TextView txtidfppb = (TextView) cview.findViewById(R.id.textnoFppb);
        TextView txtnama = (TextView) cview.findViewById(R.id.textNama);
        TextView txtnohp = (TextView) cview.findViewById(R.id.textNoHp);
        TextView txtAlamat = (TextView) cview.findViewById(R.id.textAlamat);
        TextView txtTelepon = (TextView) cview.findViewById(R.id.textTelp);
        Button btnLihat = (Button) cview.findViewById(R.id.btnLihatData);


        txtidfppb.setText(item.get(pos).getIdFpb());
        txtnama.setText(item.get(pos).getNama());
        txtnohp.setText(item.get(pos).getHp());
        txtAlamat.setText(item.get(pos).getAlamat());
        txtTelepon.setText(item.get(pos).getTelp());

        final String nama = item.get(pos).getNama();
        final String alamat = item.get(pos).getAlamat();
        final String kelurahan = item.get(pos).getKelurahan();
        final String kecamatan = item.get(pos).getKecamatan();
        final String kabupaten = item.get(pos).getKabupaten();
        final String provinsi = item.get(pos).getProvinsi();
        final String telp = item.get(pos).getTelp();
        final String hp = item.get(pos).getHp();
        final String kendaraan = item.get(pos).getKendaraan();
        final String portal = item.get(pos).getPortal();
        final String rt = item.get(pos).getRt();
        final String rw = item.get(pos).getRw();
        final String penerima = item.get(pos).getPenerima();
        final String tanggal_kirim = item.get(pos).getTanggal_kirim();

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(),FPPB2Get.class);
                i.putExtra("namax",nama);
                i.putExtra("alamatx",alamat);
                i.putExtra("kelx",kelurahan);
                i.putExtra("kecx",kecamatan);
                i.putExtra("kabx",kabupaten);
                i.putExtra("provx",provinsi);
                i.putExtra("telpx",telp);
                i.putExtra("hpx",hp);
                i.putExtra("portalx",portal);
                i.putExtra("rtx",rt);
                i.putExtra("rwx",rw);
                i.putExtra("penerimax",penerima);
                i.putExtra("tglx",tanggal_kirim);
                view.getContext().startActivity(i);
                ((activity)).finish();


            }
        });

        return cview;
    }
}
