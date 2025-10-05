/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta enumeracion define como las paginas cambian su orientacion
 * dependiendo de si el numero de pagina es par o impar.
 *
 * Paginas pares: comienzan desde la esquina superior izquierda de la pantalla.  
 * Paginas impares: comienzan desde el centro de la pantalla.
 */

public enum PageOrientation
{
	/**
	 * Orientacion para paginas pares.
	 */
	EVEN (0, Road.MAXNOTERRAINLENGTH, 1),

	/**
	 * Orientacion para paginas impares.
	 */
	ODD  (Road.MAXNOTERRAINLENGTH - 1, -1, -1);

	/**
	 * Determina la orientacion segun el numero de pagina.
	 * @param pageno numero de pagina
	 * @return EVEN si el numero de pagina es par, ODD si es impar
	 */
	public static PageOrientation orientationOf (final int pageno)
	{
		return ((pageno % 2) == 0) ? EVEN : ODD;
	}

	private final int starts;
	private final int ends;
	private final int delta;

	/**
	 * Constructor privado de la enumeracion PageOrientation.
	 * Define los valores de inicio, fin y delta de la orientacion.
	 * @param starts posicion inicial
	 * @param ends posicion final
	 * @param delta direccion de avance (1 hacia adelante, -1 hacia atras)
	 */
	PageOrientation (final int starts, final int ends, final int delta)
	{
		this.starts = starts;
		this.ends   = ends;
		this.delta  = delta;
	}

	/**
	 * Obtiene el indice modificado en base al identificador interno,
	 * teniendo en cuenta la orientacion de la pagina.
	 * @param id identificador interno
	 * @return indice ajustado segun la orientacion
	 */
	public int getModifiedIndexBasedOnInternalId (final int id)
	{
		if (this == EVEN)
		{
			return id;
		}
		return Road.MAXNOTERRAINLENGTH - id - 1;
	}

	/**
	 * @return valor inicial de la orientacion
	 */
	public int getstarts () { return this.starts; }

	/**
	 * @return valor final de la orientacion
	 */
	public int getends   () { return this.ends;   }

	/**
	 * @return delta o direccion de la orientacion
	 */
	public int getdelta  () { return this.delta;  }
}

