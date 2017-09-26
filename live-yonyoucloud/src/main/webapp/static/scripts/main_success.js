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
}//直播列表数据

{
    "status":1,
    "data":"{
        \"data\":{
                \"pushurl\":\"rtmp://pili-publish.vzan.com/vzanlive/131504476672337356?e=1508566067&token=ukF6gb319SJ-0vRruRI3Wo48W3-437u99TAw8bPn:AVev_uzkF3JGH7MmYWWx126hT8M=\",
                \"speaker\":\"\",
                \"rtmpurl\":\"\",
                \"banner\":\"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335\",
                \"Id\":455686,
                \"starttime\":\"2017-09-21 16:07:34\",
                \"sort\":1,
                \"title\":\"sssss\",
                \"viewcts\":101,
                \"introduction\":\"\",
                \"status\":-1,
                \"hlsurl\":\"\"
                },
        \"status\":1
    }"
}//直播话题

{
    "status":1,
    "data":{
        "headimg":"https://cdn.yonyoucloud.com/dev/yht/style/images/user.jpg",
        "nickname":"董俊超"
    }
}//直播间用户头像与昵称
*/



/*var live_list = {"status":1,"data":"[{\"totalcount\":1,\"banner\":\"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335\",\"Id\":455686,\"starttime\":\"2017-09-21 16:07:34\",\"sort\":1,\"title\":\"sssss\",\"viewcts\":92,\"status\":-1},{\"totalcount\":1,\"banner\":\"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335\",\"Id\":1234567,\"starttime\":\"2017-09-21 16:07:34\",\"sort\":1,\"title\":\"sssss\",\"viewcts\":92,\"status\":-1}]"};// test
var live_list_data = JSON.parse(live_list["data"]); 
var live_list_status = '';

var user_info = {
    "status":1,
    "data":{
        "headimg":"https://cdn.yonyoucloud.com/dev/yht/style/images/user.jpg",
        "nickname":"董俊超"
    }
};
var user_name_receive = user_info["data"].nickname;
var user_img_receive = user_info["data"].headimg;
*/





var live_list_data='';
var live_list_status='';
var tenantId = "rzfx6m98";
$.ajax({  
    url: "http://172.20.18.241/live-yonyoucloud/live/getlives?tenantId="+tenantId+"&pageNum=1&pageSize=10",//后台提供的接口
    async:false,
    type: "get",   //请求方式是get   
    dataType: "json", //数据类型是json型
    success: function (live_list) {   //成功时返回的data值，注意这个data是后台返回的值，上面的data是你要传给后台的值  
                live_list_data = JSON.parse(live_list["data"]);//json对象数组
                live_list_status = live_list[status];//成功与否
            },  
    error: function () {   
                  
    },  
    complete: function () {  
  
    }  
});



var userId = "4e28b4bf-f98d-4825-97d9-ac086a57d2ee";
var user_name_receive = '';
var user_img_receive = '';
$.ajax({
    url:"http://uastest.yyuap.com/livecloud_server/admin/l/getUserInfo?userId="+userId,
    type:"get",
    async:false,
    dataType:"json",
    sunccess:function(data){
        user_name_receive = user_info["data"].nickname;
        user_img_receive = user_info["data"].headimg;
    }
})






var app = new Vue({
    el:"#app",
    data:{
        //tenantId:tenantId||"rzfx6m98",
        live_room_id:'',//纪录直播间id
        selectedindex:0,//标签切换标志
        btns:{
            out:"全部用户",
            in :"授权用户"
        },//tab页的标题 
        items:{
            public_items:live_list_data,
            /*            [
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            ],*/ //测试用的对象数组
            private_items:
            [
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baiduxxx"},
            ]
        },
        before_click: "inherit",
        after_click:"none",
        live_topic_data:'',//纪录直播话题纪录
        user_name : user_name_receive,//直播间用户头像与昵称
        user_img : user_img_receive 
        
    },
    methods:{
        change_to_vzanmanagement:function(){
            alert("没完成的方法 userid tenantid appid现在是写死的");
            window.location.href = "http://172.20.18.241/livecloud_server/admin/l/getliveAdmin?userId=4e28b4bf-f98d-4825-97d9-ac086a57d2ee&tenantId=rzfx6m98&callback=callback&appId=1eee"
        },
        choosetab:function(){
            return this.selectedindex = arguments[0];
        },
        clicktest:function(){
            this.live_room_id = arguments[0];
            //alert(arguments[0]);
            var url = "http://172.20.18.241/live-yonyoucloud/live/getlive?liveId="+this.live_room_id;//利用直播间id拼接接口
/*            var  live_topic_data_test = {"status":1,"data":"{\"data\":{\"pushurl\":\"rtmp://pili-publish.vzan.com/vzanlive/131504476672337356?e=1508566067&token=ukF6gb319SJ-0vRruRI3Wo48W3-437u99TAw8bPn:AVev_uzkF3JGH7MmYWWx126hT8M=\",\"speaker\":\"\",\"rtmpurl\":\"\",\"banner\":\"http://j.vzan.cc//images/vzanbg/small_ind_2.png?ver=636397966118333335\",\"Id\":455686,\"starttime\":\"2017-09-21 16:07:34\",\"sort\":1,\"title\":\"sssss\",\"viewcts\":101,\"introduction\":\"\",\"status\":-1,\"hlsurl\":\"\"},\"status\":1}"};
            var a = live_topic_data_test["data"];
            this.live_topic_data = JSON.parse(a)["data"];*/
            $.ajax({  
                url: url,
                async:false,
                type: "get",   //请求方式是get   
                dataType: "json", //数据类型是json型
                success: function (live_topic) {   //成功时返回的data值，注意这个data是后台返回的值，上面的data是你要传给后台的值  
                            app.live_topic_data = JSON.parse(live_topic["data"])["data"];//json对象数组
                            //live_topic_status = live_topic[status];//成功与否
                
                            /*this.before_click = "none";
                            this.after_click = "block";*/
                            
                        },  
                error: function () {   
                  
                        },  
                complete: function () {  
  
                        }  
            });
        
        
            this.before_click = "none";
            this.after_click = "block";
            
        },
        checkto_before:function(){
            this.before_click= "inherit";
            this.after_click="none";
            this.live_topic_data='';
        }
    }
})

