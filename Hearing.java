import java.io.File;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;


public class Hearing {
    public static final String[] SONG = { //2-9 0-7
        "NONE",
        "RANDOM",
        "A-Flat Major",
        "F Minor",
        "B-Flat Major",
        "G Minor",
        "C Major",
        "E Minor",
        "B Minor",
        "E-Flat Major"
    };

    public static final long[] LENGTH = {
        -1,         -1,
        212000000,  160000000,  156000000,  172000000,
        188000000,  144000000,  220000000,  268000000
    };

    public static Clip clip;
    public static long breakPoint;
    public static int song, mode = 0;
    private static AudioInputStream ais;
    private static Random random = new Random();

    public static void play(){
        if(clip != null) clip.stop();
        if(mode == 0) return;
        File path;
        switch(mode){
        case 1:{
            song = 2+random.nextInt(8); break;}
        default:{
            song = mode;                            }
        }
        path = new File("music/" + SONG[song] + ".wav");
        try{
            ais = AudioSystem.getAudioInputStream(path);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        }catch(Exception e){e.printStackTrace();}
    }

    public static void pause(){// must % length!!
        breakPoint = clip.getMicrosecondPosition() % LENGTH[song];
        clip.stop();
    }

    public static void resume(){
        clip.setMicrosecondPosition(breakPoint);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);// i almost cried after find this bug 
    }
}
