/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * @author  hever barrera batero ; juan diego patino munoz
 * @version 1
 *
 * Esta clase implementa el concepto de un robot (store) junto con su
 * representacion visual dentro de la simulacion.
 */

public class Robot
{
	/**
	 * colores definidos para hacer distinguir los robots
	 * unos de los otros
	 */
	private static final SColor[] _colors =
	{
		SColor.r1 ,
		SColor.r2 ,
		SColor.r3 ,
		SColor.r4 ,
		SColor.r5 ,
		SColor.r6 ,
		SColor.r7 ,
		SColor.r8 ,
		SColor.r9 ,
		SColor.r10,
		SColor.r11,
		SColor.r12,
		SColor.r13,
		SColor.r14,
		SColor.r15,
		SColor.r16,
		SColor.r17
	};

	/**
	 * Arreglo que contiene las coordenadas donde los robots deberian aparecer/
	 * regenerarse cuando sea necesario dependiendo su posicion dentro de la
	 * pagina actual
	 */
	private static final int [][] _locs =
	{
		{50,  50 },
		{150, 50 },
		{250, 50 },
		{350, 50 },
		{450, 50 },
		{450, 150},
		{450, 250},
		{450, 350},
		{450, 450},
		{350, 450},
		{250, 450},
		{150, 450},
		{50,  450},
		{50,  350},
		{50,  250},
		{150, 250},
		{250, 250}
	};

	/**
	 * Tamano en pixeles del cuadrado base de la tienda.
	 */
	private static final int _sz = 25;
	
	private Circle body;
	private int    tenges;

	/**
	 * Ya que un robot puede avanzar en chunks, necesitamos
	 * saber siempre en que chunk esta para cuando lo removamos
	 * el robot poderlo hacerlo totalmente
	 */
	private int    currentlyInChunkNo;

	/**
	 * Ya que puede haber mas de un robot en el mismo chunk necesitamos
	 * saber en que posicion llego el robot al actual chunk
	 */
	private int    positionInQueue;

	/**
	 * Constructor del robot.
	 * @param nth indice que determina las coordenadas y colores
	 * @param displaynow indica si debe mostrarse al crearse
	 */
	public Robot (final int currentGlobalChunkNo, final int nth, final boolean displaynow)
	{
		this.body               = new Circle(_locs[nth][0], _locs[nth][1], _sz, _colors[nth]);
		this.tenges             = 0;
		this.currentlyInChunkNo = currentGlobalChunkNo;
		this.positionInQueue    = 0;

		this.changevisibility(displaynow);
	}

	/**
	 * Cambia la visibilidad del robot
	 * @param to true para mostrar, false para ocultar
	 */
	public void changevisibility (final boolean to)
	{
		this.body.changevisibility(to);
	}

	public void move (final boolean show, final int to)
	{
		this.body.changeposition(show, _locs[to][0], _locs[to][1]);
		this.changevisibility(show);
	}

	/**
	 * Incrementa el dinero de un robot
	 * @param by cantidad de dinero a incrementar
	 */
	public void increaseProfit (final int by)
	{
		this.tenges += by;
	}

	/**
	 * Retorna el indece global del chunk en el que esta actualemente
	 * @return indice del chunk en que se encuentra el robot
	 */
	public int getGlobalChunkNo () { return this.currentlyInChunkNo; }

	/**
	 * Retorna el indece en el que se encuentra el robot del chunk actual
	 * @return posicion dentro del chunk
	 */
	public int getPositionInQueue () { return this.positionInQueue; }

	/**
	 * Establece un nuevo valor para el chunk en el que el robot esta
	 * @param no indice global del chunk
	 */
	public void setGlobalChunkNo (final int no) { this.currentlyInChunkNo = no; }

	/**
	 * Establece la posicion en la que llego el robot al nuevo chunk
	 * @param pos posicion
	 */
	public void setPositionInQueue (final int pos) { this.positionInQueue = pos; }

	/**
	 * Retorna el numero de tenges que el robot tiene actualemente
	 * @return dinero
	 */
	public int getProfit () { return this.tenges; }
}
