package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final double CONCERT_A = 440.0;
    private static final int NUMKEYS = 37;
    public static final double[] CONCERTS = new double[NUMKEYS];
    private static final GuitarString[] strings = new GuitarString[NUMKEYS];

    public static void main(String[] args) {
        for (int i = 0; i < NUMKEYS; i++) {
            CONCERTS[i] = CONCERT_A * Math.pow(2.0, (double) (i - 24) / 12.0);
        }

        for (int i = 0; i < NUMKEYS; i++) {
            strings[i] = new GuitarString(CONCERTS[i]);
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIndex = keyboard.indexOf(key);

                if (keyIndex == -1) {
                    continue;
                } else {
                    strings[keyIndex].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < NUMKEYS; i++) {
                sample += strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < NUMKEYS; i++) {
                strings[i].tic();
            }
        }
    }
}
