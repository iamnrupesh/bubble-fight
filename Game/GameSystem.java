import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Write a description of class GameSystem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameSystem extends Actor
{
    public int pause = 50;
    private int speed = 4;
    private int seaY = 800;
    private int seaMinX = 200;
    private int seaMaxX = 900;

    private int timer = 60;
    private int second = 0;
    private long start = 0;
    private long current = 0;

    TimerMessage timerMessage;

    QAManager pqManager;

    public GameSystem(QAManager qa){
        pqManager = qa;
    }

    public void startGame(){
        second = timer;
        createTimerMessage(second);
        start = System.currentTimeMillis();
        generateQuestion();
    }

    public void generateQuestion(){
        pqManager.generateQuestion();
    }

    public boolean checkQuestion(Answer a){
        return pqManager.checkQuestion(a);
    }

    public void createTimerMessage(int s){
        timerMessage = new TimerMessage(s);
        getWorld().addObject(timerMessage,375,45);
    }

    public void act() 
    {
        //prepare coord

        if(pause>0)
            pause--;
        if(pause == 0)
        {
            // Add your action code here.

            Random r = new Random();
            int result = r.nextInt(seaMaxX-seaMinX) + seaMinX;
            Answer label = pqManager.generateAnswer();
            // System.out.println(label.getMessage());
            Bubble b = new Bubble(label, speed);
            getWorld().addObject(b,result,seaY);
            getWorld().addObject(label,result,seaY);
            pause = 50;

        }

        //timer
        if(start > 0 && System.currentTimeMillis() - start > current + 1000 && second > 0){
            current = System.currentTimeMillis() - start;

            second--;

            timerMessage.destroy();
            createTimerMessage(second);

        }
    }    
}
