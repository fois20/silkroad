/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Clase que dibuja y maneja un triangulo usado para representar
 * el techo de una tienda dentro de la simulacion.
 */

public class Triangle
{
	private final int pxrow;
	private final int pxcol;
	private final int width;
	private final int height;
	private final SColor color;
	private boolean visible;

	/**
	 * Constructor del triangulo.
	 * @param pxrow fila inicial en pixeles
	 * @param pxcol columna inicial en pixeles
	 * @param height altura del triangulo
	 * @param width ancho del triangulo
	 * @param color color del triangulo
	 */
	public Triangle (final int pxrow, final int pxcol, final int height, final int width, final SColor color)
	{
		this.pxrow   = pxrow;
		this.pxcol   = pxcol;
		this.width   = width;
		this.height  = height;
		this.color   = color;
		this.visible = false;
	}

	/**
	 * Cambia la visibilidad del triangulo.
	 * Si se activa, se dibuja; si se desactiva, se borra.
	 * @param state true para mostrar, false para ocultar
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
	 * Dibuja el triangulo en el canvas si es visible.
	 */
	private void draw ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas();

		final int [] xs = { this.pxcol, this.pxcol + (this.width / 2), this.pxcol - (this.width / 2) };
		final int [] ys = { this.pxrow, this.pxrow + this.height, this.pxrow + this.height };

		canvas.draw(this, this.color, new java.awt.Polygon(xs, ys, 3));
		canvas.pause(Misc.RENDERMS);
	}

	/**
	 * Borra el triangulo del canvas si estaba visible.
	 */
	private void erase ()
	{
		if (!this.visible) { return; }
		final Canvas canvas = Canvas.getcanvas(); 
		canvas.erase(this);
	}
}

