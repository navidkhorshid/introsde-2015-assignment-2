package ehealth.model.bl;

import ehealth.model.da.MeasureDefinitionDA;
import ehealth.model.to.MeasureDefinition;

import java.util.ArrayList;

/**
 * Created by Navid on 11/17/2015.
 */
public class MeasureDefinitionBL {
    public ArrayList<String> getMeasureTypes() throws Exception
    {
        MeasureDefinitionDA measureDefinitionDA = new MeasureDefinitionDA();
        ArrayList<String> measureTypes = new ArrayList<String>();
        measureTypes = measureDefinitionDA.selectAll();
        //measureDefinitionDA.close();
        return measureTypes;
    }

    public MeasureDefinition getMeasureDefinition(String measureType) throws Exception
    {
        MeasureDefinitionDA measureDefinitionDA = new MeasureDefinitionDA();
        MeasureDefinition measureDefinition = new MeasureDefinition();
        measureDefinition= measureDefinitionDA.selectByMeasure(measureType);
        //measureDefinitionDA.close();
        return measureDefinition;
    }
}
