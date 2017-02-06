package engine.Render;

import java.util.concurrent.CopyOnWriteArrayList;

import engine.Main;

/**
 * @author Daniel Amos Grenehed
 * */
public interface Camera {

	/**
	 * custom construcor for all Camera objects, for the engine to be able to
	 * load all different cameras without really knowing wich one its loading
	 * */
	public void LoadClass(Main fm);


	/**
	 * 
	 * */
	public CopyOnWriteArrayList<Renderable> getRenderables();

	/**
	 * 
	 * */
	public void tick();

	/**
	 * 
	 * */
	public void end();

}
