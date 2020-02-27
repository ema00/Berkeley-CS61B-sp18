package lab14;

import lab14lib.Generator;



/**
 * CS61B Lab 14: https://sp18.datastructur.es/materials/lab/lab14/lab14
 *
 * SawToothGenerator
 * Saw Tooth Generator, uses a sample rate of 44100 Hz.
 *
 * @author Emanuel Aguirre
 */
public class SawToothGenerator implements Generator {

    private final int frequency;
    private int state;


    public SawToothGenerator(double frequency) {
        this.frequency = (int) frequency;
        state = 0;
    }


    @Override
    public double next() {
        int sample = state;
        state = (state + 1) % frequency;
        return normalize(sample);
    }

    private double normalize(int sample) {
        return 2 * ((double) (sample - frequency / 2)) / ((double) frequency);
    }

}
