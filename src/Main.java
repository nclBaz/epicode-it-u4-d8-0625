import com.github.javafaker.Faker;
import entities.User;
import functional_interfaces.StringModifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

		// ********************************************* PREDICATES **********************************************
		Predicate<Integer> isGreaterThanZero = number -> number > 0;
		Predicate<Integer> isLessThanHundred = number -> number < 100;

		System.out.println(isGreaterThanZero.test(10));
		System.out.println(isLessThanHundred.test(10));

		System.out.println(isGreaterThanZero.and(isLessThanHundred).test(10));

		Predicate<User> isAdult = user -> user.getAge() >= 18;

		randomUsers.forEach(user -> {
			if (isAdult.test(user)) System.out.println("L'utente " + user + " è maggiorenne!");
			else System.out.println("L'utente " + user + " è minorenne!");
		});

		// ************************************* REMOVE IF ************************************
		System.out.println("*************************** REMOVE IF ****************************");
		// randomUsers.removeIf(user -> user.getAge() >= 18); // Rimuove dalla lista tutti quelli che sono maggiorenni
		// randomUsers.removeIf(isAdult); // Se il Predicate l'ho già definito in precedenza posso usarlo direttamente qua
		// randomUsers.forEach(user -> System.out.println(user));

		// ******************************************** STREAMS - OPERAZIONI INTERMEDIE *******************************************
		// Le operazioni intermedie degli Stream restituiscono sempre un nuovo STREAM, questo perché è prevista la possibilità di concatenare
		// più operazioni intermedie una dopo l'altra
		System.out.println("------------------------------- FILTER ------------------------------");
		// Il FILTER prende una Lambda di tipo PREDICATE (quindi deve tornare un BOOLEAN), e torna uno STREAM (di User) perché è un'operazione intermedia
		Stream<User> filteredStream = randomUsers.stream().filter(user -> user.getAge() < 18 && user.getName().equals("Gimli"));
		// Stampare il contenuto di uno Stream si può fare tramite forEach (ma comunque non stiamo ottenendo una List)
		filteredStream.forEach(user -> System.out.println(user));

		System.out.println("------------------------------- MAP ------------------------------");
		// Il MAP è pensato per TRASFORMARE I DATI. Quindi molto probabilmente il tipo di dato che c'è dentro lo Stream cambierà (a differenza
		// del filter, dove rimane uguale). La Lambda all'interno del .map stabilisce il tipo di dato restituito
		Stream<String> nomiConcatenati = randomUsers.stream().map(user -> user.getName() + " - " + user.getAge());
		nomiConcatenati.forEach(nome -> System.out.println(nome));
		Stream<Integer> streamAges = randomUsers.stream().map(user -> user.getAge());

		System.out.println("------------------------------- FILTER & MAP ------------------------------");
		Stream<String> cognomiMaggiorenni = randomUsers.stream()
				.filter(user -> user.getAge() >= 18) // Da questo step ottengo uno Stream<User>
				.map(user -> user.getSurname()); // Da questo step ottengo uno Stream<String> (SOLO I COGNOMI)

		cognomiMaggiorenni.forEach(cognome -> System.out.println(cognome));


		// ******************************************** STREAMS - OPERAZIONI TERMINALI *******************************************

		System.out.println("----------------------------------- ANYMATCH & ALLMATCH ----------------------------------------");
		// Sono l'equivalente dei metodi .some() e .every() in JavaScript
		// AnyMatch: termina lo Stream tornando un BOOLEANO, che sarà VERO se ALMENO UN elemento dello stream soddisfa una certa condizione (Predicate)
		// AllMatch: termina lo Stream tornando un BOOLEANO, che sarà VERO se TUTTI GLI elementi dello stream soddisfano una certa condizione (Predicate)
		if (randomUsers.stream().anyMatch(user -> user.getAge() >= 18)) System.out.println("Almeno uno è maggiorenne");
		else System.out.println("Sono tutti minorenni");

		if (randomUsers.stream().allMatch(user -> user.getAge() >= 18)) System.out.println("Tutti sono maggiorenni");
		else System.out.println("C'è almeno un minorenne");

		System.out.println("----------------------------------- REDUCE ----------------------------------------");
		int totalAges = randomUsers.stream()
//				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.reduce(0, (acc, currentAge) -> acc + currentAge);

		System.out.println("La somma totale delle età è: " + totalAges);


		System.out.println("----------------------------------- TO LIST ----------------------------------------");
		List<User> usersMinorenni = randomUsers.stream().filter(user -> user.getAge() < 18).toList();
		usersMinorenni.forEach(user -> System.out.println(user));
		List<String> nomiUsersMinorenni = randomUsers.stream().filter(user -> user.getAge() < 18).map(user -> user.getName()).toList();
		nomiUsersMinorenni.forEach(nome -> System.out.println(nome));


		// ************************************ DATE IN JAVA ****************************
		LocalDate today = LocalDate.now();
		System.out.println(today);

		LocalDate tomorrow = today.plusDays(1);
		System.out.println(tomorrow);

		LocalDate todayNextYear = today.plusYears(1);
		System.out.println(todayNextYear);

		System.out.println(todayNextYear.isAfter(today));

		LocalDate date = LocalDate.of(1990, 3, 5);
		System.out.println(date);


	}
}
