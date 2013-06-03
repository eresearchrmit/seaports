/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */

function setSidebarHeight(){
	setTimeout(function(){
		var height = $(document).height();
		$('.grid_12').each(function () {
			height -= $(this).outerHeight();
		});
		height -= $('#site_info').outerHeight();
		height -= 1;
		$('.sidemenu').css('height', height);					   
	},100);
}

// Setup tinyMCE
function setupTinyMCE() {
    $('textarea.tinymce').tinymce({
        // Location of TinyMCE script
        script_url: 'js/tiny-mce/tiny_mce.js',

        // General options
        theme: "advanced",
        plugins: "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,advlist",

        // Theme options
        theme_advanced_buttons1: "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2: "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
        theme_advanced_buttons3: "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
        theme_advanced_buttons4: "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
        theme_advanced_toolbar_location: "top",
        theme_advanced_toolbar_align: "left",
        theme_advanced_statusbar_location: "bottom",
        theme_advanced_resizing: true,

        // Example content CSS (should be your site CSS)
        content_css: "css/content.css",

        // Drop lists for link/image/media/template dialogs
        template_external_list_url: "lists/template_list.js",
        external_link_list_url: "lists/link_list.js",
        external_image_list_url: "lists/image_list.js",
        media_external_list_url: "lists/media_list.js",

        // Replace values for the template plugin
        template_replace_values: {
            username: "Some User",
            staffid: "991234"
        }
    });
}

//setup DatePicker
function setDatePicker(containerElement) {
    var datePicker = $('#' + containerElement);
    datePicker.datepicker({
        showOn: "button",
        buttonImage: "img/calendar.gif",
        buttonImageOnly: true
    });
}

//setup dialog box
function setupDialogBox(containerElement, associatedButton) {
    $.fx.speeds._default = 500;
    $("#" + containerElement).dialog({
        autoOpen: false,
        modal: true,
        show: "fade",
        hide: "fade",
        width:"auto"
    });
    
    $("#" + associatedButton).click(function () {
        $("#" + containerElement).dialog("open");
        return false;
    });
}

//setup dialog box by class name
function setupDialogBoxByClass(containerElement, associatedButton) {
	$.fx.speeds._default = 500;
    $("#" + containerElement).dialog({
        autoOpen: false,
        modal: true,
        show: "fade",
        hide: "fade",
        width:"auto"
    });
    
    $("." + associatedButton).click(function () {
        $("#" + containerElement).dialog("open");
        return false;
    });
}

//setup confirm box
function setupConfirmBox(containerElement, associatedLink) {
	$.fx.speeds._default = 500;
	$("." + associatedLink).click(function(e) {
		e.preventDefault();
		var targetUrl = $(this).attr("href");
		
		$("#" + containerElement).dialog({
	        autoOpen: false,
	        show: "fade",
	        hide: "fade",
	        width: "auto",
	        modal: true,
	        resizable: false,
			buttons : {
				"Confirm" : function() {
					window.location.href = targetUrl;
				},
				"Cancel" : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#" + containerElement).dialog("open");
	});
	$("#" + containerElement).hide();
}

//setup collapsible elements
function setupCollapsibles(containerClass, isExpanded) {
    $("." + containerClass).accordion({
    	collapsible: true,
    	active: isExpanded
	});
}

function setupTooltips() {
	/* Lightweight tooptips library Zebra Tooltips by Stephan Gabos
	 * http://stefangabos.ro/jquery/zebra-tooltips/
	 * http://sourceforge.net/projects/zebra-tooltips/files/
	 */
	new $.Zebra_Tooltips($('.helpTooltip'), {
        'position': 'right',
        'max_width': 500,
        'background_color': '#0561B4',
        'color': 'white',
        'border': '2px solid white'
    });
}