package lab14;

import lab14lib.Generator;


/**
 * CS61B Lab 14: https://sp18.datastructur.es/materials/lab/lab14/lab14
 *
 * AcceleratingSawToothGenerator
 * Saw Tooth Generator that increases frequency, uses a sample rate of 44100 Hz.
 *
 * @author Emanuel Aguirre
 */
public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private double factor;
    private int state;


    public AcceleratingSawToothGenerator(double period, double factor) {
        this.period = (int) period;
        this.factor = factor;
        state = 0;
    }


    @Override
    public double next() {
        int sample = state;
        state = (state + 1) % period;
        if (state == 0) {
            period = (int) (factor * period);
        }
        return normalize(sample);
    }

    private double normalize(int sample) {
        return 2 * ((double) (sample - period / 2)) / ((double) period);
    }

}
