package engine.ReadWrite;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import engine.Main;
import engine.Render.Camera;

/**
 * @author Daniel Amos Grenehed
 * @author Emil Bertilsson
 * */
public class Classloader {

	/**
	 * tries to return an xml file as a docuement
	 * @param path
	 * @return document
	 */
	private static Document loadDocument(String path) {
		try {
			File file = new File(path);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(file);
		} catch (Throwable t) {
			System.out.println("There was an error loading the order file!");
			System.exit(-1);
		}

		return null;
	}

	/**
	 * @param invokes the jar path of the element
	 * */
	public static void invokePath(Element element) {
		 URL url = null;
		try {
			url = new URL(element.getAttribute("path"));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Glassladdare
		URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		 
		Method method = null;
		try {
			method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			try {
				method.invoke(loader, url);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Searches a document for class files and returns all found as an String
	 * Array.
	 * @param document
	 * @return String[]
	 */
	private static String[] getClassNames(Document document) {
		String[] classNames = null;

		document.normalize();

		NodeList nodes = document.getElementsByTagName("class");
		Node node;
		Element element;

		classNames = new String[nodes.getLength()];

		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				element = (Element) node;
				classNames[i] = element.getTextContent();
				if (element.getAttribute("path") != "") {
					
				}
			}
		}

		return classNames;
	}

	/**
	 * gets document : gets classes: searches for classes that extends camera,
	 * then loads them in the order in which they came.
	 * @param path
	 * @return Camera[]
	 */
	private static Camera[] loadClassesInOrder(String path) {
		Document document = loadDocument(path);
		String[] classNames = getClassNames(document);
		Camera[] instances = new Camera[classNames.length];
		Class<? extends Camera> cls;

		for (int i = 0; i < classNames.length; i++) {
			try {
				cls = (Class<? extends Camera>) Class.forName(classNames[i]);
				instances[i] = cls.newInstance();
			} catch (ClassNotFoundException e) {
				System.out.println("Error: Class not found!");
			} catch (InstantiationException e) {
				System.out.println("Error: Could not instantiate class!");
			} catch (IllegalAccessException e) {
				System.out.println("Error: Could not access class!");
			}
		}

		return instances;
	}

	/**
	 * returns a camera that have been loaded from the xml file
	 * @param path, main, i
	 */
	public Camera loadOrder(String path, Main mn, int i) {
		Camera[] camera = loadClassesInOrder(path);
		doc = path;
		length = camera.length;
		camera[i].LoadClass(mn);
		return camera[i];
	}

	String doc = "";
	int length = 0;

	/**
	 * returns the number of camera objects in the provided xml docs
	 * @param path
	 * @return int
	 * */
	public int OrderLength(String path) {
		if (doc.equals(path)) {
			return length;
		} else {
			Camera[] camera = loadClassesInOrder(path);
			length = camera.length;
			doc = path;
		}
		return length;
	}

	/**
	 * returns an ArrayList of cameras that all have been instantiated
	 * @param filename, main
	 * @return ArraList<Camera>
	 */
	public ArrayList<Camera> loadCameras(String filename, Main mn) {
		ArrayList<Camera> r = new ArrayList<Camera>();
		Camera[] camera = loadClassesInOrder(filename);
		for (Camera c : camera) {
			c.LoadClass(mn);
			r.add(c);
		}
		URL[] url = null;
		ClassLoader cl = new URLClassLoader(url);
		return r;
	}


	
}
