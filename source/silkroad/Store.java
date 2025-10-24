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
		{85,  10 },
		{185, 10 },
		{285, 10 },
		{385, 10 },
		{485, 10 },
		{485, 135},
		{485, 235},
		{485, 335},
		{485, 485},
		{385, 485},
		{285, 485},
		{185, 485},
		{85,  485},
		{35,  335},
		{35,  235},
		{160, 210},
		{260, 210},
	};

	/**
	 * dado que hay MAX_NO_VISIBLE_CHUNKS_PER_FRAME exactamente en la pagina podemos
	 * calcular las coordenadas de donde deberian ir las casas dentro de la vista
	 * actual
	 *
	 * {fila_facade, column_facade, fila_roof, columna_roof}
	 *
	 * NOTE: esta valor fue winSizeComputed
	 */
	protected static int [][] _coordinates =
	{
		{75,  0,   50,  12 },
		{175, 0,   150, 12 },
		{275, 0,   250, 12 },
		{375, 0,   350, 12 },
		{475, 0,   450, 12 },
		{475, 125, 450, 137},
		{475, 225, 450, 237},
		{475, 325, 450, 337},
		{475, 475, 450, 487},
		{375, 475, 350, 487},
		{275, 475, 250, 487},
		{175, 475, 150, 487},
		{75,  475, 50,  487},
		{25,  325, 0,   337},
		{25,  225, 0,   237},
		{150, 200, 125, 212},
		{250, 200, 225, 212},
	};

	protected final int _commonsz = 25;

	public Store (final int tenges, final int localId, final boolean display, final SType type)
	{
		this.tenges    = tenges;
		this.available = true;
		this.type      = type;
		this.emptied   = 0;

		this.door = new Rectangle(
			SColor.door,
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
				return new AutoStore(tenges, localId, display, type);
			}
			case SType.FIGHTER:
			{
				return new FighterStore(tenges, localId, display, type);
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

