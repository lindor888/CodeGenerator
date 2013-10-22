<mapping resource="hibernate/${entity.className}.hbm.xml" />
#if($entity.hasSysMember)
<mapping resource="hibernate/${entity.memberName}.hbm.xml" />
#end