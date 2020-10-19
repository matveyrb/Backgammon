package sample.model;

public class Slot {
    private int count;
    private Color color = Color.NULL;


    private Slot() {

    }
    private Slot(int amount, Color color) {
        this.count = amount;
        this.color = color;
    }
    public static Slot createEmptySlot() {
        return new Slot();
    }
    public static Slot createFullWhiteSlot() {
        return new Slot(15,Color.WHITE);
    }
    public static Slot createFullBlackSlot() {
        return new Slot(15,Color.BLACK);
    }

    public void setSlot(int count, Color color) {
        this.count = count;
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public Color getColor() {
        return color;
    }

    public boolean isBlack(){
        return this.color == Color.BLACK;
    }

    public boolean isWhite() {return  this.color == Color.WHITE;}

    public boolean addChecker(Color color) {
        if (!this.color.equals(Color.NULL) && !color.equals(this.color) ) {
            return false;
        }
        if (this.color.equals(Color.NULL)) {
            this.color = color;
        }
        count++;
        return true;
    }

    public void removeChecker() {
        count--;
        color = count == 0? Color.NULL: color;
    }
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof Slot))
            return false;
        Slot other = (Slot) o;
        return other.color == color && other.count == count;
    }
}
