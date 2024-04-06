import java.util.Random;

public class randomWithProb {

    public static void main(String[] args) {

        randomWithProb tryMe =  new randomWithProb();
        System.out.print("[ \"");
        for (int i = 0; i < 100; i++) {
            System.out.print(tryMe.probableChar());
            if (i < 99) {
                System.out.print("\", \"");
            }
        }
        System.out.print("\" ]");

    }


    static double[] probs;
    static Random rand;
    static char[] chars;
    static double[] adjusted;

    public randomWithProb() {
        probs = new double[] {8.2,1.5,2.8,4.3,12.7,2.2,2.0,6.1,7.0,0.15,
            0.77,4.0,2.4,6.7,7.5,1.9,0.095,6.0,6.3,9.1,2.8,0.98,2.4,0.15,2.0,0.074};
        chars = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        rand = new Random();
        adjusted = new double[] {0,8.2, 9.7, 12.5, 16.8, 29.5, 31.7, 33.7, 39.8, 46.8, 46.95, 47.72, 51.72, 54.12, 60.82, 68.32, 70.22, 70.32, 76.32, 82.62, 91.72, 94.52, 95.5, 97.9, 98.05, 100.05, 100.12};
    }

    public char probableChar() {

        double randomprob = rand.nextDouble(0,100.121);
        for (int i = 1; i < 27; i++) {
            if (adjusted[i-1] <= randomprob && adjusted[i] >= randomprob) {
                return chars[i-1];
            }
        }
        return '\n';
    }



}
