var setting;
var zTreeObj;
setting = {
    /*async:{
        enable:true,//async是否生效
        autoParam:["id","pid","name"],
        dataType:"application/json",//接受类型
        url:"/clazz/getAllClazz"//数据源
    },*/
    data:{//表示tree的数据格式
        simpleData:{
            enable:true,//表示使用简单数据模式
            idKey:"id",//设置之后id为在简单数据模式中的父子节点关联的桥梁
            pidKey:"pId",//设置之后pid为在简单数据模式中的父子节点关联的桥梁和id互相对应
            rootId:"null"//pid为null的表示根节点
        }
    },
    view:{//表示tree的显示状态
        selectMulti:true//表示禁止多选
    },
    check:{//表示tree的节点在点击时的相关设置
        enable:true,//是否显示radio/checkbox
        chkStyle:"checkbox",//值为checkbox或者radio表示
        checkboxType:{p:"",s:""},//表示父子节点的联动效果
        radioType:"level"//设置tree的分组
    },
    callback:{//表示tree的一些事件处理函数
        onClick:zTreeOnClick,//单击事件
        //onCheck:handlerCheck
        onExpand:zTreeOnExpand//展开事件
    }
}

/**
 * 树单击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    var id = treeNode.id;
    alert(id);
    alert(zTreeObj.getCheckedNodes());//获取选中的Checked节点
    var node = zTreeObj.getNodeByTId("2");//根据ID获取node
    zTreeObj.selectNode(node);//根据node选中
};

/**
 * 树展开事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnExpand(event, treeId, treeNode) {
    alert(treeNode.id + "。。。" + treeNode.name);
};
$(document).ready(function(){
    initZTree();
});

function initZTree() {
    $.ajax({
        url: "/clazz/getAllClazz",
        type: "post",
        dataType: "json",
        success: function(data) {
            /*data = eval(data);
            alert(data[0].id);
            var nodes = [
                {name: "父节点1", children: [
                        {name: "子节点1"},
                        {name: "子节点2"}
                    ]}
            ];*/
            zTreeObj =  $.fn.zTree.init($("#treeTest"), setting, data);
        }
    });
}




