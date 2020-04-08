<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>

<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>生日</td>
    </tr>
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index + 1}</td>
                <td <#if stu.name =='小明'>style="background:red;"</#if>>${stu.name}</td>
                <td>${stu.age}</td>
                <td <#if stu.money gt 300>style="background: blue" </#if> >${stu.money}</td>
                <td>${stu.birthday?string("yyyy年MM月")}</td>
            </tr>
        </#list>
    </#if>
</table>
学生的个数：${stus?size}<br/>
${num?c}
<#assign text="{'bank':'工商银行','account':'151845155166'}"/>
<#assign data=text?eval/>
开户行：${data.bank},账户：${data.account}
<br/><br/>
<#--输出stu1的学生信息：<br/>-->
姓名：${(stuMap['stu1'].name)!''}
姓名：${(stuMap.stu1.name)!''}<br/>
<#list stuMap?keys as k>
    姓名：${stuMap[k].name},年龄：${stuMap[k].age}<br/>
</#list>
</body>
</html>