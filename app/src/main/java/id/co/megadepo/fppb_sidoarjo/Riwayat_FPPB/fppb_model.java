package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

/**
 * Created by addin on 31/10/17.
 */

public class fppb_model {

    String notemp;
    String nama;
    String tgl;

    public fppb_model() {
    }

    public fppb_model(String notemp, String nama,String tgl) {
        this.notemp=notemp;
        this.nama=nama;
        this.tgl=tgl;

    }

    public String getNotemp() {
        return notemp;
    }

    public String getTgl() {
        return tgl;
    }

    public void setNotemp(String notemp) {
        this.notemp = notemp;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
