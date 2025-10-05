/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase dibuja y maneja un circulo que se utiliza para
 * representar un robot dentro de la simulacion.
 */

public class Circle
{
	private final int     diameter;
	private final SColor  color;
	/**
	 * Estas dos variables no pueden ser final como en otras figuras
	 * ya que un circulo representa un robot, y por definicion
	 * puede cambiar de posicion.
	 */
	private int           pxrow;
	private int           pxcol;
	private boolean       visible;

	/**
	 * Constructor de la clase Circle.
	 * Inicializa un circulo con su posicion, diametro y color.
	 * Por defecto el circulo inicia invisible.
	 * @param pxrow posicion vertical en pixeles
	 * @param pxcol posicion horizontal en pixeles
	 * @param diameter diametro del circulo
	 * @param color color del circulo
	 */
	public Circle (final int pxrow, final int pxcol, final int diameter, final SColor color)
	{
		this.diameter = diameter;
		this.pxcol    = pxcol;
		this.pxrow    = pxrow;
		this.color    = color;
		this.visible  = false;
	}

	/**
	 * Cambia la visibilidad del circulo.
	 * Si se activa, se dibuja; si se desactiva, se borra.
	 * @param state nuevo estado de visibilidad
	 */
	public void changevisibility (final boolean state)
	{
		if (state)
		{
			this.visible = true;
			this.draw();
			return;
		}
		this.erase();
		this.visible = false;
	}

	/**
	 * Cambia la posicion del circulo en el canvas.
	 * Primero borra el circulo actual y luego actualiza la posicion.
	 * Si el parametro show es verdadero, se vuelve a dibujar en la nueva posicion.
	 * @param show indica si debe mostrarse despues del cambio
	 * @param newpxrow nueva coordenada vertical en pixeles
	 * @param newpxcol nueva coordenada horizontal en pixeles
	 */
	public void changeposition (final boolean show, final int newpxrow, final int newpxcol)
	{
		this.erase();
		this.pxrow = newpxrow;
		this.pxcol = newpxcol;

		if (show)
		{
			this.draw();
		}
	}

	/**
	 * Dibuja el circulo en el canvas si esta visible.
	 * Utiliza una figura geometrica de tipo Ellipse2D.
	 */
	private void draw ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas();
		canvas.draw(this, this.color, new java.awt.geom.Ellipse2D.Double(this.pxcol, this.pxrow, this.diameter, this.diameter));
		canvas.pause(Misc.RENDERMS);
	}

	/**
	 * Borra el circulo del canvas si esta visible.
	 */
	private void erase ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas(); 
		canvas.erase(this);
	}
}

