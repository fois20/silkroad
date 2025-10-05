/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase implementa el concepto de una tienda (store) junto con su
 * representacion visual dentro de la simulacion.
 */

public class Store
{
	/**
	 * Arreglo que contiene todas las coordenadas en las que debe ser
	 * colocada una tienda dentro del camino, dependiendo del indice
	 * de la pagina donde pertenece.
	 *
	 * Cada fila contiene:
	 * {fila_facade, columna_facade, fila_roof, columna_roof}
	 */
	private static int [][] _locs =
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

	/**
	 * Colores usados para cada tienda. 
	 * Cada entrada contiene dos colores: 
	 * [0] para la fachada, [1] para el techo.
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

	private static final int _sz = 25;

	private Rectangle facade;
	private Triangle  roof;
	private boolean   available;
	private boolean   shown;
	private int       tenges;

	/**
	 * Constructor de la tienda.
	 * @param tenges dinero de la tienda
	 * @param nth indice que determina las coordenadas y colores
	 * @param displaynow indica si debe mostrarse al crearse
	 */
	public Store (final int tenges, final int nth, final boolean displaynow)
	{
		this.facade    = new Rectangle(_locs[nth][0], _locs[nth][1], _sz, _sz, _colors[nth][0]);
		this.roof      = new Triangle (_locs[nth][2], _locs[nth][3], _sz, _sz, _colors[nth][1]);

		this.tenges    = tenges;
		this.available = true;

		this.changevisibility(displaynow);
	}

	/**
	 * Cambia la visibilidad de la tienda (fachada y techo).
	 * @param to true para mostrar, false para ocultar
	 */
	public void changevisibility (final boolean to)
	{
		this.facade.changevisibility(to);
		this.roof.changevisibility(to);
	}

	/**
	 * Retorna cuantos tenges almacena esta tienda
	 * @return valor de tenges
	 */
	public int getTengesAmount () { return this.tenges; }

	/**
	 * Retorna si una tienda esta disponible o no
	 * @return disponibilidad
	 */
	public boolean getAvailableness () { return this.available; }

	/**
	 * Establece si una tienda esta disponible o no
	 * @param to true si si, false si no
	 */
	public void setAvailableness (final boolean to) { this.available = to; }
}

