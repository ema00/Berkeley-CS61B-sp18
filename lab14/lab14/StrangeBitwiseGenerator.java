package lab14;

import lab14lib.Generator;


/**
 * CS61B Lab 14: https://sp18.datastructur.es/materials/lab/lab14/lab14
 *
 * StrangeBitwiseGenerator
 * Saw Tooth Generator, uses bit shifting and bitwise and to change the shape of the wave,
 * uses a sample rate of 44100 Hz.
 *
 * @author Emanuel Aguirre
 */
public class StrangeBitwiseGenerator implements Generator {

    private final int period;
    private int state;


    public StrangeBitwiseGenerator(double period) {
        this.period = (int) period;
        state = 0;
    }


    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 7) % period;
        //state & (state >> 3) & (state >> 8) % period;
        // state & (state >>> 4) % period;
        return normalize(weirdState);
    }

    private double normalize(int sample) {
        return 2 * ((double) (sample - period / 2)) / ((double) period);
    }

}
