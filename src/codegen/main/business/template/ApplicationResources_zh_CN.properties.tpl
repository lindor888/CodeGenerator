${entity.resourceKeyPath}.${entity.lowerFirstClassName}.name=${entity.i18n.zh}
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.label.add=添加${entity.i18n.zh}
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.label.save=保存
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.label.delete=删除

#foreach($field in ${entity.showFields})
#if($field.hasOptions)
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}=${field.i18n.zh}
#foreach($option in ${field.options})
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}.${option.value}=${option.zh}
#end
#elseif($field.dateField)
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}.from=开始${field.i18n.zh}
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}.to=结束${field.i18n.zh}
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}=${field.i18n.zh}
#else
${entity.resourceKeyPath}.${entity.lowerFirstClassName}.${field.i18n.key}=${field.i18n.zh}
#end
#end
