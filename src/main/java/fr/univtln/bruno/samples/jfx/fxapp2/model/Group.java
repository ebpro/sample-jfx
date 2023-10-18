package fr.univtln.bruno.samples.jfx.fxapp2.model;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

public class Group {
    @NoArgsConstructor(staticName = "newInstance")
    public static class DAO {
        public Page<Person> findAll() {return findAll(10,1);}
        public Page<Person> findAll(int pageSize, int pageNumber) {
            return findAll(pageSize, pageNumber, Comparator.comparing(Person::getName));
        }
        public Page<Person> findAll(int pageSize, int pageNumber, Comparator<? super Person> comparateur) {
            return new Page<>(datasize, pageSize, pageNumber,
                    members.values().stream().sorted(comparateur).skip(pageNumber*pageSize).limit(pageSize).toList());
        }

        public Page<Person> search(String criteria) {
            return search(10, 1, criteria);
        }
        public Page<Person> search(int pageSize, int pageNumber, String criteria) {
            return search(pageSize, pageNumber, Comparator.comparing(Person::getName), criteria);
        }
        public Page<Person> search(int pageSize, int pageNumber, Comparator<? super Person> comparateur, String criteria) {
            return new Page<>(datasize, pageSize, pageNumber,
                    members.values().stream()
                            .filter(p->p.getName().contains(criteria))
                            .sorted(comparateur)
                            .skip((pageNumber-1)*pageSize).limit(pageSize).toList());
        }
    }

    final private static long datasize = 1000;

    final private static Map<UUID, Person> members = new ConcurrentHashMap<>();

    static {
        Faker faker = new Faker(new Locale("fr"));
        LongStream.range(0, datasize)
                .mapToObj(id-> {
                        Name name=faker.name();
                        Address address=faker.address();
                        return Person.of(name.lastName()+", "+name.firstName(),
                        address.streetAddress()+" "+address.zipCode()+" "+address.cityName());})
                .forEach(p->members.put(p.getUuid(), p));
    }

    public static void main(String[] args) {
        System.out.println(new DAO().findAll());
    }
}
