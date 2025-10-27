/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa el concepto de una tienda la cual se dibuja como un cuadrado
 * con un triangulo para dar la impresion de una casa
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package silkroad;

import canvas.Rectangle;
import canvas.Triangle;
import canvas.SColor;

public class NormalStore extends Store
{
	private static final SColor[][] _colors =
	{
		{SColor.NSFC1,  SColor.NSRC1 },
		{SColor.NSFC2,  SColor.NSRC2 },
		{SColor.NSFC3,  SColor.NSRC3 },
		{SColor.NSFC4,  SColor.NSRC4 },
		{SColor.NSFC5,  SColor.NSRC5 },
		{SColor.NSFC6,  SColor.NSRC6 },
		{SColor.NSFC7,  SColor.NSRC7 },
		{SColor.NSFC8,  SColor.NSRC8 },
		{SColor.NSFC9,  SColor.NSRC9 },
		{SColor.NSFC10, SColor.NSRC10},
		{SColor.NSFC11, SColor.NSRC11},
		{SColor.NSFC12, SColor.NSRC12},
		{SColor.NSFC13, SColor.NSRC13},
		{SColor.NSFC14, SColor.NSRC14},
		{SColor.NSFC15, SColor.NSRC15},
		{SColor.NSFC16, SColor.NSRC16},
		{SColor.NSFC17, SColor.NSRC17},
	};

	private Triangle  roof;
	private Rectangle facade;

	public NormalStore (final int tenges, final int localId, final boolean display, final SType type)
	{
		super(tenges, localId, display, type);

		this.facade = new Rectangle(_colors[localId][0], _coordinates[localId][0], _coordinates[localId][1], _commonsz, _commonsz);
		this.roof   = new Triangle (_colors[localId][1], _coordinates[localId][2], _coordinates[localId][3], _commonsz, _commonsz);
		this.changevisibility(display);
	}

	@Override
	public void changevisibility (final boolean to)
	{
		this.facade.changevisibility(to);
		this.roof.changevisibility(to);
		this.door.changevisibility(to);
	}
}
