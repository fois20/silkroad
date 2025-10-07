/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * esta funcion sirve como 'helper' para otras funciones con el fin de
 * no hacer los metodos/clases demasiado pesados de ver
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

import javax.swing.JOptionPane;

public class Misc
{
	public static final String TITLE = "silkraod - ICPC's J problem simulator";

	public static int changePageDialog (final int nopages, final int curpageno)
	{
		return Integer.parseInt(JOptionPane.showInputDialog(
			null,
			String.format("page [0 - %d): ", nopages),
			String.format("%s: changing page (current: %d)", TITLE, curpageno),
			JOptionPane.INFORMATION_MESSAGE
		));
	}

	public static void invalidPageNumber (final int given)
	{
		JOptionPane.showMessageDialog(
			null,
			String.format("invalid page number (%d)", given),
			TITLE,
			JOptionPane.ERROR_MESSAGE
		);
	}

	public static void invalidLocationGivenViaBlueJDialogs (final int location, final int mx)
	{
		JOptionPane.showMessageDialog(
			null,
			String.format("invalid location provided (%d is not between [0, %d))", location, mx),
			TITLE,
			JOptionPane.ERROR_MESSAGE
		);
	}

	public static void invalidLocationToPlaceAStoreAt (final int location)
	{
		JOptionPane.showMessageDialog(
			null,
			String.format("cannot place a store there! there's already one store at location %d", location),
			TITLE,
			JOptionPane.ERROR_MESSAGE
		);
	}

	public static void invalidLocationToRemoveAStoreAt (final int location)
	{
		JOptionPane.showMessageDialog(
			null,
			String.format("cannot remove a store there! there's no store at location %d", location),
			TITLE,
			JOptionPane.ERROR_MESSAGE
		);
	}
}
