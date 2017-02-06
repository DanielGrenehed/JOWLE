package engine.Processing;

import engine.Render.Renderable;

/**
 * game processing thread
 * 
 * @see https://www.youtube.com/watch?v=KUdro0G1BV4
 * @see https://docs.oracle.com/javase/tutorial/essential/concurrency/
 *       pools.html
 * @author Daniel Amos Grenehed
 */
public class RenderableProcessor implements Runnable {
	Renderable r;

	/**
	 * constructs an Renderable Processor for updating the given Renderable:r
	 * @param r
	 * */
	public RenderableProcessor(Renderable r) {
		this.r = r;
	}

	@Override
	public void run() {
		// update target
		r.update();
	}
}
