import java.util.List;
import java.util.ArrayList;

class A
{
	public int a;
	public A (int a)
	{
		this.a = a;
	}

}

public class Tests
{
	public static void test ()
	{
		List<A> lista = new ArrayList<>();
		A abc = new A(4);

		lista.add(abc);

		A x = lista.remove(0);
		x.a = 1;

		System.out.println(abc.a);
	}
}
