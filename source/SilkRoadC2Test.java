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
import static org.junit.Assert.*;

public class SilkRoadC2Test
{
	public static final int LENGTH = Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;

	@Before
	public void prelude ()
	{
		Misc.TESTING = true;
	}

	/**
	 * moveRobots --------------------------------------------------------------------------
	 * 1) si solo hay un robot y una tienda, el robot deberia ir a esa tienda
	 * 2) robot debera ir a la tienda mas cercana si ambas tiendas tiene misma cantidad de tenges
	 * 3( los robots deben ir a las tiendas mas cercanas (0, 1, 2) va a (3, 4, 5)
	 * 4) debe ir a la tienda mas lejana, siempre y cuando produzca mayor profit
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
			{1,  5}, {2,  0, 100}, {3, 16, 1000}
		});
		s.moveRobots();
	}
}
