package engine.Processing;

import engine.Render.Camera;

/**
 *
 * @author Daniel Amos Grenehed
 * */
public class CameraProcessor implements Runnable {

	Camera camera;
	
	/**
	 * constructs an Camera Processor for updating the given Camera : c
	 * @param c
	 * */
	public CameraProcessor(Camera c) {
		this.camera = c;
	}
	
	@Override
	public void run() {
		camera.tick();
	}

}