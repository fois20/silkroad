/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * indica los posibles tipos de tienda que hayan:
 * normal: normal
 * autonomous: la tienda escoge un lugar cualquiera
 * fighter: solo robots con mas dinero pueden tomar el dinero de la tienda
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package silkroad;

public enum SType
{
	NORMAL ("normal"),
	AUTONOMOUS ("autonomous"),
	FIGHTER ("fighter");

	private String type;

	SType (final String type)
	{
		this.type = type;
	}
}

