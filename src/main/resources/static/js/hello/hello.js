$(document).ready(function () {
   //tableInit();
});

function tableInit() {
    $('#bootstrapTable').bootstrapTable({
        url: '/student/pageTest',
        pagination: true,
        pageNumber: 1,
        //pageSize: 2,
        pageList: [10, 20, 50, 100],
        sidePagination: "server",         //分页方式：client客户端分页，server服务端分页（*）
        //striped: true,                    //是否显示行间隔色
        cache: false,
        uniqueId: "id",               //每一行的唯一标识，一般为主键列
        clickToSelect: true,                //是否启用点击选中行
        height:300,
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        onClickRow:function (row,$element) {
            $('.info').removeClass('info');
            $($element).addClass('info');
        },
        columns: [
            { checkbox: true,visible: true },
            { title: '序号', width: 50, align: "center", formatter: function (value, row, index) { return index + 1; } },
            { title: '图书名称', field: 'name' },
            { title: '图书作者', field: 'age' },
            { title: '销售价格', field: 'birthday' },
            {
                title: '出版时间', field: 'PublishDate', formatter: function (value, row, index) {
                if (value == null)
                    return "";
                else {
                    var pa = /.*\((.*)\)/;
                    var unixtime = value.match(pa)[1].substring(0, 10);
                    return getShortTime(unixtime);
                }
            }
            },
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                var html = "<a href=\"javascript:EditBook(\'"+value+"\',\'"+row.name+"\')\">编辑</a>";
                html += '　<a href="javascript:DeleteBook(' + value + ')">删除</a>';
                return html;
            }
            }
        ]
    });
}
//刷新
bootstrapRefresh = function() {
    //$("#bootstrapTable").bootstrapTable('refresh',{url :'/student/pageTest'} );
    //获取选中行
    $.map($('#bootstrapTable').bootstrapTable('getSelections'), function (row) {
        alert(row.id);
    });
}

function EditBook(value,row){
    alert(value+"\n"+row);
}

function DeleteBook(value){
    alert(value);
}


doSave = function() {
    var parm = $("#studentInfo").serialize();
    /*var name = $("#name").val();
    var age = $("#age").val();*/
    $.ajax({
        url: "/student/saveStudent",
        type: "POST",
        data: parm,//{"name":name,"age":age},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

startOffice = function (path) {
    $.ajax({
        url: "/task/start",
        type: "POST",
        data: {"path":path},//{"name":name,"age":age},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

changeLogLevel = function (level) {
    $.ajax({
        url: "/task/changeLogLevelS",
        type: "POST",
        data: {"level":level},//{"name":name,"age":age},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

startAllJob = function () {
    $.ajax({
        url: "/startAllJob",
        type: "POST",
        data: {},//{"name":name,"age":age},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}
pauseJob = function (name,group) {
    $.ajax({
        url: "/pauseJob",
        type: "POST",
        data: {"name":name,"group":group},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

startJob = function (name,group) {
    $.ajax({
        url: "/startJob",
        type: "POST",
        data: {"name":name,"group":group},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

testThreadPool = function () {
    $.ajax({
        url: "/testThreadPool",
        type: "POST",
        data: {},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

function selectFile() {
    $('input[id=lefile]').click();
    var file = $('#lefile').val();
    file = file.substring(file.lastIndexOf("\\")+1,file.length);
    $('#photoCover').val(file);
}

function uploadFile(){
    var name1 = $("#name1").val();
    var input = document.getElementById('lefile');
    var data = new FormData();
    data.append('file', input.files[0]);
    data.append('name1', name1);
    $.ajax({
        url: "/file/upload",
        type: "POST",
        data: data,
        sync: false,
        contentType: false, // 注意这里应设为false
        processData: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                alert("保存成功");
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}

jump = function () {
    $.ajax({
        url: "/student/jump",
        type: "POST",
        data: {},
        sync: false,
        dataTypes: "text",
        success: function (data) {
            if("succ" == data){
                window.location.href= "/clazz/ztreeTest.html"
            }else{
                alert("保存失败");
            }
        },
        error: function (err) {
            alert("保存错误");
        }
    })
}