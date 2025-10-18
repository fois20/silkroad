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
	private boolean simulating;
	private int     waitime_ms;

	public Silkroad (final int length) throws IllegalInstruction
	{
		if (length <= 0)
		{
			throw new IllegalInstruction("cannot accept negative values or zeroes for the road");
		}

		this.length     = length;
		this.road       = new Road(this.length);
		this.ok         = true;
		this.simulating = false;
		this.waitime_ms = 1000;
	}

	public Silkroad (final int [][]days) throws IllegalInstruction
	{
		this.length = getRoadsLengthForAnyInputGiven(days);

		if (this.length <= 0)
		{
			throw new IllegalInstruction("cannot accept negative values or zeroes for the road");
		}

		this.road       = new Road(this.length);
		this.ok         = true;
		this.simulating = false;
		this.waitime_ms = 1000;

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
		catch (final IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	private void changePage (final int location, final SimAct act, final int tenges)
	{
		try
		{
			final int no = location / Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;
			this.road.changePageVisual(no);

			String title = Misc.TITLE;
			switch (act)
			{
				case SimAct.PLACING_STORE:
				{
					title = String.format(act.getTitleFmt(), no, tenges, location);
					break;
				}
				case SimAct.PLACING_ROBOT:
				{
					title = String.format(act.getTitleFmt(), no, location);
					break;
				}
				case SimAct.MOVING_ROBOT:
				{
					title = String.format(act.getTitleFmt());
					break;
				}
			}

			SilkRoadCanvas.setCanvasTitle(title);
			this.sleepmybby(this.waitime_ms);
		}
		catch (final IllegalInstruction e)
		{
			Misc.showErrorMessage(e.getMessage());
			this.ok = false;
		}
	}

	public void placeStore (final int location, final int tenges)
	{
		if (this.simulating) { changePage(location, SimAct.PLACING_STORE, tenges); }

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
		this.simulatingPrelude();
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
		if (this.simulating) { changePage(location, SimAct.PLACING_ROBOT, 0); }

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
		this.simulatingPrelude();
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
		if (this.simulating) { changePage(location, SimAct.MOVING_ROBOT, tenges); }

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
		this.simulatingPrelude();
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
	public int[][] emptiedStores () { return this.road.emptiedStores();                                                                       }

	public int[][] profitPerMove () { return this.road.profitPerMove();                                                                       }
	public int getLength         () { return this.length;                                                                                     }

	public boolean getOK         () { return this.ok;                                                                                         }
	public int getTngsMax        () { return this.road.getLastTengesMax();                                                                    }

	public void setSimulation (final boolean as, final boolean slow)
	{
		this.simulating = true;
		if (slow) { this.waitime_ms = 1500; }
	}

	/**
	 * retorna la longitud necesaria para hacer una ruta dado un input del problema, tambien
	 * se asegura de que no hayan numero negativos
	 *
	 * @param input input del problema
	 * @return longitud necesaria
	 */
	public static int getRoadsLengthForAnyInputGiven (final int [][] input) throws IllegalInstruction
	{
		if (input == null)
		{
			throw new IllegalInstruction("cannot process a non-existing input (it's null)");
		}

		int length = 0;
		for (int i = 0; i < input.length; i++)
		{
			length = Math.max(length, input[i][1]);

			if ((input[i][0] < 0) || (input[i][1] < 0) || ((input[i].length == 3) && (input[i][2] < 0)))
			{
				throw new IllegalInstruction("cannot give negative numbers on input");
			}
		}
		return length + 1;
	}

	private static void sleepmybby (final int ms)
	{
		try { Thread.sleep(ms); }
		catch (final Exception e) {}
	}

	private void simulatingPrelude ()
	{
		if (this.simulating)
		{
			SilkRoadCanvas.setCanvasTitle(Misc.TITLE);
			this.sleepmybby(this.waitime_ms);
		}
	}
}
