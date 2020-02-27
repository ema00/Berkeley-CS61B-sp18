package lab14;

import lab14lib.GeneratorAudioAnimator;



public class Main {

    public static void main(String[] args) {
        //SineWaveGenerator swg = new SineWaveGenerator(2000);
        SawToothGenerator stg = new SawToothGenerator(2000);
        //GeneratorPlayer gp = new GeneratorPlayer(swg);
        // gp.play(200000);
        GeneratorAudioAnimator ga = new GeneratorAudioAnimator(stg);
        ga.drawAndPlay(4000, 20000);
    }

}
