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
 * $Header:  $
 * $Id:  $
 * $Author:  $
 * $Date:  $
 * $Revision:  $
 *
 *******************************************************************************
 */
package ${entity.controllerPackagePath};

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gearteks.framework.common.Constants;
import com.gearteks.framework.common.HibernateDispatchAction;
import com.gearteks.framework.common.IDGetter;
import com.gearteks.framework.common.PageInfo;
import com.gearteks.framework.common.util.DateUtil;
import com.gearteks.framework.common.util.SessionUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.controller.assist.ActionAssistInterface;
import com.gearteks.framework.controller.assist.BaseAssistAction;
import com.gearteks.framework.model.User;
import ${entity.fullPathClassName};
import com.gearteks.framework.process.ProcessErrors;

/**
 * @author ${entity.author}
 */
public class ${entity.className}Action extends BaseAssistAction {

   public ActionForward doShowList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      PageInfo pageInfo = SessionUtil.getPageInfo(request);
      Collection list = ${entity.processName}.getInstance().get${entity.className}List(null, pageInfo);
      request.setAttribute(Constants.LIST, list); 
      return mapping.findForward(Constants.LIST);
   }
   
   public ActionForward doShowInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      ProcessErrors errors = new ProcessErrors();
      ${entity.formName} entityForm = (${entity.formName})form;
      User currentUser = SessionUtil.getCurrentUser();
      Date now = DateUtil.getDBCurrentTimestamp();
      boolean isAdd = ${entity.helperName}.isAdd(request);
      Integer id = ${entity.helperName}.getId(request, entityForm);
      ${entity.className} entity = new ${entity.className}();
      
      if (isAdd) {
         ${entity.helperName}.prepareDefaultValue(request, entityForm, entity);
      } else {
      	 entity = (${entity.className})${entity.processName}.getInstance().getObject(id); 
      }
      
      entityForm.reset(entity);
      ${entity.helperName}.prepareCommonData(request, entityForm, entity);
      
      boolean ifClose = StringUtil.toBoolean(request.getParameter("ifClose"), false);
      if (ifClose) {
         return this.closeWindow(mapping, form, request, response);
      } else {
         return mapping.findForward(Constants.INFO);
      }
   }
   
   public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      ProcessErrors errors = new ProcessErrors();
      ${entity.formName} entityForm = (${entity.formName})form;
      User currentUser = SessionUtil.getCurrentUser();
      Date now = DateUtil.getDBCurrentTimestamp();
      boolean isAdd = ${entity.helperName}.isAdd(request);
      Integer id = ${entity.helperName}.getId(request, entityForm);
      ${entity.className} entity = null;
      if (isAdd) {
         entity = new ${entity.className}();
         entity.set${entity.key.upperFirstFieldName}(IDGetter.getID(${entity.className}.IDGETTER_IDNAME));
         entityForm.set${entity.key.upperFirstFieldName}(entity.get${entity.key.upperFirstFieldName}());
         entity.setCreater(currentUser);
         entity.setCreateTime(now);
         entity.setTsUser(currentUser);
         entity.setTs(now);
         ${entity.helperName}.prepareDefaultValue(request, entityForm, entity);
      } else {
         entity = (${entity.className})${entity.processName}.getInstance().getObject(id);
         entity.setTsUser(currentUser);
         entity.setTs(now);
      }
      
      entityForm.copyTo(errors, entity);

      if (errors.isHaveErrors()) {
         saveErrors(request, errors);
         ${entity.helperName}.prepareCommonData(request, entityForm, entity);
         return mapping.findForward(Constants.INFO);
      }
      
      ${entity.helperName}.setAdd(request, false);
      if (isAdd) {
         entity.add();
      } else {
         entity.update();
      }
		#foreach($field in $entity.sysMemberFields)
      	${entity.className}Process.getInstance().saveMember("${field.sysMember.involvement}", id, entity.get${field.upperFirstFieldName}());
		#end   
      
      return doShowInfo(mapping, form, request, response);
   }
   
   public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      ${entity.formName} entityForm = (${entity.formName})form;
      Integer id = ${entity.helperName}.getId(request, entityForm);
      ${entity.className} entity = (${entity.className})${entity.className}Process.getInstance().getObject(id);
      if (entity != null) {
         entity.delete();
      }
         
	  boolean ifClose = StringUtil.toBoolean(request.getParameter("ifClose"), false);
      if (ifClose) {
         return this.closeWindow(mapping, form, request, response);
      } else {
         return doShowList(mapping, form, request, response);
      }
   }
   
   #if($entity.genReport)
   public ActionForward doShowReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
      
      ${entity.className}Form cf = (${entity.className}Form)form;
      PageInfo pageInfo = SessionUtil.getPageInfo(request);
      Collection list = ${entity.processName}.getInstance().get${entity.className}List(null, pageInfo);
      request.setAttribute(Constants.LIST, list); 
      
      boolean isShowExcel = StringUtil.toBoolean(cf.getExcel(), false);
      if (isShowExcel) {
	      ${entity.exportProcessName}.getInstance().export2ExcelInBatch(request, response, form, pageInfo, 
	      	new String[]{SessionUtil.getMessage("${entity.resourceKeyPath}${entity.lowerFirstClassName}.name")}, null, 
	      	${entity.exportProcessName}.EXPORT_IDENTITY);
      	return null;
      }
      
      return mapping.findForward(Constants.REPORT);
   }
   #end
   
   public ActionAssistInterface getActionAssist() {
      return null;
   }

   public String getDisPlayKeyStr() {
      return null;
   }
}
