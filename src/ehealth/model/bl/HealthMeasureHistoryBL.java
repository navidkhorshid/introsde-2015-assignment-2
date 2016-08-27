package ehealth.model.bl;

import ehealth.model.da.HealthMeasureHistoryDA;
import ehealth.model.to.HealthMeasureHistory;
import ehealth.model.to.HealthProfile;
import ehealth.model.to.Person;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Navid on 11/17/2015.
 */
public class HealthMeasureHistoryBL {
    public ArrayList<HealthMeasureHistory> getMeasureHistories(long idPerson, int idMeasureDef) throws Exception
    {
        HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();
        ArrayList<HealthMeasureHistory> healthMeasureHistories = new ArrayList<HealthMeasureHistory>();
        healthMeasureHistories = healthMeasureHistoryDA.selectByPerson_MeasureDef(idPerson, idMeasureDef);
        //healthMeasureHistoryDA.close();
        return healthMeasureHistories;
    }

    public ArrayList<HealthMeasureHistory> getMeasureHistories(long idPerson, int idMeasureDef, Date before, Date after) throws Exception
    {
        HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();
        ArrayList<HealthMeasureHistory> healthMeasureHistories = new ArrayList<HealthMeasureHistory>();
        healthMeasureHistories = healthMeasureHistoryDA.selectByPerson_MeasureDef_Date(idPerson, idMeasureDef, before, after);
        //healthMeasureHistoryDA.close();
        return healthMeasureHistories;
    }

    public HealthMeasureHistory getMeasureHistory(long idPerson, int idMeasureDef, long idMeasureHistory) throws Exception
    {
        HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();
        HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
        healthMeasureHistory = healthMeasureHistoryDA.selectByPerson_MeasureDef_mId(idPerson, idMeasureDef, idMeasureHistory);
        //healthMeasureHistoryDA.close();
        return healthMeasureHistory;
    }

    public void setMeasureHistory(Person person) throws Exception
    {
        for(HealthProfile hp: person.getMeasureType())
        {
            HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();
            HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();

            healthMeasureHistory.setPerson(new Person(person));
            healthMeasureHistory.setMeasureDefinition(new MeasureDefinitionBL().getMeasureDefinition(hp.getMeasure()));
            System.out.println(healthMeasureHistory.getMeasureDefinition().getMeasureType());
            healthMeasureHistory.setValue(hp.getValue());

            healthMeasureHistoryDA.insert(healthMeasureHistory, true);
        }

        //healthMeasureHistoryDA.close();
    }

    public HealthMeasureHistory setMeasureHistory(HealthMeasureHistory healthMeasureHistory) throws Exception
    {
        HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();

        return healthMeasureHistoryDA.insert(healthMeasureHistory, false);
        //healthMeasureHistoryDA.close();
    }

    //FOR REQUEST 10
    public void updatePersonMeasure(HealthMeasureHistory healthMeasureHistory)
    {
        HealthMeasureHistory tmpHealthMeasureHistory = new HealthMeasureHistory();
        try
        {
            new HealthMeasureHistoryDA().update(healthMeasureHistory) ;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //IT IS IMPLEMENTED IN DATABASE AS A TRIGGER, IT WASN'T USED IN THIS PROJECT
    public void deleteMeasureHistory(Person person) throws Exception
    {
        HealthMeasureHistoryDA healthMeasureHistoryDA = new HealthMeasureHistoryDA();
        healthMeasureHistoryDA.deleteByPerson(person);
        //healthMeasureHistoryDA.close();
    }
}
