package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.Assert.*;

import silkroad.*;

public class SilkRoadC4Test
{
	public static final int LENGTH = Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;

	@Before
	public void prelude ()
	{
		Misc.TESTING = true;
	}

	/**
	 * tiendas autonomas -------------------------------------------------------------------
	 * 1) las tiendas autonomas no se hacen donde se indica, sino que escogen el primer chunk
	 * no ocupado por una tienda
	 * 2) las tiendas se comportan como tiendas normales con respecto a lo demas
	 */
	@Test
	public void accordingPMAutoStoresPickTheirOwnSpot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeStore("autonomous", 10, 100);
		s.placeStore("autonomous", 7, 100);
		s.placeStore("autonomous", 8, 100);
		s.placeStore("autonomous", 16, 100);

		final int [][] returns = s.consultStores();
		final int [][] expects = {
			{0, 100},
			{1, 100},
			{2, 100},
			{3, 100},
		};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	@Test
	public void accordingPMAutoStoreActNormalWithAnythingElse () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeStore("autonomous", 10, 100);
		s.placeRobot("normal", 1);
		s.moveRobots();

		final int [][] emptied = s.emptiedStores();
		assertEquals("solo una vez", emptied[0][1], 1);
	}

	/**
	 * tiendas fighter ---------------------------------------------------------------------
	 * 1) las luchadoras no se dejaran de robots con menos plata que ellas
	 * 2) caso inverso al caso (1)
	 */
	@Test
	public void accordingPMFighterStoresDoesNotAllowRobotsWithInferiorAmountOfMoneyTakeItsCash () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("normal", 0);
		s.placeStore("fighter", 1, 100);
		s.moveRobots();

		final int [][] returns = s.consultRobots();
		final int [][] expects = {{1, -1}};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	@Test
	public void accordingPMFighterStoresCanBeEmptiedByRobotsWithMoreMoneyThanThem () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("normal", 0);
		s.placeStore("normal",  1, 101);
		s.placeStore("fighter", 2, 51);
		s.moveRobot(0, 1);
		s.moveRobot(1, 1);

		final int [][] returns = s.consultRobots();
		final int [][] expects = {{2, 150}};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	/**
	 * robot never back --------------------------------------------------------------------
	 * 1) los robots nunca volveran a su lugar de origen
	 * 2) los robots no pueden spawnear en el home de un neverback
	 */
	@Test
	public void accordingPMNVBcannotGoBackXD () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("neverback", 0);
		s.moveRobot(0, 10);
		s.reboot();

		final int [][] returns = s.consultRobots();
		final int [][] expects = {{10, 0}};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	@Test
	public void accordingPMNoRobotShouldTakeNVBsHome () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("neverback", 0);
		s.moveRobot(0, 10);
		s.reboot();
		s.placeRobot("normal", 0);
		assertFalse(s.getOK());
	}

	/**
	 * robot tender ------------------------------------------------------------------------
	 * 1) los tender solo toman la mitad de dinero de las tiendas
	 */
	@Test
	public void accordingPMTendersOnlyTakeHalfStore () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);		
		s.placeRobot("tender", 0);
		s.placeStore("normal", 1, 101);
		s.moveRobots();

		final int [][] returns = s.consultRobots();
		final int [][] expects = {{1, 50}};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	/**
	 * robot thieves -----------------------------------------------------------------------
	 * 1) los ladrones deben robar todo el dinero de los demas robos con los que compartan chunk
	 * 2) thief puede robar otro thief
	 */
	@Test
	public void accordingPMThievesStealOthers () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("normal", 0);
		s.placeRobot("normal", 1);
		s.placeStore("normal", 2, 101);
		s.placeStore("normal", 3, 101);
		s.moveRobots();

		s.moveRobot(2, 2);
		s.moveRobot(3, 1);

		s.placeRobot("thief", 5);
		s.moveRobot(5, -1);

		final int [][] returns = s.consultRobots();
		final int [][] expects = {
			{4, 0  },
			{4, 0  },
			{4, 194},
		};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	@Test
	public void accordingPMThivesRobEachOther () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeRobot("thief", 0);
		s.placeStore("normal", 1, 101);
		s.moveRobots();

		s.moveRobot(1, 1);

		s.placeRobot("thief", 3);
		s.moveRobot(3, -1);

		final int [][] returns = s.consultRobots();
		final int [][] expects = {
			{2, 0 },
			{2, 98}
		};

		assertEquals("eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}
}
