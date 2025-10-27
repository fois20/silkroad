/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author juan diego patino munoz
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
}
