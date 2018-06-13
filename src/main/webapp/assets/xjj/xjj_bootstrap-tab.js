/**
 * Bootstrap tab组件封装
 * @created 2017/11/29
 * @author xjj
 */
(function ($, window, document, undefined) {
    'use strict';

    var pluginName = 'tabs';

    //入口方法
    $.fn[pluginName] = function (options) {
        var self = $(this);
        if (this == null)
            return null;
        var data = this.data(pluginName);
        if (!data) {
            data = new BaseTab(this, options);
            self.data(pluginName, data);
        }
        return data;
    };


    var BaseTab = function (element, options) {
        this.$element = $(element);
        this.options = $.extend(true, {}, this.default, options);
        this.init();
    }

    //默认配置
    BaseTab.prototype.default = {
        showIndex: 0, //默认显示页索引
        loadAll: true,//true=一次全部加在页面,false=只加在showIndex指定的页面，其他点击时加载，提高响应速度

    }

    //结构模板
    BaseTab.prototype.template = {
        ul_nav: '<ul id="XjjTab"  class="nav nav-tabs padding-12 tab-color-blue"></ul>',
        ul_li: '<li><a href="#{0}" data-toggle="tab"><span>{1}</span></a></li>',
        ul_li_close: '<i class="xjj-tab glyphicon glyphicon-remove-sign closeable" title="关闭"></i>',
        div_content: '<div  class="tab-content no-border" style="padding:2px 0 0 0;"></div>',
        div_content_panel: '<div class="tab-pane fade" id="{0}"></div>'
    }

    //初始化
    BaseTab.prototype.init = function () {
        if (!this.options.data || this.options.data.length == 0) {
            console.error("请指定tab页数据");
            return;
        }
        //当前显示的显示的页面是否超出索引
        if (this.options.showIndex < 0 || this.options.showIndex > this.options.data.length - 1) {
            console.error("showIndex超出了范围");
            //指定为默认值
            this.options.showIndex = this.default.showIndex;
        }
        //清除原来的tab页
        this.$element.html("");
        this.builder(this.options.data);
    }

    //使用模板搭建页面结构
    BaseTab.prototype.builder = function (data) {
        var ul_nav = $(this.template.ul_nav);
        var div_content = $(this.template.div_content);

        for (var i = 0; i < data.length; i++) {
            //nav-tab
            var ul_li = $(this.template.ul_li.format(data[i].id, data[i].text));
            //如果可关闭,插入关闭图标，并绑定关闭事件
            if (data[i].closeable) {
                var ul_li_close = $(this.template.ul_li_close);

                ul_li.find("a").append(ul_li_close);
                ul_li.find("a").append("&nbsp;");
            }

            ul_nav.append(ul_li);

            //div-content
            var div_content_panel = $(this.template.div_content_panel.format(data[i].id));


            div_content.append(div_content_panel);
        }

        this.$element.append(ul_nav);
        this.$element.append(div_content);
        this.loadData();

        this.$element.find("#XjjTab li:eq(" + this.options.showIndex + ") a").tab("show");

    }

    BaseTab.prototype.loadData = function () {
        var self = this;

        //tab点击即加载事件
        //设置一个值，记录每个tab页是否加载过
        this.stateObj = {};
        var data = this.options.data;
        //如果是当前页或者配置了一次性全部加载，否则点击tab页时加载
        for (var i = 0; i < data.length; i++) {
            if (this.options.loadAll || this.options.showIndex == i) {
                if (data[i].url) {
                    $("#" + data[i].id).load(data[i].url,data[i].param);
                    this.stateObj[data[i].id] = true;
                } else {
                    console.error("id=" + data[i].id + "的tab页未指定url");
                    this.stateObj[data[i].id] = false;
                }
            } else {
                this.stateObj[data[i].id] = false;
                (function (id, url,paramter) {
                    self.$element.find("#XjjTab a[href='#" + id + "']").on('show.bs.tab', function () {
                        if (!self.stateObj[id]) {
                            $("#" + id).load(url,paramter);
                            self.stateObj[id] = true;
                        }
                    });
                }(data[i].id, data[i].url, data[i].paramter))
            }
        }

        //关闭tab事件
        this.$element.find("#XjjTab li a i.closeable").each(function (index, item) {
            $(item).click(function () {
                var href = $(this).parents("a").attr("href").substring(1);
//                if(self.getCurrentTabId()==href){
//                    self.$element.find("#XjjTab li:eq(0) a").tab("show");
//                }
//                $(this).parents("li").remove();
//                $("#" + href).remove();
                
                self.remove(href);
            })
        });

    }

    //新增一个tab页
    BaseTab.prototype.addTab=function (obj) {
        var self=this;
        
        //如果存在则删除
    	if(self.hasTab(obj.id))
    	{
    		self.remove(obj.id)
    	}
        
        //nav-tab
        var ul_li = $(this.template.ul_li.format(obj.id, obj.text));
        //如果可关闭,插入关闭图标，并绑定关闭事件
        if (obj.closeable) {
            var ul_li_close = $(this.template.ul_li_close);

            ul_li.find("a").append(ul_li_close);
            ul_li.find("a").append("&nbsp;");
        }
        
        this.$element.find("#XjjTab:eq(0)").append(ul_li);
        //div-content
        var div_content_panel = $(this.template.div_content_panel.format(obj.id));
        this.$element.find(".tab-content:eq(0)").append(div_content_panel);
        
        $("#" + obj.id).load(obj.url,{tabId:obj.id,navs:obj.navs});
        this.stateObj[obj.id] = true;

        if(obj.closeable){
            this.$element.find("#XjjTab li a[href='#" + obj.id + "'] i.closeable").click(function () {
                var href = $(this).parents("a").attr("href").substring(1);
//                if(self.getCurrentTabId()==href){
//                    self.$element.find("#XjjTab li:eq(0) a").tab("show");
//                }
//                $(this).parents("li").remove();
//                $("#" + href).remove();
                self.remove(href);
            })
        }

        this.$element.find("#XjjTab a[href='#" + obj.id + "']").tab("show");
    }

    //根据id获取活动也标签名
    BaseTab.prototype.find=function (tabId) {
        return this.$element.find("#XjjTab li a[href='#" + tabId + "']").text();
    }
    
    // 删除活动页
    BaseTab.prototype.remove=function (tabId) {
    	 
    	var self=this;
        $("#" + tabId).remove();
        this.$element.find("#XjjTab li a[href='#" + tabId + "']").parents("li").remove();
        var tabSize = $('#XjjTab').children('li').length;
        self.showTabByIdx(tabSize-1);
    }
    
    // 重新加载页面
    BaseTab.prototype.reload=function (obj) {
    	var self=this;
    	if(self.find(obj.id)!=null){
    		$("#" + obj.id).remove();
    		this.$element.find("#XjjTab li a[href='#" + obj.id + "']").parents("li").remove();
    		self.addTab(obj);
    	}else{
    		self.addTab(obj);
    	}
    }

    //根据id设置活动tab页
    BaseTab.prototype.showTab=function (tabId) {
        this.$element.find("#XjjTab li a[href='#" + tabId + "']").tab("show");
    }
    
    //根据index显示tab
    BaseTab.prototype.showTabByIdx=function (idx) {
    	this.$element.find("#XjjTab li:eq(" + idx + ") a").tab("show");
    }
    
    //根据id设置活动tab页
    BaseTab.prototype.hasTab=function (tabId) {
    	if(this.$element.find("#XjjTab li a[href='#" + tabId + "']").length >0)
		{
    		return true;
		}
    	return false;
    }

    //获取当前活动tab页的ID
    BaseTab.prototype.getCurrentTabId=function () {
        var href=this.$element.find("#XjjTab li.active a").attr("href");
        href=href.substring(1);
        return href;
    }

    
    String.prototype.format = function () {
        if (arguments.length == 0) return this;
        for (var s = this, i = 0; i < arguments.length; i++)
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        return s;
    };
})(jQuery, window, document)
