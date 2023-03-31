package at.htl.leoben.helper;

public class GridSpace implements Comparable<Integer> {

    private boolean isOccupied;
    private int appleCount;
    private int xStart;
    private int yStart;
    private int xEnd;

    private final char symbol = 'A';


    private int yEnd;

    public GridSpace(int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }



    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getAppleCount() {
        return appleCount;
    }

    public void setAppleCount(int appleCount) {
        this.appleCount = appleCount;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    @Override
    public int compareTo(Integer appleInOtherGridCount) {
        return appleInOtherGridCount.compareTo(this.appleCount);
    }

    public Object getSymbol() {
        return symbol;
    }
}
