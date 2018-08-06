package id.co.megadepo.fppb_sidoarjo.database;

public class ModelKelurahan {
     int id;
     String name;



    public ModelKelurahan(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModelKelurahan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
