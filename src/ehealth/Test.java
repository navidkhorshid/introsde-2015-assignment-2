package ehealth;

import ehealth.model.bl.PersonBL;
import ehealth.model.to.HealthProfile;
import ehealth.model.to.People;
import ehealth.model.to.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navid on 11/18/2015.
 */
public class Test {
    private List<Person> personList = new ArrayList<Person>();

    public void setPersonList(List<Person> persons)
    {
        this.personList = persons;
    }

    /*public void addPersonList(Person p)
    {
        this.personList.add(p);
    }
    */
    public List<Person> getPersonList()
    {
        return this.personList;
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
        Test test = new Test();

        test.setPersonList(initialize());

        //REQUEST 1
        //test.getPersonsBrowser();

        //REQUEST 4
        test.newPerson(test.personList.get(1));

    }

        /*PersonCollectionResource personCollectionResource = new PersonCollectionResource();
        PersonResource personResource = personCollectionResource.getPerson(1);
        personResource.deletePerson();
*/




        /*Person person = new Person();
        person.setBirthdate(new java.util.Date(System.currentTimeMillis()));
        person.setFirstname("Nima");
        person.setLastname("Shams");
        try
        {
            PersonDA personDA = new PersonDA();
            person = new Person(personDA.insert(person));
            personDA.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
*/
/*
        Person person = new Person();
        try
        {
            PersonDA personDA = new PersonDA();
            person = personDA.selectById(7);
            personDA.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(person.getBirthdate().getTime());
*/

        /*Person person = new Person();
        person.setIdPerson(6);
        try
        {
            PersonDA personDA = new PersonDA();
            personDA.deleteById(person);
            personDA.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }*/

        //THIS DOES NOT WORKS - first get people then delete them HOWEVER they work individually

        /*People people = new People();
        try
        {
            PersonBL personBL = new PersonBL();
            people.setPerson(personBL.getPersons());
        }catch (Exception e)
        {
            System.out.println("Error in the GET part"+ e.getMessage());
        }
        for(Person p : people.getPerson()) {
            p.print();
        }
        System.out.println("-----------------");

        Person person = new Person();
        person.setIdPerson(4);
        try
        {
            PersonBL personBL = new PersonBL();
            personBL.deletePerson(person);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
*/


     /*   Person person = new Person();
        person.setIdPerson(3);
        PersonBL personBL = new PersonBL();
        try
        {
            personBL.deletePerson(person);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
*/
/*

        List<HealthProfile> list = new ArrayList<HealthProfile>();

        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setValue("70");
        healthProfile.setMeasure("weight");
        list.add(healthProfile);

        healthProfile.setValue("180");
        healthProfile.setMeasure("height");
        list.add(healthProfile);


        Person person = new Person();
        person.setFirstname("Jalil");
        person.setLastname("SHAMSIZADEH");
        person.setBirthdate(new java.util.Date(System.currentTimeMillis()));
        person.setMeasureType(list);

        PersonBL user = new PersonBL();
        try
        {
            person.setIdPerson(user.setPerson(person).getIdPerson());
            System.out.println(String.valueOf(person.getIdPerson())+"IN TEST");
        }catch (Exception e)
        {
            System.out.println(e.getMessage()+"????");
        }
*/

        public static List<Person> initialize()
        {
            List<Person> listOfPerson = new ArrayList<Person>();
            List<HealthProfile> list = new ArrayList<HealthProfile>();

            HealthProfile healthProfile = new HealthProfile();
            healthProfile.setValue("70");
            healthProfile.setMeasure("weight");
            list.add(healthProfile);

            HealthProfile healthProfile1 = new HealthProfile();
            healthProfile1.setValue("180");
            healthProfile1.setMeasure("height");
            list.add(healthProfile1);


            Person person = new Person();
            person.setFirstname("Jalil");
            person.setLastname("SHAMSIZADEH");
            person.setBirthdate(new java.util.Date(System.currentTimeMillis()));
            person.setMeasure(list);

            listOfPerson.add(person);

            //2nd person
            List<HealthProfile> list2 = new ArrayList<HealthProfile>();

            HealthProfile healthProfile2 = new HealthProfile();
            healthProfile2.setValue("75.5");
            healthProfile2.setMeasure("weight");
            list2.add(healthProfile2);

            HealthProfile healthProfile22 = new HealthProfile();
            healthProfile22.setValue("177");
            healthProfile22.setMeasure("height");
            list2.add(healthProfile22);


            Person person2 = new Person();
            person2.setFirstname("Navid");
            person2.setLastname("SHAMSIZADEH");
            person2.setBirthdate(new java.util.Date(System.currentTimeMillis()));
            person2.setMeasure(list2);

            listOfPerson.add(person2);

            return listOfPerson;
        }

        //TEST REQUEST #1
        public void getPersonsBrowser() {
        System.out.println("Getting list of people...*");

        PersonBL user = new PersonBL();
        People people = new People();
        try
        {
            people.setPerson(user.getPersons());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

            for(Person p: people.getPerson())
            {
                p.print();
            }
    }
        //TEST REQUEST #2
        //TEST REQUEST #3
        //TEST REQUEST #4
        public void newPerson(Person person) {
            PersonBL user = new PersonBL();
            try
            {
                person.setIdPerson(user.setPerson(person).getIdPerson());
                System.out.println(person.getFirstname() + " with ID: " + person.getIdPerson());
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        //TEST REQUEST #5
        //TEST REQUEST #6
        //TEST REQUEST #7
        //TEST REQUEST #8
        //TEST REQUEST #9
        //TEST REQUEST #11
}

