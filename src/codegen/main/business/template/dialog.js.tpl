function ${entity.lowerFirstClassName}Dialog(${entity.key.fieldName}, title, isShowModaless, callbackFunc, callbackFuncParam) {
   var isAdd = ${entity.key.fieldName} == null;
   var pageURL = webContext + "${entity.jspPath}/${entity.lowerFirstClassName}.do?method=doShowInfo&isAdd=" + isAdd;
   if(${entity.key.fieldName}){
      pageURL += "&${entity.key.fieldName}=" + ${entity.key.fieldName};
   }
   var width = 730;
   var height = 370;
   var ret = popupSaveAndCloseDialog(pageURL, null, title, width, height, null, null, null, true);
   if (callbackFunc && typeof callbackFunc == 'function') {
       if (window.showModalDialog && !isShowModaless) {
          callbackFunc(ret, callbackFuncParam);
       } else {
          ret._callbackFunction = callbackFunc;
          ret._callbackFunctionParam = callbackFuncParam;
       }
   } else {
      return ret;
   }
}