/**
 * 
 */
package com.maqiao.was.pictureOverlay.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class Test extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2625201163686186066L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame test = new JFrame("Test");
				test.setContentPane(new Test());
				test.pack();
				test.setLocationRelativeTo(null);
				test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				test.setVisible(true);
			}
		});
	}

	Test() {
		setPreferredSize(new Dimension(800, 600));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Font f = new Font("Courier New", Font.PLAIN, 140);
		GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), "Hello");
		Shape shape = v.getOutline();
		Rectangle bounds = shape.getBounds();
		Graphics2D gg = (Graphics2D) g;
		gg.translate((getWidth() - bounds.width) / 2 - bounds.x, (getHeight() - bounds.height) / 2 - bounds.y);
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(Color.WHITE);
		gg.fill(shape);
		gg.setColor(Color.BLUE.darker().darker());
		BasicStroke bs = new BasicStroke(0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		gg.setStroke(bs);
		gg.draw(shape);
	}
}
