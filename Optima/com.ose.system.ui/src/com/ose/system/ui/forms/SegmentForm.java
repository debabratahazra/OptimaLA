/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.system.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import com.ose.system.Segment;
import com.ose.system.ui.util.StringUtils;

public class SegmentForm implements IForm
{
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Segment segment;

   // TODO: Add numSignalsAttachedText and masMappedText when supported.

   private Text killedText;
   private Text nameText;
   private Text idText;
   private Text segmentNumberText;

   public SegmentForm(IEditorPart editor, Segment segment)
   {
      if ((editor == null) || (segment == null))
      {
         throw new NullPointerException();
      }
      this.segment = segment;
   }

   public void createContents(Composite parent)
   {
      TableWrapLayout layout;

      // Create form.
      toolkit = new FormToolkit(parent.getDisplay());
      form = toolkit.createScrolledForm(parent);
      layout = new TableWrapLayout();
      layout.numColumns = 1;
      form.getBody().setLayout(layout);

      // Create information section.
      createInfoSection();

      // Refresh form contents.
      refresh();
   }

   private void createInfoSection()
   {
      Section section;
      TableWrapLayout layout;
      TableWrapData twd;
      Composite client;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Segment Information");
      twd = new TableWrapData(TableWrapData.FILL);
      section.setLayoutData(twd);

      client = toolkit.createComposite(section);
      layout = new TableWrapLayout();
      layout.numColumns = 2;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      toolkit.createLabel(client, "Killed:");
      killedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      killedText.setLayoutData(twd);

      toolkit.createLabel(client, "Name:");
      nameText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      nameText.setLayoutData(twd);

      toolkit.createLabel(client, "Segment ID:");
      idText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      idText.setLayoutData(twd);

      toolkit.createLabel(client, "Segment Number:");
      segmentNumberText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      segmentNumberText.setLayoutData(twd);
   }

   public void dispose()
   {
      toolkit.dispose();
   }

   public void setFocus()
   {
      // Nothing to be done.
   }

   public void refresh()
   {
      // Refresh form title.
      form.setText(segment.getName());

      // Refresh information section.
      refreshInfoSection();

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      killedText.setText(StringUtils.toKilledString(segment.isKilled()));
      nameText.setText(segment.getName());
      idText.setText(StringUtils.toHexString(segment.getId()));
      segmentNumberText.setText(StringUtils.toHexString(segment.getSegmentNumber()));
   }

   public Control getControl()
   {
      return form;
   }
}