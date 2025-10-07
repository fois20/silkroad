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
	private boolean   ok;
	private Road      road;

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

	public void changePage ()
	{
		final int no = Misc.changePageDialog(this.road.getNoPages(), this.road.getNoPage());
		if ((no < 0) || (no >= this.road.getNoPages()))
		{
			Misc.invalidPageNumber(no);
			return;
		}
		this.road.changePageVisual(no);
	}

	public void placeStore (final int location, final int tenges)
	{
		if ((location < 0) || (location >= this.length))
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location, this.length);
			return;
		}

		this.ok = this.road.placeStore(location, tenges);
		if (!this.ok)
		{
			Misc.invalidLocationToPlaceAStoreAt(location);
		}
	}

	public void removeStore (final int location)
	{
		if ((location < 0) || (location >= this.length))
		{
			Misc.invalidLocationGivenViaBlueJDialogs(location, this.length);
			return;
		}
		this.ok = this.road.removeStore(location);
		if (!this.ok)
		{
			Misc.invalidLocationToRemoveAStoreAt(location);
		}
	}
}
