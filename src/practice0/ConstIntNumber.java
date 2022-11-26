package practice0;

public class ConstIntNumber {
    private int kind;
    private int num;

    public ConstIntNumber(int kind,int num)
    {
        this.kind=kind;
        this.num=num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }
}
