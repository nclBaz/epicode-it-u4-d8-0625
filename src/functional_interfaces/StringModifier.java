package functional_interfaces;

@FunctionalInterface
public interface StringModifier {
	String modify(String stringa);

	// void blablabla(); <-- Le interfacce per essere FUNZIONALI devono avere ESATTAMENTE UN METODO ASTRATTO
}

//public class DotsWrapper implements StringModifier{
//	@Override
//	public String modify(String stringa) {
//		return "..." + stringa + "...";
//	}
//}

