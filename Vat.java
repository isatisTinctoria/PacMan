import javax.swing.*;

public class Vat extends JFrame{
    public Vat() throws InterruptedException{
        this.setTitle("PacMan");
        this.setResizable(false);
        this.setSize(524, 547);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Brain brain = new Brain();
        brain.Init();
        Vision vision = new Vision();
        this.add(vision);
        this.setVisible(true);
        this.addKeyListener(brain);

        for(;;){
            Thread.sleep(5);
            vision.repaint();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        new Vat();
    }
}
