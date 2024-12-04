import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Memory {
    public static class info implements Comparable<info>{
        String name;
        int score;
        public info(String name, int score){
            this.name = name;
            this.score = score;
        }
        @Override
        public int compareTo(info m){
            if(this.score == m.score){
                return this.name.compareTo(m.name);
            }else{
                return m.score - this.score;
            }
        }
    }

    public static File file;
    public static String tempName;
    public static List<info> hard = new ArrayList<>();
    public static List<info> normal = new ArrayList<>();
    public static List<info> impossible = new ArrayList<>();

    public static void clear(String path) throws Exception{
        File f = new File(path);
        FileWriter fw = new FileWriter(f);
        fw.write(""); fw.flush(); fw.close();
    }

    public static void readSingle(String path, List<info> l){
        file = new File(path);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                if(line.length() < 2) continue;
                char[] s = line.toCharArray();
                String name = ""; int score = 0; boolean isNum = false;
                for(char c : s){
                    if(isNum) score = score*10 + (c-'0');
                    else{
                        if(c == ' ') isNum = true;
                        else         name += c;}}
                l.add(new info(name, score));}
        }catch(IOException e){}
    }

    public static void writeSingle(String path, List<info> l){
        try{clear(path);}catch(Exception e){}
        try(BufferedWriter br = new BufferedWriter(new FileWriter(path))){
            for(info m : l){
                br.write(m.name + " " + String.valueOf(m.score) + "\n");
            }
        }catch(IOException e){}
    }

    public static void read(){
        readSingle("data/RANK_HARD.txt", hard);
        readSingle("data/RANK_NORMAL.txt", normal);
        readSingle("data/RANK_IMPOSSIBLE.txt", impossible);
    }

    public static void insert(int difficulty, int score){
        if(tempName == "") return;
        switch(difficulty){
        case Brain.NORMAL:{
            normal.add(new info(tempName, score));
            Collections.sort(normal);
            writeSingle("data/RANK_NORMAL.txt", normal);
            break;
        }
        case Brain.HARD:{
            hard.add(new info(tempName, score));
            Collections.sort(hard);
            writeSingle("data/RANK_HARD.txt", hard);
            break;
        }
        case Brain.IMPOSSIBLE:{
            impossible.add(new info(tempName, score));
            Collections.sort(impossible);
            writeSingle("data/RANK_IMPOSSIBLE.txt", impossible);
        }
        }
    }
}
