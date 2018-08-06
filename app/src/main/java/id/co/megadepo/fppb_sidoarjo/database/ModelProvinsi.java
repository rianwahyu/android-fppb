package id.co.megadepo.fppb_sidoarjo.database;

public class ModelProvinsi {
     int id;
     String name;



    public ModelProvinsi(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModelProvinsi() {
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
