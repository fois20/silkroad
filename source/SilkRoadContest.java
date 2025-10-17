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

		int length = 0;
		Silkroad silkr = null;

		try
		{
			length = Silkroad.getRoadsLengthForAnyInputGiven(days);
			/**
			 * no podemos usar el constructor segundo de la clase ya que este anade
			 * todo lo que se le de de una vez, nostros por otra parte queremos
			 * ir diciendo que hacer por dia
			 */
			silkr = new Silkroad(length);
		}
		catch (final IllegalInstruction e)
		{	
			System.out.println("silkroad:solution: cannot continuie: " + e.getMessage());
			System.exit(-1);
		}

		int []solution = new int[days.length];
		for (int i = 0; i < days.length; i++)
		{
			final int action = days[i][0];

			if (action == 1) { silkr.placeRobot(days[i][1]); }
			else { silkr.placeStore(days[i][1], days[i][2]); }

			silkr.moveRobots();
			solution[i] = silkr.getTngsMax();
		}

		return solution;
	}
}
