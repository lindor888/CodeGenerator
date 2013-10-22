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
   <%@ page contentType="text/html; charset=UTF-8"%>
   <%@ include file="/GTcommonJspHeader2.jsp"%>
   <%@ include file="/css.inc" %>
   <%@ page import="com.gearteks.framework.common.util.DateUtil" %>
   <%@ page import="com.gearteks.framework.process.assist.DisplayObjAssistProcess" %>
   <%@ page import="${entity.packagePath}.${entity.className}" %>
   <%@ page import="${entity.controllerPackagePath}.${entity.formName}" %>
   <%@ page import="${entity.controllerPackagePath}.${entity.helperName}" %>
   
   <title></title>
   <%
   	  int pageSize = Integer.parseInt(SystemConfig.get("GT.iManage.pageSize"));
   	  String returnValueKey = StringUtil.nvl(request.getParameter(Constants.RETURN_VALUE_KEY), "");
   	  String titleName = "title3";
   	  if (StringUtil.isEmpty(returnValueKey)) {
         titleName = "title";
   	  }
      ${entity.formName} entityForm = (${entity.formName})request.getAttribute("${entity.lowerFirstFormName}");
      int tdwidthEn1 = 190;
	   int tdwidthZh1 = 190;
	   int tdwidthEn2 = 220;
	   int tdwidthZh2 = 220;
	   int tdwidthEn3 = 3;
	   int tdwidthZh3 = 3;
	   int tdwidthEn4 = 164;
	   int tdwidthZh4 = 164;
	   int tdwidthEn5 = 300;
	   int tdwidthZh5 = 300;
	   int tdwidthEn6 = 3;
	   int tdwidthZh6 = 3;
	   int textAreaWidth =  558;
   %>
   <script language ="javascript">
      var _scrollTable;
      
      function doRefresh() {
      	if (checkError()) {
       		return;
       	}
      	showOperatingMsg(LOADING);
         var entityForm = document.getElementById("${entity.lowerFirstFormName}");
         entityForm.method.value = "doShowList";
         entityForm.submit();
      }
      
      function checkError() {
      	_errorTable.clearError();
         _errorTable.checkFormError(document.forms[0]);
      	if(_errorTable.isHaveError()){
          	ScrollTable.scroll(_scrollTable);           
            return true;
         }
         return false;
      }
      
      function body_onload() {
         if (window.top.dialogArguments && typeof(parent.hideAllButton) != 'undefined' ) {
            parent.hideAllButton();
            parent.showCloseButton();
         }
            
         initMenu("list");
         
         <% if (entityForm.isShowSearch()) { %>
              showSearch(true);
         <% } else { %>
              showSearch(false);
         <% } %>
         
         myResize();
         registerScrollableTitle();
         fixedTableWidth('${entity.lowerFirstFormName}Tbl', 'divBody');
         if (typeof menuScroll != 'undefined') { 
            menuScroll.initMenuScroll(); 
         }
      }
      
      //多数当出现横向滚动条不出现的时候，是因为垂直方向上多了一层table引起的，这时候需要调节第4个参数如：resizeDiv(divBody, 'bodyHome', 'tblHead', 22, 0);
      function myResize() {
         if (typeof divBody != 'undefined') { 
            if (window.top.dialogArguments) {
               resizeDiv(divBody, 'bodyHome', 'tblHead', 2, 2);
            } else {
               resizeDiv(divBody, 'bodyHome', 'tblHead', 2, 0);
            }
         }
      }
          
      function registerScrollableTitle() {
         var oDivBody = document.getElementById('divBody');
         var oTbl = document.getElementById("${entity.lowerFirstFormName}Tbl");
         if (oTbl) {
            _scrollTable = new ScrollTable(oDivBody, oTbl, 1, 1, true, true, true);
         }
      }
      function showSearch(isShow) {
         var tbShowSearch = document.getElementById("tbShowSearch");
         var tbSearch = document.getElementById("tbSearch");
         var tbSearchLine = document.getElementById("tbSearchLine");
         var showSearch = document.getElementById("showSearch");
         if (tbShowSearch) {
            tbShowSearch.style.display = isShow ? "none" : "";
         }
         if (tbSearch) {
            tbSearch.style.display = isShow ? "" : "none";
         }
         if (tbSearchLine) {
            tbSearchLine.style.display = isShow ? "" : "none";
         }
         showSearch.value = isShow ? "true" : "false";
         doScrollTable();
      }
      
      function doScrollTable() {
         if (typeof _scrollTable != 'undefined') {
            _scrollTable.scroll();
         }
      }
      
      function doAdd(isDialog) {
      	if (isDialog) {
      	 	${entity.lowerFirstClassName}Dialog(null, "<%=SessionUtil.getMessage("${entity.resourceKeyPath}${entity.lowerFirstClassName}.name")%>", true, doRefresh, null);
      	} else {
         var pageUrl = webContext + "${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowInfo&isAdd=true";
         window.location.href = pageUrl;
        } 
      }
      
      function doDelete(id) {
         if (!confirmPopup('<bean:message key="prompt.confirm.delete"/>')) {
            return;
         }
         var entityForm = document.getElementById("${entity.lowerFirstFormName}");
         if (id) {
            entityForm.${entity.key.fieldName}.value = id;
            entityForm.method.value = "doDelete";
         } else {
            return;
         }
         entityForm.submit();
      }
      
      function doUpdate(id, isDialog) {
      	 if (isDialog) {
      	  	${entity.lowerFirstClassName}Dialog(id, "<%=SessionUtil.getMessage("${entity.resourceKeyPath}${entity.lowerFirstClassName}.name")%>", true, doRefresh, null);
      	 } else {
         	var pageUrl = webContext + "${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowInfo&isAdd=false&${entity.key.fieldName}=" + id;
		 	window.location.href = pageUrl;
		 }
      }
      
      function openSelectDialog(callbackFuncName) {
      
      	#foreach($field in ${entity.showFields})
				#if ($field.isSysMember)
					if ('add${field.upperFirstFieldName}' == callbackFuncName) {
						#if (${field.sysMember.memberType} == 'User')
							userDialog(true, 'All', '', add${field.upperFirstFieldName}, true, 'User', '<%=SessionUtil.getMessage("prompt.title.userSelect")%>', null, null, null, 'licenseType=All');					
						#elseif (${field.sysMember.memberType} == 'Group')
							companySelectByType(add${field.upperFirstFieldName}, false, 'Company', '<bean:message key="prompt.title.companySelect.group"/>', true, 'false');
						#elseif (${field.sysMember.memberType} == 'Company')
							companySelectByType(add${field.upperFirstFieldName}, false, 'Company', '<bean:message key="prompt.title.companySelect.company"/>',true);
						#end
						return;
					}
				#end
			#end
      }
      
      #foreach($field in ${entity.showFields})
			#if ($field.isSysMember)
				function add${field.upperFirstFieldName}(idField, nameField) {
			      var obj = document.getElementById("${field.fieldName}");
			      if (obj==null) obj=document.getElementsByName("${field.fieldName}")[0];
			      var oldValue = obj.value;
			      obj.value = addDiff(oldValue,nameField);                          
			      return false;
			   }
			#end
		#end
		
   </script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" id="bodyHome" onResize="myResize(); if (typeof showMenuScroll != 'undefined') { showMenuScroll(); }" onload="body_onload(); "  scroll="no">
   <html:form action="${entity.strutsActionPath}/${entity.lowerFirstClassName}.do" styleId="${entity.lowerFirstFormName}">
   <input type="hidden" id="method" name="method" value="doShowList"/>
   <input type="hidden" id="${entity.key.fieldName}" name="${entity.key.fieldName}" value=""/>
   <html:hidden property="showSearch" styleId="showSearch"/>

   <gearLayout:topContainer>
     		<gearLayout:topMenu styleId="header"> 
                  <menu:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
                     <div id="list">
                     	<li class="selected">
                     		<a onclick="showOperatingMsg(LOADING)" target="_self" title="" 
                     			href="<%=request.getContextPath()%>${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowList"><%=SessionUtil.getMessage("menu.list.list") %></a></li>
                     </div>
                  </menu:useMenuDisplayer>
   	 		</gearLayout:topMenu>
   	 		<gearLayout:pageHeader styleId="tblHead" styleClass="<%=titleName%>">
            	<gearLayout:headerTitle>
            	</gearLayout:headerTitle>
            	<gearLayout:headerCntrItem>
            		<gearTag:img styleId="btnAdd" gearShowText="true" align="center" 
                           src="<%=request.getContextPath() + "/images/icon/add.gif"%>" 
                           titleKey="${entity.resourceKeyPath}${entity.lowerFirstClassName}.label.add" onclick="doAdd(${entity.useDialog})" 
                           gearUsePolicy="true" gearPolicy="${${entity.lowerFirstFormName}.gearPolicy}" 
                           border="0" align="center" style="cursor:pointer" styleClass="image"/>
            	</gearLayout:headerCntrItem>
            	<gearLayout:headerCntrItem>
            		<gearTag:img styleId="btnRefresh" gearShowText="true" align="center" 
                           src="<%=request.getContextPath() + "/images/icon/refresh.gif"%>" 
                           titleKey="name.button.refresh" onclick="doRefresh()" 
                           gearUsePolicy="true" gearPolicy="none" border="0" align="center" style="cursor:pointer" styleClass="image"/>
            	</gearLayout:headerCntrItem>
            	
     		</gearLayout:pageHeader>

    	<gearLayout:scrollableContainer styleId="divBody" style="overflow:auto;height:650;">
    			<gearLayout:searchBlock showSearchLink="true" searchMethod="doRefresh();" beanName="${entity.lowerFirstFormName}"
	     		fieldWidths="<%=HtmlTagHelper.getWidth4BlockTag(tdwidthEn1, tdwidthZh1, tdwidthEn2, tdwidthZh2, tdwidthEn3, tdwidthZh3, tdwidthEn4, tdwidthZh4, tdwidthEn5, tdwidthZh5, tdwidthEn6, tdwidthZh6, null, null, null, null, null, null)%>">
			 
			   #foreach($field in ${entity.showInSearchFields})
			   	#if (!$field.dateField)
			   	<gearLayout:field titleKey="${entity.resourceKeyPath}${field.i18n.key}">  
			   	#end
				#if ($field.isSysMember)
					<gearTag:text  styleClass="text"  property="${field.fieldName}" styleId="${field.fieldName}" gearPolicy="none" 
						title="<%=TagUtils.getInstance().message(pageContext,null,null,"${entity.resourceKeyPath}${field.i18n.key}",null) %>" />
		          <img style="cursor: pointer;" align="center"
		              onClick="openSelectDialog('add${field.upperFirstFieldName}')"
		              title="<%= SessionUtil.getMessage("${entity.resourceKeyPath}${field.i18n.key}")%>"
		              src="<%=request.getContextPath()%>${field.memberIcon}">
		            
				#elseif ($field.isSysObj || $field.hasOptions)
				    <gearTag:select property="${field.fieldName}" styleId="${field.fieldName}" styleClass="selectFixed"
	              		gearHaveHidden="true" gearPolicy="none">
	              	<gearTag:option value="">&nbsp;</gearTag:option>
		            <logic:iterate scope="request" name="${field.optionName}" id="option"
		                 type="com.gearteks.framework.model.assist.OptoinInterFace">
		                 <gearTag:option value="<%=option.getOptionKey()%>"><%=option.getOptionName()%></gearTag:option>
		            </logic:iterate>
	           	</gearTag:select>
	           	
				#elseif ($field.dateField)
					<gearLayout:field titleKey="${entity.resourceKeyPath}${field.i18n.key}.from">  
						<gear:date property="from${field.upperFirstFieldName}" styleId="from${field.upperFirstFieldName}" 
							title="<%=SessionUtil.getMessage(request, "${entity.resourceKeyPath}${field.i18n.key}.from")%>" alt="date" styleClass="text" 
							onblur="refreshDate(new Array(this, gtDateCalendar))"/>
						&nbsp;<img title="<bean:message key="name.button.selectDate"/>" 
						src="<%=request.getContextPath()%>/images/icon/calendar.jpg" width="20" height="16" style="Cursor:pointer" 
						onclick="popupCalendar(this, from${field.upperFirstFieldName}, true, true)">
					</gearLayout:field>
					
					<gearLayout:field titleKey="${entity.resourceKeyPath}${field.i18n.key}.to">  
						<gear:date property="to${field.upperFirstFieldName}" styleId="to${field.upperFirstFieldName}" 
							title="<%=SessionUtil.getMessage(request, "${entity.resourceKeyPath}${field.i18n.key}.to")%>" alt="date" styleClass="text" 
							onblur="refreshDate(new Array(this, gtDateCalendar))"/>
						&nbsp;<img title="<bean:message key="name.button.selectDate"/>" 
						src="<%=request.getContextPath()%>/images/icon/calendar.jpg" width="20" height="16" style="Cursor:pointer" 
						onclick="popupCalendar(this, to${field.upperFirstFieldName}, true, true)">
						
				#elseif ($field.decimalField)
					<gearTag:text style="text-align:right;" alt="float" styleClass="text"  property="${field.fieldName}" styleId="${field.fieldName}"
						onblur="formatValue(this, 0);" onkeypress="allowPositiveCurrency(this, 2, 999999999, event);"  
						gearPolicy="none" title="<%=TagUtils.getInstance().message(pageContext,null,null,"${entity.resourceKeyPath}${field.i18n.key}",null) %>" />
						
				#elseif ($field.integerField)
					<gearTag:text style="text-align:right;" alt="int" styleClass="text"  property="${field.fieldName}" styleId="${field.fieldName}"
						onblur="formatValue(this, 0);" onkeypress="allowPositiveCurrency(this, 0, 999999999, event);"  
						gearPolicy="none" title="<%=TagUtils.getInstance().message(pageContext,null,null,"${entity.resourceKeyPath}${field.i18n.key}",null) %>" />
				#else 
					<gearTag:text  styleClass="text"  property="${field.fieldName}" styleId="${field.fieldName}"  
						gearPolicy="none" title="<%=TagUtils.getInstance().message(pageContext,null,null,"${entity.resourceKeyPath}${field.i18n.key}",null) %>" />
						
				#end
				
					</gearLayout:field>                      
				#end
     		</gearLayout:searchBlock>
     		
    			<gearTable:table name="requestScope.list" id="item"  pagesize="<%=pageSize %>"
               requestURI="<%="${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowList"%>"
               htmlId="${entity.lowerFirstFormName}Tbl" styleClass="dragTableBorder1" resizable="true"
					width="100%" border="0" cellspacing="0" cellpadding="0" defaultsort="5" needPagination="true">
                          <%
                             ${entity.className} entity = (${entity.className})item;
                          %>
#foreach($field in ${entity.showInListFields})
                          <gearTable:column titleKey="${entity.resourceKeyPath}${field.i18n.key}" width="100" 
                              sortable="false" headerClass="sortable" colExtInfo="" headerId="${field.fieldName}" nowrap="true" colExtInfo="${entity.shortName}.${field.fieldName}">
#if ($field.isBussinessNameField)
	#if ($entity.useDialog)
							<gear:link href="<%="javascript: doUpdate(" + entity.get${entity.key.upperFirstFieldName}() + ", true);" %>"><%=StringUtil.formatObjForDisplay(entity.get${field.upperFirstFieldName}()) %></gear:link>
	#else
                            <gear:link href="<%="javascript: doUpdate(" + entity.get${entity.key.upperFirstFieldName}() + ");" %>"><%=StringUtil.formatObjForDisplay(entity.get${field.upperFirstFieldName}()) %></gear:link>
    #end                        
#elseif ($field.isSysMember)
                            <%=DisplayObjAssistProcess.getDefaultToolTips(entity.get${field.upperFirstFieldName}(), request)%>
#elseif ($field.isSysObj)
                            <%=StringUtil.formatObjForDisplay(${entity.helperName}.get${field.upperFirstFieldName}Display(entity))%>
#elseif ($field.hasOptions)
                            <%=${entity.helperName}.get${field.upperFirstFieldName}Display(entity.get${field.upperFirstFieldName}() ,true)%>
#elseif (${field.hasFormat})
                            <%=StringUtil.formatObjForDisplay(${entity.helperName}.get${field.upperFirstFieldName}Display(entity))%>
#else
                            <%=StringUtil.formatObjForDisplay(entity.get${field.upperFirstFieldName}())%>
#end
                          </gearTable:column>
                          
#end
                </gearTable:table>                  
    	</gearLayout:scrollableContainer>
    </gearLayout:topContainer>
   
   </html:form>
</body>
</html>