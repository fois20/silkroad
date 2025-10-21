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

public class Store
{
	/**
	 * dado que hay MAX_NO_VISIBLE_CHUNKS_PER_FRAME exactamente en la pagina podemos
	 * calcular las coordenadas de donde deberian ir las casas dentro de la vista
	 * actual
	 *
	 * {fila_facade, column_facade, fila_roof, columna_roof}
	 *
	 * NOTE: esta valor fue winSizeComputed
	 */
	private static int [][] _coordinates =
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

	private static int [][] _doorcoords =
	{
		{87,  0  },
		{187, 0  },
		{287, 0  },
		{387, 0  },
		{487, 0  },
		{487, 125},
		{487, 225},
		{487, 325},
		{487, 475},
		{387, 475},
		{287, 475},
		{187, 475},
		{87,  475},
		{37,  325},
		{37,  225},
		{162, 200},
		{262, 200},
	};

	/**
	 * dado que cada tienda debe tener colores unicos por pagina, este
	 * array almacena los estilos para cada una de las MAX_NO_VISIBLE_CHUNKS_PER_FRAME
	 * tiendas
	 */
	private static final SColor[][] _colors =
	{
		{SColor.c1f,  SColor.c1r },
		{SColor.c2f,  SColor.c2r },
		{SColor.c3f,  SColor.c3r },
		{SColor.c4f,  SColor.c4r },
		{SColor.c5f,  SColor.c5r },
		{SColor.c6f,  SColor.c6r },
		{SColor.c7f,  SColor.c7r },
		{SColor.c8f,  SColor.c8r },
		{SColor.c9f,  SColor.c9r },
		{SColor.c10f, SColor.c10r},
		{SColor.c11f, SColor.c11r},
		{SColor.c12f, SColor.c12r},
		{SColor.c13f, SColor.c13r},
		{SColor.c14f, SColor.c14r},
		{SColor.c15f, SColor.c15r},
		{SColor.c16f, SColor.c16r},
		{SColor.c17f, SColor.c17r},
	};

	private static final int _size = 25;

	private Triangle  roof;
	private Rectangle facade;
	private Rectangle door;
	private boolean   available;
	private int       tenges;
	private int       emptied;
	private SType     type;

	public Store (final int tenges, final int localId, final boolean display, final SType type)
	{
		this.facade = new Rectangle(_colors[localId][0], _coordinates[localId][0], _coordinates[localId][1], _size, _size);
		this.roof   = new Triangle (_colors[localId][1], _coordinates[localId][2], _coordinates[localId][3], _size, _size);
		this.door   = new Rectangle(SColor.road, _doorcoords[localId][0], _doorcoords[localId][1], _size - 15, _size - 10);

		this.tenges    = tenges;
		this.available = true;
		this.type      = type;

		this.changevisibility(display);
	}

	public void changevisibility (final boolean to)
	{
		this.facade.changevisibility(to);
		this.roof.changevisibility(to);
		this.door.changevisibility(to);
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
}
