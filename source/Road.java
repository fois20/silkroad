/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * esta clase implementa la logica detras del simulador, es decir que estas
 * son las acciones que se llaman desde 'Silkroad.java'
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

import java.util.List;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Comparator;

public class Road
{
	/**
	 * este valor representa la mayor cantidad de chunks que se pueden ver
	 * en un solo frame, es 17 dado que en un grid de 5x5 teniendo chunks
	 * de 1x1 alineados de una forma en espiral se pueden almacenar maximo
	 * 17
	 *
	 * NOTE: esta valor fue winSizeComputed
	 */
	public static final int MAX_NO_VISIBLE_CHUNKS_PER_FRAME = 17;

	/**
	 * para un grid de 5x5 se calculan las posiciones de arriba a la izquierda
	 * de donde deberian ir los chunks (reactangulos) dentro de la pantalla
	 * para dar la forma de espiral
	 *
	 * NOTE: esta valor fue winSizeComputed
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
	 * valor el cual representa el tamano de un chunk (pedazo de tierra el cual representa
	 * el camino)
	 * 
	 * NOTE: esta valor fue winSizeComputed indirectamente, por ende no cambiar
	 */
	private static final int _terrainSizeInPixels = 100;

	/*   ______________________________________
	 * / la diferencia entre fullroad y terrain \
	 * | es que fullroad almacena todos los     |
	 * | estados a lo largo del mapa, mientras  |
	 * | que fullroad guarda simplemente los    |
	 * \ rectangulos que representan el terreno /
	 *   --------------------------------------
	 *          \   ^__^ 
	 *           \  (oo)\_______
	 *              (__)\       )\/\
	 *                  ||----w |
	 *                  ||     ||
	 */

	/**
	 * almacena todos los chunks para este Road, para nostros no tiene mucho sentido tener
	 * mas de un objeto de esta clase pero bueno
	 */
	private Chunk [] fullroad;

	/**
	 * almacena las representaciones de la tierra, no confundir con 'fullroad'
	 * (leer explicacion de la diferencia arriba)
	 */
	private Rectangle [] terrain;

	/**
	 * length:       longitud total de esta ruta
	 * nopages:      numero de paginas creadas para esta ruta
	 * nopage:       numero de pagina actual
	 * chunkFirst_i: indice del primer chunk visible en esta pagina
	 * chunkLast_i:  indice del ultimo chunk visible en esta pagina
	 * nostores:     numero de tiendas creadas a lo largo del mapa
	 * maxprofit:    suma total de tenges de cada  tienda sin restas
	 * norobots:     numero de robots total en el mapa
	 * profit:       profit obtenido teniendo en cuenta los pasos de los robots
	 * noday:        dia numero x
	 * tngsmax:      mayor cantidad de tenges posibles (se actualiza en moveRobots)
	 */
	private final int length;
	private final int nopages;
	private int       nopage;
	private int       chunkFirst_i;
	private int       chunkLast_i;
	private int       nostores;
	private int       maxprofit;
	private int       norobots;
	private int       profit;
	private int       noday;
	private Robot     mvp;
	private int       tngsmax;

	public Road (final int length)
	{
		this.terrain  = new Rectangle[MAX_NO_VISIBLE_CHUNKS_PER_FRAME];
		this.fullroad = new Chunk[length];
		this.length   = length;

		final PageOrientation ornts[] = { PageOrientation.EVEN, PageOrientation.ODD };
		for (int i = 0, j = 0; i < this.length; i++)
		{
			if (((i % MAX_NO_VISIBLE_CHUNKS_PER_FRAME) == 0) && (i != 0)) { j = 1 - j; }
			this.fullroad[i] = new Chunk(ornts[j], i, (i < MAX_NO_VISIBLE_CHUNKS_PER_FRAME));
		}

		final int minshow = Math.min(MAX_NO_VISIBLE_CHUNKS_PER_FRAME, this.length);
		for (int i = 0; i < minshow; i++)
		{
			this.terrain[i] = new Rectangle(
				SColor.road,
				_terraincoords[i][0],
				_terraincoords[i][1],
				_terrainSizeInPixels,
				_terrainSizeInPixels
			);
			this.terrain[i].changevisibility(true);
		}

		this.nopages      = (int) Math.ceil((double) this.length / MAX_NO_VISIBLE_CHUNKS_PER_FRAME);
		this.chunkFirst_i = 0;
		this.chunkLast_i  = minshow - 1;

		this.nostores     = 0;
		this.maxprofit    = 0;
		this.norobots     = 0;
		this.profit       = 0;
		this.noday        = 0;
		this.mvp          = null;
		this.tngsmax      = 0;
	}

	/**
	 * cambia los limites de la pagina actual con el fin de actualizarlos a la nueva
	 * vista que el usuario desea tener del terreno
	 *
	 * @param no numero de pagina (0-basado)
	 */
	public void changePageVisual (final int no) throws IllegalInstruction
	{
		if (no == this.nopage)
		{
    			return;
		}
		if ((no < 0) || (no >= this.nopages))
		{
			throw new IllegalInstruction(String.format(
				"no se puede acceder a la pagina %d dado que no existe.\n" +
				" Por favor asegurese de ponder una pagina entre 0 y %d",
				no,
				this.nopages - 1
			));
		}

		for (int i = this.chunkFirst_i; i != this.chunkLast_i; i++) { this.fullroad[i].changevisibility(false); }

		this.chunkFirst_i = no * MAX_NO_VISIBLE_CHUNKS_PER_FRAME;
		this.chunkLast_i  = no * MAX_NO_VISIBLE_CHUNKS_PER_FRAME;
		this.nopage       = no;
		this.chunkLast_i += Math.min(MAX_NO_VISIBLE_CHUNKS_PER_FRAME, this.length - this.chunkFirst_i);

		this.displayTerrainChunksBased4ThisPage();
		for (int i = this.chunkFirst_i; i != this.chunkLast_i; i++) { this.fullroad[i].changevisibility(true); }
	}

	/** ubica una tienda en la posicion dada con los tenges indicados siempre y cuando
	 * se pueda
	 *
	 * @param location posicion global de la tienda
	 * @param tenges dinero inicial de la tienda
	 */
	public void placeStore (final int location, final int tenges) throws IllegalInstruction
	{
		if (!this.locationIsOK(location) || (this.fullroad[location].getStore() != null) || (tenges <= 0))
		{
			throw new IllegalInstruction(String.format(
				"no se puede colocar una tienda en la posicion %d, asegurese\n"          +
				"de que no haya un tienda previamente colocada ahi y que la posicion\n"  +
				"este dentro del rango del mapa (rango: [0, %d])\n"                      +
				"tambien los tenges debe ser un numero mayor a cero",
				location,
				this.length - 1
			));
		}

		this.nostores++;
		this.maxprofit += tenges;
		this.fullroad[location].inagurateStore(tenges);
	}

	/**
	 * remueve una tienda dada su ubicacion global siempre y cuando haya una tienda
	 * en la posicion indicada
	 *
	 * @param location gloabl id chunk en el cual borrar la tienda
	 */
	public void removeStore (final int location) throws IllegalInstruction
	{
		if (!this.locationIsOK(location) || (this.fullroad[location].getStore() == null))
		{
			throw new IllegalInstruction(String.format(
				"no se puede eliminar una tienda en la posicion %d, asegurese\n"         +
				"de que si haya un tienda previamente colocada ahi y que la posicion\n"  +
				"este dentro del rango del mapa (rango: [0, %d])",
				location,
				this.length - 1
			));
		}

		this.nostores--;
		this.fullroad[location].closeStore();
	}

	/**
	 * ubica un robot en la posicion indicada siempre y cuando se cumplan las siguientes condiciones:
	 * a) no hayan tiendas en esa posicion
	 * b) no haya otro robot que fue colocado ahi previamente
	 * c) que la posicion este en el rango
	 *
	 * @param location posicion
	 */
	public void placeRobot (final int location) throws IllegalInstruction
	{
		final boolean willfail =
			(!this.locationIsOK(location))               ||
			(this.fullroad[location].getRobot() != null) ||
			(this.fullroad[location].getStore() != null);

		if (willfail)
		{
			throw new IllegalInstruction(String.format(
				"no se puede poner un robot en la posicion %d; posibles causas:\n" +
				" a. %d no esta en el rango [0, %d]\n"                             +
				" b. un robot ya aparece en la posicion %d\n"                      +
				" c. hay una tienda en la posicion %d",
				location,
				location,
				this.length - 1,
				location,
				location
			));
		}

		this.norobots++;
		this.fullroad[location].placeRobot();
	}

	/**
	 * remove el robot que fue colocado en la posicion indicada siempre y cuando haya
	 * un robot que borrar
	 *
	 * @param location location
	 */
	public void removeRobot (final int location) throws IllegalInstruction
	{
		if (!this.locationIsOK(location) || (this.fullroad[location].getRobot() == null))
		{
			throw new IllegalInstruction(String.format(
				"no se puede eliminar un robot en la posicion %d; posibles causas:\n" +
				" a. %d no esta en el rango [0, %d]\n"                             +
				" b. no hay robot en la posicion %d",
				location,
				location,
				this.length - 1,
				location
			));
		}

		final Robot robot = this.fullroad[location].getRobot();
		final int robotIsAt = robot.getGlobalChunkNo();

		if (location != robotIsAt)
		{
			this.fullroad[robotIsAt].colateralKill(robot.getPositionInQueue());
		}

		this.norobots--;
		this.fullroad[location].killRobot();
	}

	/**
	 * mueve un solo robot por llamado, esta funcion no intenta maximizar nada, solo hace lo
	 * que se le pide
	 *
	 * @param location globalid del robot a mover
	 * @param @meters distancia a mover
	 */
	public void moveRobot (final int location, final int meters) throws IllegalInstruction
	{
		if (!this.locationIsOK(location) || (this.fullroad[location].getNoRobotsHere() == 0))
		{
			throw new IllegalInstruction(String.format(
				"no se puede mover un robot en la posicion %d; posibles causas:\n" +
				" a. %d no esta en el rango [0, %d]\n"                             +
				" b. no hay robot(s) en la posicion %d",
				location,
				location,
				this.length - 1,
				location
			));
		}
		if (meters == 0)
		{
			return;
		}

		final Robot robot = this.fullroad[location].getFirstRobotThatCameHere();
		final int desitination = robot.getGlobalChunkNo() + meters;

		if ((desitination < 0) || (desitination >= this.length))
		{
			throw new IllegalInstruction(String.format(
				"no se puede mover el robot a la posicion %d :(, esta fuera del rango permitido (%d)",
				location,
				desitination
			));
		}

		final PageOrientation or = this.fullroad[desitination].getOrientation();
		robot.move(
			this.fullroad[desitination].getDisplayed(),
			or.getModifiedIndexBasedOnInternalId(desitination % MAX_NO_VISIBLE_CHUNKS_PER_FRAME)
		);

		final int queued = this.fullroad[desitination].newRobotGonnaBeHere(robot);

		robot.setGlobalChunkNo(desitination);
		robot.setPositionInQueue(queued);

		final Store st = this.fullroad[desitination].getStore();

		if (st != null && st.getAvailableness())
		{
			final int finalpft = st.getTengesAmount() - Math.abs(meters);
			robot.increaseProfit(finalpft);

			robot.addProducedByMovement(finalpft);
			st.setAvailableness(false);

			this.profit += finalpft;
			SilkRoadCanvas.updateProgressBar((int) ((double) this.profit * 100 / this.maxprofit));

			this.attemptToUpdateMVP(robot);
			return;
		}
		robot.addProducedByMovement(-1 * Math.abs(meters));
	}

	/**
	 * esta funcion mueve los robots buscando obtener el mayor beneficio posible, se usa
	 * una estrategia de programacion dinamica para poder resolver el problema
	 *
	 * @variable type: (0: skip robot) (1: skip store) (2: match)
	 */
	public voidmoveRobots ()
	{
		if (this.norobots == 0 || this.nostores == 0) { return; }

		final int [][]robots = this.consultRobots();
		final int [][]stores = this.consultStores();

		int  [][]dp     = new int[this.norobots + 1][this.nostores + 1];
		int  [][]choice = new int[this.norobots + 1][this.nostores + 1];

		for (int i = 1; i <= this.norobots; i++)
		{
			final int robpos = robots[i - 1][0];

			for (int j = 1; j <= this.nostores; j++)
			{
				final int nchnk = stores[j - 1][0];
				final Store str = this.fullroad[nchnk].getStore();
				final int prft  = str.getTengesAmount() - Math.abs(robpos - nchnk);

				int best = dp[i - 1][j], move = 0;

				if (dp[i][j - 1] > best)
				{
					best = dp[i][j - 1];
					move = 1;
				}
				if (dp[i - 1][j - 1] + prft > best)
				{
					best = dp[i - 1][j - 1] + prft;
					move = 2;
				}

				dp[i][j] = best;
				choice[i][j] = move;
			}
		}
		int i =  this.norobots, j = this.nostores;
		while (i > 0 && j > 0)
		{
			if (choice[i][j] == 2)
			{
				final int nchnk = stores[j - 1][0];
				final int jmpfr = robots[i - 1][0];

				try { this.moveRobot(jmpfr, nchnk -  jmpfr); }
				catch (final IllegalInstruction e) {}
				i--;
				j--;
			}
			else if (choice[i][j] == 0) { i--; }
			else                        { j--; }
		}

		this.tngsmax = dp[this.norobots][this.nostores];
	}

	public void reboot ()
	{
		for (int i = 0; i < this.length; i++)
		{
			this.fullroad[i].reboot();
		}

		this.profit = 0;
		if (this.mvp != null)
		{
			this.mvp.imTheMVP(false);
		}
		this.mvp    = null;

		this.noday++;
		SilkRoadCanvas.updateProgressBar(0);
	}

	/**
	 * devuleve informacion sobre todas las tiendas organizadas de menor a mayor por
	 * localizacion
	 * 
	 * @return [{posicion, tenges} ...]
	 */
	public int[][] consultStores ()
	{
		int [][] ans = new int[this.nostores][2];

		for (int i = 0, j = 0; i < this.length; i++)
		{
			final Store st = this.fullroad[i].getStore();
			if (st == null)
			{
				continue;
			}

			ans[j]      = new int[2];
			ans[j][0]   = i;
			ans[j++][1] = st.getTengesAmount();
		}
		return ans;
	}

	/**
	 * devuleve informacion sobre todas las tiendas organizadas de menor a mayor por
	 * localizacion
	 * 
	 * @return [{posicion, numero_de_veces_vaciada} ...]
	 *                               ` este valor solo puede ser 1 o 0 dado que una tienda
	 *                               solo puede ser desocupada una vez por dia
	 */
	public int[][] emptiedStores ()
	{
		int [][] ans = new int[this.nostores][2];

		for (int i = 0, j = 0; i < this.length; i++)
		{
			final Store st = this.fullroad[i].getStore();
			if (st == null)
			{
				continue;
			}

			ans[j]      = new int[2];
			ans[j][0]   = i;
			ans[j++][1] = st.getEmptied();
		}
		return ans;
	}

	/**
	 * devuleve informacion sobre todos los robots organizados de menor a mayor por
	 * localizacion
	 * 
	 * @return [{posicion, tenges} ...]
	 */
	public int[][] consultRobots ()
	{
		int [][] ans = new int[this.norobots][];
		for (int i = 0, j = 0; i < this.length; i++)
		{
			for (final Robot r: this.fullroad[i].getRobots())
			{
				ans[j]    = new int[2];
				ans[j][0] = i;
				ans[j][1] = r.getProfit();
				j++;
			}
		}
		return ans;
	}

	/**
	 * devuleve informacion sobre todos los robots y la cantidad de tenges
	 * que han colectado organizados de menor a mayor por localizacion
	 * 
	 * @return [{posicion, m1, m2, ..., mn} ...]
	 */
	public int [][] profitPerMove ()
	{
		int [][] ans = new int[this.norobots][];
		for (int i = 0, j = 0; i < this.length; i++)
		{
			for (final Robot r: this.fullroad[i].getRobots())
			{
				final List<Integer> pdcd = r.getProdPerMove();

				ans[j]    = new int[1 + pdcd.size()];
				ans[j][0] = i;

				for (int l = 1; l <= pdcd.size(); l++)
				{
					ans[j][l] = pdcd.get(l - 1);
				}
				j++;
			}
		}
		return ans;
	}

	/**
	 * una vez se cambia de pagina, es posible que se tengan que renderizar/ocultar pedazos de tierra,
	 * este metodo se encarga de actualizar los pedazos de tierra que se deberian y no deberian ver
	 */
	private void displayTerrainChunksBased4ThisPage ()
	{
		final PageOrientation or = PageOrientation.orienationFor(this.nopage);
		int lim = (this.chunkLast_i - this.chunkFirst_i);

		if (or == PageOrientation.ODD)
		{
			lim = MAX_NO_VISIBLE_CHUNKS_PER_FRAME - lim;
		}

		for (int i = or.getstarts(); i != or.getends(); i += or.getchange())
		{
			if (or == PageOrientation.ODD)
			{
				this.terrain[i].changevisibility((i < lim) ? false : true);
			}
			else
			{
				this.terrain[i].changevisibility((i > lim) ? false : true);
			}
		}
	}

	/**
	 * se asegura que la posicion dada este dentro del rango permitido del
	 * mapa
	 *
	 * @return true si si, false si no, daahh
	 */
	private boolean locationIsOK (final int location) { return (location >= 0) && (location < this.length); }

	/**
	 * esta funcion intenta actualizar el valor del MVP revisando si el robot dado tiene
	 * mas dinero que el mvp actual
	 *
	 * @param robot robot acual
	 */
	private void attemptToUpdateMVP (final Robot robot)
	{
		if (this.mvp == null)
		{
			this.mvp = robot;
			this.mvp.imTheMVP(true);
			return;
		}
		if (robot.getProfit() <= this.mvp.getProfit())
		{
			return;
		}

		this.mvp.imTheMVP(false);
		this.mvp = robot;
		this.mvp.imTheMVP(true);
	}

	public int getNoPages       ()  { return this.nopages; }
	public int getNoPage        ()  { return this.nopage ; }
	public int getProfit        ()  { return this.profit ; }
	public int getLastTengesMax ()  { return this.tngsmax; }
}

