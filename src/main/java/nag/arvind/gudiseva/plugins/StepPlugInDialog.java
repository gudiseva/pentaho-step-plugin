package nag.arvind.gudiseva.plugins;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

/**
 * @author Nag Arvind Gudiseva
 *
 */
public class StepPlugInDialog extends BaseStepDialog implements StepDialogInterface {
    private StepPlugInMeta stepPlugInMeta;
    private TextVar wnewField;

    public StepPlugInDialog(Shell parent, Object in, TransMeta tr, String sname) {
        super(parent, (BaseStepMeta) in, tr, sname);
        stepPlugInMeta = (StepPlugInMeta) in;
    }

    @Override
    public String open() {
        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
        props.setLook(shell);
        setShellImage(shell, stepPlugInMeta);

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                stepPlugInMeta.setChanged();
            }
        };
        changed = stepPlugInMeta.hasChanged();

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        shell.setText("Step Plugin"); // Messages.getString("KafkaTopicPartitionConsumerDialog.Shell.Title"));

        int middle = props.getMiddlePct();
        int margin = Const.MARGIN;

        // Step name
        wlStepname = new Label(shell, SWT.RIGHT);
        wlStepname.setText("Step Name"); // Messages.getString("KafkaTopicPartitionConsumerDialog.StepName.Label"));
        props.setLook(wlStepname);
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment(0, 0);
        fdlStepname.right = new FormAttachment(middle, -margin);
        fdlStepname.top = new FormAttachment(0, margin);
        wlStepname.setLayoutData(fdlStepname);
        wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wStepname);
        wStepname.addModifyListener(lsMod);
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment(middle, 0);
        fdStepname.top = new FormAttachment(0, margin);
        fdStepname.right = new FormAttachment(100, 0);
        wStepname.setLayoutData(fdStepname);
        Control lastWidget = wStepname;

        Label wlnewField = new Label(shell, SWT.RIGHT);
        wlnewField.setText("New Field [Type:String]"); // Messages.getString("KafkaTopicPartitionConsumerDialog.TopicName.Label"));
        props.setLook(wlnewField);
        FormData fdlnewField = new FormData();
        fdlnewField.top = new FormAttachment(lastWidget, margin);
        fdlnewField.left = new FormAttachment(0, 0);
        fdlnewField.right = new FormAttachment(middle, -margin);
        wlnewField.setLayoutData(fdlnewField);
        wnewField = new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wnewField);
        wnewField.addModifyListener(lsMod);
        FormData fdnewField = new FormData();
        fdnewField.top = new FormAttachment(lastWidget, margin);
        fdnewField.left = new FormAttachment(middle, 0);
        fdnewField.right = new FormAttachment(100, 0);
        wnewField.setLayoutData(fdnewField);
        lastWidget = wnewField;

        // Buttons
        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(BaseMessages.getString("System.Button.OK")); //$NON-NLS-1$
        wCancel = new Button(shell, SWT.PUSH);
        wCancel.setText(BaseMessages.getString("System.Button.Cancel")); //$NON-NLS-1$

        setButtonPositions(new Button[] { wOK, wCancel }, margin, null);

        lsCancel = new Listener() {
            public void handleEvent(Event e) {
                cancel();
            }
        };
        lsOK = new Listener() {
            public void handleEvent(Event e) {
                ok();
            }
        };
        wCancel.addListener(SWT.Selection, lsCancel);
        wOK.addListener(SWT.Selection, lsOK);

        lsDef = new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                ok();
            }
        };

        // Set the shell size, based upon previous time…
        setSize(shell, 200, 150, true);
        getData(stepPlugInMeta, true);
        // consumerMeta.setChanged(changed);

        // setTableFieldCombo();

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        return stepname;
    }

    private void ok() {
        if (Const.isEmpty(wStepname.getText())) {
            return;
        }
        setData(stepPlugInMeta);
        dispose();
    }

    private void cancel() {
        stepname = null;
        stepPlugInMeta.setChanged(changed);
        dispose();
    }

/**
 * Copy information from the meta-data input to the dialog fields.
 */
    /**
     * @param stepPlugInMeta
     * @param copyStepname
     */
    private void getData(StepPlugInMeta stepPlugInMeta, boolean copyStepname) {
        if (copyStepname) {
            wStepname.setText(stepname);
            if (stepPlugInMeta.getNewField() != null)
                wnewField.setText(stepPlugInMeta.getNewField());
        }
    }

    /**
     * Copy information from the dialog fields to the meta-data input
     */
    private void setData(StepPlugInMeta stepPlugInMeta) {
        stepname = wStepname.getText();
        stepPlugInMeta.setNewField(wnewField.getText());
        stepPlugInMeta.setChanged();
    }
}