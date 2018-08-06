package id.co.megadepo.fppb_sidoarjo.Util;

public class ModelRiwayat {
    String idFpb, nama, alamat,kelurahan,kecamatan,kabupaten,
    provinsi,telp,hp,kendaraan,portal,rt,rw,penerima,tanggal_kirim;

    public ModelRiwayat() {
    }

    public ModelRiwayat(String idFpb, String nama, String alamat, String kelurahan, String kecamatan, String kabupaten, String provinsi, String telp, String hp, String kendaraan, String portal, String rt, String rw, String penerima, String tanggal_kirim) {
        this.idFpb = idFpb;
        this.nama = nama;
        this.alamat = alamat;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
        this.telp = telp;
        this.hp = hp;
        this.kendaraan = kendaraan;
        this.portal = portal;
        this.rt = rt;
        this.rw = rw;
        this.penerima = penerima;
        this.tanggal_kirim = tanggal_kirim;
    }

    public String getIdFpb() {
        return idFpb;
    }

    public void setIdFpb(String idFpb) {
        this.idFpb = idFpb;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getTanggal_kirim() {
        return tanggal_kirim;
    }

    public void setTanggal_kirim(String tanggal_kirim) {
        this.tanggal_kirim = tanggal_kirim;
    }
}
