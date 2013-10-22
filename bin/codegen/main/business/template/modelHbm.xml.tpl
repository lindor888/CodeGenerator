<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="${entity.packagePath}.${entity.className}" table="${entity.tableName}">
        <id name="${entity.key.fieldName}" type="integer">
            <column name="${entity.PK}" />
            <generator class="assigned" />
        </id>
        
#foreach($field in $entity.fieldListExcluePkAndSys)
		<property name="$field.fieldName" type="$field.fieldType">
            <column name="$field.columnName" />
        </property>
        
#end
#foreach($field in $entity.fieldListSysObjCurrency)
		<many-to-one name="${field.fieldName}" class="com.gearteks.framework.model.Currency" not-null="false">
           <column name="CurrencyId" />
        </many-to-one>
        
#end
		<property name="ts" type="timestamp">
            <column name="Ts"/>
        </property>
        
        <many-to-one name="tsUser" class="com.gearteks.framework.model.User" not-null="true">
            <column name="TsUserId"/>
        </many-to-one>

        <property name="createTime" type="timestamp">
            <column name="CreateTs"/>
        </property>
        
        <many-to-one name="creater" class="com.gearteks.framework.model.User" not-null="true">
            <column name="CreaterId"/>
        </many-to-one>

#foreach($field in $entity.sysMemberFields)        
        <set name="${field.fieldName}" lazy="true" inverse="true" where="involvement='${field.sysMember.involvement}'">
            <key>
                <column name="${entity.PK}" not-null="true" />
            </key>
            <one-to-many class="${entity.fullPathClassName}Member" />
        </set>
        
#end    
    </class>
    
</hibernate-mapping>
