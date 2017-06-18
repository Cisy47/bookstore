package Entity;

/**
 * Created by siqi.lou on 15/06/2017.
 */
public class Describe {
    Integer id;
    String des;

    public int getId() {
        return id;
    }

    public  String getIdStr(){
        return id.toString();
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Describe(int id, String des) {
        this.id = id;
        this.des = des;
    }

    public Describe(){}
}
