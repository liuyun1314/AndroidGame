package afinal.itc298.com.finalgamev01;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by jayke on 7/12/2017.
 */

public class EnemyBossMini extends GameObject {

    private Paint paint = new Paint();
    private Rect rect = new Rect(0,0,150,150);
    private Handler handler;
    private Bitmap bmp;
    Random r = new Random();
    private int width,height;
    private static final int BMP_ROWS = 4;//number of rows in spritesheet
    private static final int BMP_COLUMNS = 4;//number of columns
    private int srcX, srcY;//sprite position

    private int timer = 15;
    private int timer2 = 50;

    public EnemyBossMini(int x, int y, ID id, Handler handler, Bitmap bmp) {
        super(x, y, id);
        this.handler = handler;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        velX = 16;
        velY = 0;

    }

    public Rect getBounds() {
        return new Rect(rect);
    }

    @Override
    public void tick(){
        x+=velX;
        y+=velY;

        if(timer <= 0) velX = 0;//when timer is less than 0, bossEnemy will stop moving
        else timer--;

        if(timer <= 0) timer2--;
        if(timer2 <= 0){
            if(velY == 0) velY = 10;

            if(velY > 0) velY += 0.5f;
            else if(velY < 0) velY -= 0.5f;

            velY = GamePanel.clamp(velY, -40, 40);//clamps velocity of X so it does not got below -10 or above 10

            int spawn = r.nextInt(10);
            if(spawn == 0) handler.addObject(new EnemyBulletSpread((int) x+10, (int) y+5, ID.EnemyBulletSmart, handler, GamePanel.bmpBulletSpread));

        }

        //f(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;//if object hits ceiling or floor, reverse velocity
        if(y <= 0 || y >= Constants.SCREEN_HEIGHT - 40) velY *= -1;//if object hits sides, reverse velocity

    }

    @Override
    public void render(Canvas canvas) {

        srcX = 2 * width;//column of sprite
        srcY = 1 * height;//row of sprite

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);//getting section of sprite image
        Rect dst = new Rect((int)(x - rect.width()/2), (int)(y - rect.height()/2),(int)(x + rect.width()/2),(int)(y + rect.height()/2));

        canvas.drawBitmap(bmp,src,dst, null);

        //fill
        //paint.setStyle(Paint.Style.FILL);
        //paint.setColor(Color.MAGENTA);
        //canvas.drawRect(rect, paint);


        //sets position of rect, middle point is the center of rectangle
        rect.set((int)(x - rect.width()/2), (int)(y - rect.height()/2),(int)(x + rect.width()/2),(int)(y + rect.height()/2));
    }
}
