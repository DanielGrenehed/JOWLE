package engine.Common;

/**
 * @author Daniel Amos Grenehed
 */
public class Template<T> {

	private String Name = "";

	private T content;

	/**
	 * constructs template with name as name and node as content
	 * @param name, node
	 */
	public Template(String name, T node) {
		Name = name;
		setContent(node);
	}

	/**
	 * Returns the name of the template
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Returns the content of the template. This must be cast to the wanted
	 * class-type to be able to use it
	 */
	public T getContent() {
		return content;
	}

	/**
	 * sets the content of the template to node
	 * @param node
	 */
	public void setContent(T node) {
		this.content = node;
	}

	/**
	 * compares the class of the content in the template to the class of object
	 * obj, returns true if they are of the same class
	 * @param obj
	 */
	public boolean isInstanceOf(Object obj) {
		if (obj.getClass() == content.getClass())
			return true;
		return false;
	}

}