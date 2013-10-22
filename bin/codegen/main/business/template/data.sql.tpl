#if($entity.genCurd)
INSERT into id_counter values (0, '$entity.idCounter');
insert into func_url(FuncId, ActionName, MethodName) values(${entity.viewFuncId}, '${entity.lowerFirstClassName}', 'doShowInfo');
insert into func_url(FuncId, ActionName, MethodName) values(${entity.viewFuncId}, '${entity.lowerFirstClassName}', 'doShowList');
insert into func_url(FuncId, ActionName, MethodName) values(${entity.editFuncId}, '${entity.lowerFirstClassName}', 'doSave');
insert into func_url(FuncId, ActionName, MethodName) values(${entity.editFuncId}, '${entity.lowerFirstClassName}', 'doDelete');
#end

#if($entity.genReport)
insert into func_url(FuncId, ActionName, MethodName) values(${entity.viewFuncId}, '${entity.lowerFirstClassName}', 'doShowReport');

insert into reports(ReportId, ReportName, ReportResKey, Href, ActionName, MethodName, ClassControllor, MethodControllor, IsDefault, Ord, GroupId) 
values(${entity.reportId}, '${entity.reportName}', '${entity.resourceKeyPath}.${entity.lowerFirstClassName}.name', 
'${entity.strutsActionPath}/${entity.lowerFirstClassName}.do?method=doShowReport', 
'${entity.lowerFirstClassName}', 'show${entity.className}', 
'com.gearteks.imanage.controller.reports.ReportsControllor', 'isShow${entity.className}', 0, 20, 1);
#end