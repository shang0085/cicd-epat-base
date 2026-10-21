package sg.edu.nus.iss.d13revision;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;
import sg.edu.nus.iss.d13revision.services.PersonService;

public class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
    }

    @Test
    public void testGetPersons_returnsDefaultTwo() {
        List<Person> persons = personService.getPersons();
        assertEquals(2, persons.size());
    }

    @Test
    public void testAddPerson_increasesListSize() {
        personService.addPerson(new Person("John", "Doe"));
        assertEquals(3, personService.getPersons().size());
    }

    @Test
    public void testAddPerson_storesCorrectData() {
        personService.addPerson(new Person("John", "Doe"));
        Person added = personService.getPersons().get(2);
        assertEquals("John", added.getFirstName());
        assertEquals("Doe", added.getLastName());
    }

    @Test
    public void testUpdatePerson_updatesFirstAndLastName() {
        Person existing = personService.getPersons().get(0);
        String id = existing.getId();

        Person updated = new Person(id, "Updated", "Name");
        personService.updatePerson(updated);

        Person result = personService.getPersons().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("Name", result.getLastName());
    }

    @Test
    public void testUpdatePerson_listSizeUnchanged() {
        Person existing = personService.getPersons().get(0);
        Person updated = new Person(existing.getId(), "X", "Y");
        personService.updatePerson(updated);
        assertEquals(2, personService.getPersons().size());
    }

    @Test
    public void testRemovePerson_decreasesListSize() {
        Person existing = personService.getPersons().get(0);
        personService.removePerson(existing);
        assertEquals(1, personService.getPersons().size());
    }

    @Test
    public void testRemovePerson_removesCorrectPerson() {
        Person existing = personService.getPersons().get(0);
        String removedId = existing.getId();
        personService.removePerson(existing);

        boolean stillPresent = personService.getPersons().stream()
                .anyMatch(p -> p.getId().equals(removedId));
        assertFalse(stillPresent);
    }

    @Test
    public void testRemovePerson_nonExistentIdDoesNothing() {
        Person ghost = new Person("nonexistent-id", "Ghost", "Person");
        personService.removePerson(ghost);
        assertEquals(2, personService.getPersons().size());
    }
}
