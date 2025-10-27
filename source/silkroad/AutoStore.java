/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa el concepto de una tienda la cual se dibuja como un cuadrado
 * con un triangulo para dar la impresion de una casa
 *
 * @author juan diego patino munoz
 * @version 1
 */
package silkroad;

import canvas.Rectangle;
import canvas.SColor;

public class AutoStore extends Store
{
	private static final SColor[][] _colors =
	{
		{SColor.ASC1 }, 
		{SColor.ASC2 }, 
		{SColor.ASC3 }, 
		{SColor.ASC4 }, 
		{SColor.ASC5 }, 
		{SColor.ASC6 }, 
		{SColor.ASC7 }, 
		{SColor.ASC8 }, 
		{SColor.ASC9 }, 
		{SColor.ASC10},
		{SColor.ASC11},
		{SColor.ASC12},
		{SColor.ASC13},
		{SColor.ASC14},
		{SColor.ASC15},
		{SColor.ASC16},
		{SColor.ASC17},
	};

	private Rectangle facade;

	public AutoStore (final int tenges, final int localId, final boolean display, final SType type)
	{
		super(tenges, localId, display, type);

		this.facade = new Rectangle(_colors[localId][0], _coordinates[localId][0], _coordinates[localId][1], _commonsz, _commonsz);
		this.changevisibility(display);
	}

	@Override
	public void changevisibility (final boolean to)
	{
		this.facade.changevisibility(to);
		this.door.changevisibility(to);
	}
}
