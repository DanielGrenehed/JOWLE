package engine.Common;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Daniel Amos Grenehed
 */
public class MemoryList {

	CopyOnWriteArrayList<Template> MemoryList = new CopyOnWriteArrayList<Template>();
	ObjectSizeFetcher osf = new ObjectSizeFetcher();
	
	/**
	 * @return if found, a template with the same class as obj and with the name
	 * Name
	 * 
	 * @param obj, Name
	 * 
	 */
	public Template getFromMemory(Object obj, String Name) {
		for (Template node : MemoryList) {
			if (node.isInstanceOf(obj)) {
				System.out.println(node.getName());
				if (node.getName().equals(Name)) {
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * creates and adds a template of obj and with the name Name to the
	 * MemoryList
	 * 
	 * @param obj,Name
	 */
	public void addToMemory(Object obj, String Name) {
		MemoryList.add(new Template<>(Name, obj));
	}

	/**
	 * removes the tem template from the MemoryList
	 * 
	 * @param tem
	 */
	public void forget(Template tem) {
		MemoryList.remove(tem);
	}

	/**
	 * creates and removes a template with the obj as content and Name as name.
	 * 
	 * @param obj, Name
	 */
	public void removeFromMemory(Object obj, String Name) {
		forget(getFromMemory(obj, Name));
	}

	/**
	 * Transversing the MemoryList for a template matching the given parameters,
	 * returns true if found
	 * 
	 * @param obj, Name
	 * @return boolean
	 */
	public boolean contains(Object obj, String Name) {
		for (Template node : MemoryList) {
			if (node.isInstanceOf(obj) && node.getName().equals(Name))
				return true;
		}
		return false;
	}
	
	/**
	 * @return the size of the MemoryList
	 * */
	public long getListSize() {
		return osf.getObjectSize(MemoryList);
	}
	
	/**
	 * @return the largest object in memory list
	 * */
	public Template getLargestObject() {
		long length=0;
		Template T = null;
		for (Template t: MemoryList) {
			if (length < osf.getObjectSize(t)) {
				length = osf.getObjectSize(t);
				T = t;
			}
		}
		return T;
	}

}