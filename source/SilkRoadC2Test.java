/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * Esta clase presenta pruebas de unidad siguiendo los estandares de should
 * and shouldNot, cada metodo principal de la simulacion tiene un should y un
 * should not
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

public class SilkRoadC2Test
{
	public static final int LENGTH = Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;

	@Before
	public void prelude ()
	{
		Misc.TESTING = true;
	}

	@BeforeEach
	public void preludeach ()
	{
		try { Thread.sleep(1000); }
		catch (final Exception e) {}
	}

	/**
	 * moveRobots --------------------------------------------------------------------------
	 * 1) si solo hay un robot y una tienda, el robot deberia ir a esa tienda
	 * 2) robot debera ir a la tienda mas cercana si ambas tiendas tiene misma cantidad de tenges
	 * 3) los robots deben ir a las tiendas mas cercanas (0, 1, 2) va a (3, 4, 5)
	 * 4) debe ir a la tienda mas lejana, siempre y cuando produzca mayor profit
	 * 5) el robot no se deberia mover si la tienda produce profit negativo
	 * 6) el robot no se deberia mover si no hay tiendas a las cuales
	 */
	@Test
	public void accordingPMshouldGoToTheOnlyExistingStore () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int[][] {
			{1, 0},
			{2, 1, 100}
		});

		final int [][] hopesfor = new int [][] {{1, 99}};
		s.moveRobots();

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	@Test
	public void accordingPMshouldGoToTheClosestStoreIfBorhStoresHaveSameAmountOfTenges () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int[][] {
			{2, 0, 100},
			{1, 1},
			{2, 5, 100}
		});

		final int [][] hopesfor = new int [][] {{0, 99}};
		s.moveRobots();

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	@Test
	public void accordingPMshouldR1GoesS1R2GoesS2RnGoesSn () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int[][] {
			{1, 0},
			{1, 1},
			{1, 2},
			{2, 3, 100},
			{2, 4, 100},
			{2, 5, 100}
		});
		s.moveRobots();

		final int [][] hopesfor = new int[][] {
			{3, 97},
			{4, 97},
			{5, 97}
		};

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	@Test
	public void accordingPMshouldGoToFarestStoreAsLongAsItProducesMoreProfit () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1,  5}, {2, 0, 100}, {3, 16, 1000}
		});
		s.moveRobots();

		final int [][] hopesfor = new int[][] {
			{16, 1000 - Math.abs(5 - 16)}
		};

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	@Test
	public void accordingPMshouldSkipStoreIfProfitIsNegative () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int[][] {
			{1, 0},
			{2, 10, 5}
		});

		s.moveRobots();

		final int [][] hopesfor = new int [][] {
			{0, 0}
		};

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	@Test
	public void accordingPMshouldNotMoveIfThereAreNoStores () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int[][] {
			{1, 5}
		});

		s.moveRobots();

		final int [][] hopesfor = new int [][] {
			{5, 0}
		};

		final int [][] returns = s.consultRobots();
		assertEquals("eq len", returns.length, hopesfor.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], hopesfor[i]);
		}
	}

	/**
	 * emptiedStores -----------------------------------------------------------------------
	 * 1) ninguna tienda esta vacia al inicio de un dia
	 * 2) despues que una tienda sea desocupada por un robot, el retorno debera ser {pos, 1}
	 * 3) test (2) pero aplicado a mas tiendas con mas robots
	 * 4) si no hay robots ninguna tienda deberia ser desocupada
	 * 5) el numero de veces que ha sido vaciada no se reinicia a cero, sino que se mantiene
	 * 6) mismo que (5) pero con otra tienda
	 */
	@Test
	public void accordingPMnoStoreShouldBeEmptyAtBeginning () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{2, 0, 100},
			{2, 1, 100},
			{2, 2, 100},
			{2, 3, 100},
			{2, 4, 100}
		});

		final int [][] returns = s.emptiedStores();
		final int [][] expected = new int [][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0},
			{4, 0}
		};

		assertEquals("long eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	
	@Test
	public void accordingPMoneStoreShouldBeEmptyAfterSingleRobotMove () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{2, 2, 50}
		});
		s.moveRobots();

		final int [][] returns = s.emptiedStores();
		final int [][] expected = new int [][] {
			{2, 1}
		};

		assertEquals("length eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	@Test
	public void accordingPMcorrespondingStoresShouldBeEmptiedForEachRobot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{1, 5},
			{1, 10},
			{2, 2, 100},
			{2, 6, 100},
			{2, 12, 100}
		});
		s.moveRobots();

		final int [][] returns = s.emptiedStores();
		final int [][] expected = new int [][] {
			{2, 1},
			{6, 1},
			{12, 1}
		};

		assertEquals("length eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	@Test
	public void accordingPMnoRobotShouldLeaveStoresFullIfNoRobotsExist () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{2, 0, 50},
			{2, 5, 80},
			{2, 10, 100}
		});
		s.moveRobots();

		final int [][] returns = s.emptiedStores();
		final int [][] expected = new int [][] {
			{0, 0},
			{5, 0},
			{10, 0}
		};

		assertEquals("length eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	@Test
	public void accordingPMstoreShouldKeepAccumulativeCountAfterReboot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{2, 2, 100}
		});

		s.moveRobots();
		int [][] afterDay1 = s.emptiedStores();
		assertArrayEquals(new int [] {2, 1}, afterDay1[0]); // vaciado una vez

		s.reboot();
		s.moveRobots();

		int [][] afterDay2 = s.emptiedStores();
		assertArrayEquals(new int [] {2, 2}, afterDay2[0]); // debería acumular (ahora 2)
	}

	@Test
	public void accordingPMmultipleStoresShouldAccumulateCountsOverDays () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{1, 5},
			{2, 2, 50},
			{2, 7, 50}
		});

		s.moveRobots();
		int [][] afterDay1 = s.emptiedStores();
		assertArrayEquals(new int [] {2, 1}, afterDay1[0]);
		assertArrayEquals(new int [] {7, 1}, afterDay1[1]);

		s.reboot();
		s.moveRobots();

		int [][] afterDay2 = s.emptiedStores();
		int [][] expected = new int [][] {
			{2, 2},
			{7, 2}
		};

		assertEquals("length eq", expected.length, afterDay2.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], afterDay2[i]);
		}
	}

	/**
	 * profitPerMove -----------------------------------------------------------------------
	 * 1) si ningun robot se mueve, no se deberia generar ningun profit
	 * 2) cuando un robot se mueve sin ir a una tienda tiene movimientos negativos
	 * 3) cuando un robot se mueve a una tienda se debe reflejar sus ganacias
	 * 4) registrar logs correctamente
	 * 5) logs independientes
	 */
	@Test
	public void accordingPMSholdNotBeAnyProfitIfThereAreNoMovements () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{1, 1}
		});

		final int [][] returns = s.profitPerMove();
		final int [][] expects = new int [][] {
			{0},
			{1}
		};

		assertEquals("long eq", returns.length, expects.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], expects[i]);
		}
	}

	@Test
	public void accordingPMshouldNegativeProfitsCountToo () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{2, 10, 100}
		});
		s.moveRobot(0, 1);
		assertArrayEquals(new int [][] {{1, -1}}, s.profitPerMove());
	}

	@Test
	public void accordingPMshouldHavePositiveProfitWhenRobotCollectsTenges () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{2, 1, 50}
		});

		s.moveRobot(0, 1);

		final int [][] returns = s.profitPerMove();
		final int [][] expected = new int [][] {
			{1, 49}
		};

		assertEquals("len eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	@Test
	public void accordingPMshouldTrackAllProfitsPerMove () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{2, 2, 100}
		});

		s.moveRobot(0, 1);
		s.moveRobot(1, 1);

		final int [][] returns = s.profitPerMove();
		final int [][] expected = new int [][] {
			{2, -1, 99}
		};

		assertEquals("eq len", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}

	@Test
	public void accordingPMshouldKeepIndependentProfitsForEachRobot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{1, 5},
			{2, 1, 50},
			{2, 6, 70}
		});

		s.moveRobot(0, 1);
		s.moveRobot(5, 1);

		final int [][] returns = s.profitPerMove();
		final int [][] expected = new int [][] {
			{1, 49},
			{6, 69}
		};

		System.out.printf("%d %d", expected.length, returns.length);

		assertEquals("len eq", expected.length, returns.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], returns[i]);
		}
	}
}
