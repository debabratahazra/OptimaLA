/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.system.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class SetEnvVarDialog extends Dialog
{
   private String title;
   private String name;
   private String value;
   private Text nameText;
   private Text valueText;
   private CLabel errorMessageLabel;

   public SetEnvVarDialog(Shell parent, String title)
   {
      this(parent, title, null, null);
   }

   public SetEnvVarDialog(Shell parent, String title, String name, String value)
   {
      super(parent);
      if (title == null)
      {
         throw new NullPointerException();
      }
      this.title = title;
      this.name = name;
      this.value = value;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText(title);
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      // Set default values here to get them validated.
      nameText.setText((name != null) ? name : "");
      valueText.setText((value != null) ? value : "");
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      ModifyHandler modifyHandler;
      GridData gd;
      Label nameLabel;
      Label valueLabel;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));
      modifyHandler = new ModifyHandler();

      nameLabel = new Label(subcomp, SWT.NONE);
      nameLabel.setText("Name:");

      nameText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      nameText.setLayoutData(gd);
      nameText.addModifyListener(modifyHandler);

      valueLabel = new Label(subcomp, SWT.NONE);
      valueLabel.setText("Value:");

      valueText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      valueText.setLayoutData(gd);
      valueText.addModifyListener(modifyHandler);

      errorMessageLabel = new CLabel(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      name = nameText.getText().trim();
      value = valueText.getText().trim();
      super.okPressed();
   }

   private void updateDialog()
   {
      String errorMessage = isNameValid(nameText.getText());
      if (errorMessage == null)
      {
         errorMessage = isValueValid(valueText.getText());
      }
      setErrorMessage(errorMessage);
   }

   private void setErrorMessage(String errorMessage)
   {
      errorMessageLabel.setText((errorMessage == null) ? "" : errorMessage);
      errorMessageLabel.setImage((errorMessage == null) ? null :
         PlatformUI.getWorkbench().getSharedImages()
         .getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
      getButton(IDialogConstants.OK_ID).setEnabled(errorMessage == null);
      getShell().update();
   }

   private String isNameValid(String text)
   {
      String errorMessage = null;
      String str = text.trim();
      if (str.length() == 0)
      {
         errorMessage = "Name not specified";
      }
      else if (str.indexOf(' ') != -1)
      {
         errorMessage = "Name cannot contain spaces";
      }
      return errorMessage;
   }

   private String isValueValid(String text)
   {
      return null;
   }

   public String getName()
   {
      return name;
   }

   public String getValue()
   {
      return value;
   }

   class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateDialog();
      }
   }
}
