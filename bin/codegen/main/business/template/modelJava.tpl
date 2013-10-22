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
package ${entity.packagePath};

import java.io.Serializable;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.common.util.SessionUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.model.Currency;
import com.gearteks.framework.model.User;
import com.gearteks.framework.model.assist.BusinessBaseModel;
import com.gearteks.framework.model.assist.CreaterInterFace;
import com.gearteks.framework.model.assist.LastModifyInterFace;
import com.gearteks.framework.object.SecuredObject;
import com.gearteks.framework.security.rbac.AclFactory;
import com.gearteks.framework.common.IDGetter;

/**
 * @author ${entity.author}
 */
public class ${entity.className} extends BusinessBaseModel implements java.io.Serializable, CreaterInterFace, LastModifyInterFace, SecuredObject {

   public static final String ${entity.objTypeConstant} = "${entity.className}";
   
   public static final String IDGETTER_IDNAME = IDGetter.${entity.idGetter};

#foreach($field in ${entity.sysMemberFields})
   public static final String ${field.constantMemberName} = "${field.sysMember.involvement}";
   
#end
#foreach($field in $entity.fieldListExclueSys)
   private $field.fieldType _$field.fieldName;
   
#end
#foreach($field in $entity.sysMemberFields)
   private $field.fieldType _$field.fieldName = new HashSet();
   
#end
#foreach($field in $entity.fieldListSysObj)
   private $field.sysObjClassName _$field.fieldName;
   
#end
#if ($entity.sysMemberFields)
   private Set _${entity.lowerFirstMemberName} = new HashSet();
	
#end
   private User _creater;

   private Date _createTime;

   private Date _ts;
   
   private User _tsUser;

#foreach($field in $entity.fieldListExclueSys)
   public void set${field.upperFirstFieldName}(${field.fieldType} ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public $field.fieldType get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
#foreach($field in $entity.sysMemberFields)
   public void set${field.upperFirstFieldName}(${field.fieldType} ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public $field.fieldType get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
#foreach($field in $entity.fieldListSysObj)
   public void set${field.upperFirstFieldName}(${field.sysObjClassName} ${field.fieldName}) {
      _$field.fieldName = $field.fieldName;
   }

   public $field.sysObjClassName get${field.upperFirstFieldName}() {
      return _$field.fieldName;
   }
   
#end
#if ($entity.sysMemberFields)
   public void set${entity.memberName}(Set ${entity.lowerFirstMemberName}) {
      _$entity.lowerFirstMemberName = $entity.lowerFirstMemberName;
   }

   public Set get${entity.memberName}() {
      return _$entity.lowerFirstMemberName;
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
   
   public String getName() {
      return _${entity.bussinessNameField};
   }

   public void setName(String name) {
      _${entity.bussinessNameField} = name;
   }

   public Serializable getKey() {
      return _${entity.key.fieldName};
   }

   public void setKey(Serializable key) {
      _${entity.key.fieldName} = (${entity.key.fieldType}) key;
   }

   public boolean editAllowed(User operateUser, Date now) throws GTSystemException {
      boolean editAllowed = AclFactory.getAclTagChecker().isAllowed(SessionUtil.getRequest(), "${entity.lowerFirstClassName}", "doSave");
      return editAllowed;
   }

   public boolean readAllowed(User operateUser, Date now) throws GTSystemException {
      boolean readAllowed = AclFactory.getAclTagChecker().isAllowed(SessionUtil.getRequest(), "${entity.lowerFirstClassName}", "doShowList");
      return readAllowed;
   }

   public String getType() {
      // TODO Auto-generated method stub
      return null;
   }
}
