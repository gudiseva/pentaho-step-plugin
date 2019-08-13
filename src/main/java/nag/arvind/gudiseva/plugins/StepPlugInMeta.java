package nag.arvind.gudiseva.plugins;

import java.util.List;
import java.util.Map;

import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

/**
 * @author Nag Arvind Gudiseva
 *
 */

public class StepPlugInMeta extends BaseStepMeta implements StepMetaInterface {

    private String newField;

    public StepPlugInMeta() {
        super(); // allocate BaseStepInfo
    }

    public String getNewField() {
        return newField;
    }

    public void setNewField(String newField) {
        this.newField = newField;
    }

    @Override
    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                 Trans trans) {
        return new StepPlugInStep(stepMeta, stepDataInterface, cnr, transMeta, trans);
    }

    @Override
    public StepDataInterface getStepData() {
        return new StepPlugInData();
    }

    @Override
    public void setDefault() {
        // TODO Auto-generated method stub
    }

    public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters)
            throws KettleXMLException {

        try {
            newField = XMLHandler.getTagValue(stepnode,"NEWFIELD");
        } catch (Exception e) {
            throw new KettleXMLException("Load XML: Excption ", e); // Messages.getString("KafkaTopicPartitionConsumerMeta.Exception.loadXml"), e);
        }
    }

    public String getXML() throws KettleException {
        StringBuilder retVal = new StringBuilder();
        if (newField != null) {
            retVal.append("    ").append(XMLHandler.addTagValue("NEWFIELD", newField));
        }
        return retVal.toString();
    }

    public void readRep(Repository rep, ObjectId stepId, List<DatabaseMeta> databases, Map<String, Counter> counters)
            throws KettleException {
        try {
            newField = rep.getStepAttributeString(stepId, "NEWFIELD");
        } catch (Exception e) {
            throw new KettleException("Unexpected error reading step Step Plugin from the repository", e);
        }
    }

    public void saveRep(Repository rep, ObjectId transformationId, ObjectId stepId) throws KettleException {
        try {
            if (newField != null) {
                rep.saveStepAttribute(transformationId, stepId, "NEWFIELD", newField);
            }
        } catch (Exception e) {
            throw new KettleException("Unexpected error saving step Step Plugin from the repository", e);
        }
    }

    public void getFields(RowMetaInterface rowMeta, String origin, RowMetaInterface[] info, StepMeta nextStep,
                          VariableSpace space) throws KettleStepException {

        ValueMetaInterface newFieldMeta = new ValueMeta(getNewField(), ValueMetaInterface.TYPE_STRING);
        newFieldMeta.setName("NewField");
        newFieldMeta.setOrigin(origin);
        rowMeta.addValueMeta(newFieldMeta);
    }

    public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev,
                      String input[], String output[], RowMetaInterface info) {
        CheckResult cr;
        if (prev == null || prev.size() == 0) {
            cr = new CheckResult(CheckResult.TYPE_RESULT_WARNING, "Not receiving any fields from previous steps!",
            stepMeta);
            remarks.add(cr);
        }
    }
}