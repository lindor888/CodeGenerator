CodeGenerator
=============

Swing + Apach Velocity编写的代码生成工具

说明: 用于生成代码的模板是定制的，无法实现多兼容性，故仅可借鉴。

1. 支持普通类型字段定义

2. 支持One-to-One

3. 支持One-to-Many


启动方式：

1. 导出生成eclipse插件

2. 直接运行codegen.swing.CodeGenWindow.java


备注：每次生成完成后会保留最近的一份设置在临时文件中，当下一次点击代码生成工具的时候会主动获取上一次的设置。
