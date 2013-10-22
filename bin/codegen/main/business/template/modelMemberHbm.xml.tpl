<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="${entity.packagePath}.${entity.memberName}" table="${entity.memberTableName}">
        <composite-id name="comp_id" class="${entity.packagePath}.${entity.memberName}PK">
            <key-property name="${entity.key.fieldName}" type="integer">
                <column name="${entity.PK}" />
            </key-property>
            <key-property name="involvement" type="string">
                <column name="Involvement"/>
            </key-property>
            <key-property name="objType" type="string">
                <column name="ObjType"/>
            </key-property>
            <key-property name="objId" type="integer">
                <column name="ObjId" />
            </key-property>
        </composite-id>
        <many-to-one name="${entity.lowerFirstClassName}" class="${entity.fullPathClassName}" update="false" insert="false" fetch="select">
            <column name="${entity.PK}" not-null="true" />
        </many-to-one>
    </class>
</hibernate-mapping>
