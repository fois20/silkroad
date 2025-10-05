/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____| 
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Clase que implementa una ventana grafica y maneja la logica
 * relacionada con el dibujo y actualizacion de figuras.
 * (este codigo fue modificado por nosotros y refinado por ChatGPT
 * para hacerlo mas rapido)
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

public class Canvas extends JFrame
{
	private static Canvas    _host          = null;
	private static final int _winsz         = 500;
	private static final JProgressBar _pbar = new JProgressBar(0, 100);

	private List<Object> objs;
	private final Map<Object, ShapeDesc> shapes;
	private final Color                  bg;
	private final CanvasPane             canvas;
	private Graphics2D                   graphic;
	private Image                        img;

	/**
	 * Obtiene la instancia unica de la clase Canvas.
	 * Si no existe, crea una nueva ventana con configuracion por defecto.
	 * @return instancia unica de Canvas
	 */
	public static Canvas getcanvas ()
	{
		if (_host == null)
		{
			_host = new Canvas(Misc.T0, _winsz, _winsz, SColor.sand.getcolor());
		}

		_host.setVisible(true);
		return _host;
	}

	/**
	 * Actualiza el valor de la barra de progreso
	 * @param to actualizar a este valor
	 */
	public static void updateProgressBar (final int to)
	{
		_pbar.setValue(to);
	}

	/**
	 * Constructor privado de la clase Canvas.
	 * Inicializa el panel, la barra de progreso y los elementos graficos.
	 * @param title titulo de la ventana
	 * @param width ancho de la ventana
	 * @param height alto de la ventana
	 * @param bg color de fondo de la ventana
	 */
	private Canvas (final String title, final int width, final int height, final Color bg)
	{
		super(title);
		this.canvas = new CanvasPane();
		this.bg     = bg;
		this.objs   = new ArrayList<>();
		this.shapes = new HashMap<>();

		this.canvas.setPreferredSize(new Dimension(width, height));
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
	 * Sobrescribe el metodo setVisible para inicializar el buffer
	 * grafico la primera vez que se muestra la ventana.
	 * @param state indica si la ventana debe mostrarse o no
	 */
	@Override
	public void setVisible (final boolean state)
	{
		if (this.graphic == null)
		{
			final Dimension size =  this.canvas.getSize();
			this.img = this.canvas.createImage(size.width, size.height);
			this.graphic = (Graphics2D) this.img.getGraphics();

			this.graphic.setColor(bg);
			this.graphic.fillRect(0, 0, size.width, size.height);
			this.graphic.setColor(Color.BLACK);
		}
		super.setVisible(state);
	}

	/**
	 * Dibuja una figura asociada a un objeto en el canvas.
	 * Si ya existia, se reemplaza.
	 * @param ref referencia al objeto
	 * @param color color de la figura
	 * @param shape figura geometrica a dibujar
	 */
	public void draw (final Object ref, final SColor color, final Shape shape)
	{
		this.objs.remove(ref);
		this.objs.add(ref);
		this.shapes.put(ref, new ShapeDesc(shape, color));
		this.redraw();
	}

	/**
	 * Borra una figura asociada a un objeto en el canvas.
	 * @param ref referencia al objeto que se desea eliminar
	 */
	public void erase (final Object ref)
	{
		this.objs.remove(ref);
		this.shapes.remove(ref);
		this.redraw();
	}

	/**
	 * Cambia el color de fondo actual del canvas.
	 * @param color color nuevo de fondo
	 */
	public void setbackgroundcolor (final SColor color)
	{
		this.graphic.setColor(color.getcolor());
	}

	/**
	 * Pausa la ejecucion durante un tiempo dado en milisegundos.
	 * @param ms tiempo de espera en milisegundos
	 */
	public void pause (final int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Redibuja todos los elementos en el canvas.
	 * Limpia el fondo y vuelve a pintar las figuras almacenadas.
	 */
	private void redraw ()
	{
		this.clscanvas();
		for (Object obj: this.objs)
		{
			this.shapes.get(obj).draw(this.graphic);
		}
		this.canvas.repaint();
	}

	/**
	 * Limpia el canvas con el color de fondo actual.
	 */
	private void clscanvas ()
	{
		final Color c0 = this.graphic.getColor();
		final Dimension sz = this.canvas.getSize();

		this.graphic.setColor(this.bg);
		this.graphic.fill(new Rectangle(0, 0, sz.width, sz.height));

		this.graphic.setColor(c0);
	}

	/**
	 * Clase interna que representa el panel principal donde se dibuja.
	 * Se encarga de mostrar el buffer de imagen en pantalla.
	 */
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

	/**
	 * Clase interna que almacena la informacion de una figura y su color.
	 * Permite dibujarla en el contexto grafico.
	 */
	private class ShapeDesc
	{
		private final Shape  shape;
		private final SColor color;

		/**
		 * Constructor que inicializa una figura con su color.
		 * @param shape figura geometrica
		 * @param color color de la figura
		 */
		public ShapeDesc (final Shape shape, final SColor color)
		{
			this.shape = shape;
			this.color = color;
		}

		/**
		 * Dibuja la figura en el contexto grafico recibido.
		 * @param g contexto grafico donde se dibuja
		 */
		private void draw (final Graphics2D g)
		{
			g.setColor(color.getcolor());
			g.draw(shape);
			g.fill(shape);
		}
	}
}

