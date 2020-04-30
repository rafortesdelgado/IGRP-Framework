<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8" indent="yes" doctype-system="about:legacy-compat"/><xsl:template match="/"><html><head><xsl:call-template name="IGRP-head"/><link rel="stylesheet" type="text/css" href="{$path}/plugins/view/igrp.view.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/toolsbar/toolsbar.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/datatable/dataTables.bootstrap.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/igrp.tables.css?v={$version}"/><style>th.ative .container-box{padding-left: 7px !important;}</style></head><body class="{$bodyClass} sidebar-off"><xsl:call-template name="IGRP-topmenu"/><form method="POST" class="IGRP-form" name="formular_default" enctype="multipart/form-data"><div class="container-fluid"><div class="row"><xsl:call-template name="IGRP-sidebar"/><div class="col-sm-9 col-md-10 col-md-offset-2 col-sm-offset-3 main" id="igrp-contents"><div class="content"><div class="row row-msg"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:apply-templates mode="igrp-messages" select="rows/content/messages"/></div></div></div><div class="row " id="row-890c79c6"><div class="gen-column col-sm-10"><div class="gen-inner"/></div><div class="gen-column col-sm-2"><div class="gen-inner"><xsl:if test="rows/content/view_1"><div class="box clearfix view-block gen-container-item " has-img="false" template="info" item-separator-border="true" gen-class="" item-name="view_1" style="style=&quot;float:right!important&quot;;"><div class="box-body"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/view_1/fields"/><xsl:if test="rows/content/view_1/fields/view_1_img"><img src="{rows/content/view_1/fields/view_1_img/value}"/></xsl:if><div class="view-body clearfix "><xsl:if test="rows/content/view_1/fields/help"><div class="view-item gen-fields-holder" item-name="help" style="float:right!important;"><a href="{rows/content/view_1/fields/help/value}" target="_newtab" sharpadbclient="" target-fields="" request-fields=""><i class="fa [object Object]"/><span><span><xsl:value-of select="rows/content/view_1/fields/help/label"/></span></span></a></div></xsl:if></div></div></div></xsl:if></div></div></div><div class="row " id="row-3cea4f08"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:if test="rows/content/toolsbar_1"><div class="toolsbar-holder default gen-container-item " gen-structure="toolsbar" gen-fields=".btns-holder&gt;a.btn" gen-class="" item-name="toolsbar_1"><div class="btns-holder  btn-group-justified" role="group"><xsl:apply-templates select="rows/content/toolsbar_1" mode="gen-buttons"><xsl:with-param name="vertical" select="'true'"/><xsl:with-param name="outline" select="'false'"/></xsl:apply-templates></div></div></xsl:if><xsl:if test="rows/content/table_1"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_1"><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_1" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_1/fields/transacao"><th class="bs-checkbox gen-fields-holder ative" align="left" style="width:20px; padding-left:0px;"><label class="container-box "><span><xsl:value-of select="rows/content/table_1/fields/transacao/label"/></span><input type="checkbox" class="IGRP_checkall" check-rel="transacao" data-title=" " data-toggle="tooltip"/><span class="checkmark"/></label></th></xsl:if><xsl:if test="rows/content/table_1/fields/nome"><th td-name="nome" align="left" show-label="true" class="text  gen-fields-holder" group-in="" style="padding-left:0px;"><span><xsl:value-of select="rows/content/table_1/fields/nome/label"/></span></th></xsl:if><xsl:if test="rows/content/table_1/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_1/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="transacao"><td align="left" data-row="{position()}" data-title="{../../label/transacao}" class="bs-checkbox ative" item-name="transacao" data-order="{transacao_check=transacao}" style="width:20px; padding-left:0px;"><xsl:if test="transacao != '-0'"><label class="container-box "><input type="checkbox" name="p_transacao_fk" value="{transacao}" check-rel="transacao" class="checkdcontrol"><xsl:if test="transacao_check=transacao"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/><span class="checkmark"/></label></xsl:if><input type="hidden" name="p_transacao_check_fk" class="transacao_check"><xsl:if test="transacao_check=transacao"><xsl:attribute name="value"><xsl:value-of select="transacao_check"/></xsl:attribute></xsl:if></input><xsl:if test="transacao_check!=transacao"><input type="hidden" name="p_transacao_fk" value="{transacao}" class="transacao"/></xsl:if></td></xsl:if><xsl:if test="nome"><td align="left" data-order="{nome}" data-row="{position()}" data-title="{../../../fields/nome/label}" class="text" item-name="nome" style="padding-left:0px;"><span class=""><xsl:value-of select="nome"/></span></td></xsl:if><xsl:if test="//rows/content/table_1/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/><xsl:with-param name="type" select="'inl'"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/toolsbar_3"><div class="toolsbar-holder default gen-container-item " gen-structure="toolsbar" gen-fields=".btns-holder&gt;a.btn" gen-class="" item-name="toolsbar_3"><div class="btns-holder  btn-group-justified" role="group"><xsl:apply-templates select="rows/content/toolsbar_3" mode="gen-buttons"><xsl:with-param name="vertical" select="'true'"/><xsl:with-param name="outline" select="'false'"/></xsl:apply-templates></div></div></xsl:if><xsl:if test="rows/content/form_1"><div class="box igrp-forms gen-container-item hidden" gen-class="hidden" item-name="form_1"><div class="box-body"><div role="form"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/form_1/fields"/></div></div><xsl:apply-templates select="rows/content/form_1/tools-bar" mode="form-buttons"/></div></xsl:if></div></div></div></div></div></div></div><xsl:call-template name="IGRP-bottom"/></form><script type="text/javascript" src="{$path}/core/igrp/form/igrp.forms.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/jquery.dataTables.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/dataTables.bootstrap.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/igrp.table.js?v={$version}"/></body></html></xsl:template><xsl:include href="../../../xsl/tmpl/IGRP-functions.tmpl.xsl?v=30"/><xsl:include href="../../../xsl/tmpl/IGRP-variables.tmpl.xsl?v=30"/><xsl:include href="../../../xsl/tmpl/IGRP-home-include.tmpl.xsl?v=30"/><xsl:include href="../../../xsl/tmpl/IGRP-utils.tmpl.xsl?v=30"/><xsl:include href="../../../xsl/tmpl/IGRP-table-utils.tmpl.xsl?v=30"/><xsl:include href="../../../xsl/tmpl/IGRP-form-utils.tmpl.xsl?v=30"/></xsl:stylesheet>
