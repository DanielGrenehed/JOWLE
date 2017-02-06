package engine.Processing;

import javax.sound.sampled.Clip;

/**
 * runnable audioPlayer
 * @autor Daniel Amos Grenehed
 * 
 * @see https://www.cs.cmu.edu/~illah/CLASSDOCS/javasound.pdf
 * @see https://docs.oracle.com/javase/tutorial/sound/
 * @see https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/Line.
 *       html
 * @see http://stackoverflow.com/questions/6389121/how-can-a-java-
 *       application -play-a-sound-clip
 * @see http://www.jsresources.org/examples/
 */
public class AudioProcessor implements Runnable {

	Clip a;

	/**
	 * constructs an audio processor with the given audio-clip
	 * @param a
	 * */
	public AudioProcessor(Clip a) {
		this.a = a;
	}

	@Override
	public void run() {
		a.start();
	}

}
