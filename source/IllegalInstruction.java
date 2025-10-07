/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa una exception la cual es lanzada cuando se provee mal un
 * global ID o simplemente no se puede realizar la operacion
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

public class IllegalInstruction extends Exception
{
	public IllegalInstruction (final String msg)
	{
		super(msg);
	}
}
