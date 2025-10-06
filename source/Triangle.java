/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase triangulo la cual representara los techos de las casas
 * (esta clase hereda de ShapeCommon)
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public class Triangle extends ShapeCommon
{
	private int width;
	private int height;

	public Triangle (final SColor color, final int pxrow, final int pxcol, final int width, final int height)
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

		final int [] xs = { this.pxcol, this.pxcol + (this.width / 2), this.pxcol - (this.width / 2) };
		final int [] ys = { this.pxrow, this.pxrow + this.height, this.pxrow + this.height };

		canvas.draw(this, this.color, new java.awt.Polygon(xs, ys, 3));
		canvas.pause();
	}
}
