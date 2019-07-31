package synthesizer;


/**
 * GuitarString
 * Implementation of a class that replicates the sound of a plucked string.
 * Implementation of the Karplus-Strong algorithm using a Bonded Queue data structure.
 *
 * See here for further explanation:
 * https://sp18.datastructur.es/materials/hw/hw1/hw1
 *
 * @author CS 61B / Emanuel Aguirre
 */
public class GuitarString {

    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor


    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;


    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int bufferSize = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<Double>(bufferSize);
        while (!buffer.isFull()) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.capacity(); i++) {
            double noise = Math.random() - 0.5;
            buffer.dequeue();
            buffer.enqueue(noise);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     * Dequeues the front sample and enqueues a new sample that is the average
     * of the two multiplied by the DECAY factor.
     */
    public void tic() {
        double firstSample = buffer.dequeue();
        double secondSample = buffer.peek();
        double soundSample = (firstSample + secondSample) / 2 * DECAY;
        buffer.enqueue(soundSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
