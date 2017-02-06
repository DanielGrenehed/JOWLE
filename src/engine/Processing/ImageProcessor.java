package engine.Processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import engine.Render.Renderable;

/**
 * @author Daniel Amos Grenehed
 */
public abstract class ImageProcessor {

	/**
	 * uses an array of images to create one image that contains all of the
	 * images in tha array, with the width and height passed along
	 */
	public static BufferedImage createImageFromArray(BufferedImage[] bia, int WIDTH, int HEIGHT) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = image.createGraphics();

		for (BufferedImage img : bia) {
			g.drawImage(img, 0, 0, null);
		}

		return image;
	}

	/**
	 * uses an list of images to create one image that contains all of the
	 * images in the list and has the heigt and width passed along
	 */
	public static BufferedImage createImageFromList(List<Renderable> burk, int WIDTH, int HEIGHT) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		for (Renderable r : burk) {
			r.paint(g);
		}
		return image;
	}

	/**
	 * uses an list of images to create one image that contains all of the
	 * images in the list and has the heigt and width passed along with
	 * antialiasing on or off
	 */
	public static BufferedImage createImageFromList(List<Renderable> burk, int WIDTH, int HEIGHT, boolean antialiasing) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		if (antialiasing)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Renderable r : burk) {
			r.paint(g);
		}
		return image;
	}

	/***/
	public static BufferedImage ScaleImage(BufferedImage img,int w ,int h) {
		BufferedImage img2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img2.createGraphics();
		g.drawImage(img.getScaledInstance(w, h, Image.SCALE_DEFAULT), 0, 0, null);
		g.dispose();
		return img2;
	}

	/**
	 * changes the general color of the image to the specified color.
	 */
	public BufferedImage setImageColor(BufferedImage image, Color c) {
		int iw = image.getWidth(), ih = image.getHeight();
		relColor rc = new relColor();
		rc.BeginColor(c.getRGB());
		int BIG, cc;
		for (int x = 0; x < iw; x++) {
			for (int y = 0; y < ih; y++) {
				cc = image.getRGB(x, y);// färg av pixel
				BIG = rc.largestValue(cc);// största rgb värde
				image.setRGB(x, y, rc.getRGB(BIG, ((cc >> 24) & 0xff)));
				// sätt nya färgen
			}
		}
		return image;
	}

	private class relColor {
		private double R, G, B;
		private int BIG;

		public relColor() {
		}

		public relColor(int color) {
			BeginColor(color);
		}

		public void BeginColor(int color) {
			// få största färgvärde
			BIG = largestValue(color);
			// separera färgen
			int r, g, b, a;
			r = (color >> 16) & 0xff;
			g = (color >> 8) & 0xff;
			b = (color >> 0) & 0xff;
			// dividera med största talet(BIG)
			R = divide(r);
			G = divide(g);
			B = divide(b);

		}

		public int largestValue(int color) {
			int r, g, b, Big;
			r = (color >> 16) & 0xff;
			g = (color >> 8) & 0xff;
			b = (color >> 0) & 0xff;
			Big = r;
			if (g > Big)
				Big = g;
			if (b > Big)
				Big = b;
			return Big;
		}

		public double divide(int value) {
			return (double) value / (double) BIG;
		}

		public int getRGB(int br, int A) {
			if (br <= 0) {
				return ((A & 0x0ff) << 24 | (0 & 0x0ff) << 16) | ((0 & 0x0ff) << 8) | (0 & 0x0ff);
			} else {
				if (br > 255)
					br = 255;
				int r = (int) (R * br);
				int g = (int) (G * br);
				int b = (int) (B * br);
				int rgb = ((A & 0x0ff) << 24 | (r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
				return rgb;

			}
		}

	}

}