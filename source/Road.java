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
	 */
	private final int length;
	private final int nopages;
	private int       nopage;
	private int       chunkFirst_i;
	private int       chunkLast_i;
	private int       nostores;
	private int       maxprofit;

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
			System.out.println("hii");
		}

		this.nopages      = (int) Math.ceil((double) this.length / MAX_NO_VISIBLE_CHUNKS_PER_FRAME);
		this.chunkFirst_i = 0;
		this.chunkLast_i  = minshow - 1;

		this.nostores     = 0;
		this.maxprofit    = 0;
	}

	/**
	 * cambia los limites de la pagina actual con el fin de actualizarlos a la nueva
	 * vista que el usuario desea tener del terreno
	 *
	 * @param no numero de pagina (0-basado)
	 */
	public void changePageVisual (final int no)
	{
		if (no == this.nopage)
		{
    			return;
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
	 * @return true si fue una operacion exitosa, false si no
	 */
	public boolean placeStore (final int location, final int tenges)
	{
		if (this.fullroad[location].getStore() != null)
		{
			return false;
		}

		this.nostores++;
		this.maxprofit += tenges;

		this.fullroad[location].inagurateStore(tenges);
		return true;
	}

	/**
	 * remueve una tienda dada su ubicacion global siempre y cuando haya una tienda
	 * en la posicion indicada
	 *
	 * @param location gloabl id chunk en el cual borrar la tienda
	 * @return true si fue una operacion exitosa, false si no
	 */
	public boolean removeStore (final int location)
	{
		if (this.fullroad[location].getStore() == null)
		{
			return false;
		}

		this.nostores--;
		this.fullroad[location].closeStore();
		return true;
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

	public int getNoPages () { return this.nopages; }
	public int getNoPage  () { return this.nopage;  }
}
