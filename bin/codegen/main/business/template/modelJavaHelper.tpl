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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.common.util.DateUtil;
import com.gearteks.framework.common.util.SessionUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.model.User;
import com.gearteks.framework.process.CurrencyProcess;

/**
 * @author ${entity.author}
 */
public class ${entity.className}Helper {

   public static boolean isAdd(HttpServletRequest request) {
      Object obj = request.getAttribute("isAdd");
      if (obj == null) {
         return StringUtil.toBoolean(request.getParameter("isAdd"), false);
      } else {
         return StringUtil.toBoolean(obj, false);
      }
   }

   public static void setAdd(HttpServletRequest request, boolean isAdd) {
      request.setAttribute("isAdd", isAdd);
   }

   public static Integer getId(HttpServletRequest request, ${entity.formName} form) {
      return form.get${entity.key.upperFirstFieldName}();
   }

   public static void prepareCommonData(HttpServletRequest request, ${entity.formName} form, ${entity.className} entity) throws GTSystemException {
      User currentUser = SessionUtil.getCurrentUser();
      Date now = DateUtil.getDBCurrentTimestamp();
      form.setCanEdit(entity.editAllowed(currentUser, now));
      form.setGearPolicy(form.getCanEdit() ? "none" : "display");
#foreach($field in ${entity.fieldListReadonly})
#if(!$field.pk)
      form.set${field.upperFirstFieldName}(entity.get${field.upperFirstFieldName}());
#end
#end

#if(${entity.hasSysObjCurrency})
      request.setAttribute("currencyCol", CurrencyProcess.getInstance().getCurrencyList());
#end
   }
   
   public static void prepareDefaultValue(HttpServletRequest request, ${entity.formName} form, ${entity.className} entity) throws GTSystemException {
#foreach($field in ${entity.fieldListHasDefault})
#if (${field.isDefaultValueAuto})
      entity.set${field.upperFirstFieldName}(${field.defaultValueAuto});
#else
      entity.set${field.upperFirstFieldName}(${field.defaultValue});
#end
#end
   }
   
#foreach($field in ${entity.fieldListHasOption})
   public static String get${field.upperFirstFieldName}Display(String value, boolean isHtml) {
      String display = SessionUtil.getMessage(get${field.upperFirstFieldName}Key(value));
      return StringUtil.formatStringForDisplay(display, isHtml);
   }
   
   public static String get${field.upperFirstFieldName}Key(String value) {
      return "${entity.resourceKeyPath}${field.i18n.key}." + value;
   }
   
#end
#foreach($field in ${entity.fieldListHasFormat})
   public static String get${field.upperFirstFieldName}Display(${entity.className} ${entity.lowerFirstClassName}) {
#if($field.isNumberFormat)
      return StringUtil.formatNumber(${entity.lowerFirstClassName}.get${field.upperFirstFieldName}(), 2, 2);
#elseif(${field.isTextFormat})
      return ${entity.lowerFirstClassName}.get${field.upperFirstFieldName}();
#else
      return ${entity.lowerFirstClassName}.get${field.upperFirstFieldName}();
#end
   }
   
#end
#foreach($field in ${entity.fieldListSysObj})
#if($field.isSysObjCurrency)
   public static String get${field.upperFirstFieldName}Display(${entity.className} ${entity.lowerFirstClassName}) {
      return ${entity.lowerFirstClassName}.get${field.upperFirstFieldName}() == null ? null : ${entity.lowerFirstClassName}.get${field.upperFirstFieldName}().getName();
   }
#end
   
#end

}