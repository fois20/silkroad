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

	public static void showErrorMessage (final String msg)
	{
		JOptionPane.showMessageDialog(
			null,
			msg,
			TITLE,
			JOptionPane.ERROR_MESSAGE
		);
	}

	public static void showInformationMessage (final String msg)
	{
		JOptionPane.showMessageDialog(
			null,
			msg,
			TITLE,
			JOptionPane.INFORMATION_MESSAGE
		);
	}
}
