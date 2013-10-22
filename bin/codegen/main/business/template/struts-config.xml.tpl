<form-bean name="${entity.lowerFirstFormName}" type="${entity.controllerPackagePath}.${entity.formName}"/>

      <action path="${entity.strutsActionPath}/${entity.lowerFirstClassName}" type="${entity.controllerPackagePath}.${entity.actionName}" name="${entity.lowerFirstFormName}" scope="request" parameter="method">
         <forward name="list" path="${entity.jspPath}/${entity.lowerFirstClassName}List.jsp"/>
         <forward name="info" path="${entity.jspPath}/${entity.lowerFirstClassName}Info.jsp"/>
         #if($entity.genReport)
         	<forward name="report" path="${entity.jspPath}/${entity.lowerFirstClassName}Report.jsp"/>
         #end
      </action>