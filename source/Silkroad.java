/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * esta clase implementa los controles de la simulacion, la clase la cual provee
 * todos los metodos para realizar y hacer la simulacion avanzar
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public class Silkroad
{
	private final int length;
	private boolean ok;
	private Road road;

	public Silkroad (final int length)
	{
		this.length = length;
		this.road = new Road(this.length);
	}

	public Silkroad (final int [][]initialState)
	{
		this.length = 0;
		this.road = new Road(this.length);
	}

	/**
	 */
	public void changePage (int to)
	{
		this.road.changePageVisual(to);
	}
}
