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
	NORMAL     ("normal"),
	AUTONOMOUS ("autonomous"),
	FIGHTER    ("fighter");

	public String type;

	SType (final String type)
	{
		this.type = type;
	}

	public static SType getTypeBasedOnName (final String name) throws IllegalInstruction
	{
		switch (name.toLowerCase())
		{
			case "normal":     return NORMAL;
			case "autonomous": return AUTONOMOUS;
			case "fighter":    return FIGHTER;
			default:           throw new IllegalInstruction("no se conoce el tipo de tienda: " + name);
		}
	}
}

