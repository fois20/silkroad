/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase rectangulo la cual representara el camino y las fachadas
 * de las casas
 * (esta clase hereda de ShapeCommon)
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public class Rectangle extends ShapeCommon
{
	private int width;
	private int height;

	public Rectangle (final SColor color, final int pxrow, final int pxcol, final int width, final int height)
	{
		super(color, pxrow, pxcol);
		this.width = width;
		this.height = height;
	}

	@Override
	protected void draw ()
	{
		if (!this.visibility) { return; }
		final SilkRoadCanvas canvas = SilkRoadCanvas.getSilkRoadCanvas();
		canvas.draw(this, this.color, new java.awt.Rectangle(this.pxcol, this.pxrow, this.width, this.height));
		canvas.pause();
	}
}
