import java.util.Random;

public class Cell{
    public static final int STYLESPEED = 20;

    public boolean alive = true;
    public boolean visible = true;
    public boolean positive = true;
    private Random random = new Random();
    public Skeleton box = new Skeleton();
    public int type = 0, direction = 0, style = 0, styleTick = 0;
    public double x = 0, y = 0, w = 10, h = 10, v, vx = 0, vy = 0, invincibleTick = -1;

    public Cell(int type, double v){
        this.type = type;
        this.v = v;
    }

    public void setBox(){
        box.xc = x;
        box.yc = y;
        box.left = x - w;
        box.top = y - h;
        box.right = x + w;
        box.bottom = y + h;
    }

    public void Init(int[][] board, int direction){
        alive = true;
        if(type == Gene.PACMAN){
            invincibleTick = 20;
            this.x = Map.box[8][8].xc;
            this.y = Map.box[8][8].yc;
        }
        else{
            for(;;){
                int row = random.nextInt(Map.BOARD_H);
                int col = random.nextInt(Map.BOARD_W);
                if(board[row][col] != Map.BRICK){
                    this.x = Map.box[row][col].xc;
                    this.y = Map.box[row][col].yc;
                    break;
                }
            }
        }
        this.direction = direction;
        if(direction == 0){
            this.positive = true;
        }
        else{
            this.positive = false;
        }
        this.setBox();
    }

    public void setV(){
        switch(direction){
        case 0:{
            vx = v;  vy = 0;  positive = true;  break;}
        case 1:{
            vx = 0;  vy = -v;                   break;}
        case 2:{
            vx = -v; vy = 0;  positive = false; break;}
        case 3:{
            vx = 0;  vy = v;                          }
        }
    }

    public void move(){
        x += vx;
        y += vy;
    }

    public void nextStyle(){
        styleTick = (styleTick + 1) % STYLESPEED;
        if(styleTick == 0){
            style = (style + 1) % 4;
        }
    }

    public void refresh(){
        if(alive){
            setV();
            move();
            setBox();
            nextStyle();
            if(type == Gene.PACMAN){
                if(invincibleTick >= 0){
                    invincibleTick -= 0.05;
                }
            }
        }
    }
}