package net.shan.practice.copy;

import net.shan.practice.cache.ChessDO;

import java.util.Arrays;

/**
 * Created by jiangshan on 14-4-22.
 */
public class Copy {

    public static void main(String[] args){
        new Copy().test2();
    }

    private void test2(){
        ChessDO[][] a = new ChessDO[4][4];
        a[0][0] = new ChessDO();
        a[0][0].setX(1);
        a[0][0].setY(2);
        a[1][1] = new ChessDO();
        a[1][1].setX(3);
        a[1][1].setY(4);
        ChessDO[][] b = copyChesses(a);
        a[0][0].setX(10);
        a[0][0].setY(20);
        a[1][1].setX(30);
        a[1][1].setY(40);

        System.out.println("a " + a[0][0].getX() + " " + a[0][0].getY() + " " + a[1][1].getX() + " " + a[1][1].getY());

        System.out.println("b " + b[0][0].getX() + " " + b[0][0].getY() + " " + b[1][1].getX() + " " + b[1][1].getY());
    }

    private ChessDO[][] copyChesses(ChessDO[][] sources){
        ChessDO[][] targets = new ChessDO[sources.length][];
        for(int i=0; i<sources.length; i++){
            targets[i] = new ChessDO[sources[i].length];
            for(int j=0; j<sources[i].length; j++){
                ChessDO tmp = sources[i][j];
                if(tmp == null){
                    continue;
                }
                targets[i][j] = new ChessDO();
                targets[i][j].setX(sources[i][j].getX());
                targets[i][j].setY(sources[i][j].getY());
            }
        }
        return targets;
    }
}
