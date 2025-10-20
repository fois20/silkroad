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
package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.Assert.*;

import silkroad.Silkroad;
import silkroad.Road;
import silkroad.IllegalInstruction;
import silkroad.Misc;

public class SilkRoadC1Test
{
	public static final int LENGTH = Road.MAX_NO_VISIBLE_CHUNKS_PER_FRAME;

	@Before
	public void prelude ()
	{
		Misc.TESTING = true;
	}

	/**
	 * constructores -----------------------------------------------------------------------
	 * 1) El primer constructor debe crear la ruta con la longitud especificada
	 * 2) El primer constructor debe crear solo rutas con longitudes positivas
	 * 3) Cuando se usa el segundo constructor se debe tomar el valor mas grande en la posicion days[i][1] + 1 para armar la ruta
	 * 4) No se puede crear una ruta con input nulo
	 * 5) No se pueden dar valores negativos en el input
	 */
	@Test
	public void accordingPMshouldCreateRoadWithSpcifiedLength () throws IllegalInstruction
	{
		final int length = LENGTH;
		final Silkroad s = new Silkroad(length);
		assertEquals("longitudes iguales", length, s.getLength());
	}

	@Test(expected = IllegalInstruction.class)
	public void accordingPMshouldNotAllowNegagiveLengts () throws IllegalInstruction
	{
		new Silkroad(-1);
	}

	@Test
	public void accordingPMshouldCreateRoadWithLength6 () throws IllegalInstruction
	{
		final int [][] input = new int[][] {{1, 0}, {1, 1}, {1, 2}, {2, 3, 100}, {2, 4, 100}, {2, 5, 100}};
		final Silkroad s = new Silkroad(input);
		assertEquals("longitudes iguales", 6, s.getLength());
	}

	@Test(expected = IllegalInstruction.class)
	public void accordingPMshouldNotAllowNullInput () throws IllegalInstruction
	{
		new Silkroad(null);
	}

	@Test(expected = IllegalInstruction.class)
	public void accordingPMshouldNotAllowNegativeValuesInInput () throws IllegalInstruction
	{
		new Silkroad(new int [][]{
			{1, 0},
			{1, -2},
			{1, 2},
			{-9, 3, 100},
			{2, 4, 100},
			{-10, 5, -47}}
		);
	}

	/**
	 * creacion de tiendas -----------------------------------------------------------------
	 * 1) las tiendas se crean en el orden que se provee pero se devuelven en orden de la ruta
	 * 2) no se puede crear una tienda en el mismo lugar dos veces
	 * 3) no se puede crear una tienda con tenges negativos
	 * 4) no se pueden poner tiendas en posiciones negativas ni mayores a la longitud de la ruta
	 */
	@Test
	public void accordingPMshouldPlaceStoresAtPositionsGivenWithCorrectAmountOfTenges () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, 1},
			{1, 2},
			{2, 3},
			{3, 4},
			{4, 5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }

		final int [][] returns = s.consultStores();


		assertEquals("longitudes iguales", returns.length, stores.length);
		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(returns[i], stores[i]);
		}
	}

	@Test
	public void accordingPMshouldNotPlaceStoreAtSameSpotTwice () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, 1},
			{1, 2},
			{2, 3},
			{3, 4},
			{4, 5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }

		s.placeStore(0, 1);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldOnlyAccpetPostiveTenges () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, -1},
			{1, -2},
			{2, -3},
			{3, -4},
			{4, -5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldNotAllowLocationsOuttaBounds () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);
		s.placeStore(-1, 1);
		assertFalse(s.getOK());

		s.placeStore(LENGTH + 1, 1);
		assertFalse(s.getOK());
	}

	/**
	 * elminacion de tiendas ---------------------------------------------------------------
	 * 1) cuando se eliminan todas las tiendas deben quedar cero tiendas disponibles
	 * 2) no se puede elminar tiendas que no existan
	 * 3) cuando se quiera eliminar una tienda, esta tiene que estar dentro de los lims del mapa
	 */
	@Test
	public void accordingPMshouldRemoveStoresAtPositionsGiven () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, 1},
			{1, 2},
			{2, 3},
			{3, 4},
			{4, 5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }
		for (final int[] store: stores) { s.removeStore(store[0]); }

		assertEquals("deben hacer cero tiendas", 0, s.consultStores().length);
	}

	@Test
	public void accordingPMshouldNotRemoveUnexistingStores () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, 1},
			{2, 3},
			{4, 5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }

		s.removeStore(1);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshoudlOnlyRemoveWithinBounds () throws IllegalInstruction
	{
		final int [][] stores = new int[][] {
			{0, 1},
			{2, 3},
			{4, 5}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int[] store: stores) { s.placeStore(store[0], store[1]); }

		s.removeStore(-1);
		assertFalse(s.getOK());

		s.removeStore(LENGTH + 1);
		assertFalse(s.getOK());
	}

	/**
	 * creacion de robots ------------------------------------------------------------------
	 * 1) robots deben ser creados en la posicion dada con una cantidad 0 inicial de dinero 
	 * 2) robots deben estar dentro de los limites
	 * 3) robots deven tener posiciones unicas dentro del mapa al aparecer
	 * 4) si una tienda fue puesta en x, un robot no puede aparecer en x
	 */
	@Test
	public void accordingPMshouldCreateRobotsAtPositiionsGiven () throws IllegalInstruction
	{
		final int [][] robs = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0},
			{4, 0},
			{5, 0},
			{6, 0},
			{7, 0},
			{8, 0},
			{9, 0},
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int [] robot: robs) { s.placeRobot(robot[0]); }

		final int [][] returns = s.consultRobots();
		assertEquals(returns.length, robs.length);

		for (int i = 0; i < returns.length; i++)
		{
			assertArrayEquals(robs[i], returns[i]);
		}
	}

	@Test
	public void accordingPMshouldNotPlaceRobotsOuttaBounds () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);

		s.placeRobot(-1);
		assertFalse(s.getOK());

		s.placeRobot(LENGTH + 1);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldNotBePossibleToPlaceTwoRobotsAtSameSpot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);

		s.placeRobot(0);
		assertTrue(s.getOK());

		s.placeRobot(0);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldNotBePossibleToPlaceARobotWhereAStoreWassPlaced () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(LENGTH);

		s.placeStore(0, 100);
		assertTrue(s.getOK());

		s.placeRobot(0);
		assertFalse(s.getOK());
	}

	/**
	 * eliminacion de robots ---------------------------------------------------------------
	 * 1) no se pueden eliminar robots que no existan
	 * 2) se deben eliminar robots dentro del rango de la ruta
	 * 3) si se eliminan los robots, consultRobots debe dar un array vacio
	 */
	@Test
	public void accordingPMshouldNotBePossibleToRemoveUnexistingRobots () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(1);
		s.removeRobot(0);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldNotBePossibleToRemoveRobotsOuttaBounds () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {{1, 0}, {1, 1}, {1, 2}});
		s.removeRobot(-1);
		assertFalse(s.getOK());

		s.removeRobot(4);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldNotBeElementsWithinArrayReturnedByConsultRobotsAfterRemovingAllRobots () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {{1, 0}, {1, 1}, {1, 2}});
		s.removeRobot(0);
		s.removeRobot(1);
		s.removeRobot(2);
		assertEquals("long debe ser 0", 0, s.consultRobots().length);
	}

	/**
	 * moviendo el robot -------------------------------------------------------------------
	 * 1) solo se pueden mover robots que existan (posiciones con robots como spawn)
	 * 2) los movimientos solo pueden mover al robot dentro de los limites
	 * 3) los robots se deben mover siempre que sea posible
	 * 4) todos los robots pueden estar en un solo chunk (siempre y cuando no reaparezcan ahi)
	 */
	@Test
	public void accordingPMshouldOnlyBePossibleToMoverExistingRobots () throws IllegalInstruction
	{
		final int [][] robs = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0},
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int [] robot: robs) { s.placeRobot(robot[0]); }

		for (int i = 0; i < robs.length; i++)
		{
			s.moveRobot(robs[i][0], 1);
			assertTrue(s.getOK());
		}

		s.moveRobot(0, 1);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldOnlyBePossibleToMoverRobotsWithinRoadBounds () throws IllegalInstruction
	{
		final int [][] robs = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int [] robot: robs) { s.placeRobot(robot[0]); }

		for (int i = 0; i < robs.length; i++)
		{
			s.moveRobot(robs[i][0], 1);
			assertTrue(s.getOK());
		}

		s.moveRobot(1, -2);
		assertFalse(s.getOK());

		s.moveRobot(1, LENGTH + 1);
		assertFalse(s.getOK());
	}

	@Test
	public void accordingPMshouldMoveRobotsAsLongItsPossible () throws IllegalInstruction
	{
		final int [][] robs = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0}
		};

		final int [][]shouldbe = new int[][] {
			{1, 0},
			{2, 0},
			{3, 0},
			{4, 0}
		};

		final Silkroad s = new Silkroad(LENGTH);
		for (final int [] robot: robs) { s.placeRobot(robot[0]); }
		for (int i = 0; i < robs.length; i++) { s.moveRobot(robs[i][0], 1); }

		final int [][] returns = s.consultRobots();

		assertEquals("long iguales", returns.length, shouldbe.length);
		for (int i = 0; i < robs.length; i++)
		{
			assertArrayEquals(returns[i], shouldbe[i]);
		}
	}

	@Test
	public void accordingPMshouldBePossibleToHaveAllRobotsInTheSameChunk () throws IllegalInstruction
	{
		final int [][] robs = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0}
		};

		final int [] needsToMove  = new int [] {
			3,
			2,
			1,
			0
		};

		final int [][]shouldbe = new int[][] {
			{3, 0},
			{3, 0},
			{3, 0},
			{3, 0}
		};

		final Silkroad s = new Silkroad(LENGTH);

		for (final int [] robot: robs) { s.placeRobot(robot[0]); }
		for (int i = 0; i < robs.length; i++) { s.moveRobot(robs[i][0], needsToMove[i]); }

		final int [][] returns = s.consultRobots();

		assertEquals("long iguales", returns.length, shouldbe.length);
		for (int i = 0; i < robs.length; i++)
		{
			assertArrayEquals(returns[i], shouldbe[i]);
		}
	}

	/**
	 * reboot ------------------------------------------------------------------------------
	 * 1) todos los robots y tiendas deben volver a sus posiciones iniciales cuando se haga
	 * un reboot
	 */
	@Test
	public void accordingPMshouldAllRobotsComeBackOnceReboot () throws IllegalInstruction
	{
		final Silkroad s = new Silkroad(new int [][] {
			{1, 0},
			{1, 1},
			{1, 2},
			{1, 3},
			{1, 4},
			{2, 5, 100},
			{2, 6, 100},
			{2, 7, 100},
			{2, 8, 100},
			{2, 9, 100},
		});

		s.moveRobots();
		s.reboot();

		final int [][] robots = new int[][] {
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0},
			{4, 0}
		};

		final int [][] stores = new int[][] {
			{5, 100},
			{6, 100},
			{7, 100},
			{8, 100},
			{9, 100}
		};

		final int [][] r_ret = s.consultRobots();
		final int [][] s_ret = s.consultStores();

		assertEquals("long eq", r_ret.length, robots.length);
		assertEquals("long eq", s_ret.length, stores.length);

		for (int i = 0; i < r_ret.length; i++) { assertArrayEquals(r_ret[i], robots[i]); }
		for (int i = 0; i < s_ret.length; i++) { assertArrayEquals(s_ret[i], stores[i]); }
	}

	/* +------------------------------------------------------------------------------------------------------------------------------+
	 * | los metodos 'consultStores' y 'consultRobots' no son testeados directamente ya que esto ocurre de manera indirecta           |
	 * | en los otros tests, lo que quiere decir que si estos metodos fallaran todos los demas tests tambien lo harian dado           |
	 * | que dependen de los metodos consult                                                                                          |
	 * |                                                                                                                              |
	 * | los metodos 'makeVisible' y 'makeInvisible' no tiene pruebas de unidad por el simple hecho de que las pruebas deben ser      |
	 * | en modo invisible, ademas que es algo muy sencillo de validar.                                                               |
	 * |                                                                                                                              |
	 * | el metodo 'finish' no fue testado dado que literalmente acaba el programa.                                                   |
	 * |                                                                                                                              |
	 * | el metodo 'profit' no puede ser testado dada la naturaleza del metodo en si, ya que segun las indicaciones este metodo       |
	 * | no debe retornar ningun valor                                                                                                |
	 * |                                                                                                                              |
	 * | el metodo 'ok' se prueba implicitamente en todos los demas test, no hay necesidad de hacer mas pruebas ya que tambien es un  |
	 * | piral para los otros tests                                                                                                   |
	 * +------------------------------------------------------------------------------------------------------------------------------+
         *                                           /
	 *                ,.-----__                /
	 *             ,:::://///,:::-.          /
	 *            /:''/////// ``:::`;/|/   /
	 *           /'   ||||||     :://'`\
	 *         .' ,   ||||||     `/(  e \
	 *   -===~__-'\__X_`````\_____/~`-._ `.
	 *               ~~        ~~       `~-'
	 */
}