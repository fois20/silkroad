/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * Este enum hace referencia a que accion es la que se va a representar
 * en la simulacion con el fin de cambiar los titulos
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public enum SimAct
{
	PLACING_STORE ("placing store on %dth page with %d TEN at the chunk %d."),
	PLACING_ROBOT ("placing robot on %dth page at the chunk %d"),
	MOVING_ROBOT  ("");

	final String title;

	SimAct (final String title)
	{
		this.title = title;
	}

	public String getTitleFmt () { return this.title; }
}
