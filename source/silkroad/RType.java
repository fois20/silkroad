/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * indica los posibles tipos de robots que hayan:
 * normal: normal
 * neverback: nunca se devuelve
 * tender: solo toma la mitad del dinero
 * thief: roba el dinero de los robots que queden en el mismo lugar que el
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package silkroad;

public enum RType
{
	NORMAL ("normal"),
	NVBACK ("neverback"),
	TENDER ("tender"),
	THIEF  ("thief");

	public String type;

	RType (final String type)
	{
		this.type = type;
	}

	public static RType getTypeBasedOnName (final String name) throws IllegalInstruction
	{
		switch (name.toLowerCase())
		{
			case "normal":    return NORMAL;
			case "neverback": return NVBACK;
			case "tender":    return TENDER;
			case "thief":     return THIEF;
			default:          throw new IllegalInstruction("no se conoce el tipo de robot: " + name);
		}
	}
}
