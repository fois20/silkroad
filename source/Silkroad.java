/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase representa el punto de inicio del proyecto. 
 * El proyecto debe ser ejecutado desde BlueJ, lo cual implica
 * que no existe un metodo main tradicional.
 *
 * Nota: no confundir con la clase Road, ya que Silkroad provee
 * los controles de la simulacion.
 */

public class Silkroad
{
	/** Longitud total de la carretera, especificada al inicio. */
	public static int LENGTH;

	/** Indica si la ultima operacion fue realizada con exito. */
	private boolean ok;

	/**
	 * Constructor que inicializa la simulacion con una carretera
	 * de la longitud dada.
	 * @param length longitud de la carretera
	 */
	public Silkroad (final int length)
	{
		Silkroad.LENGTH = length;
		Road.createRoad();
	}

	/**
	 * Constructor alternativo que recibe informacion de dias.
	 * Actualmente no implementado.
	 * @param days matriz con datos de dias
	 */
	public Silkroad (final int[][] days)
	{
	}

	/**
	 * Cambia la pagina visible en la simulacion, pidiendo el
	 * numero de pagina al usuario.
	 */
	public void changePage ()
	{
		final int pageno = Misc.changePageDialog();
		if ((pageno < 0) || (pageno >= Road.NOPAGES))
		{
			Misc.invalidPageNumberDialog(pageno);
			return;
		}
		Road.changePageVisual(pageno);
	}

	/**
	 * Intenta colocar una tienda en la posicion indicada.
	 * @param location posicion del chunk
	 * @param tenges identificador de la tienda
	 */
	public void placeStore (final int location, final int tenges)
	{
		if (location < 0 || location >= LENGTH)
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location);
			return;
		}

		this.ok = Road.placeStore(location, tenges);
		if (!this.ok)
		{
			Misc.invalidLocationToPlaceAStoreAt(location);
		}
	}

	/**
	 * Intenta remover una tienda en la posicion indicada.
	 * @param location posicion del chunk
	 */
	public void removeStore (final int location)
	{
		if (location < 0 || location >= LENGTH)
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location);
			return;
		}

		this.ok = Road.removeStore(location);
		if (!this.ok)
		{
			Misc.invalidLocationToRemoveAStoreAt(location);
		}
	}

	/**
	 * intenta colocar un robot en la posicion dada
	 * @param location posicion del chunk
	 */
	public void placeRobot (final int location)
	{
		if (location < 0 || location >= LENGTH)
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location);
			return;
		}
		this.ok = Road.placeRobot(location);
		if (!this.ok)
		{
			Misc.invalidLocationToPlaceARobotAt(location);
		}
	}

	/**
	 * intenta borrar un robot en la posicion especificada
	 * @param location posicion del chunk donde spawnea el robot
	 */
	public void removeRobot (final int location)
	{
		if (location < 0 || location >= LENGTH)
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location);
			return;
		}
		this.ok = Road.removeRobot(location);
		if (!this.ok)
		{
			Misc.invalidLocationToRemoveARobotAt(location);
		}
	}

	/**
	 * Intenta mover un robot en la posicion dada 'metros' chunks
	 * @param location posicion en la que el robot esta
	 * @param meters numero de chunks a mover
	 */
	public void moveRobot (final int location, final int meters)
	{
		if (location < 0 || location >= LENGTH)
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location);
			return;
		}
		this.ok = Road.moveRobot(location, meters);
		if (!this.ok)
		{
			Misc.cannotMoveRobotAtThatLocation(location);
		}
	}

	/**
	 * Simula el paso de un dia, hace que todos los robots vuelvan
	 * a sus posiciones iniciales y las tiendas se reestablezcan
	 */
	public void makeANewDay ()
	{
		this.ok = Road.newDay();
	}

	/**
	 * Crea una ventana para indicar el profit actual de
	 * este dia
	 */
	public void consultProfit ()
	{
		Misc.consultProfitDialog();
		this.ok = true;
	}

	/**
	 * imprime un arraglo 2D de enteros que representa las tiendas del silkroad
	 * ordenadas de menor a mayor por localizacion
	 */
	public int [][] stores ()
	{
		this.ok = true;
		return Road.consultStores();
	}
	
	/**
	 * imrpime un arraglo 2D de enteros que representa los robots del silkroad
	 * ordenadas de menor a mayor por localizacion
	 *
	 * @return array 2D [posicion, tenges]
	 */
	public int[][] robots ()
	{
		this.ok = true;
		return Road.consultRobots();
	}

	/**
	 * Muestra una ventana la cual indica si la ultima operacion tuvo exito
	 * o no
	 */
	public void ok ()
	{
		Misc.okDialog(this.ok);
		this.ok = true;
	}

	/**
	 * Establece la visibilidad del juego como verdadera, es el opuesto
	 * del metodo 'makeInvisible'
	 */
	public void makeVisible ()
	{
		Canvas.getcanvas().setVisible(true);
		this.ok = true;
	}

	/**
	 * Establece la visibilidad del juego como false, es el opuesto
	 * del metodo 'makeVisible'
	 */
	public void makeInvisible ()
	{
		Canvas.getcanvas().setVisible(false);
		this.ok = true;
	}
}

