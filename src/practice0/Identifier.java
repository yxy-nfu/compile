package practice0;

public class Identifier {
    private int kind;//自定义类型
    private String name;//标识符名
    private String kind_id; //标识符类型
    private int number;//值
    private long number1;
    public Identifier(int kind,String name,String kind_id){
        this.kind=kind;
        this.name=name;
        this.kind_id=kind_id;
    }

    public Identifier(int kind,String name,String kind_id,int number){
        this.kind=kind;
        this.name=name;
        this.kind_id=kind_id;
        this.number=number;
    }
    public Identifier(int kind,String name,String kind_id,long number1){
        this.kind=kind;
        this.name=name;
        this.kind_id=kind_id;
        this.number1=number1;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind_id() {
        return kind_id;
    }

    public void setKind_id(String kind_id) {
        this.kind_id = kind_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNumber1(long number1) {
        this.number1 = number1;
    }

    public long getNumber1() {
        return number1;
    }
}
