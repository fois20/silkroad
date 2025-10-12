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
	private int     length;
	private boolean ok;
	private Road    road;

	public Silkroad (final int length)
	{
		this.length = length;
		this.road   = new Road(this.length);
		this.ok     = true;
	}

	public Silkroad (final int [][]days)
	{
		this.length = 0;
		for (int i = 0; i < days.length; i++)
		{
			this.length = Math.max(this.length, days[i][1]);
		}

		this.road = new Road(++this.length);
		this.ok   = true;

		for (int i = 0; i < days.length; i++)
		{
			try
			{
				if (days[i][0] == 1) { this.road.placeRobot(days[i][1]); }
				else { this.road.placeStore(days[i][1], days[i][2]); }
			}
			catch (IllegalInstruction e)
			{
				Misc.showErrorMessage(e.getMessage());
				this.ok = false;
			}
		}
	}

	public void changePage ()
	{
		try
		{
			final int no = Misc.changePageDialog(this.road.getNoPages(), this.road.getNoPage());
			this.road.changePageVisual(no);
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void placeStore (final int location, final int tenges)
	{
		try
		{
			this.road.placeStore(location, tenges);
			this.ok = true;
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void removeStore (final int location)
	{
		try
		{
			this.road.removeStore(location);
			this.ok = true;
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void placeRobot (final int location)
	{
		try
		{
			this.road.placeRobot(location);
			this.ok = true;
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void removeRobot (final int location)
	{
		try
		{
			this.road.removeRobot(location);
			this.ok = true;
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void moveRobot (final int location, final int meters)
	{
		try
		{
			this.road.moveRobot(location, meters);
			this.ok = true;
		}
		catch (IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public int[][] consultStores () { return this.road.consultStores();                                                                       }
	public int[][] consultRobots () { return this.road.consultRobots();                                                                       }

	public void makeVisible      () { SilkRoadCanvas.getSilkRoadCanvas().setVisible(true);                                                    }
	public void makeInvisible    () { SilkRoadCanvas.getSilkRoadCanvas().setVisible(false);                                                   }

	public void finish           () { System.exit(0);                                                                                         }
	public void reboot           () { this.road.reboot();                                                                                     }

	public void ok               () { Misc.showInformationMessage(String.format("%s", this.ok ? "EXITOSA" : "FALLO"));                        }
	public void profit           () { Misc.showInformationMessage(String.format("ganacias hasta ahora: %d TENGE(S)", this.road.getProfit())); }

	public void moveRobots       () { this.road.moveRobots();                                                                                 }

}
