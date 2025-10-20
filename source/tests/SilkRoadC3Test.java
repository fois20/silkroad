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
import silkroad.SilkRoadContest;
import silkroad.Road;
import silkroad.IllegalInstruction;
import silkroad.Misc;

public class SilkRoadC3Test
{
	@Test
	public void accordingPMshouldPass01 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 0},
			{2, 3, 80},
			{1, 5},
			{1, 2},
			{1, 8},
			{1, 4},
			{2, 10, 100}
		});

		final int [] shouldbe  = {0, 77, 78, 79, 79, 79, 177};
		assertArrayEquals(shouldbe, ans);
	}

	@Test
	public void accordingPMshouldPass02 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 1},
			{2, 5, 60},
			{1, 3},
			{2, 7, 120}
		});
		final int [] shouldbe = {0, 56, 58, 172};
		assertArrayEquals(shouldbe, ans);
	}

	@Test
	public void accordingPMshouldPass03 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 0},
			{1, 2},
			{2, 4, 90},
			{2, 8, 150},
			{1, 5}
		});
		final int [] shouldbe = {0, 0, 88, 230, 235};
		assertArrayEquals(shouldbe, ans);
	}

	@Test
	public void accordingPMshouldPass04 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{2, 2, 50},
			{1, 1},
			{1, 4},
			{2, 6, 70}
		});
		final int [] shouldbe = {0, 49, 49, 117};
		assertArrayEquals(shouldbe, ans);
	}

	@Test
	public void accordingPMshouldPass05 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 0},
			{2, 5, 100},
			{2, 8, 120},
			{1, 6},
			{1, 10}
		});
		final int [] shouldbe = {0, 95, 112, 213, 217};
		assertArrayEquals(shouldbe, ans);
	}


	@Test
	public void accordingPMshouldPass06 ()
	{
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 2},
			{1, 4},
			{2, 3, 40},
			{2, 6, 70},
			{2, 9, 90}
		});
		final int [] shouldbe = {0, 0, 39, 107, 151};
		assertArrayEquals(shouldbe, ans);
	}

	@Test
	public void accordingPMshouldPass07 () {
		final int [] ans = new SilkRoadContest().solve(new int [][]{
			{1, 5},
			{2, 2, 60},
			{2, 8, 100},
			{1, 0},
			{2, 10, 150}
		});
		final int [] shouldbe = {0, 57, 97, 155, 237};
		assertArrayEquals(shouldbe, ans);
	}

	   @Test
	   public void accordingPMshouldPass08 ()
	   {
		   final int [] ans = new SilkRoadContest().solve(new int [][]{
			   {2, 3, 80},
			   {1, 0},
			   {2, 7, 120},
			   {1, 5},
			   {1, 9}
		   });
		   final int [] shouldbe = {0, 77, 113, 195, 196};
		   assertArrayEquals(shouldbe, ans);
	   }

	   @Test
	   public void accordingPMshouldPass09 ()
	   {
		   final int [] ans = new SilkRoadContest().solve(new int [][]{
			   {1, 2},
			   {2, 5, 60},
			   {2, 10, 150},
			   {1, 8},
			   {1, 0}
		   });
		   final int [] shouldbe = {0, 57, 142, 205, 205};
		   assertArrayEquals(shouldbe, ans);
	   }

	   @Test
	   public void accordingPMshouldPass10() {
		   final int [] ans = new SilkRoadContest().solve(new int [][]{
			   {1, 1},
			   {1, 4},
			   {2, 3, 70},
			   {2, 8, 90},
			   {1, 10},
			   {2, 12, 110}
		   });
		   final int [] shouldbe = {0, 0, 69, 154, 157, 262};
		   assertArrayEquals(shouldbe, ans);
	   }
}