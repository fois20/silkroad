package silkroad;

import java.util.List;
import java.util.ArrayList;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import canvas.*;

public class TenderRobot extends Robot
{
	private static final SColor[] _colors =
	{
		SColor.RC1 ,
		SColor.RC2 ,
		SColor.RC3 ,
		SColor.RC4 ,
		SColor.RC5 ,
		SColor.RC6 ,
		SColor.RC7 ,
		SColor.RC8 ,
		SColor.RC9 ,
		SColor.RC10,
		SColor.RC11,
		SColor.RC12,
		SColor.RC13,
		SColor.RC14,
		SColor.RC15,
		SColor.RC16,
		SColor.RC17
	};

	public TenderRobot (final int globalId, final int localId, final boolean display)
	{
		super(globalId, localId, display);
		this.body = new Triangle(_colors[localId], _coordinates[localId][0], _coordinates[localId][1], _size, _size);
		this.type = RType.TENDER;
		this.changevisibility(display);
	}

	@Override
	public void move (final MoveRobotContext cntx)
	{
		this.setGlobalChunkNo(cntx.globalIdTo);
		this.body.changeposition(cntx.show, _coordinates[cntx.localIdTo][0], _coordinates[cntx.localIdTo][1]);
		this.changevisibility(cntx.show);
	}
}
