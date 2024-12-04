import java.awt.*;
import javax.swing.*;

public class Vision extends JPanel{
    public static final int RENDER_W = 10;
    public static final int WINDOW_W = Map.BLOCK_W * Map.BOARD_W;
    public static final int WINDOW_H = Map.BLOCK_W * Map.BOARD_H;

    public static void renderBackGround(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_W, WINDOW_H);
    }

    public static void renderDesktop(Graphics g){
        g.setColor(Color.MAGENTA);
        g.drawString("*PACMAN*", 214, 260);
        for(int i = Brain.PLAY; i <= Brain.MUSIC; i++){
            int x, y;
            String s;
            switch(i){
            case Brain.PLAY:{
                x = 100; y = 200; s = "PLAY";   break;}
            case Brain.RANK:{
                x = 368; y = 200; s = "RANK";   break;}
            case Brain.HELP:{
                x = 100; y = 320; s = "HELP";   break;}
            default:{
                x = 364; y = 320; s = "MUSIC";        }
            }
            if(i == Brain.desktop){
                g.setColor(Color.YELLOW);
            }
            else{
                g.setColor(Color.MAGENTA);
            }
            g.drawString(s, x, y);
        }
    }

    public static void renderModeSelecting(Graphics g){
        g.setColor(Color.MAGENTA);
        g.drawString("*DIFFICULTY*", 195, 260);
        for(int i = Brain.EASY; i <= Brain.IMPOSSIBLE; i++){
            int x, y;
            String s;
            switch(i){
            case Brain.EASY:{
                x = 100; y = 200; s = "EASY";    break;}
            case Brain.NORMAL:{
                x = 359; y = 200; s = "NORMAL";  break;}
            case Brain.HARD:{
                x = 100; y = 320; s = "HARD";    break;}
            default:{
                x = 340; y = 320; s = "IMPOSSIBLE";    }
            }
            if(i == Brain.difficulty){
                g.setColor(Color.YELLOW);
            }
            else{
                g.setColor(Color.MAGENTA);
            }
            g.drawString(s, x, y);
        }
    }

    public static void renderMusicSelecting(Graphics g){
        for(int i = 0; i < 10; i++){
            int x = 45, y = 20 + 30*(i+1);
            if(i == Hearing.mode){
                g.setColor(Color.YELLOW);
            }else{
                g.setColor(Color.MAGENTA);
            }
            g.drawString(Hearing.SONG[i], x, y);
        }
    }

    public static void renderDeadWindow(Graphics g){
        if(Brain.level == 6){
            g.setColor(new Color(129, 216, 207));
            String s1 = "CONGRATULATIONS!";
            String s2 = "score: " + String.valueOf(Brain.score);
            String s3 = "type your name: " + Memory.tempName;
            String s4 = "press [Enter] to retry";
            g.setFont(new Font("Minecraft", Font.BOLD, 28));
            g.drawString(s1, 92, 100);
            g.setFont(new Font("Minecraft", Font.BOLD, 14));
            g.drawString(s2, 210, 200);
            g.drawString(s3, 160, 230);
            g.drawString(s4, 160, 260);
        }else{
            g.setColor(Color.ORANGE);
            String s2 = "score: " + String.valueOf(Brain.score);
            String s4 = "press [Enter] to retry";
            g.drawString(s2, 210, 200);
            g.drawString(s4, 160, 260);
        }
    }

    public static void renderRankWindow(Graphics g){
        int count;
        g.setColor(Color.GREEN);
        g.drawString("*NORMAL*", 63, 110);
        g.drawString("*HARD*", 225, 110);
        g.drawString("*IMPOSSIBLE*", 345, 110);
        g.drawLine(30, 120, 480, 120);
        g.drawLine(180, 90, 180, 420);
        g.drawLine(330, 90, 330, 420);
        count = 1;
        for(Memory.info m : Memory.normal){
            g.drawString(String.valueOf(m.score)+" "+m.name, 45, 110+count*30);
            count++; if(count > 10) break;
        }
        count = 1;
        for(Memory.info m : Memory.hard){
            g.drawString(String.valueOf(m.score)+" "+m.name, 195, 110+count*30);
            count++; if(count > 10) break;
        }
        count = 1;
        for(Memory.info m : Memory.impossible){
            g.drawString(String.valueOf(m.score)+" "+m.name, 345, 110+count*30);
            count++; if(count > 10) break;
        }
    }

    public static void renderHelpWindow(Graphics g){
        g.setColor(Color.GREEN);
        g.drawString("*CONTROL*", 88, 110);
        g.drawString("*REWARD*", 334, 110);
        g.drawLine(30, 120, 480, 120);
        g.drawLine(255, 90, 255, 420);
        g.drawString("UP---------move up", 45, 140);
        g.drawString("DOWN-----move down", 45, 170);
        g.drawString("LEFT------move left", 45, 200);
        g.drawString("RIGHT----move right", 45, 230);
        g.drawString("P-----------pause", 45, 260);
        g.drawString("BEAN-----------10", 285, 140);
        g.drawString("GHOST----------20", 285, 170);
        g.drawString("VITAMIN---------50", 285, 200);
    }

    public static void renderWall(Graphics g){
        switch(Brain.chapter){
        case Brain.GAMEOVER:{
            g.setColor(Color.DARK_GRAY);        break;}
        case Brain.GAMING:{
            switch(Brain.section){
            case Brain.PREYED:{
                g.setColor(Color.LIGHT_GRAY);   break;}
            case Brain.PREYING:{
                g.setColor(Color.CYAN);               }
            }}
        }
        for(int i = 0; i < Map.BOARD_H; i++){
            for(int j = 0; j < Map.BOARD_W; j++){
                int lx = (int)Map.box[i][j].left;
                int rx = (int)Map.box[i][j].right;
                int ty = (int)Map.box[i][j].top;
                int by = (int)Map.box[i][j].bottom;
                if(Brain.edge[i][j][0] == true){
                    g.drawLine(rx, ty, rx, by);
                }
                if(Brain.edge[i][j][1] == true){
                    g.drawLine(lx, ty, rx, ty);
                }
                if(Brain.edge[i][j][2] == true){
                    g.drawLine(lx, ty, lx, by);
                }
                if(Brain.edge[i][j][3] == true){
                    g.drawLine(lx, by, rx, by);
                }
            }
        }
    }

    public static void renderFruit(Graphics g){
        for(int i = 0; i < Map.BOARD_H; i++){
            for(int j = 0; j < Map.BOARD_W; j++){
                int x = (int)Map.box[i][j].xc;
                int y = (int)Map.box[i][j].yc;
                if(Brain.board[i][j] == Map.BEAN){
                    g.setColor(Color.YELLOW);
                    g.fillOval(x-2, y-2, 4, 4);
                }
            }
        }
        if(Brain.vitamin.act == Vitamin.GENERATED){
            int x = (int)Map.box[Brain.vitamin.box.row][Brain.vitamin.box.col].xc;
            int y = (int)Map.box[Brain.vitamin.box.row][Brain.vitamin.box.col].yc;
            g.setColor(Color.PINK);
            g.fillRect(x-3, y-3, 6, 6);
        }
    }

    public static void renderGroup(Graphics g){ // remember to break in case
        for(int i = 0; i < Brain.SIZE; i++){
            if(!Brain.group[i].visible){
                continue;
            }
            Image image;
            int x = (int)Brain.group[i].x;
            int y = (int)Brain.group[i].y;
            switch(Brain.group[i].type){
            case Gene.PACMAN:{
                if(
                    Brain.group[i].invincibleTick > 0 &&
                    ((int)Brain.group[i].invincibleTick) % 2 == 0
                ){
                    image = new ImageIcon("pic/Blank.gif").getImage();
                    break;
                }
                switch(Brain.group[i].style){
                case 0:{
                    image = new ImageIcon("pic/PacMan1.gif").getImage();
                    break;
                }
                case 2:{
                    switch(Brain.group[i].direction){
                    case 0:{
                        image = new ImageIcon("pic/PacMan3right.gif").getImage();   break;}
                    case 1:{
                        image = new ImageIcon("pic/PacMan3up.gif").getImage();      break;}
                    case 2:{
                        image = new ImageIcon("pic/PacMan3left.gif").getImage();    break;}
                    default:{
                        image = new ImageIcon("pic/PacMan3down.gif").getImage();          }
                    }break;
                }
                default:{
                    switch(Brain.group[i].direction){
                    case 0:{
                        image = new ImageIcon("pic/PacMan2right.gif").getImage();   break;}
                    case 1:{
                        image = new ImageIcon("pic/PacMan2up.gif").getImage();      break;}
                    case 2:{
                        image = new ImageIcon("pic/PacMan2left.gif").getImage();    break;}
                    default:{
                        image = new ImageIcon("pic/PacMan2down.gif").getImage();          }
                    }
                }
                }break;
            }
            default:{
                switch(Brain.section){
                case Brain.PREYED:{
                    if(Brain.group[i].positive){
                        image = new ImageIcon("pic/Ghost1.gif").getImage();
                    }else{
                        image = new ImageIcon("pic/Ghost2.gif").getImage();
                    }break;
                }
                default:{
                    if(Brain.group[i].positive){
                        image = new ImageIcon("pic/GhostScared1.gif").getImage();
                    }else{
                        image = new ImageIcon("pic/GhostScared2.gif").getImage();
                    }break;
                }
                }
            }
            }
            g.drawImage(image, x-RENDER_W, y-RENDER_W, (RENDER_W<<1), (RENDER_W<<1), null);
        }
    }

    public static void renderChances(Graphics g){
        Image image = new ImageIcon("pic/PacMan3left.gif").getImage();
        for(int i = 0; i < Brain.chances; i++){
            int x = (int)Map.box[16][i+1].xc;
            int y = (int)Map.box[16][i+1].yc;
            g.drawImage(image, x-RENDER_W, y-RENDER_W, (RENDER_W<<1), (RENDER_W<<1), null);
        }
    }

    public static void renderScore(Graphics g){
        int x = (int)Map.box[16][12].xc - 10;
        int y = (int)Map.box[16][12].yc + 7;
        String value = "Score: " + String.valueOf(Brain.score);
        g.drawString(value, x, y);
    }

    public static void renderLevel(Graphics g){
        int x = (int)Map.box[0][7].xc - 1;
        int y = (int)Map.box[0][7].yc + 7;
        String value = "LEVEL " + String.valueOf(Brain.level);
        g.drawString(value, x, y);
    }

    public static void renderBonus(Graphics g){
        int x = (int)Map.box[16][7].xc + 7;
        int y = (int)Map.box[16][7].yc + 7;
        int minutes = Brain.vitamin.expire / 60;
        int seconds = Brain.vitamin.expire % 60;
        String s1 = String.valueOf(minutes);
        String s2 = String.valueOf(seconds);
        if(s1.length() == 1){
            s1 = "0" + s1;
        }
        if(s2.length() == 1){
            s2 = "0" + s2;
        }
        g.drawString(s1+":"+s2, x, y);
    }

    public static void renderPausedWindow(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("*PAUSED*", 215, 260);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Minecraft", Font.BOLD, 14));
        Brain.refresh();
        renderBackGround(g);
        renderWall(g);
        switch(Brain.chapter){
        case Brain.GAMEOVER:{
            switch(Brain.section){
            case Brain.DEAD:{
                renderDeadWindow(g);    break;}
            case Brain.DESKTOP:{
                renderDesktop(g);       break;}
            case Brain.RANKWINDOW:{
                renderRankWindow(g);    break;}
            case Brain.HELPWINDOW:{
                renderHelpWindow(g);    break;}
            case Brain.MODESELECTING:{
                renderModeSelecting(g); break;}
            case Brain.MUSICSELECTING:{
                renderMusicSelecting(g);      }
            }break;
        }
        case Brain.GAMING:{
            renderScore(g);
            renderLevel(g);
            if(Brain.section == Brain.PREYING){
                renderBonus(g);
            }
            renderFruit(g);
            renderGroup(g);
            if(Brain.difficulty != Brain.EASY){
                renderChances(g);
            }
            if(Brain.paused){
                renderPausedWindow(g);
            }
        }
        }
    }
}
