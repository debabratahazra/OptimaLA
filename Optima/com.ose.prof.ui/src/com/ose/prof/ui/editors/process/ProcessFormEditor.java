/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.prof.ui.editors.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentChangeListener;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.model.DomHelper;
import com.ose.xmleditor.validation.ValidationNode;
import com.ose.xmleditor.validation.Validator;
import com.ose.xmleditor.validation.errors.Error;

public class ProcessFormEditor extends FormEditor implements
      DocumentChangeListener
{
   private DocumentModel model;

   private boolean dirty;

   private ImageCache imageCache;

   private ImageDescriptor titleImageDescriptor;

   public void init(IEditorSite site, IEditorInput editorInput)
         throws PartInitException
   {
      if (!(editorInput instanceof IURIEditorInput))
      {
         throw new PartInitException("Invalid editor input");
      }

      try
      {
         IURIEditorInput uriInput;
         Document document;

         super.init(site, editorInput);
         uriInput = (IURIEditorInput) editorInput;
         document = DomHelper.parse(new File(uriInput.getURI()),
                                    new ProcessEntityResolver());
         model = new ProcessModel(document);
         model.addDocumentChangedListener(this);
         imageCache = new ImageCache();
         titleImageDescriptor =
            ImageCache.createImageDescriptor("obj16/", "prof_procs.gif");
         update();
      }
      catch (ParserConfigurationException e)
      {
         throw new PartInitException(
            "Error parsing Profiled Processes Settings file", e);
      }
      catch (SAXException e)
      {
         throw new PartInitException(
            "Error parsing Profiled Processes Settings file", e);
      }
      catch (IOException e)
      {
         ProfilerPlugin.errorDialog(
            "Error",
            "The selected file is not a valid Profiled Processes Settings file",
            e);
         throw new PartInitException(
            "Error parsing Profiled Processes Settings file", e);
      }
   }

   protected void addPages()
   {
      try
      {
         // Since form editors are multi-page editors and we only have
         // one page, we suppress the tab decoration for the pages.
         Composite container = getContainer();
         if (container instanceof CTabFolder)
         {
            CTabFolder tabFolder = (CTabFolder) container;
            tabFolder.setSingle(true);
            tabFolder.setTabHeight(0);
         }
         addPage(new EditorFormPage(this));
      }
      catch (PartInitException e)
      {
         ProfilerPlugin.errorDialog(
               "Error while opening Profiled Processes Settings file",
               "Could not open Profiled Processes Settings file",
               e);
      }
   }

   public void dispose()
   {
      super.dispose();
      getModel().removeDocumentChangedListener(this);
      imageCache.dispose();
   }

   public boolean isDirty()
   {
      return dirty;
   }

   public void doSave(IProgressMonitor monitor)
   {
      OutputStream out = null;

      try
      {
         IURIEditorInput input;
         File file;
         IWorkbench workbench;
         IWorkbenchPage page;
         IEditorReference[] references;

         monitor.beginTask("Save", IProgressMonitor.UNKNOWN);
         input = (IURIEditorInput) getEditorInput();
         file = new File(input.getURI());
         out = new FileOutputStream(file);
         DomHelper.transform(getModel().getDocument(), out);
         markDirty(false);

         workbench = ProfilerPlugin.getDefault().getWorkbench();
         page = workbench.getActiveWorkbenchWindow().getActivePage();
         references = page.findEditors(input,
            "com.ose.prof.ui.editors.process.ProcessFormEditor",
            IWorkbenchPage.MATCH_INPUT);
         for (int i = 0; i < references.length; i++)
         {
            IEditorPart editor = references[i].getEditor(true);
            if (editor.isDirty())
            {
               editor.doSave(new NullProgressMonitor());
            }
         }

         refreshWorkspaceFile(monitor, file);
         update();
      }
      catch (Exception e)
      {
         ProfilerPlugin.errorDialog("Error", e.getMessage(), e);
      }
      finally
      {
         monitor.done();
         try
         {
            if (out != null)
            {
               out.close();
            }
         } catch (IOException ignore) {}
      }
   }

   public void doSaveAs()
   {
      SaveAsDialog dialog = new SaveAsDialog(getEditorSite().getShell());

      if (dialog.open() == Window.OK)
      {
         IPath path;
         String extension;
         IFile file;

         path = dialog.getResult();
         extension = path.getFileExtension();
         if ((extension == null) || (extension.length() == 0))
         {
            path = path.addFileExtension("process");
         }

         file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
         if (!file.exists())
         {
            try
            {
               file.getLocation().toFile().createNewFile();
            }
            catch (IOException e)
            {
               ProfilerPlugin.log(e);
            }
         }

         setInput(new FileEditorInput(file));
         firePropertyChange(FormEditor.PROP_INPUT);
         setPartName(file.getName());
         doSave(new NullProgressMonitor());
      }
   }

   public boolean isSaveAsAllowed()
   {
      return true;
   }

   public DocumentModel getModel()
   {
      return model;
   }

   public void documentChanged(DocumentChangeEvent event)
   {
      markDirty(true);
   }

   private void markDirty(boolean dirty)
   {
      this.dirty = dirty;
      editorDirtyStateChanged();
   }

   private void update()
   {
      IEditorInput input;
      IFile workspaceFile = null;
      boolean hasErrors;
      Image image;

      input = getEditorInput();
      if (input instanceof IFileEditorInput)
      {
         workspaceFile = ((IFileEditorInput) input).getFile();
         setPartName(workspaceFile.getName());
      }
      else
      {
         File file = new File(((IURIEditorInput) input).getURI());
         setPartName(file.getName());
      }

      hasErrors = markErrors(getModel().getDocument(), workspaceFile);
      image = imageCache.getImage(titleImageDescriptor);
      if (!hasErrors)
      {
         setTitleImage(image);
      }
      else
      {
         setTitleImage(imageCache.getImage(new ErrorDecoratedImageDescriptor(image)));
      }
   }

   private boolean markErrors(Document document, IFile file)
   {
      boolean errorsFound = false;
      List nodes;
      NodeList nodeList;
      Validator validator;

      nodes = new ArrayList();
      nodeList = document.getElementsByTagName("process");
      if (nodeList != null)
      {
         for (int i = 0; i < nodeList.getLength(); i++)
         {
            ValidationNode node = new ValidationNode(nodeList.item(i));
            nodes.add(node);
         }
      }

      validator = new Validator(new ProcessConditionProvider());
      validator.validate(nodes.toArray());

      try
      {
         if (file != null)
         {
            file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
         }

         for (int i = 0; i < nodes.size(); i++)
         {
            ValidationNode node = (ValidationNode) nodes.get(i);
            if (node.hasErrors())
            {
               errorsFound = true;
               if (file != null)
               {
                  List errors = node.getErrors();
                  for (int j = 0; j < errors.size(); j++)
                  {
                     Error error = (Error) errors.get(j);
                     IMarker marker = file.createMarker(IMarker.PROBLEM);
                     Map markerAttributes = new HashMap();
                     markerAttributes.put(IMarker.SEVERITY,
                                          new Integer(IMarker.SEVERITY_ERROR));
                     markerAttributes.put(IMarker.MESSAGE, error.getMessage());
                     markerAttributes.put(IMarker.LOCATION, "Unknown");
                     marker.setAttributes(markerAttributes);
                  }
               }
            }
         }
      }
      catch (CoreException e)
      {
         ProfilerPlugin.log(e);
      }

      return errorsFound;
   }

   private static void refreshWorkspaceFile(IProgressMonitor monitor, File file)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      if (file.isFile())
      {
         IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().
            getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
         if (workspaceFile != null)
         {
            try
            {
               workspaceFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
            }
            catch (CoreException e)
            {
               throw new IOException(e.getMessage());
            }
         }
      }
   }
}
