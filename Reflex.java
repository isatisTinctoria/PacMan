import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Reflex {
    public static List<Integer> KEY = new ArrayList<>();
    public static HashSet<Integer> clk = new HashSet<>();
    public static HashSet<Integer> _clk = new HashSet<>();
    public static HashSet<Integer> clk_ = new HashSet<>();

    public static void Init(){
        KEY.add(KeyEvent.VK_UP);
        KEY.add(KeyEvent.VK_DOWN);
        KEY.add(KeyEvent.VK_LEFT);
        KEY.add(KeyEvent.VK_RIGHT);
        KEY.add(KeyEvent.VK_ENTER);
        KEY.add(KeyEvent.VK_ESCAPE);
        KEY.add(KeyEvent.VK_BACK_SPACE);
        for(int VK_ALPHA = 65; VK_ALPHA <= 90; VK_ALPHA++){
            KEY.add(VK_ALPHA);
        }
    }

    public static void add(int VK){
        clk.add(VK);
    }

    public static void remove(int VK){
        clk.remove(VK);
    }

    public static boolean typed(int VK){
        return clk_.contains(VK);
    }

    public static boolean pressed(int VK){
        return clk.contains(VK);
    }

    public static void refresh(){
        for(int VK : KEY){
            if(clk.contains(VK) && !_clk.contains(VK)){
                clk_.add(VK);
                _clk.add(VK);
            }
            else{
                clk_.remove(VK);
            }
            if(!clk.contains(VK) && _clk.contains(VK)){
                _clk.remove(VK);
            }
        }
    }
}
