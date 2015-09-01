/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.chart.ui;

import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;

/**
 * Bar chart in 2D.
 */
public class BarChart2D extends CartesianChart2D
{   
   private ColorRGBA deselectedColor = new ColorRGBA(0.8f, 0.8f, 0.8f);
   
   public BarChart2D(String title,
                     ChartContentProvider contentProvider,
                     ChartColorProvider colorProvider)
   {
      super(title, contentProvider, colorProvider);

      verticalGridLines = true;

      disableNotifications();
      
      // Configurables
      setItemWindow(0, 7);
      setItemWidth(24.0f);
      setItemTickStep(3);
      
      enableNotifications();
   }

   protected void drawAxesTicks(GraphicsContext g, int width, int height)
   {
      int itemTickStep = getItemTickStep();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();
      float itemWidth = getItemWidth();
      float xOffset = 0.5f * itemWidth + (itemTickStep - 1 - itemOffset % itemTickStep) * itemWidth;
      float xStep = itemWidth * (float)itemTickStep;
      float xLimit = itemWidth * itemCount;
      float yOffset = (float)-getMinValue() * getValueScale();
      float tickLength = 6.0f;
      
      g.drawTicksX(xOffset, xStep, xLimit, 6.0f / getInnerScale(height), yOffset);
      
      double maxValue = getMaxValue();
      double minValue = getMinValue();
      float base = (float)-minValue * getValueScale();
      float yStep;
      
      final String boundsTestString = "0";
      double threshold = g.getTextBounds2D(GraphicsContext.FONT_LABEL, boundsTestString).getHeight();
      
      if (maxValue >= Math.abs(minValue))
      {
         yStep = (float)(getAdaptiveStepSize(maxValue, height, threshold) * getValueScale());
      }
      else
      {
         yStep = (float)(getAdaptiveStepSize(Math.abs(minValue), height, threshold) * getValueScale());         
      }
            
      if (getMaxValue() > 0)
      {
         // Draw positive Y ticks
         g.drawTicksY(base, yStep, 1.01f, tickLength, 0.0f);
      }
      
      if (getMinValue() < 0)
      {
         // Draw negative Y ticks
         g.drawTicksY(base, -yStep, -0.01f, tickLength, 0.0f);
      }
   }
   
   protected void drawValues(GraphicsContext g, int width, int height)
   {
      ChartContentProvider content = getContentProvider();
      ChartColorProvider colors = getColorProvider();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();
      int seriesOffset = 0;
      int layerOffset = 0;
      float itemWidth = getItemWidth();
      double minValue = getMinValue();
      float base = (float)-minValue * getValueScale();
      Range itemSelection = getItemSelection();
      ColorRGBA seriesColor = colors.getSeriesColor(layerOffset, seriesOffset);
      
      for (int i = 0; i < itemCount; i++)
      {
         // FIXME: 0.25f and 0.5f should be variables/constants
         float x0 = (float)i * itemWidth + 0.25f * itemWidth;
         float x1 = x0 + 0.5f * itemWidth;

         float y0 = base;
         float y1 = base;

         double value = content.getValue(layerOffset,
                                         seriesOffset,
                                         i + itemOffset);            
         
         if (!Double.isNaN(value))
         {
            float scaledValue;
            scaledValue = (float)value * getValueScale();                              
            y1 = y0 + scaledValue;
            
            if (itemSelection.contains(itemOffset + i))
            {
               g.setForeground(seriesColor);
            }
            else
            {
               g.setForeground(deselectedColor);
            }
            
            g.fillRectangle2D(x0, y0, x1, y1);
            y0 = y1;
         }
         else
         {
            // Loss could be marked in this else clause,
            // if desired in the future            
         }
      }
   }
}