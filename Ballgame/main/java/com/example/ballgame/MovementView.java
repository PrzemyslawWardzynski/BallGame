package com.example.ballgame;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;




public class MovementView extends SurfaceView implements SurfaceHolder.Callback {

    private boolean endFlag = false;
    private Activity parent;
    private Context parento;
    private long startTime;
    private float endTime;

    private int xPos;
    private int yPos;


    private int xVel=0;
    private int yVel=0;

    private int width;
    private int height;



    private int circleRadius;
    private Paint circlePaint;

    private Rect obstacle;
    private Rect obstacle2;

    private Bitmap bmp;
    private Bitmap obst;
    private Bitmap obst2;
    private Bitmap ball;

    UpdateThread updateThread;

    public MovementView(Context context, Activity parent) {

        super(context);
        getHolder().addCallback(this);
        this.parent = parent;
        parento = context;
        startTime = System.currentTimeMillis();
        circleRadius = 30;
        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);

        ball = BitmapFactory.decodeResource(getResources(),R.drawable.maybeballo);
        ball = Bitmap.createScaledBitmap(ball,60,60,true);

        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.sbg2c);
        obst = BitmapFactory.decodeResource(getResources(),R.drawable.obstacle1);
        obst2 = BitmapFactory.decodeResource(getResources(),R.drawable.obstacle2);


    }
    @Override
    protected void onDraw(Canvas canvas) {

        if(canvas != null) {
            canvas.drawBitmap(bmp, 0,0,circlePaint);
            if (!endFlag) canvas.drawBitmap(ball,xPos-circleRadius,yPos-circleRadius,circlePaint);
            canvas.drawBitmap(obst,80,height/3,circlePaint);
            canvas.drawBitmap(obst2, 240+ (width-160)/8, height*2/3,circlePaint);



        }
    }

    public void updatePhysics() {
        xPos += xVel;
        yPos += yVel;

        int width0 = 80;
        int widthE = width - 80;
        int heigth0 = 66;
        int heightE = height-66;
        if (yPos - circleRadius < heigth0 || yPos + circleRadius > heightE) {
            if (yPos - circleRadius < heigth0) {
                yPos = circleRadius + heigth0;
            }else{
                yPos = heightE - circleRadius;
            }

        }
        if (xPos - circleRadius < width0 || xPos + circleRadius > widthE) {
            if (xPos - circleRadius < width0) {
                xPos = circleRadius + width0;
            } else {
                xPos = widthE - circleRadius;
            }

        }
        //OBST1
        //OBST BOTTOM
        if (yPos - circleRadius < obstacle.bottom && yPos - circleRadius > obstacle.top){
            if(xPos > obstacle.left && xPos < obstacle.right){
                yPos = obstacle.bottom + circleRadius;
            }
        }
        //OBST TOP
        if (yPos + circleRadius > obstacle.top && yPos + circleRadius < obstacle.bottom){
            if(xPos > obstacle.left && xPos < obstacle.right){
                yPos = obstacle.top - circleRadius;
            }
        }
        //OBST LEFT
        if (xPos + circleRadius > obstacle.left && xPos < obstacle.right){
            if(yPos > obstacle.top && yPos < obstacle.bottom){
                xPos = obstacle.left - circleRadius;
            }
        }

        if (xPos - circleRadius < obstacle.right && xPos > obstacle.left){
            if(yPos > obstacle.top && yPos < obstacle.bottom){
                xPos = obstacle.right + circleRadius;
            }
        }


        //OBST2
        //OBST2 BOTTOM
        if (yPos - circleRadius < obstacle2.bottom && yPos - circleRadius > obstacle2.top){
            if(xPos < obstacle2.right){
                yPos = obstacle2.bottom + circleRadius;
            }
        }
        //OBST2 TOP
        if (yPos + circleRadius > obstacle2.top && yPos + circleRadius < obstacle2.bottom){
            if(xPos < obstacle2.right){
                yPos = obstacle2.top - circleRadius;
            }
        }
        //OBST2 RIGHT
        if (xPos - circleRadius < obstacle2.right){
            if(yPos > obstacle2.top && yPos < obstacle2.bottom){
                xPos = obstacle2.right + circleRadius;
            }
        }
        //END HOLE
        if((xPos-100)*(xPos-100) + (yPos-100)*(yPos-100) < 40*40){
            endFlag = true;
            endTime = (System.currentTimeMillis() - startTime)/1000F;
            updateThread.setRunning(false);
            xPos = 100;
            circleRadius = 0;
            yPos = heightE/3+30;
        }
        if(endFlag == true){

            Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
            gameOverIntent.putExtra("timeScore", endTime);
            getContext().startActivity(gameOverIntent);
            parent.finish();
            endFlag = false;
        }

    }
    public void changeVelocity(int xVel, int yVel){
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        bmp = Bitmap.createScaledBitmap(bmp, width, height, true);

        int width0 = width - 160;
        obst = Bitmap.createScaledBitmap(obst,width0*3/4,105,true);
        obstacle2 = new Rect(80, height/3,80 + width0*3/4, height/3 + 105);

        width0 /=2;

        obst2 = Bitmap.createScaledBitmap(obst2,width0*3/4,105,true);

        obstacle = new Rect(240 + width0/4,height*2/3,width0 + 240,height*2/3 + 105);



        Log.d("rozmiar",width + " " + height);
        xPos = width / 2;
        yPos = height - 5*circleRadius;

        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d("barulla", "surfaceDestroyed: ");
        boolean retry = true;

        updateThread.setRunning(false);
        while (retry) {
            try {
                updateThread.join();
                retry = false;

            } catch (InterruptedException e) {
            }
        }


    }
}