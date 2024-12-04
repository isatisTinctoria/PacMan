import java.util.Random;

public class Vitamin {
    public static final int EATEN = 0;
    public static final int GENERATED = 1;
    public static final int GENERATING = 2;
    public static final int VITAMIN_INTERVAL = 1000;
    public static final int VITAMIN_SHELFLIFE = 3000;
    public static final int VITAMIN_EXPIRATION = 1500;

    private Random random = new Random();
    public Skeleton box = new Skeleton();
    public int act, nextVitaminTick, shelfLife, expire;

    public void Init(){
        act = GENERATING;
        nextVitaminTick = VITAMIN_INTERVAL + random.nextInt(VITAMIN_INTERVAL<<1);
    }

    public Vitamin(){
        Init();
    }

    public void eaten(){
        act = EATEN;
        expire = VITAMIN_EXPIRATION;
    }

    public void refresh(int[][] board){
        switch(act){
        case GENERATING:{
            nextVitaminTick--;
            if(nextVitaminTick == 0){
                act = GENERATED;
                shelfLife = VITAMIN_SHELFLIFE;
                for(;;){
                    box.row = random.nextInt(Map.BOARD_H);
                    box.col = random.nextInt(Map.BOARD_W);
                    if(board[box.row][box.col] != 1){
                        break;
                    }
                }
            }
            break;
        }
        case GENERATED:{
            shelfLife--;
            if(shelfLife == 0){
                Init();
            }
            break;
        }
        case EATEN:{
            expire--;
            if(expire == 0){
                Init();
            }
            break;
        }
        }
    }
}
