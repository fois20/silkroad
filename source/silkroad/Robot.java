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

import canvas.Circle;
import canvas.SColor;

public class Robot
{
	private static final SColor[] _normal =
	{
		SColor.normalRobotC1 ,
		SColor.normalRobotC2 ,
		SColor.normalRobotC3 ,
		SColor.normalRobotC4 ,
		SColor.normalRobotC5 ,
		SColor.normalRobotC6 ,
		SColor.normalRobotC7 ,
		SColor.normalRobotC8 ,
		SColor.normalRobotC9 ,
		SColor.normalRobotC10,
		SColor.normalRobotC11,
		SColor.normalRobotC12,
		SColor.normalRobotC13,
		SColor.normalRobotC14,
		SColor.normalRobotC15,
		SColor.normalRobotC16,
		SColor.normalRobotC17
	};

	/**
	 * posiciones en las que deberia aparecer/mover un robot dentro del
	 * visual actual
	 */
	private static final int [][] _coordinates =
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

	private final int _size     = 25;
	private final int _delay_ms = 500;

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
	private Circle         body;
	private int            tenges;
	private int            currentlyInChunk;
	private int            positionInQueue;
	private List<Integer>  moneyPerMove;
	private Timer          timer;
	private ActionListener blinker;
	private boolean        imMVP;

	public Robot (final int globalId, final int localId, final boolean display)
	{
		this.body             = new Circle(_normal[localId], _coordinates[localId][0], _coordinates[localId][1], _size);
		this.tenges           = 0;
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
		this.changevisibility(display);
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

	public void move (final boolean show, final int localIdTo)
	{
		this.body.changeposition(show, _coordinates[localIdTo][0], _coordinates[localIdTo][1]);
		this.changevisibility(show);
	}

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
	public Circle getBody ()               { return this.body; }

	public void setPositionInQueue (final int pos) { this.positionInQueue = pos; }
	public void setGlobalChunkNo (final int no)    { this.currentlyInChunk = no; }
}
