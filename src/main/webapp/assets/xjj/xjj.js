;
(function($, window) {
	var XJJ = {};
	if (!window.XJJ) {
		window.XJJ = XJJ;
	}

	// xjj
	// public------------------------------------------------------------------------------------------------
	XJJ.ajax = function(options) {
		var opts = {
			tabId:undefined,
			url : undefined,// 提交地址，如果是form表单提交，可以不写，则使用form的action属性
			form : undefined,// 提交的表单id或者是name
			target : false,// 返回结果容器id，如果是html类型，则返回结果直接填充对应的容器
			method : undefined,// 提交方式，如果是from表单提交，可以不写，则使用from的method属性
			dataType : 'html',// 数据类型，默认html，支持jquery的ajax类型
			data : undefined,// 提交的数据[{name:'name1',value:'val1'},{name:'name2',value:'val2'}]
			onSubmit : false,// 提交前执行
			onSuccess : false,// 提交成功后，返回结果处理前执行
			onCompleted : false,// 提交成功后，返回结果处理后执行
			onError : false
		// 提交失败是执行
		};
		opts = XJJ.getOptions(options);
		// 如果是form表单提交，则信息默认从表单获取
		var $form = $("#" + opts.form) || $('form[name=' + opts.form + ']');
		if ($form && $form.length > 0) {
			// alert("$form.length="+$form.attr('action'));
			if (opts.data) {
				$.each(opts.data, function(index, q) {
					var $element = $form.find('[name=' + q.name + ']');
					if ($element.length > 0) {
						$element.val(q.value);
					} else {
						$("<input type='hidden' name='" + q.name
										+ "' value='" + q.value + "' />")
								.appendTo($form);
					}
				});
			}
			opts.url = $form.attr('action');
			opts.data = $form.formSerialize();

			// alert($form.formSerialize() +'--' +$form.attr('method') +'--'+
			// $form.attr('action'));
		}
		
		// 提交前执行
		if (!opts.url) {
			
			return this;
		}

		// 提交前执行
		if (opts.onSubmit && opts.onSubmit.apply(this, opts) === false) {
			return this;
		}
		
		//加上tabId
		if(null==opts.data||undefined==opts.data||""==opts.data)
		{
			opts.data="tabId="+opts.tabId;
		}else
		{
			opts.data=opts.data+"&tabId="+opts.tabId;
		}
		
		
		// 开始远程调用
		$.ajax({
					type : opts.method,
					url : opts.url,
					dataType : opts.dataType,
					data : opts.data,
					success : function(data, textStatus) {
						if (opts.onSuccess
								&& opts.onSuccess.apply(this, [ data,
										textStatus ]) === false) {
							return;
						}
						if (opts.dataType == 'html' && opts.target) {
							var $target = $('#' + opts.target);
							
							$target.empty();
							$target.html(data);
						}
						if (opts.onCompleted) {
							opts.onCompleted.apply(this, [ data, textStatus ]);
						}
					},
					error : opts.onError
				});

		return this;
	};
	/**
	 * ajax加载html内容到指定容器
	 * 
	 * @param href
	 *            要加载的页面url
	 * @param target
	 *            获取到的html填充目标
	 */
	XJJ.html = function(href, target, text) {
		if (!target) {
			target = 'bscenter';
		}
		$('#' + target).html(
				"<img  src='" + PROJECT_HOME
						+ "/style/load.gif' width='100%' />");
		$.ajax({
			url : href,
			data : {},
			type : 'post',
			dataType : 'html',
			success : function(html) {
				$('#' + target).html(html);
			}
		});
	}

	// XJJ options
	XJJ.options = {
		id : undefined,
		url : undefined,
		queryForm : 'queryForm',
		inputForm : 'inputForm',
		listId : 'xjjlist',
		pageSize : 10,
		currentPage : 1,
		cssDatePicker : 'date',
		cssTime : 'time',
		cssNumber : 'number',
		cssEditor : 'editor',
		cssTinyEditor : 'tinyeditor',
		cssQuestionEditor : 'questioneditor',

		/* ajax */
		source : undefined,
		form : undefined,
		method : 'POST',
		dataType : 'html'
	};
	XJJ.getOptions = function(options) {
		var opts = {};
		if (options) {
			if (typeof option == 'string') {
				opts = eval(options);
			} else {
				opts = options;
			}
		}
		idopts = {};
		if (options && options.id) {
			var id = options.id;
			idopts = {
				queryForm : id + 'queryForm',
				inputForm : id + 'inputForm',
				listId : id + 'xjjlist',
				tabId : id
			};
		}
		return $.extend({}, XJJ.options, idopts, opts);
	};

	/**
	 * 添加
	 * 
	 * @param url
	 * @param title
	 */
	XJJ.add = function(url, title, inputFormId) {
		if (url && title) {
			XJJ.dialog({
				tabId:inputFormId,
				url : url,
				title : title,
				onOk : function() {
					XJJ.save({id:inputFormId});
				}
			});
		}
	};

	/**
	 * 预览
	 * 
	 * @param url
	 * @param title
	 */
	XJJ.view = function(url, title) {
		if (url && title) {
			XJJ.dialog({
				url : url,
				title : title,
				showCancel : false,
				showOk : false
			});
		}
	};
	/**
	 * 编辑
	 * 
	 * @param url
	 * @param title
	 */
	XJJ.edit = function(url, title, inputFormId) {
		if (url && title) {
			if (url.endWith('input')) {// toobar修改
				var $input = $('input.bscheck:checked');
				var length = $input.length;
				if (length != 1) {
					XJJ.msger('请选择要修改的一条数据！');
				} else {
					url += '/' + $input.qdata().id;
					XJJ.dialog({
						tabId:inputFormId,
						url : url,
						title : title,
						onOk : function() {
							XJJ.save({
								id : inputFormId
							});
						}
					});
				}
			} else {
				XJJ.dialog({
					tabId:inputFormId,
					url : url,
					title : title,
					onOk : function() {
						XJJ.save({
							id : inputFormId
						});
					}
				});
			}
		}
	};
	
	
	/**
	 * XJJ.on
	 * 事件绑定
	 */
	XJJ.on = function(obj, event, func){
		$(document).off(event, obj).on(event, obj, func);
	};

	// xjj msg options
	XJJ.msgoptions = {
		url : undefined,
		fade : 'fade',
		close : true,
		title : '',
		btn : false,
		showOk : true,
		onOk : undefined,
		showCancel : true,
		onCancel : undefined,
		msg : 'loading......',
		big : true,
		show : false,
		remote : false,
		backdrop : 'static',
		keyboard : true,
		buttons : []
	};
	// bs弹出窗口
	XJJ.dialog = function(opt) {

		opt = $.extend({}, XJJ.msgoptions, opt);
		var start = '<div class="modal '
				+ opt.fade
				+ '" id="bsmodal" tabindex="-1" role="dialog" aria-labelledby="bsmodaltitle" aria-hidden="true">';
		if (opt.big) {
			start += '<div class="modal-dialog modal-lg"><div class="modal-content">';
		} else {
			start += '<div class="modal-dialog modal-sm"><div class="modal-content">';
		}

		var head = '<div class="modal-header">';
		if (opt.close) {
			head += '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"><i class="glyphicon glyphicon-remove-sign closeable" title="关闭"></i></span></button>';
		}
		head += '<h3 class="modal-title" id="bsmodaltitle">' + opt.title
				+ '</h3></div>';

		var body = '<div id="bsmodalbody" class="modal-body">加载中</div>';
		var foot = '<div class="modal-footer">';
		if (opt.showOk) {
			foot += '<button type="button" class="btn btn-primary bsok">确定</button>';
		}
		if (opt.showCancel) {
			foot += '<button type="button" class="btn btn-default bscancel">取消</button>';
		}
		if (opt.buttons && opt.buttons.length > 0) {
			for ( var item in opt.buttons) {
				foot += '<button type="button" class="btn btn-default bs'
						+ item.title + '">' + item.title + '</button>';
			}
		}
		var end = '</div></div></div></div>';
		$('body').append(start + head + body + foot + end);
		if (opt.msg) {// 填充弹出框内容
			$("#bsmodalbody").html(opt.msg);
		}
		if (opt.url) {// 填充弹出框内容
			XJJ.ajax({
				tabId:opt.tabId,
				url : opt.url,
				dataType : 'html',
				target : 'bsmodalbody'
			});
		}
		// init
		$modal = $("#bsmodal");
		$modal.modal(opt);

		// bind
		if (opt.showOk) {
			XJJ.on('button.bsok', 'click', function() {

				if (opt.onOk) {
					$('button.bsok').attr("disabled", true);
					if (!$("#bsmodal form").valid()) {
						$('button.bsok').attr("disabled", false);
						return false;
					}
					opt.onOk();
				}

			});
		}
		if (opt.showCancel) {
			XJJ.on('button.bscancel', 'click', function() {
				if (opt.onCancel) {
					opt.onCancel();
				}
				$modal.modal('hide');
			});
		}
		if (opt.buttons && opt.buttons.length > 0) {
			for ( var item in opt.buttons) {
				XJJ.on('button.bs' + item.title, 'click', function() {
					if (item.onClick) {
						if (item.onClick()) {
							$modal.modal('hide');
						}
					}
				});
			}
		}
		XJJ.on('#bsmodal', 'hidden.bs.modal', function() {
			$modal.remove();
		});
		// show
		$modal.modal('show');
	};
	
	
	//======================Tab页开始=============================
	XJJ.tabOptions={
		id:undefined,
		text: '新境界',
		navs: undefined,
		url : undefined,
		closeable: true
	};

	XJJ.showTab=function(options){
		var tOpions = $.extend({},XJJ.tabOptions,options);
		
		$("#tabContainer").data("tabs").addTab(tOpions);
	};
	//======================Tab页结束=============================
	
	
	// XJJ crud query
	XJJ.query = function(options){
		var opt=XJJ.getOptions(options);
		if(!opt.url){
			var $form=$("#"+opt.queryForm);
			opt.url=$form.qdata().url;
			
			var startDate = $("#"+opt.queryForm+" input[name='startDate']").val();
			var endDate = $("#"+opt.queryForm+" input[name='endDate']").val();
			
			if(startDate && endDate && null != startDate && null != endDate && ""!=startDate && ""!=endDate)
			{
				if(startDate>endDate)
		    	{
		    		return;
		    	}
			}
			
		}
		if(!opt.url){
			var $list=$("#"+opt.listId);
			opt.url=$list.qdata().url;
		}
		
		XJJ.list(opt);
	};
	// XJJ crud list
	XJJ.list = function(options){
		var opt=XJJ.getOptions(options);
		var opts={
				tabId   : opt.tabId,
				url		: opt.url,
				form	: opt.queryForm,
				target  : opt.listId,
				data	: [{name:'pageSize',value:opt.pageSize},{name:'currentPage',value:opt.currentPage}],
				dataType: 'html'
			}
		opts.onCompleted = function(html,status){
			if(opt.onCompleted){
				opt.onCompleted.apply(this,[html,status]);
			}
		};
		
		XJJ.ajax(opts);
	};
	
	// XJJ crud page
	XJJ.page = function(options){
		var opt=XJJ.getOptions(options);;
		var $size=$('#bsmodal').find('.crudsize');
		if(!$size.val()){
			$size=$('input.crudsize');
		}
		var size = parseInt($.trim($size.val()));
		
		opt.pageSize 		= size;
		XJJ.query(opt);
	};
	
	XJJ.go = function(options){
		var opt=XJJ.getOptions(options);
		var id = opt.id;
		var $page=$('#bsmodal').find('.crudpage');
		if(!$page.val()){
			if(id){
				$page=$('#'+id+'bslist input.crudpage');
			}else{
				$page=$('input.crudpage');
			}
		}
		var page = parseInt($.trim($page.val()));
		var $size=$('#bsmodal').find('.crudsize');
		if(!$size.val()){
			if(id){
				$size=$('#'+id+'bslist input.crudsize');
			}else{
				$size=$('input.crudsize');
			}
		}
		var size = parseInt($.trim($size.val()));
		
		if(size&&size!=null){
			opt.pageSize 		= size;
		}
		if(page&&page!=null){
			opt.currentPage 	= page;
		}
		
		XJJ.query(opt);
	};
	
	
	// XJJ msg confirm
	XJJ.confirm = function(options, ok, cancel){
		var opt = $.extend({}, XJJ.msgoptions);
		if(typeof options == 'string'){
			opt.msg = options;
		}else{
			$.extend(opt, options);
		}
		opt.title='确认信息';
		opt.showOk=true;
		opt.showCancel=true;
		opt.onOk=ok;
		opt.onCancel=cancel;
		opt.big=false;
		XJJ.dialog(opt);
	}
	
	XJJ.msg = function(msg, type){
		var dmsg = msg ? msg : 'msg';
		var dtype = type ? type : 'ok';
		
		if(dtype == 'ok'){
			$.gritter.add({  
			    title: '提示消息',  
			    text: dmsg,  
			    sticky: false,  
			    time: 3000,  
			    speed:500,  
			    class_name: 'gritter-success gritter-light'
			});  
		}else{
			$.gritter.add({  
			    title: '温馨提示',  
			    text: dmsg,  
			    sticky: false,  
			    time: 3000,  
			    speed:500,  
			    class_name: 'gritter-error gritter-light'
			});
		}
	};
	// bs msg msgok
	XJJ.msgok = function(msg){
		XJJ.msg(msg, 'ok');
	};
	// bs msg msger
	XJJ.msger = function(msg){
		XJJ.msg(msg, 'er');
	};
	
	
	// XJJ crud save
	XJJ.save = function(options){
		
		var opts = {
				id:undefined,
				message:		false,
				form:			undefined,
				target:		undefined,
				method:		undefined,
				url:				undefined,
				onCompleted:undefined,
				onSubmit:		undefined,
				onSuccess:	undefined,
				onError:		undefined
		};
		if(options&&options.id){
			opts.id=options.id;
		}
		if(options&&options.url){
			opts.url=options.url;
		}else{
			options=XJJ.getOptions(options);
			opts.form=options.inputForm;
		}
		opts.method	= opts.method || 'POST';
		opts.dataType='json';
		
		//如果从前端传来了回调函数，就使用前端的
		if(options.onCompleted)
		{
			opts.onCompleted = options.onCompleted;
		}else
		{
			opts.onCompleted=function(data,textStatus){
				if(data){
					if(data.type == 'success'){
						$modal.modal('hide');
						XJJ.msgok(data.message);
						opts.onCompleted=null;
						XJJ.go(opts);
					}else{
						
						$('button.bsok').attr("disabled",false);
						XJJ.msger(data.message);
						return false;
					}
				}else{
					XJJ.msger('ajax fail!');
					return false;
				}
		    }
		}
		
		console.info("====ajax opts==="+opts);
		console.info(opts);
		XJJ.ajax(opts);
	};
	
	
	/**
	 * 删除
	 * @param url 删除地址
	 * @param msg 确认框消息
	 * @param isBatchDel 是否批量删除
	 */
	XJJ.del = function(url, msg,isBatchDel,options){
		var opt=XJJ.getOptions(options);
		if(url && msg){
			
			var ids = XJJ.selectedIds(opt);
			if(isBatchDel){
				if(!ids){
					XJJ.msger("请选择要操作的数据！");
					return;
				}
			}else{
				if((url.endWith('delete')||url.endWith('delete/'))&&!ids){
					XJJ.msger("请选择要操作的数据！");
					return;
				}
			}
			
			opt.url=url;
			
			if(ids.length>1){// 删除多条
				opt.url+=ids;
				XJJ.confirm(msg, function(){
					XJJ.save(opt);
				});
			}else{
				XJJ.confirm(msg, function(){
					
					XJJ.save(opt);
				});
			}
			
		}
	};
	// XJJ crud del ids
	XJJ.selectedIds = function(options){
		var opt=XJJ.getOptions(options);
		var ids = '?';
		$('#'+opt.listId).find('input.bscheck').each(function(){
			var $this = $(this);
			if($this.prop('checked')){
				var id = $this.qdata().id;
				if(id) ids = ids + 'ids=' + id + '&';
			}
		});
		return ids == '?' ? '' : (ids.substring(0, ids.length-1));
	};
	
	
	
	/**
	 * 初始化添加或者修改页面
	 * - 日期输入控件的格式化
	 * - 提交按钮的事件添加
	 * - 由于使用ajax提交，控件的回车时间变为Tab
	 * - 停止其他的默认提交行为
	 * - 校验信息的初始化
	 */
	XJJ.initInput=function(options){
		var opts = XJJ.getOptions(options);
		var $input = $("#"+opts.inputForm);
		//处理页面样式
		$input.find('input[type!=button][type!=submit][type!=reset][type!=radio][type!=checkbox]').addClass('form-control');
		$input.find('select').addClass('form-control');
		//多选框初始化
		//$input.find("select.multiselect").multiselect({enableCaseInsensitiveFiltering:true,maxHeight:250,nonSelectedText:'请选择',numberDisplayed:6});	//加入日期
		$input.find('input.'+opts.cssDatePicker).datetimepicker({format: 'yyyy-mm-dd',minView:2,autoclose:true,todayBtn:true,language:'zh-CN'});
		$input.find('input.'+opts.cssTime).datetimepicker({format: 'yyyy-mm-dd hh:ii:ss',autoclose:true,todayBtn:true,language:'zh-CN'});
		//$input.find('input.'+options.cssNumber).numberpicker();
		
	
		//form中加入回车事件变为Tab
		$input.find('input[type!=button][type!=submit][type!=reset]').keydown(function(event){
	   		if(event.keyCode ==13){
	           event.keyCode = 9;
	           event.preventDefault();//阻止默认的提交动作
	        }
	   	});
		
	   	//加入校验
		$input.validation();
		//加入低版本浏览器placeholder支持
		if( !('placeholder' in document.createElement('input')) ){
			$('input[placeholder],textarea[placeholder]').each(function(){
				var that = $(this),   
				text= that.attr('placeholder');    
				that.tooltip({title:text,trigger:'hover'});
				   
			});   
		}
	};
	
	
	
	XJJ.initQuery=function(options){
		var opts = XJJ.getOptions(options);
		var $query = $("#"+opts.queryForm);
		//处理页面样式
		$query.find('input[type!=button][type!=submit][type!=reset][type!=radio][type!=checkbox]').addClass('form-control');
		$query.find('select').addClass('form-control');
		//多选框初始化
		$query.find('input.'+opts.cssDatePicker).datetimepicker({format: 'yyyy-mm-dd',minView:2,autoclose:true,todayBtn:true,language:'zh-CN'});
		$query.find('input.'+opts.cssTime).datetimepicker({format: 'yyyy-mm-dd hh:ii:ss',autoclose:true,todayBtn:true,language:'zh-CN'});
		
	
		//form中加入回车事件变为Tab
		$query.find('input[type!=button][type!=submit][type!=reset]').keydown(function(event){
	   		if(event.keyCode ==13){
	           event.keyCode = 9;
	           event.preventDefault();//阻止默认的提交动作
	        }
	   	});
		
	  
		//加入低版本浏览器placeholder支持
		if( !('placeholder' in document.createElement('input')) ){
			$('input[placeholder],textarea[placeholder]').each(function(){
				var that = $(this),   
				text= that.attr('placeholder');    
				that.tooltip({title:text,trigger:'hover'});
				   
			});   
		}
	};
	
	/**
	 * 绑定全选
	 */
	XJJ.on('input.bscheckall','change',function(){
		var $this = $(this);
		var isCheck = $this.prop('checked');
		if(isCheck){
			$this.parents('table').find('input.bscheck').each(function(){
				$(this).prop('checked', true);
			});
		}else{
			$this.parents('table').find('input.bscheck').each(function(){
				$(this).prop('checked', false);
			});
		}
	});
	
})(jQuery, window);
