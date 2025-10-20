/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * esta enumeracion define como las paginas cambian su orientacion
 * dependiendo de si el numero de pagina es par o impar
 *
 * Paginas pares: comienzan desde la esquina superior izquierda de la pantalla
 * Paginas impares: comienzan desde el centro de la pantalla
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 */
package silkroad;

public enum PageOrientation
{
	EVEN (0, Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME, 1),
	ODD  (Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME - 1, -1, -1);

	/**
	 * retorna la orientacion que deberia tener una pagina basada en su indice
	 * @param index indice de la pagina
	 * @return orientacion apropiada
	 */
	public static PageOrientation orienationFor (final int index)
	{
		return ((index % 2) == 0) ? EVEN : ODD;
	}

	public static int getPageGivenLocation (final int loc)
	{
		return loc / Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;
	}

	final int starts;
	final int ends;
	final int change;

	PageOrientation (final int starts, final int ends, final int change)
	{
		this.starts = starts;
		this.ends = ends;
		this.change = change;
	}

	/**
	 * cuando un robot o tienda necesita ser colocada/movida/eliminada
	 * se debe saber en que posicion hacerlo basado en la orientacion
	 * de la pagina, esta funcion provee esa informacion
	 *
	 * @param id posicion dentro de la pagina
	 * @return posicion en la que deberia ir basado en la pagina
	 */
	public int getModifiedIndexBasedOnInternalId (final int id)
	{
		if (this == EVEN) { return id; }
		return this.starts - id;
	}

	public int getstarts () { return this.starts; }
	public int getends   () { return this.ends;   }
	public int getchange () { return this.change; }
}