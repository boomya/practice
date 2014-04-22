package net.shan.practice.cache;

/**
 * Created by jiangshan on 14-4-22.
 */
public class ChessDO {
    public int x = 0;
    public int y = 0;
    public int value = 0;
    /**
     * 如果发生合并操作,则step的值为当前棋盘的step,只有step的值小于当前step的时候才可以合并
     */
    public int step = 0;
//    /**
//     * 通过(上,右,下,左)操作
//     */
//    public byte operate = 'u'; //u r d l

    public int getX() {

        return x;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}

