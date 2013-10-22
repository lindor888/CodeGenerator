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
import java.util.Date;
import java.util.Iterator;

import com.gearteks.framework.common.BaseValidatorActionForm;
import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.common.util.CommonUtil;
import com.gearteks.framework.common.util.SessionUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.model.User;
import com.gearteks.framework.model.assist.CreaterInterFace;
import com.gearteks.framework.model.assist.LastModifyInterFace;
import com.gearteks.framework.object.BusinessObject;
import com.gearteks.framework.process.ProcessErrors;
import com.gearteks.framework.process.assist.DisplayObjAssistProcess;
import com.gearteks.framework.process.assist.MatchObjectAssist;
import com.gearteks.framework.process.CurrencyProcess;

/**
 * @author ${entity.author}
 */
public class ${entity.className}Form extends BaseValidatorActionForm implements CreaterInterFace, LastModifyInterFace {

#foreach($field in $entity.fieldListExclueSys)
   private $field.fieldType _$field.fieldName;
   
#end
#foreach($field in $entity.sysMemberFields)
   private String _$field.fieldName;
   
#end
#foreach($field in $entity.fieldListSysObj)
   private Integer _$field.fieldName;
   
#end
   private User _creater;

   private Date _createTime;

   private Date _ts;
   
   private User _tsUser;
   
   private boolean _canEdit = true;
   
   private boolean _showSearch;
   
   private String _gearPolicy = "none";
   
   public String getGearPolicy() {
      return _gearPolicy;
   }

   public void setGearPolicy(String gearPolicy) {
      _gearPolicy = gearPolicy;
   }
   
   public boolean getAdd() {
      return ${entity.helperName}.isAdd(SessionUtil.getRequest());
   }

#foreach($field in $entity.fieldListExclueSys)
   public void set${field.upperFirstFieldName}(${field.fieldType} ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public ${field.fieldType} get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
#foreach($field in $entity.sysMemberFields)
   public void set${field.upperFirstFieldName}(String ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public String get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
#foreach($field in $entity.fieldListSysObj)
   public void set${field.upperFirstFieldName}(Integer ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public Integer get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
   public Date getCreateTime() {
      return _createTime;
   }

   public void setCreateTime(Date createTime) {
      _createTime = createTime;
   }
   
   public User getCreater() {
      return _creater;
   }

   public void setCreater(User creater) {
      _creater = creater; 
   }
   
   public Date getTs() {
      return _ts;
   }

   public void setTs(Date ts) {
      _ts = ts;
   }
   
   public User getTsUser() {
      return _tsUser;
   }

   public void setTsUser(User tsUser) {
      _tsUser = tsUser; 
   }
   
   public boolean getCanEdit() {
      return _canEdit;
   }
   
   public void setCanEdit(boolean canEdit) {
      _canEdit = canEdit; 
   }
   
   public boolean isShowSearch() {
      return _showSearch;
   }

   public void setShowSearch(boolean showSearch) {
      _showSearch = showSearch;
   }
   
   public void copyTo(ProcessErrors errors, ${entity.className} entity) throws GTSystemException {
#foreach($field in $entity.fieldListExclueSys)
#if (!${field.pk} && !${field.readonly})
      entity.set${field.upperFirstFieldName}(this.get${field.upperFirstFieldName}());
#end
#end
#foreach($field in $entity.fieldListSysObj)
      entity.set${field.upperFirstFieldName}(${field.sysObjLoadCode});
#end

#foreach($field in $entity.sysMemberFields)
		entity.set${field.upperFirstFieldName}(new HashSet());
      if (!StringUtil.isEmpty(this.get${field.upperFirstFieldName}())) {
         ProcessErrors tempErrors = new ProcessErrors();
         Collection memberCol = MatchObjectAssist.${field.matchMethod}(this.get${field.upperFirstFieldName}(), tempErrors, null, null,
            SessionUtil.getMessage("${field.i18n.key}"));
         if (tempErrors.isHaveErrors()) {
            errors.addErrors(tempErrors);
            CommonUtil.addFieldNamesErrorRelated("${field.fieldName}");
         }
         if (memberCol != null && !memberCol.isEmpty()) {
            for (Iterator it = memberCol.iterator(); it.hasNext();) {
               BusinessObject bo = (BusinessObject) it.next();
               ${entity.memberName} member = ${entity.memberName}.toMember(${entity.className}.${field.constantMemberName}, bo, entity);
               if (member != null) {
                  entity.get${field.upperFirstFieldName}().add(member);
               }
            }
            entity.get${entity.memberName}().addAll(entity.get${field.upperFirstFieldName}());
         }
      }
#end
   }
   
   public void reset(${entity.className} obj) throws GTSystemException {
      if (obj == null) {
         return;
      }

#foreach($field in $entity.fieldListExclueSys)
      this.set${field.upperFirstFieldName}(obj.get${field.upperFirstFieldName}());
#end
    
#foreach($field in $entity.sysMemberFields)
      this.set${field.upperFirstFieldName}(DisplayObjAssistProcess.getDisplayNames(obj.get${field.upperFirstFieldName}()));
#end
#foreach($field in $entity.fieldListSysObj)
      this.set${field.upperFirstFieldName}(obj.get${field.upperFirstFieldName}() == null ? null : obj.get${field.upperFirstFieldName}().getId());
#end
   }
}
