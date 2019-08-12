package nag.arvind.gudiseva.plugins;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

public class StepPlugInData extends BaseStepData implements StepDataInterface {

    public RowMetaInterface outputRowMeta;

    public StepPlugInData() {
        super();
    }

}