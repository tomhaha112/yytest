var app1 = new Vue({
    el:"#list",
    data:{
        parentMessage:"测试",
        items:[
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
            
        ]
    }
})


  var app = new Vue({
    el:"#app",
    data:{
        selectedindex:0,
        btns:{
            out:"全部用户",
            in :"授权用户"
        },
        items:{
            public_items:
            [
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            {a:"1",b:"12",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"2",b:"23",linkaddress:"http://baidu.com",linkname:"baidu"},
            {a:"3",b:"34",linkaddress:"http://baidu.com",linkname:"baidu"}, 
            ],
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
        }
    },
    methods:{
        choosetab:function(){
            return this.selectedindex = arguments[0];
        }
    }
})