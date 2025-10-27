/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase necesaria para representar un robot, ademas de su logica
 * la representacion visual es un circulo
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package silkroad;

import java.util.List;
import java.util.ArrayList;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import canvas.*;

public abstract class Robot
{
	/**
	 * posiciones en las que deberia aparecer/mover un robot dentro del
	 * visual actual
	 */
	protected static final int [][] _coordinates =
	{
		{50,  50 },
		{150, 50 },
		{250, 50 },
		{350, 50 },
		{450, 50 },
		{450, 150},
		{450, 250},
		{450, 350},
		{450, 450},
		{350, 450},
		{250, 450},
		{150, 450},
		{50,  450},
		{50,  350},
		{50,  250},
		{150, 250},
		{250, 250}
	};

	protected final int _size     = 25;
	protected final int _delay_ms = 500;

	/**
	 * body            : instancia a la clase circulo necesaria para representarlo visualmente
	 * tenges          : tenges que ha almacenado
	 * currentlyInChunk: numero de chunk global en el que se encuentra
	 * positionInQueue : posicion en la cola del chunk actual
	 * moneyPerMove    : profit generado por movimiento
	 * timer           : timer
	 * blinker         : accion que hace la ilusion de parpadeo
	 * imMVP:          : soy MVP?
	 */
	protected ShapeCommon    body;
	protected int            tenges;
	protected int            currentlyInChunk;
	protected int            positionInQueue;
	protected List<Integer>  moneyPerMove;
	protected Timer          timer;
	protected ActionListener blinker;
	protected boolean        imMVP;
	protected RType          type;
	protected int            homeId;

	public Robot (final int globalId, final int localId, final boolean display)
	{
		this.tenges           = 0;
		this.homeId           = globalId;
		this.currentlyInChunk = globalId;
		this.positionInQueue  = 0;
		this.moneyPerMove     = new ArrayList<>();

		this.blinker = new ActionListener()
		{
			private static int toggle = 1;

			public void actionPerformed (final ActionEvent e)
			{
				if (toggle == 1) { body.changevisibility(false); }
				else { body.changevisibility(true); }

				toggle = 1 - toggle;
			}
		};

		this.timer = new Timer(_delay_ms, this.blinker);
	}

	public void changevisibility (final boolean to)
	{
		this.body.changevisibility(to);

		if (this.imMVP && to)
		{
			this.timer.start();
		}
		else if (this.imMVP && !to)
		{
			this.timer.stop();
		}
	}

	public abstract void move (final MoveRobotContext context);

	public void increaseProfit (final int by)
	{
		this.tenges += by;
	}

	public void addProducedByMovement (final int prod)
	{
		this.moneyPerMove.add(prod);
	}

	public void imTheMVP (final boolean amI)
	{
		if (amI && this.body.amIVisible()) { this.timer.start(); }
		else { this.timer.stop(); }

		this.imMVP = amI;
	}

	public int getGlobalChunkNo ()         { return this.currentlyInChunk; }
	public int getPositionInQueue ()       { return this.positionInQueue; }
	public int getProfit ()                { return this.tenges; }
	public List<Integer> getProdPerMove () { return this.moneyPerMove; }
	public ShapeCommon getBody ()          { return this.body; }
	public RType getType ()                { return this.type; }

	public void setPositionInQueue (final int pos) { this.positionInQueue = pos; }
	public void setGlobalChunkNo (final int no)    { this.currentlyInChunk = no; }

	public static Robot createRobot (final int globalId, final int localId, final boolean display, final RType type)
	{
		switch (type)
		{
			case RType.NORMAL:
			{
				return new NormalRobot(globalId, localId, display);
			}
			case RType.NVBACK:
			{
				return new NBackRobot(globalId, localId, display);
			}
			case RType.TENDER:
			{
				return new TenderRobot(globalId, localId, display);
			}
			case RType.THIEF:
			{
				return new ThiefRobot(globalId, localId, display);
			}
		}
		return null;
	}
}
