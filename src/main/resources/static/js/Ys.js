var InitOptions=function () {
    //默认的BootStrapFileInput属性
    // 注:官方默认上传方式为:异步  此处上传方式:同步
    var defaultBootFileInput={
        language: 'zh', //设置语言
        uploadUrl: "", //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        showUpload: false, //是否显示上传按钮
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        dropZoneEnabled: false,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        maxFileSize: 2000,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        uploadAsync: false, //同步上传
        allowedPreviewTypes: ['image'],
        allowedFileTypes: ['image'],
        maxFileCount: 1, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        autoReplace:true,
        ajaxOperations: {
            deleteThumb: '删除文件',
            uploadThumb: '上传文件',
            uploadBatch: '上传文件',
            uploadExtra: '表单数据上传'
        },
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        /*   initialPreview: [
              //预览图片的设置
             "<img src='/assets/img/avatar.png' class='file-preview-image' alt='肖像图片' title='肖像图片'>",
              ],*/
        /*  overwriteInitial: false, //不覆盖已存在的图片
        initialPreviewFileType: 'image',
        initialPreviewConfig: [
            {caption: "People-1.jpg", size: 576237, width: "120px", url: "/site/file-delete", key: 1},
        ],
        fileActionSettings:{
            showZoom: true,
            showDrag: false,
            showRemove:false,
            showUpload:false
        },*/
        /*   uploadExtraData:function (previewId, index) {
               //向后台传递id作为额外参数，是后台可以根据id修改对应的图片地址。
               var obj = {};
               obj.id = fishId;
               return obj;
           },*/

        layoutTemplates :{
            actionUpload:'',//去除上传预览缩略图中的上传图片
            //actionDelete:''
        }
    };

    /**
     * 初始化bootstrap的fileinput文件上传组件
     * @param options 对象格式
     * @returns control
     * @constructor
     */
    var HandlerInputFile=function (options) {
        //var oFileInput = new FileInput();
        //初始化fileinput控件（第一次初始化）
            var control = $(options.id);
            $.extend(defaultBootFileInput,options);
          //第一个为目标数据 后面的继承目标数据重写 并返回第一个目标数据
           //初始化上传控件的样式
            control.fileinput(defaultBootFileInput);

           // $(this).fileinput("upload");此方法为手动提交上传到服务器
            control.on('fileerror', function(event, data, msg) {
                //异步上传错误处理结果
            });
            control.on("fileuploaded", function (event, data, previewId, index) {
               // 异步上传完成的处理结果
            });
            control.on("filebatchselected", function(event, files) {
                //文件选则完成用这个方法
            });
            control.on('filebatchuploaderror', function(event, data, msg) {
              //同步上传错误处理结果
                $.modal.alertError("上传失败,联系管理员");
            });
            control.on('filebatchuploadsuccess', function(event, data, previewId, index) {
              //同步上传完成处理结果
            });

            control.on("fileclear",function(event, data, msg){
                //点击浏览框右上角X 清空文件前响应事件
             });

            control.on("filecleared",function(event, data, msg){
                 //点击浏览框右上角X 清空文件后响应事件
              });
            return control;

    };

    /**
     * 初始化wangEdit富文本框
     *  editor.txt.html() 可以获取富文本框内容 html格式
     * @constructor
     */
    var HandlerWangEdit=function (elemId,editUrl) {
        var E = window.wangEditor
        var editor = new E(elemId)
        editor.customConfig.uploadImgServer = editUrl;
        editor.customConfig.uploadFileName = 'editFile';
        // 将图片大小限制为 1M
        editor.customConfig.uploadImgMaxSize = 1 * 1024 * 1024
        editor.customConfig.showLinkImg= false;
        // 限制一次最多上传 1 张图片
        editor.customConfig.uploadImgMaxLength = 1;
        // 或者 var editor = new E( document.getElementById('editor') )
        editor.create();
        return editor;
    };

    return{
        initBootFile:function (options) {
           return HandlerInputFile(options);
        },
        InitWangEdit:function (elemId,editUrl) {
            return HandlerWangEdit(elemId,editUrl);
        }
    }

}();


