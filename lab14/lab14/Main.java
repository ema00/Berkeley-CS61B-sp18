package lab14;

import lab14lib.GeneratorAudioAnimator;



public class Main {

    public static void main(String[] args) {
        //SineWaveGenerator g = new SineWaveGenerator(2000);
        //SawToothGenerator g = new SawToothGenerator(2000);
        //AcceleratingSawToothGenerator g = new AcceleratingSawToothGenerator(200, 1.1);
        StrangeBitwiseGenerator g = new StrangeBitwiseGenerator(200);
        GeneratorAudioAnimator ga = new GeneratorAudioAnimator(g);
        ga.drawAndPlay(4096, 1000000);
        //GeneratorPlayer gp = new GeneratorPlayer(g);
        // gp.play(200000);
    }

}
