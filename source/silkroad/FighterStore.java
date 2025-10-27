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

import canvas.Triangle;
import canvas.SColor;

public class FighterStore extends Store
{
	private static final SColor[][] _colors =
	{
		{SColor.FSC1 }, 
		{SColor.FSC2 }, 
		{SColor.FSC3 }, 
		{SColor.FSC4 }, 
		{SColor.FSC5 }, 
		{SColor.FSC6 }, 
		{SColor.FSC7 }, 
		{SColor.FSC8 }, 
		{SColor.FSC9 }, 
		{SColor.FSC10},
		{SColor.FSC11},
		{SColor.FSC12},
		{SColor.FSC13},
		{SColor.FSC14},
		{SColor.FSC15},
		{SColor.FSC16},
		{SColor.FSC17},
	};

	private Triangle facade;

	public FighterStore (final int tenges, final int localId, final boolean display, final SType type)
	{
		super(tenges, localId, display, type);

		this.facade = new Triangle(
			_colors[localId][0],
			_coordinates[localId][0] - (_commonsz / 2) + 2,
			_coordinates[localId][1] + (_commonsz / 2) + 2,
			_commonsz + 10,
			_commonsz + 10
		);
		this.changevisibility(display);
	}

	@Override
	public void changevisibility (final boolean to)
	{
		this.facade.changevisibility(to);
		this.door.changevisibility(to);
	}
}
