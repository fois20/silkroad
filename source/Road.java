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
		if (!this.locationIsOK(location) || (this.fullroad[location].getStore() != null))
		{
			throw new IllegalInstruction(String.format(
				"no se puede colocar una tienda en la posicion %d, asegurese\n"          +
				" de que no haya un tienda previamente colocada ahi y que la posicion\n" +
				" este dentro del rango del mapa (rango: [0, %d])",
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

		final PageOrientation or = this.fullroad[location].getOrientation();
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
			st.setAvailableness(false);

			this.profit += finalpft;
			SilkRoadCanvas.updateProgressBar((int) ((double) this.profit * 100 / this.maxprofit));
		}
	}

	public void reboot ()
	{
		for (int i = 0; i < this.length; i++)
		{
			this.fullroad[i].reboot();
		}

		this.profit = 0;
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
	 * devuleve informacion sobre todos los robots organizados de menor a mayor por
	 * localizacion
	 * 
	 * @return [{posicion, tenges} ...]
	 */
	public int[][] consultRobots ()
	{
		int [][] ans = new int[this.norobots][];
		for (int i = 0, k = 0; i < this.length; i++)
		{
			final List<Robot> robs = this.fullroad[i].getRobots();
			final int nrbs = robs.size();

			if (nrbs == 0) { continue; }
			ans[k] = new int[nrbs];
			for (int j = 0; j < nrbs; j++)
			{
				ans[k][j] = robs.get(j).getProfit();
			}
			k++;
		}
		return ans;
	}

	/**
	 * mueve todos los robots intentando maximizar el profit que pueden hacer
	 * usando un algoritmo greedy O(n^2)
	 */
	public void moveRobots ()
	{
		List<SmartMove> moves = new ArrayList<>();
		for (int i = 0; i < this.length; i++)
		{
			final List<Robot> robots = this.fullroad[i].getRobots();
			for (int j = robots.size() - 1; j >= 0; j--)
			{
				final Robot r = robots.get(j);
				final SmartMove move = this.getSmartMove(r);

				if ((move.getJumpTo() == r.getGlobalChunkNo()) || (move.getProfit() == -1)) { continue; }
				moves.add(move);
			}
		}

		for (SmartMove move: moves)
		{
			try { this.moveRobot(move.getJumpFrom(), move.getMeters()); }
			catch (IllegalInstruction e) {}
		}
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
	 * devuleve la posicion de la tienda a la cual el robot deberia moverse en orden de tener
	 * el mayor profit posible dentro de todas sus posibles opciones
	 *
	 * @param robot robot a mover
	 * @return informacion sobre el movimiento
	 */
	private SmartMove getSmartMove (final Robot robot)
	{
		final int robotIsAt = robot.getGlobalChunkNo();
		SmartMove move      = new SmartMove(robotIsAt);

		for (int i = 0; i < this.length; i++)
		{
			final Store st = this.fullroad[i].getStore();
			if (st == null) { continue; }
			
			final int profit = st.getTengesAmount() - Math.abs(robotIsAt - i);

			if (profit > move.getProfit() && st.getAvailableness())
			{
				move.setProfit(profit);
				move.setMeters(i - robotIsAt);
				move.setJumpTo(i);
			}
		}
		return move;
	}

	/**
	 * se asegura que la posicion dada este dentro del rango permitido del
	 * mapa
	 *
	 * @return true si si, false si no, daahh
	 */
	private boolean locationIsOK (final int location) { return (location >= 0) && (location < this.length); }

	/**
	 * clase creada con el fin de tener informacion sobre el mejor movimiento a hacer
	 * para obtener el mayor profit posible por robot
	 */
	private class SmartMove
	{
		private int jumpfrom;
		private int jumpto;
		private int meters;
		private int profit;

		public SmartMove (final int jumpfrom)
		{
			this.jumpfrom = jumpfrom;
			this.jumpto   = jumpfrom;
			this.meters   = -1;
			this.profit   = -1;
		}

		public int getJumpFrom () { return this.jumpfrom; }
		public int getJumpTo   () { return this.jumpto;   }
		public int getMeters   () { return this.meters;   }
		public int getProfit   () { return this.profit;   }

		public void setJumpTo (final int x) { this.jumpto = x; }
		public void setMeters (final int x) { this.meters = x; }
		public void setProfit (final int x) { this.profit = x; }
	}

	public int getNoPages () { return this.nopages; }
	public int getNoPage  () { return this.nopage ; }
	public int getProfit  () { return this.profit ; }
}
