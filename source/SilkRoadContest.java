/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * Esta clase implementa la solucion de todo el problema de la mataron,
 * tiene dos modos de solucion lenta y rapida
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public class SilkRoadContest
{
	/**
	 * este metodo resuelve la mataraton del problema, es decir que para cualquier input
	 * valido para el problema, retornara el array que debeeria ser dado como output
	 *
	 * @param days arreglo que contiene lo que ocurre por dia {{dia1}, {dia2}, {dia3}}
	 * @return arreglo con la mayor cantidad de dinero que se puede hacer por dia
	 */
	public int[] solve (final int [][] days)
	{
		/**
		 * no estamos testeando pero no necesitamos la representacion visual
		 * de la simulacion plus lo hace mas rapido
		 */
		Misc.TESTING = true;

		final Silkroad s = this.createSilkRoad(days, "solver");
		int [] solution  = new int[days.length];

		for (int i = 0; i < days.length; i++)
		{
			final int action = days[i][0];

			if (action == 1) { s.placeRobot(days[i][1]); }
			else { s.placeStore(days[i][1], days[i][2]); }

			s.moveRobots();
			solution[i] = s.getTngsMax();
		}

		return solution;
	}

	/**
	 * este metodo hace visible toda la solucion que se generaria si se ejecutase el metodo
	 * 'solve', basicamente es lo que hara la simulacion interactiva y automatica
	 *
	 * @param slow aumenta la rapidez por 1.5 (tiempo normal 1000 ms)
	 */
	public void simulate (final int [][] days, final boolean slow)
	{
		final Silkroad s = this.createSilkRoad(days, "simulator");
		s.setSimulation(true, slow);

		for (int i = 0; i < days.length; i++)
		{
			final int action = days[i][0];
			if (action == 1) { s.placeRobot(days[i][1]); }
			else { s.placeStore(days[i][1], days[i][2]); }
		}
	}

	private Silkroad createSilkRoad (final int [][] days, final String caller)
	{
		try
		{
			int length = Silkroad.getRoadsLengthForAnyInputGiven(days);
			return new Silkroad(length);
		}
		catch (final IllegalInstruction e)
		{
			System.out.printf("soad:contest%s: cannot continuie: %s", caller, e.getMessage());
			System.exit(-1);
		}
		return null;
	}
}
