package DonkeyGame;

public class Donkey {

    private int d_PosX;
    private int d_PosY;
    private static int num;
    private static final int LEFT_LANE_X = 200;
    private static final int RIGHT_LANE_X = 285;
    private static final int MAX_NUM = 20;


    public Donkey() {
        int laneID = (int) (Math.random() * 2);
        d_PosX = laneID == 0 ? LEFT_LANE_X : RIGHT_LANE_X;
        num++;
    }


    public static void setNum(int num) {
        Donkey.num = num;
    }

    public void setD_PosX(int d_PosX) {
        this.d_PosX = d_PosX;
    }

    public void setD_PosY(int d_PosY) {
        this.d_PosY = d_PosY;
    }

    public boolean isLeft() {
        return d_PosX == LEFT_LANE_X;
    }


    public static int getNum() {
        return num;
    }

    public int getD_PosX() {
        return d_PosX;
    }

    public int getD_PosY() {
        return d_PosY;
    }

    public static int getMaxNum() {
        return MAX_NUM;
    }

}
