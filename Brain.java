import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Brain extends Map implements KeyListener{
    public static final int SIZE =                  5;
    public static final int CHANCES =               6;
    public static final int INF =                   0x7f7f7f7f;
    public static final int SCORE_BEAN =            10;
    public static final int SCORE_GHOST =           20;
    public static final int SCORE_VITAMIN =         50;
    public static final int GHOST_INTERVAL =        200;
    public static final int TURN_SENSITIVITY =      8;
    public static final double BOUNCE_SENSITIVITY = 0;// zero -> no vibration

    public static final int GAMEOVER =              1;
    public static final int DESKTOP =               1000;
    public static final int PLAY =                  1000000;
    public static final int RANK =                  1000001;
    public static final int HELP =                  1000002;
    public static final int MUSIC =                 1000003;
    public static final int RANKWINDOW =            1001;
    public static final int HELPWINDOW =            1002;
    public static final int MUSICSELECTING =        1003;
    public static final int MODESELECTING =         1004;
    public static final int EASY =                  1004000;
    public static final int NORMAL =                1004001;
    public static final int HARD =                  1004002;
    public static final int IMPOSSIBLE =            1004003;
    public static final int DEAD =                  1005;
    public static final int GAMING =                2;
    public static final int PREYED =                2000;
    public static final int PREYING =               2001;

    public static boolean paused;
    public static int desktop, difficulty;
    public static int chapter, section, level, chances, score;

    public static Random random = new Random();
    public static Vitamin vitamin = new Vitamin();

    public static Cell[] group = new Cell[SIZE];
    public static int[] lifecycle = new int[SIZE];
    public static boolean[] turned = new boolean[SIZE];
    public static Skeleton[] locate = new Skeleton[SIZE];

    public void Init(){
        Reflex.Init();
        Memory.read();
        for(int i = 0; i < SIZE; i++){
            locate[i] = new Skeleton();
        }
        chapter = GAMEOVER;
        section = DESKTOP;
        desktop = PLAY;
        loadMap(1);
    }

    @Override
    public void keyPressed(KeyEvent e){
        Reflex.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e){
        Reflex.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    public static void initGaming(){
        section = PREYED;
        score = 0;
        level = 1;
        switch(difficulty){
        case EASY:{
            chances = INF;          break;}
        case NORMAL:{
            chances = CHANCES;      break;}
        case HARD:{
            chances = CHANCES>>1;   break;}
        case IMPOSSIBLE:{
            chances = 1;                  }
        }
        paused = false;
        loadLevel(1);
    }

    public static void endGaming(){
        if(Hearing.clip != null){ // song is 0
            Hearing.clip.stop();
        }
        chapter = GAMEOVER;
        section = DEAD;
        Memory.tempName = "";
    }

    public static void loadLevel(int n){
        loadMap(n);
        vitamin.Init();
        for(int i = 0; i < SIZE; i++){
            group[i] = new Cell(Gene.TYPE[n-1][i], Gene.SPEED[n-1][i]);
            turned[i] = false;
            lifecycle[i] = -1;
            group[i].Init(board, 0);
        }
        switch(Hearing.mode){
        case 1:{
            Hearing.play(); break;}
        default:{
            if(n == 1){
            Hearing.play();}      }
        }
    }

    public static void grow(){
        for(int i = 0; i < SIZE; i++){
            lifecycle[i]--;
            if(lifecycle[i] < -1){
                lifecycle[i] = -1;
            }
            if(i != Gene.PACMAN){
                if(lifecycle[i] == 0){
                    group[i].Init(board, 0);
                    group[i].visible = true;
                }
                if(lifecycle[i] > 0){
                    group[i].alive = false;
                }
            }
        }
    }

    public static void feed(){
        vitamin.refresh(board);
        if(vitamin.act == Vitamin.EATEN){
            section = PREYING;
        }else{
            section = PREYED;
        }
        int r = locate[0].row, c = locate[0].col;
        if(board[r][c] == BEAN){
            rest--;
            score += SCORE_BEAN;
            board[r][c] = VACCUM;
            if(rest == 0){
                level++;
                if(level == 6){
                    endGaming();
                }else{
                    loadLevel(level);
                }
            }
        }
        if(
            vitamin.act == Vitamin.GENERATED &&
            r == vitamin.box.row && c == vitamin.box.col
        ){
            vitamin.eaten();
            score += SCORE_VITAMIN;
        }
        for(int i = 1; i < SIZE; i++){
            if(group[i].alive && group[Gene.PACMAN].box.intersect(group[i].box)){
                switch(section){
                case PREYED:{
                    if(group[0].invincibleTick > 0){
                        break;
                    }
                    chances--;
                    if(chances == 0){
                        endGaming();
                    }else{
                        group[Gene.PACMAN].Init(board, 0);
                    }break;
                }
                case PREYING:{
                    lifecycle[i] = GHOST_INTERVAL;
                    group[i].visible = false;
                    score += SCORE_GHOST;
                }
                }
            }
        }
    }

    public static void moveGroup(){
        for(Cell cur : group){
            cur.refresh();
        }
    }

    public static void relocate(){
        for(int i = 0; i < SIZE; i++){
            locate[i].row = (int)(group[i].y / BLOCK_W);
            locate[i].col = (int)(group[i].x / BLOCK_W);
            locate[i].xc = locate[i].col * BLOCK_W + BLOCK_W/2;
            locate[i].yc = locate[i].row * BLOCK_W + BLOCK_W/2;
        }
    }

    public static void collisionTest(){
        for(int i = 0; i < SIZE; i++){
            boolean flag = false;
            switch(group[i].direction){
            case RIGHT:{
                if(group[i].x - locate[i].xc > BOUNCE_SENSITIVITY){
                    flag = true;
                }break;
            }
            case TOP:{
                if(locate[i].yc - group[i].y > BOUNCE_SENSITIVITY){
                    flag = true;
                }break;
            }
            case LEFT:{
                if(locate[i].xc - group[i].x > BOUNCE_SENSITIVITY){
                    flag = true;
                }break;
            }
            case BOTTOM:{
                if(group[i].y - locate[i].yc > BOUNCE_SENSITIVITY){
                    flag = true;
                }
            }
            }
            int nrow = locate[i].row + DY[group[i].direction];
            int ncol = locate[i].col + DX[group[i].direction];
            if(board[nrow][ncol] != BRICK){
                flag = false;
            }
            if(flag){
                group[i].x = locate[i].xc;
                group[i].y = locate[i].yc;
            }
        }
    }

    public static void target(int i, int rt, int ct){
        int rs = locate[i].row;
        int cs = locate[i].col;
        List<Integer> directions = new ArrayList<>();
        for(int k = 0; k < 4; k++){
            if(board[rs + DY[k]][cs + DX[k]] != BRICK){
                directions.add(k);
            }
        }
        int x = (int)group[i].x;
        int y = (int)group[i].y;
        if(
            directions.size() == 2 &&
            (directions.get(0)+2)%4 == directions.get(1)
        ){
            group[i].direction = search(rs, cs, rt, ct, 0);
            return;
        }
        if(x == locate[i].xc && y == locate[i].yc && turned[i] == false){
            turned[i] = true;
            group[i].direction = search(rs, cs, rt, ct, 0);
        }
        if((x != locate[i].xc || y != locate[i].yc) && turned[i] == true){
            turned[i] = false;
        }
    }

    public static void drunk(int i){
        int x = (int)group[i].x;
        int y = (int)group[i].y;
        if(x == locate[i].xc && y == locate[i].yc && turned[i] == false){
            turned[i] = true;
            List<Integer> directions = new ArrayList<>();
            for(int k = 0; k < 4; k++){
                if(k == (group[i].direction + 2) % 4){
                    continue;
                }
                int nrow = locate[i].row + DY[k];
                int ncol = locate[i].col + DX[k];
                if(board[nrow][ncol] != 1){
                    directions.add(k);
                }
            }
            int idx = random.nextInt(directions.size());
            group[i].direction = directions.get(idx);
        }
        if((x != locate[i].xc || y != locate[i].yc) && turned[i] == true){
            turned[i] = false;
        }
    }

    public static void redirect(){
        for(int i = 0; i < SIZE; i++){
            switch(group[i].type){
            case Gene.PACMAN:{
                if(Reflex.pressed(KeyEvent.VK_RIGHT)){
                    int nrow = locate[i].row;
                    int ncol = locate[i].col + 1;
                    if(board[nrow][ncol] != BRICK &&
                       Math.abs(group[i].y - locate[i].yc) < TURN_SENSITIVITY){
                        if(group[i].direction == TOP || group[i].direction == BOTTOM){
                            group[i].y = locate[i].yc;
                        }
                        group[i].direction = RIGHT;
                    }
                }
                if(Reflex.pressed(KeyEvent.VK_UP)){
                    int nrow = locate[i].row - 1;
                    int ncol = locate[i].col;
                    if(board[nrow][ncol] != BRICK &&
                       Math.abs(group[i].x - locate[i].xc) < TURN_SENSITIVITY){
                        if(group[i].direction == LEFT || group[i].direction == RIGHT){
                            group[i].x = locate[i].xc;
                        }
                        group[i].direction = TOP;
                    }
                }
                if(Reflex.pressed(KeyEvent.VK_LEFT)){
                    int nrow = locate[i].row;
                    int ncol = locate[i].col - 1;
                    if(board[nrow][ncol] != BRICK &&
                       Math.abs(group[i].y - locate[i].yc) < TURN_SENSITIVITY){
                        if(group[i].direction == TOP || group[i].direction == BOTTOM){
                            group[i].y = locate[i].yc;
                        }
                        group[i].direction = LEFT;
                    }
                }
                if(Reflex.pressed(KeyEvent.VK_DOWN)){
                    int nrow = locate[i].row + 1;
                    int ncol = locate[i].col;
                    if(board[nrow][ncol] != BRICK &&
                       Math.abs(group[i].x - locate[i].xc) < TURN_SENSITIVITY){
                        if(group[i].direction == LEFT || group[i].direction == RIGHT){
                            group[i].x = locate[i].xc;
                        }
                        group[i].direction = BOTTOM;
                    }
                }
                break;
            }
            case Gene.RED_GHOST:{
                target(i, locate[0].row, locate[0].col);
                break;
            }
            case Gene.PINK_GHOST:{
                int pos = nextCorner(locate[0].row, locate[0].col, group[0].direction);
                int rt = pos / BOARD_W;
                int ct = pos % BOARD_W;
                target(i, rt, ct);
                break;
            }
            case Gene.BLUE_GHOST:{
                int rt = (locate[0].row<<1) - locate[i].row;
                int ct = (locate[0].col<<1) - locate[i].col;
                if(inMap(rt, ct)){
                    int pos = diffuse(rt, ct);
                    rt = pos / BOARD_W;
                    ct = pos % BOARD_H;
                    target(i, rt, ct);
                }
                else{
                    target(i, locate[0].row, locate[0].col);
                }
                break;
            }
            case Gene.ORANGE_GHOST:{
                if(
                    Math.abs(locate[0].row - locate[i].row) <= 5 &&
                    Math.abs(locate[0].col - locate[i].col) <= 5 
                ){
                    drunk(i);
                }
                else{
                    target(i, locate[0].row, locate[0].col);
                }
                break;
            }
            default:{
                drunk(i);
            }
            }
        }
    }

    public static void frozen(){
        if(Reflex.typed(KeyEvent.VK_P)){
            paused = !paused;
            if(paused == true){
                Hearing.pause();
            }else{
                Hearing.resume();
            }
        }
    }

    public static void reflection(){
        grow();
        feed();
    }

    public static void motion(){
        moveGroup();
        relocate();
        collisionTest();
        redirect();
    }

    public static void runDead(){
        if(Reflex.typed(KeyEvent.VK_ENTER)){
            Memory.insert(difficulty, score);
            chapter = GAMING;
            initGaming();
        }
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            Memory.insert(difficulty, score);
            section = MODESELECTING;
        }
        if(difficulty != EASY && level == 6){
            if(Reflex.typed(KeyEvent.VK_BACK_SPACE)){
                if(Memory.tempName.length() != 0){
                    Memory.tempName = Memory.tempName.substring(0, Memory.tempName.length()-1);
                } 
            }
            for(int VK_ALPHA = 65; VK_ALPHA <= 90; VK_ALPHA++){
                if(Reflex.typed(VK_ALPHA)){
                    Memory.tempName += (char)(VK_ALPHA);
                }
            }
        }
    }

    public static void runDesktop(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            System.exit(0);
        }
        if(Reflex.typed(KeyEvent.VK_ENTER)){
            difficulty = EASY;
            switch(desktop){
            case PLAY:{
                section = MODESELECTING;break;}
            case RANK:{
                section = RANKWINDOW;   break;}
            case HELP:{
                section = HELPWINDOW;   break;}
            case MUSIC:{
                section = MUSICSELECTING;     }
            }
        }
        if(Reflex.typed(KeyEvent.VK_LEFT)){
            desktop = (desktop - PLAY + 3) % 4 + PLAY;
        }
        if(Reflex.typed(KeyEvent.VK_RIGHT)){
            desktop = (desktop - PLAY + 1) % 4 + PLAY;
        }
    }

    public static void runRankWindow(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            section = DESKTOP;
        }
    }

    public static void runHelpWindow(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            section = DESKTOP;
        }
    }

    public static void runModeSelecting(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            desktop = PLAY;
            section = DESKTOP;
        }
        if(Reflex.typed(KeyEvent.VK_ENTER)){
            chapter = GAMING;
            initGaming();
        }
        if(Reflex.typed(KeyEvent.VK_LEFT)){
            difficulty = (difficulty - EASY + 3) % 4 + EASY;
        }
        if(Reflex.typed(KeyEvent.VK_RIGHT)){
            difficulty = (difficulty - EASY + 1) % 4 + EASY;
        }
    }

    public static void runMusicSelecting(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            section = DESKTOP;
        }
        if(Reflex.typed(KeyEvent.VK_LEFT)){
            Hearing.mode = (Hearing.mode + 9) % 10;
        }
        if(Reflex.typed(KeyEvent.VK_RIGHT)){
            Hearing.mode = (Hearing.mode + 1) % 10;
        }
    }

    public static void runPaused(){
        if(Reflex.typed(KeyEvent.VK_ESCAPE)){
            chapter = GAMEOVER;
            section = MODESELECTING;
        }
    }

    public static void refresh(){
        Reflex.refresh();
        switch(chapter){
        case GAMEOVER:{
            switch(section){
            case DEAD:{
                runDead();          break;}
            case DESKTOP:{
                runDesktop();       break;}
            case RANKWINDOW:{
                runRankWindow();    break;}
            case HELPWINDOW:{
                runHelpWindow();    break;}
            case MODESELECTING:{
                runModeSelecting(); break;}
            case MUSICSELECTING:{
                runMusicSelecting();      }
            }break;
        }
        case GAMING:{
            frozen();
            if(!paused){
                reflection();
                motion();
            }
            else{
                runPaused();
            }
        }
        }
    }
}
