/**
 *         __ __ __                        __
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * en este archivo se implementa la solicion del problema en si, esta solucion no hace parte
 * de la simulacion, sino que mas bien se reescribio para ilustrar lo dificil que era realmente
 * la solucion a este problema, simplemente no tiene sentido...
 *
 * solucion tomada de: https://github.com/SnapDragon64/ACMFinalsSolutions/blob/master/finals2024/thesilkroadDK.cc
 */

import java.util.Set;
import java.util.LinkedHashSet;

import java.util.List;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;

public class Java
{
	public static void main (String [] args)
	{
		final int [][] robots = new int [][] {{20, 0}, {50, 0}};
		final int [][] stores = new int [][] {{15, 15}, {40, 50}, {80, 20}, {70, 30}};

		final JProblemSolverHelper s = new JProblemSolverHelper(stores, robots);
	}
}

class Matrix implements Cloneable
{
	public static final int NxN = 5;

	private List<List<Long>> matrix;

	public Matrix (final long init)
	{
		this.matrix = new ArrayList<>();
		for (int i = 0; i < NxN; i++)
		{
			this.matrix.add(new ArrayList<>());
			for (int j = 0; j < NxN; j++)
			{
				this.matrix.get(i).add(init);
			}
		}
	}

	public List<List<Long>> getMatrix () { return this.matrix; }
	public Long getAt (final int row, final int col) { return this.matrix.get(row).get(col); }

	public void setAt (final int row, final int col, final long x) { this.matrix.get(row).set(col, x); }

	@Override
	public Matrix clone ()
	{
		try
		{
			Matrix clone = (Matrix) super.clone();
			List<List<Long>> clonedMatrix = new ArrayList<>();

			for (int i = 0; i < NxN; i++)
			{
				List<Long> row = new ArrayList<>();
				for (int j = 0; j < NxN; j++)
				{
					row.add(this.getAt(i, j));
				}
				clonedMatrix.add(row);
			}

			clone.matrix = clonedMatrix;

			return clone;
		}
		catch (final Exception e) {}

		return null;
	}

	public void print ()
	{
		for (int i = 0; i < NxN; i++)
		{
			for (int j = 0; j < NxN; j++)
			{
				System.out.printf("%-20d ", this.matrix.get(i).get(j));
			}
			System.out.println();
		}
		System.out.printf("\n\n");
	}
}

class JProblemSolverHelper
{
	private static final int L2 = 0;
	private static final int L1 = 1;
	private static final int Z0 = 2;
	private static final int R1 = 3;
	private static final int R2 = 4;

	private int nodays;
	private int [][] input;

	/**
	 * T: que hacer (poner robot o tienda)
	 * X: posiciones de los robots/tiendas
	 * C: cantidad de tenges por tienda
	 */
	private long [] T, C, X;

	/**
	 * coords:
	 * cc:
	 * cr:
	 */
	private List<Long> coords, cc, cr;

	private Matrix base;

	/**
	 * el constructor recrea el input que seria dado por un juez con base
	 * en la informacion que se ha generado hasta este punto de la simulacion
	 *
	 * @param stores informacion de las tiendas generadas hasta ahora
	 * @param stores informacion de los robots generadas hasta ahora
	 */
	public JProblemSolverHelper (final int [][] stores, final int [][] robots)
	{
		this.rebuildinput(stores, robots);
		this.initArrayNLists();

		this.base = new Matrix((long) -1e18);
		this.solve();
	}

	/**
	 * recrea el input que se da en el problema de verdad como si no fuese
	 * una simulacion, por ejemplo si el usario que usa la simulacion crea
	 * una tienda en la posicion 2 con 100 tenges y un robot en la posicion 0
	 * esta funcion convertira 'input' en
	 *
	 * 2
	 * 2 2 100
	 * 1 0
	 *
	 * @param stores informacion de las tiendas generadas hasta ahora
	 * @param stores informacion de los robots generadas hasta ahora
	 */
	private void rebuildinput (final int [][] stores, final int [][] robots)
	{
		this.nodays = stores.length + robots.length;
		this.input = new int[this.nodays][3];

		for (int i = 0; i < robots.length; i++)
		{
			this.input[i][0] = 1;
			this.input[i][1] = robots[i][0];
			this.input[i][2] = robots[i][1];
		}

		for (int i = robots.length, j = 0; i < this.nodays; i++)
		{
			this.input[i][0] = 2;
			this.input[i][1] = stores[j][0];
			this.input[i][2] = stores[j++][1];
		}

		this.T = new long[this.nodays];
		this.X = new long[this.nodays];
		this.C = new long[this.nodays];
	}

	/**
	 * llena las arreglos de T, C y X con el respectivo input anteriormente
	 * calculado en el meotodo 'rebuildinput', tambien inicializa coords, cr y cc
	 */
	private void initArrayNLists ()
	{
		List<Long> temp = new ArrayList<>();

		for (int i = 0; i < this.nodays; i++)
		{
			this.T[i] = this.input[i][0];
			this.X[i] = this.input[i][1];
			temp.add(X[i]);
			if (this.T[i] == 2) { this.C[i] = this.input[i][2]; }
		}

		/**
		 * remueve las posiciones repetidas ya que no necesitamos hacer el mismo calculo
		 * para dos robots/tiendas iguales
		 */
		Collections.sort(temp);
		Set<Long> uniq = new LinkedHashSet<>(temp);
		this.coords    = new ArrayList<Long>(uniq);

		do
		{
			this.coords.add((long) 1e9);
		} while (Integer.bitCount(coords.size() - 1) != 1);

		this.cc = new ArrayList<>();
		this.cr = new ArrayList<>();

		for (int i = 0; i < this.coords.size(); i++) { this.cc.add(0L); this.cr.add(0L); }
	}

	private Matrix createMatrix (final int xi)
	{
		final Matrix ret = this.base.clone();
		final long seg   = this.coords.get(xi + 1) - this.coords.get(xi);

		ret.setAt(Z0, L2, this.cc.get(xi) - 2 * seg);
		ret.setAt(L2, L2, ret.getAt(Z0, L2));

		ret.setAt(Z0, L1, this.cc.get(xi) - seg);
		ret.setAt(L1, L1, ret.getAt(Z0, L1));

		ret.setAt(Z0, Z0, ((this.cr.get(xi) != 0) ? this.cc.get(xi) : 0));

		ret.setAt(R2, Z0, this.cc.get(xi));
		ret.setAt(R1, Z0, ret.getAt(R2, Z0));

		ret.setAt(R1, R1, this.cc.get(xi) - seg);
		ret.setAt(R2, R2, this.cc.get(xi) - 2 * seg);

		if (this.cr.get(xi) != 0)
		{
			ret.setAt(L1, R2, this.cc.get(xi) - 2 * seg);

			ret.setAt(Z0, R1, this.cc.get(xi) - seg);
			ret.setAt(L2, R1, ret.getAt(Z0, R1));

			ret.setAt(L1, Z0, this.cc.get(xi));
		}

		return ret;
	}

	private Matrix multiplication (final Matrix a, final Matrix b)
	{
		final Matrix ret = this.base.clone();
		for (int s = 0; s < Matrix.NxN; s++)
		{
			for (int m = 0; m < Matrix.NxN; m++)
			{
				for (int e = 0; e < Matrix.NxN; e++)
				{
					final long op1 = ret.getAt(s, e), op2 = a.getAt(s, m) + b.getAt(m, e);
					ret.setAt(s, e, Math.max(op1, op2));
				}
			}
		}
		return ret;
	}
	
	private void solve ()
	{
		final List<List<Matrix>> segTree = new ArrayList<>();
		segTree.add(new ArrayList<Matrix>());

		for (int xi = 0; xi + 1 < this.coords.size(); xi++)
		{
			final Matrix matx = this.createMatrix(xi);
			segTree.get(0).add(matx);
		}

		while (segTree.getLast().size() != 1)
		{
			final List<Matrix> cur = new ArrayList<>();	
			for (int i = 0; i < segTree.getLast().size(); i++) cur.add(segTree.getLast().get(i));
			
			final List<Matrix> nxt = new ArrayList<>();

			for (int i = 0; i + 1 < cur.size(); i += 2)
			{
				final Matrix mult = this.multiplication(
					cur.get(i),
					cur.get(i + 1)
				);
				nxt.add(mult);
			}
			segTree.add(nxt);
		}
		
		for (int i = 0; i < this.nodays; i++)
		{
			int xi = this.lowerbound(this.X[i]);

			if (this.T[i] == 1) { this.cr.set(xi, this.cr.get(xi) + 1); }
			else { this.cc.set(xi, this.cc.get(xi) + this.C[i]); }

			segTree.get(0).set(xi, this.createMatrix(xi));
			for (int j = 0; j + 1 < segTree.size(); j++, xi /= 2)
			{
				segTree.get(j + 1).set(
					xi / 2,
					this.multiplication(
						segTree.get(j).get(xi & ~1),
						segTree.get(j).get(xi | 1)
				));
			}
			System.out.println(segTree.getLast().get(0).getAt(Z0, Z0));
		}
	}

	private int lowerbound (final long key)
	{
		int lo = 0, hi = this.coords.size();
		while (lo < hi)
		{
			final int mid = lo + ((hi - lo) >>> 1);
			if (this.coords.get(mid) < key) { lo = mid + 1; }
			else { hi = mid; }
		}
		return lo;
	}
}

