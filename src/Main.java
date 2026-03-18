import com.github.javafaker.Faker;
import entities.User;
import functional_interfaces.StringModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public class Main {
	public static void main(String[] args) {
		// Se esiste un'interfaccia funzionale già definita, potrò creare
		// delle LAMBDA FUNCTION a partire da essa

		StringModifier starsWrapper = stringa -> "***" + stringa + "***";
		System.out.println(starsWrapper.modify("HELLO"));
		StringModifier dotsWrapper = stringa -> "..." + stringa + "...";
		System.out.println(dotsWrapper.modify("HELLO"));

		StringModifier stringInverter = stringa -> {
			String[] split = stringa.split("");
			String inverted = "";
			for (int i = split.length - 1; i >= 0; i--) {
				inverted += split[i];
			}
			return inverted;
		};

		System.out.println(stringInverter.modify("HELLO"));

		// ********************************** SUPPLIER *******************************************
		Supplier<Integer> randomIntSupplier = () -> {
			Random random = new Random();
			return random.nextInt(1, 100000);
		};

		Supplier<String> randomStringSupplier = () -> "ciao";

		Supplier<List<Integer>> randomListSupplier = () -> {
			List<Integer> randomList = new ArrayList<>();
			Random random = new Random();
			for (int i = 0; i < 100; i++) {
				randomList.add(random.nextInt(1, 1000));
			}
			return randomList;
		};

		Supplier<ArrayList<User>> randomUserSupplier = () -> {
			ArrayList<User> randomUsers = new ArrayList<>();
			Random random = new Random();
			Faker faker = new Faker(Locale.ITALIAN);
			for (int i = 0; i < 100; i++) {
				randomUsers.add(new User(faker.lordOfTheRings().character(), faker.name().lastName(), random.nextInt(1, 101)));
			}
			return randomUsers;
		};

		ArrayList<User> randomUsers = randomUserSupplier.get();

		randomUsers.forEach(user -> System.out.println(user));


	}
}
