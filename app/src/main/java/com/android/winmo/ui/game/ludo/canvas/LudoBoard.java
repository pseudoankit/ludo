package com.android.winmo.ui.game.ludo.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class LudoBoard extends View {

    private Paint paintOutline, paintPath, boardOutlineBlack, p1, p2, p3, p4,pw;
    private int top, boxSize;

    public LudoBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        assign(canvas);
        drawBoard(canvas);
    }
//
//    private void drawCircles(Canvas canvas) {
//        canvas.drawCircle(3 * boxSize, top + 3 * boxSize, 3 * boxSize - 20, paintBigCircle);
//        canvas.drawCircle(12 * boxSize, top + 3 * boxSize, 3 * boxSize - 20, paintBigCircle);
//        canvas.drawCircle(3 * boxSize, bottom - 3 * boxSize, 3 * boxSize - 20, paintBigCircle);
//        canvas.drawCircle(12 * boxSize, bottom - 3 * boxSize, 3 * boxSize - 20, paintBigCircle);
//    }

    private void drawBoard(Canvas canvas) {

        //token outer box color
        canvas.drawRect(0, top, 6 * boxSize, top + 6 * boxSize, p1);//box1-frame--top-left
        canvas.drawRect(9 * boxSize, top, 15*boxSize, top + 6 * boxSize, p2);//box2-frame--top-right
        canvas.drawRect(0, top + 9 * boxSize, 6 * boxSize, top+15*boxSize, p3);//box3-frame--bottom-left
        canvas.drawRect(9 * boxSize, top + 9 * boxSize, 15*boxSize, top+15*boxSize, p4);//box4-frame--bottom-right

        //outer token box outline
        canvas.drawRect(0, top, 6 * boxSize, top + 6 * boxSize, paintOutline);//box1-frame--top-left-black-stroke
        canvas.drawRect(9 * boxSize, top, 15*boxSize, top + 6 * boxSize, paintOutline);//box2-frame--top-right-black-stroke
        canvas.drawRect(0, top + 9 * boxSize, 6 * boxSize, top+15*boxSize, paintOutline);//box3-frame--bottom-left
        canvas.drawRect(9 * boxSize, top + 9 * boxSize, 15*boxSize, top+15*boxSize, paintOutline);//box4-frame--bottom-right-black-stroke


        //token inner box color
        canvas.drawRect(boxSize, top+boxSize, 6 * boxSize-boxSize, top + 6 * boxSize-boxSize, pw);//box1-frame--top-left
        canvas.drawRect(9 * boxSize+boxSize, top+boxSize, 14*boxSize, top + 6 * boxSize-boxSize, pw);//box2-frame--top-right
        canvas.drawRect(boxSize, top + 9 * boxSize+boxSize, 6 * boxSize-boxSize, top+14*boxSize, pw);//box3-frame--bottom-left
        canvas.drawRect(9 * boxSize+boxSize, top + 9 * boxSize+boxSize, 14*boxSize, top+14*boxSize, pw);//box4-frame--bottom-right

        //inner token box outline
        canvas.drawRect(boxSize, top+boxSize, 6 * boxSize-boxSize, top + 6 * boxSize-boxSize, paintOutline);//box1-frame--top-left
        canvas.drawRect(9 * boxSize+boxSize+1, top+boxSize, 14*boxSize, top + 6 * boxSize-boxSize, paintOutline);//box2-frame--top-right
        canvas.drawRect(boxSize, top + 9 * boxSize+boxSize, 6 * boxSize-boxSize, top+14*boxSize, paintOutline);//box3-frame--bottom-left
        canvas.drawRect(9 * boxSize+boxSize, top + 9 * boxSize+boxSize, 14*boxSize, top+14*boxSize, paintOutline);//box4-frame--bottom-right

        for (int i = 0; i <= 5; i++) {
            //color and outline to each box in path

            canvas.drawRect(i * boxSize, top + 6 * boxSize, boxSize + i * boxSize, top + 7 * boxSize, paintPath);//left-top
            canvas.drawRect(i * boxSize, top + 8 * boxSize, boxSize + i * boxSize, top + 9 * boxSize, paintPath);//left-bottom
            canvas.drawRect(9 * boxSize + i * boxSize, top + 6 * boxSize, 10 * boxSize + i * boxSize, top + 7 * boxSize, paintPath);//right-top
            canvas.drawRect(9 * boxSize + i * boxSize, top + 8 * boxSize, 10 * boxSize + i * boxSize, top + 9 * boxSize, paintPath);//right-bottom

            canvas.drawRect(i * boxSize, top + 6 * boxSize, boxSize + i * boxSize, top + 7 * boxSize, paintOutline);//left-top
            canvas.drawRect(i * boxSize, top + 8 * boxSize, boxSize + i * boxSize, top + 9 * boxSize, paintOutline);//left-bottom
            canvas.drawRect(9 * boxSize + i * boxSize, top + 6 * boxSize, 10 * boxSize + i * boxSize, top + 7 * boxSize, paintOutline);//right-top
            canvas.drawRect(9 * boxSize + i * boxSize, top + 8 * boxSize, 10 * boxSize + i * boxSize, top + 9 * boxSize, paintOutline);//right-bottom

            canvas.drawRect(6 * boxSize, top + i * boxSize, 7 * boxSize, top + boxSize + i * boxSize, paintPath);//top-left
            canvas.drawRect(8 * boxSize, top + i * boxSize, 9 * boxSize, top + boxSize + i * boxSize, paintPath);//top-right
            canvas.drawRect(6 * boxSize, top + 9 * boxSize + i * boxSize, 7 * boxSize, top + 10 * boxSize + i * boxSize, paintPath);//bottom-left
            canvas.drawRect(8 * boxSize, top + 9 * boxSize + i * boxSize, 9 * boxSize, top + 10 * boxSize + i * boxSize, paintPath);//bottom-right

            canvas.drawRect(6 * boxSize, top + i * boxSize, 7 * boxSize, top + boxSize + i * boxSize, paintOutline);//top-left
            canvas.drawRect(8 * boxSize, top + i * boxSize, 9 * boxSize, top + boxSize + i * boxSize, paintOutline);//top-right
            canvas.drawRect(6 * boxSize, top + 9 * boxSize + i * boxSize, 7 * boxSize, top + 10 * boxSize + i * boxSize, paintOutline);//bottom-left
            canvas.drawRect(8 * boxSize, top + 9 * boxSize + i * boxSize, 9 * boxSize, top + 10 * boxSize + i * boxSize, paintOutline);//bottom-right

        }

        //boxes before home paths
        canvas.drawRect(0, top + 7 * boxSize, boxSize, top + 8 * boxSize, paintPath);//left-center-last
        canvas.drawRect(7 * boxSize, top + 14 * boxSize, 8 * boxSize, top + 15 * boxSize, paintPath);//bottom-center
        canvas.drawRect(14 * boxSize, top + 7 * boxSize, 15 * boxSize, top + 8 * boxSize, paintPath);//right-center
        canvas.drawRect(7 * boxSize, top, 8 * boxSize, top + boxSize, paintPath);//top-center

        //outline to boxes before home paths
        canvas.drawRect(0, top + 7 * boxSize, boxSize, top + 8 * boxSize, paintOutline);//left-center
        canvas.drawRect(7 * boxSize, top + 14 * boxSize, 8 * boxSize, top + 15 * boxSize, paintOutline);//bottom-center
        canvas.drawRect(14 * boxSize, top + 7 * boxSize, 15 * boxSize, top + 8 * boxSize, paintOutline);//right-center
        canvas.drawRect(7 * boxSize, top, 8 * boxSize, top + boxSize, paintOutline);//top-center

        //drawing home path
        for (int i = 0; i <= 4; i++) {
            canvas.drawRect(boxSize + i * boxSize, top + 7 * boxSize, 2 * boxSize + i * boxSize, top + 8 * boxSize, p1);//left-home-line
            canvas.drawRect(7 * boxSize, top + 9 * boxSize + i * boxSize, 8 * boxSize, top + 10 * boxSize + i * boxSize, p3);//bottom-home-line
            canvas.drawRect(9 * boxSize + i * boxSize, top + 7 * boxSize, 10 * boxSize + i * boxSize, top + 8 * boxSize, p4);//right-home-line
            canvas.drawRect(7 * boxSize, top + boxSize + i * boxSize, 8 * boxSize, top + 2 * boxSize + i * boxSize, p2);//top-home-line

            canvas.drawRect(boxSize + i * boxSize, top + 7 * boxSize, 2 * boxSize + i * boxSize, top + 8 * boxSize, paintOutline);//left-home-line
            canvas.drawRect(7 * boxSize, top + 9 * boxSize + i * boxSize, 8 * boxSize, top + 10 * boxSize + i * boxSize, paintOutline);//bottom-home-line
            canvas.drawRect(9 * boxSize + i * boxSize, top + 7 * boxSize, 10 * boxSize + i * boxSize, top + 8 * boxSize, paintOutline);//right-home-line
            canvas.drawRect(7 * boxSize, top + boxSize + i * boxSize, 8 * boxSize, top + 2 * boxSize + i * boxSize, paintOutline);//top-home-line
        }

        //1st place of each token
        canvas.drawRect(6 * boxSize+1, top + 13 * boxSize+1, 7 * boxSize-1, top + 14* boxSize-1, p3);//3
        canvas.drawRect(8 * boxSize+1, top + boxSize+1, 9 * boxSize-1, top + 2 * boxSize-1, p2);//2
        canvas.drawRect(13 * boxSize+1, top + 8 * boxSize+1, 14 * boxSize-1 , top + 9 * boxSize-1, p4);//4
        canvas.drawRect( boxSize+1, top + 6 * boxSize+1, 2 * boxSize-1, top + 7 * boxSize-1, p1);//1


    }

    private void assign(Canvas canvas) {

        int width = getResources().getDisplayMetrics().widthPixels;
        int hgt = getResources().getDisplayMetrics().heightPixels;
        boxSize = width / 15;
        top = (hgt - width) / 2;
        int bottom = (hgt + width) / 2;

        Log.d(TAG, "assign: canvas" + width +","+ hgt+"," + boxSize+","+top+","+bottom);

        p1 = new Paint();
        p2 = new Paint();
        p3 = new Paint();
        p4 = new Paint();
        pw = new Paint();
        p1.setColor(Color.YELLOW);
        p2.setColor(Color.BLUE);
        p3.setColor(Color.GREEN);
        p4.setColor(Color.RED);
        pw.setColor(Color.WHITE);

        paintOutline = new Paint();            //for drawing black outline
        paintOutline.setStrokeWidth(2f);
        paintOutline.setColor(Color.BLACK);
        paintOutline.setStyle(Paint.Style.STROKE);

        paintPath = new Paint();        //drawing
        paintPath.setColor(Color.WHITE);
        paintPath.setStyle(Paint.Style.FILL);

        boardOutlineBlack = new Paint();       //for background color of board
        boardOutlineBlack.setColor(Color.BLACK);
        boardOutlineBlack.setStrokeWidth(5f);
        boardOutlineBlack.setStyle(Paint.Style.STROKE);
    }
}
