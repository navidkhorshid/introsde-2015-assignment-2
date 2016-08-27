package ehealth.model.bl;

import ehealth.model.da.PersonDA;
import ehealth.model.to.Person;

import java.util.ArrayList;

public class PersonBL
{
    //get details of a Person
    public Person getPerson(long person_id) throws Exception
    {
        PersonDA personDA = new PersonDA();
        Person person = new Person();
        person = personDA.selectById(person_id);
        //personDA.close();

        return new Person(person);
    }

    public ArrayList<Person> getPersons() throws Exception
    {
        PersonDA personDA = new PersonDA();
        ArrayList<Person> persons = new ArrayList<Person>();
        persons = personDA.selectAll();
        //personDA.close();
        return persons;
    }

    public ArrayList<Person> getPersonsByMinMax(String measureName, double min, double max) throws Exception
    {
        PersonDA personDA = new PersonDA();
        ArrayList<Person> persons = new ArrayList<Person>();
        persons = personDA.selectByPerson_MeasureType_Min_Max(measureName,min,max);
        //personDA.close();
        return persons;
    }

    //get count of Person
    public long getPersonCount() throws Exception
    {
        PersonDA personDA = new PersonDA();
        long count = 0;
        count = personDA.selectCount();
        //personDA.close();
        return count;
    }

    //UserRegistration
    public Person setPerson(Person person) throws Exception
    {
        PersonDA personDA = new PersonDA();
        person = personDA.insert(person);
        //If it has healthProfile then also try to insert their values
        if(person.getMeasureType().size()!=0)
            new HealthMeasureHistoryBL().setMeasureHistory(person);
        //personDA.close();
        return person;
    }

    //Edit Person
    public void updatePerson(Person person) throws Exception
    {
        PersonDA personDA = new PersonDA();
        personDA.updateById(person);
        //personDA.close();
    }

    public String getFirstId() throws Exception
    {
        PersonDA personDA = new PersonDA();
        return String.valueOf(personDA.selectFirstPerson());

    }

    public String getLastId() throws Exception
    {
        PersonDA personDA = new PersonDA();
        return String.valueOf(personDA.selectLastPerson());
    }

    public void deletePerson(Person person) throws Exception
    {
        //person.print();
        PersonDA personDA = new PersonDA();
        personDA.deleteById(new Person(person));
        //THERE IS A TRIGGER IN DATABASE WHICH TAKES CARE OF DELETING ROWS IN HealthMeasureHistory
        //personDA.close();
    }
}
