/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase circulo la cual representara un robot en la simulaicon
 * (esta clase hereda de ShapeCommon)
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package canvas;

import silkroad.Misc;

public class Circle extends ShapeCommon
{
	private final int diameter;

	public Circle (final SColor color, final int pxrow, final int pxcol, final int diameter)
	{
		super(color, pxrow, pxcol);
		this.diameter = diameter;
	}

	@Override
	protected void draw ()
	{
		if (Misc.TESTING) { return; }
		if (!this.visibility) { return; }
		final SilkRoadCanvas canvas = SilkRoadCanvas.getSilkRoadCanvas();
		canvas.draw(this, this.color, new java.awt.geom.Ellipse2D.Double(this.pxcol, this.pxrow, this.diameter, this.diameter));
		canvas.pause();
	}

	public void changeposition (final boolean show, final int newpxrow, final int newpxcol)
	{
		if (Misc.TESTING) { return; }
		this.erase();
		this.pxrow = newpxrow;
		this.pxcol = newpxcol;

		if (show)
		{
			this.draw();
		}
	}

	public boolean amIVisible () { return this.visibility; }
}