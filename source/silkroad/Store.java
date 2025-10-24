/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase basica de una tienda abstractamente debido a que hay
 * varios tipos de tienda
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package silkroad;

import canvas.Rectangle;
import canvas.Triangle;
import canvas.SColor;

public abstract class Store
{
	protected boolean   available;
	protected int       tenges;
	protected int       emptied;
	protected SType     type;
	protected Rectangle door;

	private static int [][] _doorcoords =
	{
		{87,  10 },
		{187, 10 },
		{287, 10 },
		{387, 10 },
		{487, 10 },
		{487, 135},
		{487, 235},
		{487, 335},
		{487, 485},
		{387, 485},
		{287, 485},
		{187, 485},
		{87,  485},
		{37,  335},
		{37,  235},
		{162, 210},
		{262, 210},
	};

	protected final int _commonsz = 25;

	public Store (final int tenges, final int localId, final boolean display, final SType type)
	{
		this.tenges    = tenges;
		this.available = true;
		this.type      = type;
		this.emptied   = 0;

		this.door = new Rectangle(
			SColor.road,
			_doorcoords[localId][0],
			_doorcoords[localId][1],
			_commonsz - 15,
			_commonsz - 10
		);
	}

	public static Store createStore (final int tenges, final int localId, final boolean display, final SType type)
	{
		switch (type)
		{
			case SType.NORMAL:
			{
				return new NormalStore(tenges, localId, display, type);
			}
			case SType.AUTONOMOUS:
			{
			}
			case SType.FIGHTER:
			{
			}
		}
		return null;
	}

	public int     getTengesAmount  () { return this.tenges;    }
	public int     getEmptied ()       { return this.emptied;   }
	public boolean getAvailableness () { return this.available; }

	public void setAvailableness (final boolean to)
	{
		if (!to) { this.emptied++; }
		this.door.changevisibility(to);
		this.available = to;
	}

	public abstract void changevisibility (final boolean to);
}
