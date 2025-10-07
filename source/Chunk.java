/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * esta clase permite almacenar informacion sobre los pedazos de terreno (chunks),
 * indicando si hay tiendas, que robots spawnean aca, que robots estan aca etc...
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

import java.util.List;
import java.util.ArrayList;

public class Chunk
{
	private PageOrientation orientation;
	private Store           store;
	private Robot           robot;
	private List<Robot>     robots;
	private int             internalId;
	private int             globalId;
	private boolean         displayed;

	public Chunk (final PageOrientation orientation, final int nochunk, final boolean show)
	{
		this.orientation = orientation;
		this.store       = null;
		this.robot       = null;
		this.robots      = new ArrayList<>();
		this.internalId  = nochunk % Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;
		this.globalId    = nochunk;
		this.displayed   = show;
	}

	public void changevisibility (final boolean to)
	{
	}
}
