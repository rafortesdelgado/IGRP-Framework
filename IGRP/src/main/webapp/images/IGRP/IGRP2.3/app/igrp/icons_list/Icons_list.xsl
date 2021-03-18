<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8" indent="yes" doctype-system="about:legacy-compat"/><xsl:template match="/"><html><head><xsl:call-template name="IGRP-head"/><style>.gen-fa-icon {   display: inline-block;   padding: 5px;   margin: 2px;   font-size: 16px;   cursor: pointer;   color: #5d5d5d;}.fa {   display: inline-block;   font: normal normal normal 14px/1 FontAwesome;   font-size: inherit;   text-rendering: auto;   vertical-align: middle;   -webkit-font-smoothing: antialiased;   -moz-osx-font-smoothing: grayscale;}a:focus, a:hover {   color: #23527c;   text-decoration: underline;}.gen-fa-icon.active {   box-shadow: 0px 4px 7px #7b7b7b;   outline: 1px solid #ececec;   color: black;   background: white;}ul li a {   padding: 10px 5px !important;   font-size: 12px;   text-transform: uppercase;}.tab-content {   max-height: 360px;   overflow-y: auto;   background: whitesmoke;}.nav-tabs&gt;li.active&gt;a, .nav-tabs&gt;li.active&gt;a:focus, .nav-tabs&gt;li.active&gt;a:hover {   background-color: none !important;   }.nav .open&gt;a, .nav .open&gt;a:focus, .nav .open&gt;a:hover {   background-color: none !important; }.nav-tabs&gt;li.active&gt;a, .nav-tabs&gt;li.active&gt;a:focus, .nav-tabs&gt;li.active&gt;a:hover {   background-color: none !important;   }.nav .open&gt;a, .nav .open&gt;a:focus, .nav .open&gt;a:hover {   background-color: none !important; }.pull-right {   float: right!important;}</style></head><body class="{$bodyClass} sidebar-off"><xsl:call-template name="IGRP-topmenu"/><form method="POST" class="IGRP-form" name="formular_default" enctype="multipart/form-data"><div class="container-fluid"><div class="row"><xsl:call-template name="IGRP-sidebar"/><div class="col-sm-9 col-md-10 col-md-offset-2 col-sm-offset-3 main" id="igrp-contents"><div class="content"><div class="row row-msg"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:apply-templates mode="igrp-messages" select="rows/content/messages"/></div></div></div><div class="row " id="row-07d2e658"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:if test="rows/content/form_1"><div class="box igrp-forms gen-container-item clean" gen-class="clean" item-name="form_1"><div class="box-body"><div role="form"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/form_1/fields"/></div></div><xsl:apply-templates select="rows/content/form_1/tools-bar" mode="form-buttons"/></div></xsl:if><xsl:if test="rows/content/sectionheader_1"><section class="content-header gen-container-item " gen-class="" item-name="sectionheader_1"><h2 class="disable-output-escaping"><xsl:value-of disable-output-escaping="yes" select="rows/content/sectionheader_1/fields/sectionheader_1_text/value"/></h2></section></xsl:if></div></div></div></div></div></div></div><xsl:call-template name="IGRP-bottom"/></form><script type="text/javascript" src="{$path}/core/igrp/form/igrp.forms.js?v={$version}"/><script>$(function () {   $.ajax({      type: "GET",      dataType: "json",      url: path + '/core/fontawesome/fa.icons.json',      success: function (fa) {         var holder = $('<div class="gen-fa-setter"/>'),            ul = $('<ul class="nav nav-tabs icons-list-tab"/>'),            contents = $('<div class="tab-content icons-list-tab"/>'),            searcher = $('<div class="form-group icon-searcher col-md-12"><input class="form-control" type="text" placeholder="Pesquisar"/></div>'),            searchRes = $('<div class="icon-search-fa clearfix"/>'),            idx = 0,            dropdownMenu = $('<li class="dropdown pull-right tabdrop"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-align-justify"/><b class="caret"/></a></li>'),            ulDropdownMenu = $('<ul class="dropdown-menu"/>');         holder.append([searcher, ul, contents, searchRes]);         searcher.on('keyup', 'input', function () {            var word = $(this).val(),               parent = $(this).parents('.gen-fa-setter');            if (word.length &gt; 0) {               $('.icons-list-tab', parent).hide();               $('.gen-fa-icon', parent).hide();               $('.icon-search-fa', parent).html('');               var items = $('.gen-fa-icon[title*="' + word + '"]:not(.duplicated)', parent).clone().show();               $('.icon-search-fa', parent).append(items);               $('.icon-search-fa', parent).show();            } else {               $('.icons-list-tab', parent).show();               $('.icons-list-tab .gen-fa-icon', parent).show();               $('.icon-search-fa', parent).html('');            }         });                  for (var c in fa) {            var icons = fa[c],               id = 'gen-fa-' + c,               active = idx == 0 ? 'active in' : '',               li = $('<li rel="' + c + '" class="' + active + '"><a data-toggle="tab" href="#'+id+'">' + c + '</a></li>'),               content = $('<div id="' + id + '" class="tab-pane fade ' + active + '"/>');            for (var i in icons) {               var icon = $('<span class="gen-fa-icon " parent="' + c + '" rel="' + icons[i].value + '" title="'                  + icons[i].label + '"><i class="fa ' + icons[i].value + '"/></span>');               content.append(icon);            }            contents.append(content);            if (idx &gt; 3) {               ulDropdownMenu.append(li);            } else {               ul.append(li);            }            idx++;         }         dropdownMenu.append(ulDropdownMenu);         ul.append(dropdownMenu);         $('.content').append(holder[0]);         var searchIcon = $('#p_fwl_search');         if (searchIcon[0]) {            searchIcon = searchIcon.val();            if (searchIcon) {               var chooseParent = $('span.gen-fa-icon[rel="' + searchIcon + '"]:last');               $('ul li[rel="' + chooseParent.attr('parent') + '"] a').click();               chooseParent.addClass('active');            }         }      }   });   $('body').on('click', '.gen-fa-icon', function () {      var _window, _close, iconClass, form;      _window = window.opener ? window.opener ? _close = true : _close = false : window.parent;      iconClass = $(this).attr('rel');      form = _window.document.forms['formular_default'];      var lookupName = $("*[name='jsonLookup']"),         fieldName;      lookupName = lookupName.val();      lookupName = decodeURIComponent(lookupName);      lookupName = $.parseJSON(lookupName);      $.each(lookupName, function (key) {         fieldName = key;      });      $("*[name='" + fieldName + "']", form).val(iconClass);      _close ? window.close() : _window.$.IGRP.components.iframeNav.hide();   });});</script></body></html></xsl:template><xsl:include href="../../../xsl/tmpl/IGRP-functions.tmpl.xsl?v=18"/><xsl:include href="../../../xsl/tmpl/IGRP-variables.tmpl.xsl?v=18"/><xsl:include href="../../../xsl/tmpl/IGRP-home-include.tmpl.xsl?v=18"/><xsl:include href="../../../xsl/tmpl/IGRP-utils.tmpl.xsl?v=18"/><xsl:include href="../../../xsl/tmpl/IGRP-form-utils.tmpl.xsl?v=18"/></xsl:stylesheet>
