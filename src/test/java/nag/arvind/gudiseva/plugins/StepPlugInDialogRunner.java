package nag.arvind.gudiseva.plugins;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mockito.Mockito;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.core.PropsUI;

public class StepPlugInDialogRunner {

    public static void main(String[] args) {

        Display display = new Display();
        Shell parent = new Shell(display);

        TransMeta transMeta = Mockito.mock(TransMeta.class);
        StepPlugInMeta meta = new StepPlugInMeta();
        //meta.setHeaderName(new String[0]);
        //meta.setParameterField(new String[0]);
        //meta.setMatrixParameterField(new String[0]);
        String stepname = "stepname";
        PropsUI.init(display, 0);
        StepPlugInDialog dialog = new StepPlugInDialog(parent, meta, transMeta, stepname);

        dialog.open();
    }
}
