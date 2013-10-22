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
package ${entity.packagePath};
 
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.gearteks.framework.common.Constants;
import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.model.Company;
import com.gearteks.framework.model.Party;
import com.gearteks.framework.model.User;
import com.gearteks.framework.model.assist.DisplayObjInterFace;
import com.gearteks.framework.object.BusinessObject;
import com.gearteks.framework.object.DbObject;
import com.gearteks.framework.process.CompanyProcess;
import com.gearteks.framework.process.PartyProcess;
import com.gearteks.framework.process.UserProcess;


/**
 * @author ${entity.author}
 */
public class ${entity.memberName} extends DbObject implements Comparable, Serializable, DisplayObjInterFace {

   private ${entity.memberName}PK _comp_id;

   private ${entity.className} _${entity.lowerFirstClassName};

   public ${entity.memberName}() {
   }

   public ${entity.memberName}(${entity.memberName}PK comp_id, ${entity.className} ${entity.lowerFirstClassName}) {
      this._comp_id = comp_id;
      this._${entity.lowerFirstClassName} = $entity.lowerFirstClassName;
   }

   public ${entity.memberName}PK getComp_id() {
      return this._comp_id;
   }

   public void setComp_id(${entity.memberName}PK comp_id) {
      this._comp_id = comp_id;
   }

   public ${entity.className} get${entity.className}() {
      return this._$entity.lowerFirstClassName;
   }

   public void set${entity.className}(${entity.className} $entity.lowerFirstClassName) {
      this._$entity.lowerFirstClassName = $entity.lowerFirstClassName;
   }

   public int compareTo(Object o) {
      try {
         BusinessObject otherMem = ((${entity.memberName}) o).getDisplayObj();
         BusinessObject thisMem = this.getDisplayObj();
         if (thisMem instanceof Comparable && otherMem instanceof Comparable) {
            return ((Comparable) thisMem).compareTo(((Comparable) otherMem));
         }
         return thisMem.getName().compareTo(otherMem.getName());
      } catch (Exception e) {
         return 0;
      }
   }

   public BusinessObject getDisplayObj() throws GTSystemException {
      Integer thisId = this.getComp_id().getObjId();
      String type = this.getComp_id().getObjType();

      if (type.equals(Constants.OBJ_TYPE_COMPANY)) {
         return CompanyProcess.getInstance().getCompany(thisId);
      } else if (type.equals(Constants.OBJ_TYPE_PARTY)) {
         return PartyProcess.getInstance().getParty(thisId);
      } else if (type.equals(Constants.OBJ_TYPE_USER)) {
         return UserProcess.getInstance().getUser(thisId);
      } else if (type.equals("BusinessObject")) {
         return BaseProcess.getInstance().getObject(thisId);
      } else {
         return null;
      }
   }

   public void setDisplayType(String displayType) {
      // TODO Auto-generated method stub
   }

   public boolean add() throws GTSystemException {
      ${entity.className} obj = this.get${entity.className}();
      if (obj != null) {
         this.getComp_id().set${entity.key.upperFirstFieldName}(obj.get${entity.key.upperFirstFieldName}());
      }
      return super.add();
   }

   public String toString() {
      return new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
   }

   public boolean equals(Object other) {
      if ((this == other)) {
         return true;
      }

      if (!(other instanceof ${entity.memberName})) {
         return false;
      }

      ${entity.memberName} castOther = (${entity.memberName}) other;

      return new EqualsBuilder().append(this.getComp_id(), castOther.getComp_id()).isEquals();
   }

   public int hashCode() {
      return new HashCodeBuilder().append(getComp_id()).toHashCode();
   }

   public ${entity.memberName} copyTo() {
      ${entity.memberName}PK pk = new ${entity.memberName}PK();
      pk.set${entity.key.upperFirstFieldName}(this.getComp_id().get${entity.key.upperFirstFieldName}());
      pk.setInvolvement(this.getComp_id().getInvolvement());
      pk.setObjId(this.getComp_id().getObjId());
      pk.setObjType(this.getComp_id().getObjType());
      ${entity.memberName} qm = new ${entity.memberName}(pk, this.get${entity.className}());
      return qm;
   }
   
   public static ${entity.memberName} toMember(String involvement, BusinessObject bo, ${entity.className} ${entity.lowerFirstClassName}) {
      String objType = null;
      if (bo instanceof User) {
         objType = Constants.OBJ_TYPE_USER;
      } else if (bo instanceof Party) {
         objType = Constants.OBJ_TYPE_PARTY;
      } else if (bo instanceof Company) {
         objType = Constants.OBJ_TYPE_COMPANY;
      } else if (type.equals("BusinessObject")) {
         objType = "BusinessObject";
      }
      if (objType != null) {
         ${entity.memberName}PK id = new ${entity.memberName}PK(${entity.lowerFirstClassName}.get${entity.key.upperFirstFieldName}(), involvement, objType, bo.getId());
         return new ${entity.memberName}(id, ${entity.lowerFirstClassName});
      } else {
         return null;
      }
   }

}
