/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase dibuja y maneja la forma de un rectangulo, usado para
 * representar las tiendas y el terreno del camino.
 */

public class Rectangle
{
	private final int    pxrow;
	private final int    pxcol;
	private final int    width;
	private final int    height;
	private final SColor color;
	private boolean      visible;

	/**
	 * Constructor de la clase Rectangle.
	 * Inicializa un rectangulo con su posicion, dimensiones y color.
	 * Por defecto inicia invisible.
	 * @param pxrow posicion vertical en pixeles
	 * @param pxcol posicion horizontal en pixeles
	 * @param height altura del rectangulo en pixeles
	 * @param width ancho del rectangulo en pixeles
	 * @param color color del rectangulo
	 */
	public Rectangle (final int pxrow, final int pxcol, final int height, final int width, final SColor color)
	{
		this.pxrow   = pxrow;
		this.pxcol   = pxcol;
		this.width   = width;
		this.height  = height;
		this.color   = color;
		this.visible = false;
	}

	/**
	 * Cambia el estado de visibilidad del rectangulo.
	 * Si el estado solicitado ya coincide con el actual, no hace nada.
	 * Si se vuelve visible, lo dibuja; si se vuelve invisible, lo borra.
	 * @param state nuevo estado de visibilidad
	 */
	public void changevisibility (final boolean state)
	{
		if (state == this.visible)
		{
			return;
		}
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
	 * Dibuja el rectangulo en el canvas si esta visible.
	 */
	private void draw ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas();
		canvas.draw(this, this.color, new java.awt.Rectangle(this.pxcol, this.pxrow, this.width, this.height));
		canvas.pause(Misc.RENDERMS);
	}

	/**
	 * Borra el rectangulo del canvas si esta visible.
	 */
	private void erase ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas(); 
		canvas.erase(this);
	}
}

