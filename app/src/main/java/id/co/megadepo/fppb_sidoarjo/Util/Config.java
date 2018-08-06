package id.co.megadepo.fppb_sidoarjo.Util;

public class Config {
//    public static String Url="http://152317301379.ip-dynamic.com:8007";

    public static String Url="http://138.138.39.7";

    public static String Lokasi;

    public static String Title;

    public static String TextDepo;

    public static float lattitude;

    public static float lontitude;

    public static String getLokasi() {
        return Lokasi;
    }

    public static void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public static String getUrl() {
        return Url;
    }

    public static void setUrl(String url) {
        Url = url;
    }

    public static String getTitle() {
        return Title;
    }

    public static void setTitle(String title) {
        Title = title;
    }

    public static String getTextDepo() {
        return TextDepo;
    }

    public static void setTextDepo(String textDepo) {
        TextDepo = textDepo;
    }

    public static float getLattitude() {
        return lattitude;
    }

    public static void setLattitude(float lattitude) {
        Config.lattitude = lattitude;
    }

    public static float getLontitude() {
        return lontitude;
    }

    public static void setLontitude(float lontitude) {
        Config.lontitude = lontitude;
    }
}
