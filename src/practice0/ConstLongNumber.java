package practice0;

public class ConstLongNumber {
    private  int kind;
    private long number;
    public ConstLongNumber(int kind,long number) {
        this.kind=kind;
        this.number=number;
    }
    public void setNumber(long number) {
        this.number = number;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public long getNumber() {
        return number;
    }
}
