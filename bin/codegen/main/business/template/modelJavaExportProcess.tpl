/*******************************************************************************
 * PROPRIETARY/CONFIDENTIAL
 * Copyright (c) 2004-2009 WisageTech 
 *
 * All rights reserved. This medium contains confidential and proprietary
 * source code and other information which is the exclusive property of
 * WisageTech.  None of these materials may be used,
 * disclosed, transcribed, stored in a retrieval system, translated into
 * any other language or other computer language, or transmitted in any form
 * or by any means (electronic, mechanical, photocopied, recorded or
 * otherwise) without the prior written permission of WisageTech.
 *******************************************************************************
 *
 * $Header: $
 * $Id: $
 * $Author: $
 * $Date: $
 * $Revision: $
 *
 *******************************************************************************
 */
package ${entity.controllerPackagePath};

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;

import com.gearteks.framework.common.PageInfo;
import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.common.util.SessionUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.common.taglib.html.HtmlTagHelper;
import com.gearteks.imanage.imexport.process.AbstractExcelBatchExportService;
import com.gearteks.imanage.imexport.process.ExcelOperationBaseUtil;
import com.gearteks.imanage.report.tool.POIUtil;
import ${entity.fullPathClassName};

/**
 * @author ${entity.author}
 */
public class ${entity.exportProcessName} extends AbstractExcelBatchExportService {

	public static final String EXPORT_IDENTITY = "${entity.lowerFirstClassName}";
	
	private static ${entity.exportProcessName} _instance = new ${entity.exportProcessName}();

   public static ${entity.exportProcessName} getInstance() {
      return _instance;
   }

   public ${entity.exportProcessName}() {
   }
   
   @Override
   public HSSFCellStyle createHeaderCellStyle(HSSFWorkbook hssfWorkbook,
         String identify) {
      // TODO Auto-generated method stub
      return super.createDefaultHeaderCellStyle(hssfWorkbook);
   }

   @Override
   public void genExcelHeader(HttpServletRequest request,
         HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, int sourceIndex,
         int rowNumber, int length, String identify) {
      // TODO Auto-generated method stub
      length = 3500;
      short columnNumber = 0;
      
      HSSFCellStyle cellStyleA = createHeaderCellStyle(hssfWorkbook, identify);
      String columnTitle = "";
      #foreach($field in $entity.showInListFields)
         columnTitle = SessionUtil.getMessage("${entity.resourceKeyPath}${field.i18n.key}");
         hssfSheet.setColumnWidth(columnNumber, length * 2);
         POIUtil.getInstance().setCell(hssfSheet, rowNumber, columnNumber++, cellStyleA, columnTitle);
      #end
      
      HSSFRow row = POIUtil.getInstance().findOrCreateRow(hssfSheet, rowNumber);
      row.setHeight((short) 500);
   }

   @Override
   public String[] genPageHeader(HttpServletRequest request, ActionForm form,
         String identify) {
      // TODO Auto-generated method stub
      return new String[]{SessionUtil.getMessage("${entity.resourceKeyPath}${entity.lowerFirstClassName}.name")};
   }

   @Override
   public Integer[] getExcelHeaderRowsNum(HttpServletRequest request,
         String identify) {
      // TODO Auto-generated method stub
      return new Integer[]{${entity.showInListFieldsSize}};
   }

   @Override
   public Collection[] getNeed2ExportData(HttpServletRequest request,
         ActionForm form, PageInfo pageInfo, Map parameters, String identify)
         throws GTSystemException {
         
      Collection exportDatas = (Collection)request.getAttribute("list");
      if (exportDatas == null) {
         exportDatas = ${entity.processName}.getInstance().get${entity.className}List(null, null);
      }
      return new Collection[]{exportDatas};
   }

   @Override
   public String getReportName(ActionForm form, String identify) {
      // TODO Auto-generated method stub
      
      return SessionUtil.getMessage("${entity.resourceKeyPath}${entity.lowerFirstClassName}.name", null);
   }

   @Override
   public void writeExcelData(HttpServletRequest request, List subList,
         Workbook workbook, Sheet sheet, int sourceIndex, int rowNumber,
         String identify) {
      // TODO Auto-generated method stub
      int columnNumber = -1;
      RowRecord rowRecord = null;
      int rowNum = -1;
      
      ExtendedFormatRecord xfrLeft = ExcelOperationBaseUtil.createCellXF4Report(workbook);
      short xfrIndexLeft = (short) (workbook.getNumExFormats() - 1);
      
      ExtendedFormatRecord xfrCenter = ExcelOperationBaseUtil.createCellXF4Report(workbook);
      xfrCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      short xfrIndexCenter = (short) (workbook.getNumExFormats() - 1);
      
      ExtendedFormatRecord xfrRight = ExcelOperationBaseUtil.createCellXF4Report(workbook);
      xfrRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
      short xfrIndexRight = (short) (workbook.getNumExFormats() - 1);

      ExtendedFormatRecord xfrLeftWrap = ExcelOperationBaseUtil.createCellXF4Report(workbook);
      xfrLeftWrap.setWrapText(true);
      short xfrIndexLeftWrap = (short) (workbook.getNumExFormats() - 1);
      
      String value = HtmlTagHelper.EMPTY_DISPLAY_VALUE;
      for (Object obj : subList) {
         ${entity.className} entity = (${entity.className})obj;
         columnNumber = 0;
         rowRecord = ExcelOperationBaseUtil.addRowRecord(sheet, rowNumber);
         rowNum = rowRecord.getRowNumber();
         
         #foreach($field in ${entity.showInListFields})
            #if ($field.isSysMember)
               value =DisplayObjAssistProcess.getDefaultToolTips(entity.get${field.upperFirstFieldName}(), request);
            #elseif ($field.isSysObj)
               value =StringUtil.formatObjForDisplay(${entity.helperName}.get${field.upperFirstFieldName}Display(entity));
            #elseif ($field.hasOptions)
               value =${entity.helperName}.get${field.upperFirstFieldName}Display(entity.get${field.upperFirstFieldName}() ,true);
            #elseif (${field.hasFormat})
               value =StringUtil.formatObjForDisplay(${entity.helperName}.get${field.upperFirstFieldName}Display(entity));
            #else
               value = StringUtil.formatObjForDisplay(entity.get${field.upperFirstFieldName}());
            #end
            ExcelOperationBaseUtil.addRowCell(workbook, sheet, Integer.valueOf(rowNum), Integer.valueOf(columnNumber++), 
               StringUtil.nvl(value, HtmlTagHelper.EMPTY_DISPLAY_VALUE), xfrIndexLeft);
         #end
         rowNumber++; 
      }
   }
   
   @Override
   public void writeExcelSumaryData(HttpServletRequest request, Workbook workbook, Sheet sheet, int sourceIndex, int rowNumber, String identify) {
   
   }

}
