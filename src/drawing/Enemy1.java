package drawing;

public class Enemy1 extends Enemy implements Runnable{
    
    public Enemy1(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 20;
        health = 10;        
    }
}
