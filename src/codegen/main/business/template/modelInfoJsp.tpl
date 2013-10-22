<%--
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
 --%>
<html>
<head>
   <%@ page language="Java" contentType="text/html; charset=UTF-8"%>
   <%@ include file="/GTcommonJspHeader2.jsp"%>
   <%@ include file="/css.inc" %>
   <%@ page import="com.gearteks.imanage.constants.DbFieldValue" %>
   <%@ page import="com.gearteks.framework.common.Constants" %>
   <%@ page import="${entity.packagePath}.${entity.className}" %>
   <%@ page import="${entity.controllerPackagePath}.${entity.formName}" %>
   <%@ page import="${entity.controllerPackagePath}.${entity.helperName}" %>
   <TITLE></TITLE>
   <%
      String returnValueKey = StringUtil.nvl(request.getParameter(Constants.RETURN_VALUE_KEY), "");
   	  String titleName = "title3";
   	  if (StringUtil.isEmpty(returnValueKey)) {
         titleName = "title";
   	  }
      ${entity.formName} ${entity.lowerFirstFormName} = (${entity.formName})request.getAttribute("${entity.lowerFirstFormName}");
#if(${entity.hasSysObjCurrency})
      Collection currencyCol = (Collection)request.getAttribute("currencyCol");
#end
      int tdwidthEn1 = 160;
      int tdwidthZh1 = 160;
      int tdwidthEn2 = 186;
      int tdwidthZh2 = 186;
      int tdwidthEn3 = 3;
      int tdwidthZh3 = 3;
      int tdwidthEn4 = 160;
      int tdwidthZh4 = 160;
      int tdwidthEn5 = 186;
      int tdwidthZh5 = 186;
      int tdwidthEn6 = 3;
      int tdwidthZh6 = 3;
      int textAreaWidth = 433;
   %>
   <script>
      var params = window.top.dialogArguments;
      function body_onload() {
         if ('${${entity.lowerFirstFormName}.canEdit}' == 'true') {
            if (window.top.dialogArguments && typeof(parent.hideAllButton) != 'undefined' ) {
               parent.hideAllButton();
               parent.showSaveButton(doSave, true);
               if ('${${entity.lowerFirstFormName}.add}' == 'true') {
                  parent.showCancelButton();
               } else {
                  parent.showCloseButton();
               }
            }
         } else {
            if (window.top.dialogArguments) {
               parent.hideAllButton();
               parent.showCloseButton();
            }
         }
         
         if (window.top.dialogArguments) {
         	gtQuery("div[id=list]").hide();
         }
         
         initMenu('info');
         resizeDiv(divBody, 'coHomeBody', 'tblHead',2,2);
         
         selectFixedEnhancement(document.getElementById("coHomeBody"));
         _errorTable.initHighlight();
        
         Alert4NoSave.setNeedAlert(true);
      }
     
      function doSave(ifClose) {
         var ${entity.lowerFirstFormName} = document.getElementById("${entity.lowerFirstFormName}");
         _errorTable.checkFormError(${entity.lowerFirstFormName});
         if(_errorTable.isHaveError()){           
            return;
         }
         ${entity.lowerFirstFormName}.ifClose.value=ifClose;
         if(!ifClose) {
            if (!confirmPopup('<bean:message key="prompt.confirm.save"/>')) {
               return;
            }
         }
         showOperatingMsg(SAVING);
         ${entity.lowerFirstFormName}.submit();
      }
      
      function doDelete(ifClose) {
         if (!confirmPopup('<bean:message key="prompt.confirm.delete"/>')) {
            return;
         }
         ${entity.lowerFirstFormName}.method.value = "doDelete";
         ${entity.lowerFirstFormName}.ifClose.value=ifClose;
         showOperatingMsg(SAVING);
         ${entity.lowerFirstFormName}.submit();
      }  
   </script>
</head>
<body onLoad="body_onload();" id="coHomeBody" onResize="resizeDiv(divBody, 'coHomeBody', 'tblHead',2,2);" scroll="no">
   <html:form action="${entity.strutsActionPath}/${entity.lowerFirstClassName}.do" style="margin:0px" styleId="${entity.lowerFirstFormName}">
   		<html:hidden property="${entity.key.fieldName}"/>
   		<input type="hidden" id="ifClose" name="ifClose" value="false"/>
   		<input type="hidden" id="method" name="method" value="doSave"/>
   		<input type="hidden" id="isAdd" name="isAdd" value="${${entity.lowerFirstFormName}.add}"/>
   		
   		<gearLayout:topContainer>
     		<gearLayout:topMenu styleId="header"> 
                  <menu:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
                     <div id="list">
                     	<li>
                     		<a onclick="showOperatingMsg(LOADING)" target="_self" title="" 
                     			href="<%=request.getContextPath()%>${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowList"><%=SessionUtil.getMessage("menu.list.list") %></a></li>
                     </div>
                     <div id="info">
                     	<li class="selected">
                     		<a onclick="showOperatingMsg(LOADING)" target="_self" title="" 
                     			href="<%=request.getContextPath()%>${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowInfo&${entity.key.fieldName}=${${entity.lowerFirstFormName}.${entity.key.fieldName}}&isAdd=${${entity.lowerFirstFormName}.add}"><%=SessionUtil.getMessage("menu.info.info") %></a></li>
                     </div>
                  </menu:useMenuDisplayer>
   	 		</gearLayout:topMenu>
   	 		<gearLayout:pageHeader styleId="tblHead" styleClass="<%=titleName%>">
            	<gearLayout:headerTitle>
            		${${entity.lowerFirstClassName}.${entity.bussinessNameField}}
            	</gearLayout:headerTitle>
            	<gearLayout:headerCntrItem>
            		<gearTag:img styleId="btnSave" gearShowText="true" align="center" 
                           src="<%=request.getContextPath() + "/images/icon/add.gif"%>" 
                           titleKey="${entity.resourceKeyPath}${entity.lowerFirstClassName}.label.save" onclick="doSave()" 
                           gearUsePolicy="true" gearPolicy="${${entity.lowerFirstFormName}.gearPolicy}" 
                           border="0" align="center" style="cursor:pointer" styleClass="image"/>
            	</gearLayout:headerCntrItem>
            	<%if (!${entity.lowerFirstFormName}.getAdd()) {%>  
            	<gearLayout:headerCntrItem>
            		<gearTag:img styleId="btnDel" gearShowText="true" align="center" 
                           src="<%=request.getContextPath() + "/images/icon/del.gif"%>" 
                           titleKey="${entity.resourceKeyPath}${entity.lowerFirstClassName}.label.delete" onclick="doDelete(${entity.useDialog})" 
                           gearUsePolicy="true" gearPolicy="${${entity.lowerFirstFormName}.gearPolicy}" 
                           border="0" align="center" style="cursor:pointer" styleClass="image"/>
            	</gearLayout:headerCntrItem>
            	<%}%>
     		</gearLayout:pageHeader>
     		
   		<gearLayout:scrollableContainer styleId="divBody" style="overflow:auto;height:650;">
   		
   		<gearLayout:fieldsetClassic titleKey="${entity.nameResourceKey}" width="100%">
            			<gearLayout:fieldsetBody>
            			
            				 <gearLayout:fieldBlock rowFieldsNum="2" fieldWidths="<%=HtmlTagHelper.getWidth4BlockTag(tdwidthEn1, tdwidthZh1, tdwidthEn2, tdwidthZh2, tdwidthEn3, tdwidthZh3, tdwidthEn4, tdwidthZh4, tdwidthEn5, tdwidthZh5, tdwidthEn6, tdwidthZh6) %>">
            				 
#foreach($field in $entity.showFields)            				 
<gearLayout:field titleKey="${entity.resourceKeyPath}${field.i18n.key}" required="${field.required}">
#if (${field.isSysMember})
<gearTag:member property="${field.fieldName}" styleId="${field.fieldName}" gearPolicy="${field.gearPolicy}" ${field.memberTypeAttr} alt="${field.alt}" value="${field.value}" canShowMuti="true" ${field.styleClassAttr} titleKey="${entity.resourceKeyPath}${field.i18n.key}" value="<%=StringUtil.toHtmlSingleTagValue(${entity.lowerFirstFormName}.get${field.upperFirstFieldName}())%>"/>
#elseif (${field.isSysObj} && ${field.isSysObjCurrency})
<gearTag:select titleKey="${entity.resourceKeyPath}${field.i18n.key}" property="${field.fieldName}" gearUsePolicy="true" gearPolicy="${field.gearPolicy}" styleClass="selectFixed">
<% if (currencyCol != null && !currencyCol.isEmpty()) { %>
   <gearTag:options collection="currencyCol" property="currencyId" labelProperty="currencyName"/>
<% } %>
</gearTag:select>
#elseif (${field.hasOptions} && ${field.readonly})
<%=${entity.className}Helper.getStatusDisplay(${entity.lowerFirstFormName}.get${field.upperFirstFieldName}(), true)%>
#elseif (${field.hasOptions} && !${field.readonly})
<gearTag:select titleKey="${entity.resourceKeyPath}${field.i18n.key}" property="${field.fieldName}" gearUsePolicy="true" gearPolicy="${field.gearPolicy}" styleClass="selectFixed">
#foreach($option in ${field.options})
   <gearTag:option value="${option.value}"><%=SessionUtil.getMessage("${entity.resourceKeyPath}${field.i18n.key}.${option.value}")%></gearTag:option>
#end      
</gearTag:select>
#else
<gearTag:text ${field.maxLengthAttr} ${field.styleClassAttr} property="${field.fieldName}" styleId="${field.fieldName}" gearHaveHidden="true" titleKey="${entity.resourceKeyPath}${field.i18n.key}" alt="${field.alt}" gearUsePolicy="true" gearPolicy="${field.gearPolicy}" />
#end
</gearLayout:field>
                    			
#end
                        	 </gearLayout:fieldBlock>
            			</gearLayout:fieldsetBody>
                   </gearLayout:fieldsetClassic>
   		
   		</gearLayout:scrollableContainer>
     </gearLayout:topContainer>
   </html:form>
</body>
</html>