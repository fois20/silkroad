/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase responsable de proveer un canvas en el cual
 * renderizar la informacion de la simulacion
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SilkRoadCanvas extends JFrame
{
	/**
	 * _host: instancia de la misma clase desde la cual manipularemos el Canvas
	 *
	 * _windowSize: tamano fijo de la ventana, si este valor cambia todos los
	 * valores marcados como (winSizeComputed) tendran que cambiar tambien
	 *
	 * _pbar: instancia de JProgressBar para poder indicar que tanto se ha recolectado en la
	 * simulacion
	 */
	private static SilkRoadCanvas _host     = null;
	private static final int _windowSize    = 500;
	private static final JProgressBar _pbar = new JProgressBar(0, 100);

	private List<Object>                 objs;
	private final Map<Object, ShapeDesc> shapes;
	private final Color                  bgcolor;
	private final CanvasPane             canvas;
	private Graphics2D                   graphic;
	private Image                        img;

	/**
	 * @return unica instancia del Canvas, si no ha sido creada la creara y luego
	 * la retornara
	 */
	public static SilkRoadCanvas getSilkRoadCanvas ()
	{
		if (_host == null)
		{
			_host = new SilkRoadCanvas(Misc.TITLE, SColor.sand.getcolor());
		}
		_host.setVisible(true);
		return _host;
	}

	/**
	 * actualiza el valor de la barra de progreso al valor indicado
	 * por la variable `to`
	 */
	public static void updateProgressBar (final int to)
	{
		_pbar.setValue(to);
	}

	private SilkRoadCanvas (final String title, final Color bgcolor)
	{
		super(title);
		this.canvas  = new CanvasPane();
		this.bgcolor = bgcolor;
		this.objs    = new ArrayList<>();
		this.shapes  = new HashMap<>();

		this.canvas.setPreferredSize(new Dimension(_windowSize, _windowSize));
		final JPanel root = new JPanel(new BorderLayout());

		root.add(this.canvas, BorderLayout.CENTER);
		_pbar.setValue(0);
		_pbar.setStringPainted(true);

		root.add(_pbar, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(root);
		pack();
	}

	/**
	 * Sobre-escrite el metodo para hacer visible el marco pero antes se asegura
	 * de que la instancia de Graphics2D exista, si no existe la crea y luego
	 * si llama la funcion original de JFrame
	 *
	 * @param state true si se quiere mostrar el frame, false si no
	 */
	@Override
	public void setVisible (final boolean state)
	{
		if (this.graphic == null)
		{
			this.img = this.canvas.createImage(_windowSize, _windowSize);
			this.graphic = (Graphics2D) this.img.getGraphics();

			this.graphic.setColor(this.bgcolor);
			this.graphic.fillRect(0, 0, _windowSize, _windowSize);
			this.graphic.setColor(Color.BLACK);
		}
		super.setVisible(state);

	}

	/**
	 * dibuja una nueva figura en el frame y los anade dentro del marco de referencia
	 * que tenemos hacia todas las figuras
	 *
	 * @param ref instancia de la figura a dinujar
	 * @param color color de la figura
	 * @param shape tipo de la figura
	 */
	public void draw (final Object ref, final SColor color, final Shape shape)
	{
		this.objs.remove(ref);
		this.objs.add(ref);
		this.shapes.put(ref, new ShapeDesc(shape, color));
		this.redraw();
	}

	/**
	 * elimina cualquier referencia al objeto que se desea eliminar y redibuja
	 * el visual actual
	 *
	 * @param ref objecto referencia
	 */
	public void erase (final Object ref)
	{
		this.objs.remove(ref);
		this.shapes.remove(ref);
		this.redraw();
	}

	public void setbackgroundcolor (final SColor color)
	{
		this.graphic.setColor(color.getcolor());
	}

	public void pause ()
	{
		try { Thread.sleep(10); } catch (Exception e) {}
	}

	/**
	 * limpia el canvas por completo y luego dibuja una a una todas las figuras;
	 * este metodo es lento ya que dibuja todas las figuras
	 */
	private void redraw ()
	{
		this.clscnavas();
		for (Object obj: this.objs)
		{
			this.shapes.get(obj).draw(this.graphic);
		}
		this.canvas.repaint();
	}

	/**
	 * basicamente pinta todo el canvas el color del background para dar la ilusion
	 * de que desaparecio
	 */
	private void clscnavas ()
	{
		final Color c0 = this.graphic.getColor();
		this.graphic.setColor(this.bgcolor);
		this.graphic.fill(new Rectangle(0, 0, _windowSize, _windowSize));
		this.graphic.setColor(c0);
	}

	private class CanvasPane extends JPanel
	{
		@Override
		protected void paintComponent (final Graphics g)
		{
			super.paintComponent(g);
			if (img != null)
			{
				g.drawImage(img, 0, 0, null);
			}
		}
	}

	private class ShapeDesc
	{
		private Shape  shape;
		private SColor color;

		public ShapeDesc (final Shape shape, final SColor color)
		{
			this.shape = shape;
			this.color = color;
		}

		private void draw (final Graphics2D g)
		{
			g.setColor(color.getcolor());
			g.draw(shape);
			g.fill(shape);
		}
	}
}
