/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase almacena toda la logica e informacion de la simulacion.
 *
 * Nota: no confundir con la clase Silkroad. Road provee el backend
 * de la simulacion.
 */

import java.util.List;

public class Road
{
	/**
	 * Arreglo que almacena todos los chunks de la carretera.
	 */
	private static Chunk [] _fullroad;

	/**
	 * Arreglo que almacena los rectangulos que simulan el terreno.
	 */
	private static Rectangle [] _terrain;

	/**
	 * Numero maximo de cuadrados de terreno que se pueden dibujar
	 * a la vez en la ventana.
	 */
	public static final int MAXNOTERRAINLENGTH = 17;

	/** Tamano en pixeles de cada cuadrado de terreno (100x100).
	 */
	private static final int _terrainSqrSize = 100;

	/**
	 * Coordenadas precalculadas de cada cuadrado del terreno dentro
	 * de la ventana. Permite optimizar el renderizado.
	 */
	private static final int[][] _terraincoords =
	{
		{0,     0},
		{100,   0},
		{200,   0},
		{300,   0},
		{400,   0},
		{400, 100},
		{400, 200},
		{400, 300},
		{400, 400},
		{300, 400},
		{200, 400},
		{100, 400},
		{0  , 400},
		{0,   300},
		{0,   200},
		{100, 200},
		{200, 200},
	};

	/**
	 * Indice del primer chunk visible en la pagina actual.
	 */
	private static int _chunk0i;

	/**
	 * Indice del ultimo chunk visible en la pagina actual.
	 */
	private static int _chunkNi;

	/**
	 * Maximo numero de tenges que se puden obtener (no se le resta
	 * el numero de pasos)
	 */
	public static int _maxprofit = 0;;

	/**
	 * Indica el numero de tiendas que no han sido eliminadas aun, es decir
	 * si se crean tres y luego se elimina una, esta variable sera dos
	 */
	public static int _noStores = 0;

	/**
	 * Indica el numero de robots que no han sido eliminados aun, es decir
	 * si se crean tres y luego se elimina uno, esta variable sera dos
	 */
	public static int _noRobots = 0;

	/**
	 * Numero total de paginas del mapa.
	 */
	public static int NOPAGES = 1;

	/**
	 * Numero de pagina actual.
	 */
	public static int CURPAGE = 0;

	/**
	 * Numero total de tenges obtenidos este dia
	 */
	public static int PROFIT = 0;

	/**
	 * Numero total de dias, empezando por cero
	 */
	public static int DAYNO = 0;

	/**
	 * Crea la carretera inicial con todos sus chunks y rectangulos de terreno.
	 */
	public static void createRoad ()
	{
		_terrain  = new Rectangle[MAXNOTERRAINLENGTH];
		_fullroad = new Chunk[Silkroad.LENGTH];

		final PageOrientation orientations[] =
		{
			PageOrientation.EVEN,
			PageOrientation.ODD
		};

		for (int i = 0, j = 0; i < Silkroad.LENGTH; i++)
		{
			if (((i % MAXNOTERRAINLENGTH) == 0) && (i != 0))
			{
				j = 1 - j;
			}

			_fullroad[i] = new Chunk(orientations[j], i, (i < MAXNOTERRAINLENGTH));
		}

		final int minwinterrgen = Math.min(MAXNOTERRAINLENGTH, Silkroad.LENGTH);
		for (int i = 0; i < minwinterrgen; i++)
		{
			_terrain[i] = new Rectangle(_terraincoords[i][0], _terraincoords[i][1], _terrainSqrSize, _terrainSqrSize, SColor.road);
			_terrain[i].changevisibility(true);
		}

		NOPAGES  = (int) Math.ceil((double) Silkroad.LENGTH / MAXNOTERRAINLENGTH);
		_chunk0i = 0;
		_chunkNi = minwinterrgen - 1;
	}

	/**
	 * Cambia la pagina visualizada en pantalla.
	 * @param pageno numero de la nueva pagina
	 */
	public static void changePageVisual (final int pageno)
	{
		if (pageno == CURPAGE)
		{
			return;
		}

		for (int i = _chunk0i; i != _chunkNi; i++) { _fullroad[i].changevisibility(false); }

		_chunk0i = pageno * MAXNOTERRAINLENGTH;
		_chunkNi = pageno * MAXNOTERRAINLENGTH;
		CURPAGE  = pageno;
		_chunkNi += Math.min(MAXNOTERRAINLENGTH, Silkroad.LENGTH - _chunkNi);

		displayTerrainBasedOnThisPage();
		for (int i = _chunk0i; i != _chunkNi; i++) { _fullroad[i].changevisibility(true); }
	}

	/**
	 * Intenta colocar una tienda en la posicion indicada.
	 * @param location posicion del chunk
	 * @param tenges identificador de la tienda
	 * @return true si se coloco la tienda, false si ya existia una
	 */
	public static boolean placeStore (final int location, final int tenges)
	{
		if (_fullroad[location].getStore() != null)
		{
			return false;
		}

		_noStores++;
		_fullroad[location].inaugurateStore(tenges);
		_maxprofit += tenges;
		return true;
	}

	/**
	 * Intenta eliminar una tienda en la posicion indicada.
	 * @param location posicion del chunk
	 * @return true si se elimino, false si no habia tienda
	 */
	public static boolean removeStore (final int location)
	{
		if (_fullroad[location].getStore() == null)
		{
			return false;
		}

		_noStores--;
		_fullroad[location].closeStore();
		return true;
	}

	/**
	 * Intenta colocar un robot en la posicion indicada.
	 * @param location posicion del chunk
	 * @return true si se coloco el robot, false si ya existia una
	 */
	public static boolean placeRobot (final int location)
	{
		if (_fullroad[location].getStore() != null)
		{
			return false;
		}
		if (_fullroad[location].getRobot() != null)
		{
			return false;
		}

		_noRobots++;
		_fullroad[location].placeRobot();
		return true;
	}

	/**
	 * Intenta remover un robot en la posicion especificada plus
	 * la referencia el robot donde sea que este
	 * @param location posicion en la que spawnea el robot a eliminar
	 * @return true si se elimino, false si no
	 */
	public static boolean removeRobot (final int location)
	{	
		if (_fullroad[location].getRobot() == null)
		{
			return false;
		}

		final Robot robot   = _fullroad[location].getRobot();
		final int robotIsAt = robot.getGlobalChunkNo();

		if (location != robotIsAt)
		{
			_fullroad[robotIsAt].colateralKill(robot.getPositionInQueue());
		}

		_noRobots--;
		_fullroad[location].killRobot();
		return true;
	}

	/**
	 * Intenta mover un robot en la posicion dada 'metros' chunks
	 * @param location posicion en la que el robot esta
	 * @param meters numero de chunks a mover
	 * @return true si se logro mover el robot, false si no
	 */
	public static boolean moveRobot (final int location, final int meters)
	{
		if (_fullroad[location].getNoRobotsHere() == 0)
		{
			return false;
		}
		if (meters == 0)
		{
			return true;
		}

		final Robot robot    = _fullroad[location].getFirstRobotThatCameHere();
		final int desination = robot.getGlobalChunkNo() + meters;

		if ((desination < 0) || (desination >= Silkroad.LENGTH))
		{
			return false;
		}

		final PageOrientation or = _fullroad[desination].getOrientation();
		robot.move(_fullroad[desination].getDisplayed(), or.getModifiedIndexBasedOnInternalId(desination % MAXNOTERRAINLENGTH));

		final int posdat = _fullroad[desination].newRobotGonnaBeHere(robot);

		robot.setGlobalChunkNo(desination);
		robot.setPositionInQueue(posdat);

		final Store storeathatpos = _fullroad[desination].getStore();
		if (storeathatpos != null && storeathatpos.getAvailableness())
		{
			final int cost = Math.abs(meters);
			final int finalprofit = storeathatpos.getTengesAmount() - cost;

			robot.increaseProfit(finalprofit);
			storeathatpos.setAvailableness(false);

			PROFIT += finalprofit;
			Canvas.updateProgressBar((int) ((double) PROFIT * 100 / _maxprofit));
		}
		return true;
	}

	/**
	 * Simula el paso de un dia, hace que todos los robots vuelvan
	 * a sus posiciones iniciales y las tiendas se reestablezcan
	 * @return true siempre
	 */
	public static boolean newDay ()
	{
		for (int i = 0; i < Silkroad.LENGTH; i++)
		{
			_fullroad[i].newDayBaby();
		}

		PROFIT = 0;
		DAYNO++;
		Canvas.updateProgressBar(0);
		return true;
	}

	/**
	 * Retorna un arraglo 2D de enteros que representa las tiendas del silkroad
	 * ordenadas de menor a mayor por localizacion
	 *
	 * @return array 2D [posicion, tenges]
	 */
	public static int[][] consultStores ()
	{
		int [][] ans = new int[_noStores][2];

		for (int i = 0, j = 0; i < Silkroad.LENGTH; i++)
		{
			final Store store = _fullroad[i].getStore();
			if (store == null)
			{
				continue;
			}

			ans[j]      = new int [2];
			ans[j][0]   = i;
			ans[j++][1] = store.getTengesAmount();

		}

		return ans;
	}

	/**
	 * Retorna un arraglo 2D de enteros que representa los robots del silkroad
	 * ordenadas de menor a mayor por localizacion
	 *
	 * @return array 2D [posicion, tenges]
	 */
	public static int[][] consultRobots ()
	{
		int [][] ans = new int[_noRobots][];

		for (int i = 0, k = 0; i < Silkroad.LENGTH; i++)
		{
			final List<Robot> robs = _fullroad[i].getRobots();
			final int nrbs = robs.size();

			if (nrbs ==  0)
			{
				continue;
			}

			ans[k] = new int [nrbs];
			for (int j = 0; j < nrbs; j++)
			{
				ans[k][j] = robs.get(j).getProfit();
			}
			k++;
		}
		return ans;
	}

	/**
	 * Muestra el terreno dependiendo de la pagina actual
	 * y su orientacion (par o impar).
	 */
	private static void displayTerrainBasedOnThisPage ()
	{
		final PageOrientation or = PageOrientation.orientationOf(CURPAGE);
		int lim = (_chunkNi - _chunk0i);

		if (or == PageOrientation.ODD)
		{
			lim = MAXNOTERRAINLENGTH - lim;
		}

		for (int i = or.getstarts(); i != or.getends(); i += or.getdelta())
		{
			if (or == PageOrientation.ODD)
			{
				_terrain[i].changevisibility((i < lim) ? false : true);
			}
			else if (or == PageOrientation.EVEN)
			{
				_terrain[i].changevisibility((i > lim) ? false : true);
			}
		}
	}
}

