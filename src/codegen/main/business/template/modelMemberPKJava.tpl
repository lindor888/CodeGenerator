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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author ${entity.author}
 */
public class ${entity.memberName}PK implements java.io.Serializable {

   private Integer _${entity.key.fieldName};

   private String _involvement;

   private String _objType;

   private Integer _objId;

   public ${entity.memberName}PK() {
   }

   public ${entity.memberName}PK(Integer ${entity.key.fieldName}, String involvement, String objType, Integer objId) {
      this._${entity.key.fieldName} = ${entity.key.fieldName};
      this._involvement = involvement;
      this._objType = objType;
      this._objId = objId;
   }

   public Integer get${entity.key.upperFirstFieldName}() {
      return this._${entity.key.fieldName};
   }

   public void set${entity.key.upperFirstFieldName}(Integer ${entity.key.fieldName}) {
      this._${entity.key.fieldName} = ${entity.key.fieldName};
   }

   public String getInvolvement() {
      return this._involvement;
   }

   public void setInvolvement(String involvement) {
      this._involvement = involvement;
   }

   public String getObjType() {
      return this._objType;
   }

   public void setObjType(String objType) {
      this._objType = objType;
   }

   public Integer getObjId() {
      return this._objId;
   }

   public void setObjId(Integer objId) {
      this._objId = objId;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      }
      if (other == null) {
         return false;
      }
      if (!(other instanceof ${entity.memberName}PK)) {
         return false;
      }
       ${entity.memberName}PK castOther = ( ${entity.memberName}PK) other;

      return new EqualsBuilder().append(this.get${entity.key.upperFirstFieldName}(), castOther.get${entity.key.upperFirstFieldName}()).append(
         this.getInvolvement(), castOther.getInvolvement()).append(this.getObjType(), castOther.getObjType()).append(
         this.getObjId(), castOther.getObjId()).isEquals();
   }

   public int hashCode() {
      return new HashCodeBuilder().append(this.get${entity.key.upperFirstFieldName}()).append(this.getInvolvement())
         .append(this.getObjType()).append(this.getObjId()).toHashCode();
   }

   public String toString() {
      return new ToStringBuilder(this).append("${entity.key.fieldName}", this.get${entity.key.upperFirstFieldName}()).append("involvement",
         this.getInvolvement()).append("objType", this.getObjType()).append("objId", this.getObjId()).toString();
   }
}
