import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {
    public static final int TOP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 0;
    public static final int BOTTOM = 3;

    public static final int BEAN = 0;
    public static final int BRICK = 1;
    public static final int VACCUM = 2;
    
    public static final int BOARD_W = 17;
    public static final int BOARD_H = 17;
    public static final int BLOCK_W = 30;

    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, -1, 0, 1};

    public static final int[][][] LEVEL = {
        {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        },
        {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        },
        {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        },
        {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        },
        {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        }
    };
    
    public static int rest = 0;
    public static int[][] board = new int[17][17];
    public static Skeleton[][] box = new Skeleton[17][17];
    public static boolean[][][] edge = new boolean[17][17][4];

    public Map(){
        for(int i = 0; i < BOARD_H; i++){
            for(int j = 0; j < BOARD_W; j++){
                double left = j * BLOCK_W;
                double top = i * BLOCK_W;
                double right = (j+1) * BLOCK_W;
                double bottom = (i+1) * BLOCK_W;
                double xc = left + BLOCK_W/2;
                double yc = top + BLOCK_W/2;
                box[i][j] = new Skeleton(xc, yc, left, top, right, bottom);
            }
        }
    }

    public static boolean inMap(int r, int c){
        return r >= 0 && r < BOARD_H && c >= 0 && c < BOARD_W;
    }

    public static void loadMap(int n){ // two loops cannot be merged
        rest = 0;
        for(int i = 0; i < BOARD_H; i++){
            for(int j = 0; j < BOARD_W; j++){
                board[i][j] = LEVEL[n-1][i][j];
                if(board[i][j] != 1){
                    rest++;
                }
            }
        }
        for(int i = 0; i < BOARD_H; i++){
            for(int j = 0; j < BOARD_W; j++){
                for(int k = 0; k < 4; k++){
                    int ni = i + DY[k];
                    int nj = j + DX[k];
                    if(
                        board[i][j] == BRICK &&
                        inMap(ni, nj) &&
                        board[ni][nj] != BRICK
                    ){
                        edge[i][j][k] = true;
                    }
                    else{
                        edge[i][j][k] = false;
                    }
                }
            }
        }
    }

    public static class node{
        int r, c, d;
        public node(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }
    
    public static int search(int rs, int cs, int rt, int ct, int d){
        if(rs == rt && cs == ct){
            return d;
        }
        Queue<node> Q = new LinkedList<>();
        int[][] vis = new int[BOARD_H][BOARD_W];
        for(int i = 0; i < BOARD_H; i++){
            for(int j = 0; j < BOARD_W; j++){
                vis[i][j] = -1;
            }
        }
        Q.add(new node(rs, cs, d));
        vis[rs][cs] = d;
        while(!Q.isEmpty()){
            node n = Q.poll();
            for(int k = 0; k < 4; k++){
                int nr = n.r + DY[k];
                int nc = n.c + DX[k];
                if(nr == rt && nc == ct){
                    vis[nr][nc] = k;
                    int r = nr, c = nc;
                    for(;;){
                        int ans = vis[r][c];
                        r -= DY[ans];
                        c -= DX[ans];
                        if(r == rs && c == cs){
                            return ans;
                        }
                    }
                }
                if(
                    inMap(nr, nc) &&
                    LEVEL[0][nr][nc] != BRICK && vis[nr][nc] == -1
                ){
                    vis[nr][nc] = k;
                    Q.add(new node(nr, nc, k));
                }
            }
        }
        return 0;
    }

    public static int nextCorner(int r, int c, int d){
        for(;;){
            List<Integer> directions = new ArrayList<>();
            for(int k = 0; k < 4; k++){
                if(board[r + DY[k]][c + DX[k]] != BRICK){
                    directions.add(k);
                }
            }
            if(
                !(directions.size() == 2 &&
                (directions.get(0)+2)%4 == directions.get(1))
            ){
                return r*BOARD_W + c;
            }
            r += DY[d];
            c += DX[d];
        }
    }

    public static int diffuse(int r, int c){
        if(board[r][c] != BRICK){
            return r*BOARD_W + c;
        }
        for(int k = 1; ; k++){
            int nr = r, nc = c + k;
            if(inMap(nr, nc) && board[nr][nc] != BRICK){
                return nr*BOARD_W + nc;
            }
            for(int j = 0; j < k; j++){
                nr--; nc--;
                if(inMap(nr, nc) && board[nr][nc] != BRICK){
                    return nr*BOARD_W + nc;
                }
            }
            for(int j = 0; j < k; j++){
                nr++; nc--;
                if(inMap(nr, nc) && board[nr][nc] != BRICK){
                    return nr*BOARD_W + nc;
                }
            }
            for(int j = 0; j < k; j++){
                nr++; nc++;
                if(inMap(nr, nc) && board[nr][nc] != BRICK){
                    return nr*BOARD_W + nc;
                }
            }
            for(int j = 0; j < k-1; j++){
                nr--; nc++;
                if(inMap(nr, nc) && board[nr][nc] != BRICK){
                    return nr*BOARD_W + nc;
                }
            }
        }
    }
}