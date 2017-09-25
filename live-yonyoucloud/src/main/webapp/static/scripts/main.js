/*
{
    "status":1,
    "data":"[
    {
        \"totalcount\":1,
        \"banner\":\"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335\",
        \"Id\":455686,
        \"starttime\":\"2017-09-21 16:07:34\",
        \"sort\":1,
        \"title\":\"sssss\",
        \"viewcts\":27,
        \"status\":-1
    }
    ]"
}
var live_list = JSON.parse(live_list_receive);
var live_list_data = live_list[data];//json对象数组
var live_list_status = live_list[status];//成功与否
*/
 
var live_list_data_test= [
    {
        "totalcount":1,
        "banner":"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335",
        "Id":455686,
        "starttime":"2017-09-21 16:07:34",
        "sort":2,
        "title":"sssss111",
        "viewcts":27,
        "status":-1
    },
        {
        "totalcount":1,
        "banner":"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335",
        "Id":455686,
        "starttime":"2017-09-21 16:07:34",
        "sort":1,
        "title":"should be one ",
        "viewcts":27,
        "status":-1
    }
    ];//测试数据

var app1 = new Vue({
    el:"#list",
    data:{
        parentMessage:"测试",
        items:live_list_data_test
        /*[
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}   
        ]*/
    },
    computed: {
        orderedItems: function () {
            return this.items.sort(function(a,b){
                return a.sort-b.sort;
            });//orderby sort
  }
}
})





