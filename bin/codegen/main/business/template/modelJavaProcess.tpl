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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.gearteks.framework.common.PageInfo;
import com.gearteks.framework.common.exception.GTSystemException;
import com.gearteks.framework.common.util.CommonUtil;
import com.gearteks.framework.common.util.StringUtil;
import com.gearteks.framework.process.assist.BusinessAssistProcess;
import ${entity.fullPathClassName};

/**
 * @author ${entity.author}
 */
public class ${entity.processName} extends BusinessAssistProcess {

   private static ${entity.processName} _instance = new ${entity.processName}();

   public static ${entity.processName} getInstance() {
      return _instance;
   }

   public ${entity.processName}() {
   }

   public Collection<${entity.className}> get${entity.className}List(Map paramMap, PageInfo pageInfo) throws GTSystemException {
      StringBuilder hql = new StringBuilder();
      List paramList = new ArrayList();

      hql.append("select ${entity.shortName} from ${entity.className} ${entity.shortName}");
 	  hql.append(" order by ");
      if (pageInfo != null && !StringUtil.isEmpty(pageInfo.getExtInfo())) {
         hql.append(pageInfo.getExtInfo() + " " + pageInfo.getOrderBy() + ",");
      }
      hql.append(" ${entity.shortName}.${entity.key.fieldName}");
      return getQueryData(hql.toString(), paramList, pageInfo);
   }
  
   public String getKeyStr() {
      return ${entity.className}.IDGETTER_IDNAME;
   }

   public Class getModelClass() {
      return ${entity.className}.class;
   }
   
   public void saveMember(String involvement, Integer ${entity.key.fieldName}, Collection newCol) throws GTSystemException {
      Collection oldCol = getMember(${entity.key.fieldName}, involvement);
      CommonUtil.saveSubTable(oldCol, newCol, false);
   }
   
   public Collection getMember(Integer ${entity.key.fieldName}, String involvement) throws GTSystemException {
      StringBuilder hql = new StringBuilder();
      List paramList = new ArrayList();
      
      hql.append(" select ${entity.memberShortName} from ${entity.memberName} ${entity.memberShortName}");
      hql.append(" where ${entity.memberShortName}.comp_id.${entity.key.fieldName} = ? and ${entity.memberShortName}.comp_id.involvement = ?");
      paramList.add(${entity.key.fieldName});
      paramList.add(involvement);
      
      return getQueryData(hql.toString(), paramList);
   }
}
