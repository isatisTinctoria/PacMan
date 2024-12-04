public class Skeleton {
    public int row = 0, col = 0;
    public double xc, yc, left, top, right, bottom;

    public Skeleton(){
        left = top = right = bottom = 0;
    }

    public Skeleton(double xc, double yc, double left, double top, double right, double bottom){
        this.xc = xc;
        this.yc = yc;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean intersect(Skeleton other){
        if(
            this.left <= other.right && other.left <= this.right &&
            this.top <= other.bottom && other.top <= this.bottom
        ){
            return true;
        }
        return false;
    }
}
