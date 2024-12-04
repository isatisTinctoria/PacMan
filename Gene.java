public class Gene {
    public static final int PACMAN = 0;
    public static final int RED_GHOST = 1;
    public static final int PINK_GHOST = 2;
    public static final int BLUE_GHOST = 3;
    public static final int GREEN_GHOST = 4;
    public static final int ORANGE_GHOST = 5;

    public static final int[][] TYPE = {
        {PACMAN,        GREEN_GHOST,    GREEN_GHOST,    GREEN_GHOST,    GREEN_GHOST },
        {PACMAN,        RED_GHOST,      GREEN_GHOST,    GREEN_GHOST,    GREEN_GHOST },
        {PACMAN,        RED_GHOST,      PINK_GHOST,     GREEN_GHOST,    GREEN_GHOST },
        {PACMAN,        RED_GHOST,      PINK_GHOST,     BLUE_GHOST,     GREEN_GHOST },
        {PACMAN,        RED_GHOST,      PINK_GHOST,     BLUE_GHOST,     ORANGE_GHOST}
    };

    public static final double[][] SPEED = {
        {0.3,           0.1,            0.1,            0.1,            0.1         },
        {0.3,           0.15,           0.1,            0.1,            0.1         },
        {0.3,           0.2,            0.2,            0.15,           0.15        },
        {0.3,           0.25,           0.25,           0.25,           0.2         },
        {0.3,           0.3,            0.3,            0.3,            0.3         }
    };
}
