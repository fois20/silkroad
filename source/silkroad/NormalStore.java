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

	private static final SColor[][] _colors =
	{
		{SColor.normalFC1,  SColor.normalRC1 },
		{SColor.normalFC2,  SColor.normalRC2 },
		{SColor.normalFC3,  SColor.normalRC3 },
		{SColor.normalFC4,  SColor.normalRC4 },
		{SColor.normalFC5,  SColor.normalRC5 },
		{SColor.normalFC6,  SColor.normalRC6 },
		{SColor.normalFC7,  SColor.normalRC7 },
		{SColor.normalFC8,  SColor.normalRC8 },
		{SColor.normalFC9,  SColor.normalRC9 },
		{SColor.normalFC10, SColor.normalRC10},
		{SColor.normalFC11, SColor.normalRC11},
		{SColor.normalFC12, SColor.normalRC12},
		{SColor.normalFC13, SColor.normalRC13},
		{SColor.normalFC14, SColor.normalRC14},
		{SColor.normalFC15, SColor.normalRC15},
		{SColor.normalFC16, SColor.normalRC16},
		{SColor.normalFC17, SColor.normalRC17},
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
