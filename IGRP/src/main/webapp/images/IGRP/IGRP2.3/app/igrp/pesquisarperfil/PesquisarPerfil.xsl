<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8" indent="yes" doctype-system="about:legacy-compat"/><xsl:template match="/"><html><head><xsl:call-template name="IGRP-head"/><link rel="stylesheet" type="text/css" href="{$path}/plugins/view/igrp.view.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/toolsbar/toolsbar.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/datatable/dataTables.bootstrap.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/igrp.tables.css?v={$version}"/><style>th.bs-checkbox span.checkmark {    display: none;}th.igrp-table-ctx-th{  width: 230px !important;}</style></head><body class="{$bodyClass} sidebar-off"><xsl:call-template name="IGRP-topmenu"/><form method="POST" class="IGRP-form" name="formular_default" enctype="multipart/form-data"><div class="container-fluid"><div class="row"><xsl:call-template name="IGRP-sidebar"/><div class="col-sm-9 col-md-10 col-md-offset-2 col-sm-offset-3 main" id="igrp-contents"><div class="content"><div class="row row-msg"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:apply-templates mode="igrp-messages" select="rows/content/messages"/></div></div></div><div class="row " id="row-131ec2b5"><div class="gen-column col-sm-6"><div class="gen-inner"><xsl:if test="rows/content/sectionheader_1"><section class="content-header gen-container-item " gen-class="" item-name="sectionheader_1"><h2 class="disable-output-escaping"><xsl:value-of disable-output-escaping="yes" select="rows/content/sectionheader_1/fields/sectionheader_1_text/value"/></h2></section></xsl:if></div></div><div class="gen-column col-sm-3"><div class="gen-inner"><xsl:if test="rows/content/view_1"><div class="box clearfix view-block gen-container-item sukundi" has-img="false" template="info" gen-class="sukundi" item-name="view_1"><div class="box-body"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/view_1/fields"/><xsl:if test="rows/content/view_1/fields/view_1_img"><img src="{rows/content/view_1/fields/view_1_img/value}"/></xsl:if><div class="view-body clearfix "><xsl:if test="rows/content/view_1/fields/help"><div class="view-item gen-fields-holder" item-name="help"><a href="{rows/content/view_1/fields/help/value}" target="_newtab" target-fields="" request-fields=""><i class="fa fa-question-circle"/><span><span><xsl:value-of select="rows/content/view_1/fields/help/label"/></span></span></a></div></xsl:if></div></div></div></xsl:if></div></div><div class="gen-column col-sm-3"><div class="gen-inner"><xsl:if test="rows/content/toolsbar_1"><div class="toolsbar-holder default gen-container-item " gen-structure="toolsbar" gen-fields=".btns-holder&gt;a.btn" gen-class="" item-name="toolsbar_1"><div class="btns-holder  pull-right" role="group"><xsl:apply-templates select="rows/content/toolsbar_1" mode="gen-buttons"><xsl:with-param name="vertical" select="'true'"/><xsl:with-param name="outline" select="'false'"/></xsl:apply-templates></div></div></xsl:if></div></div></div><div class="row " id="row-f0fb3b5c"><div class="gen-column col-sm-12"><div class="gen-inner"><xsl:if test="rows/content/table_1"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_1"><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_1" class="table table-striped   IGRP_contextmenu " exports="null"><thead><tr><xsl:if test="rows/content/table_1/fields/organica"><th td-name="organica" align="left" show-label="true" class="text  gen-fields-holder" group-in=""><span><xsl:value-of select="rows/content/table_1/fields/organica/label"/></span></th></xsl:if><xsl:if test="rows/content/table_1/fields/estado"><th class="bs-checkbox gen-fields-holder" align="center"><label class="container-box "><span><xsl:value-of select="rows/content/table_1/fields/estado/label"/></span><input type="checkbox" class="IGRP_checkall" check-rel="estado" data-title="Estado" data-toggle="tooltip"/><span class="checkmark"/></label></th></xsl:if><xsl:if test="rows/content/table_1/fields/descricao"><th td-name="descricao" align="left" show-label="true" class="text  gen-fields-holder" group-in=""><span><xsl:value-of select="rows/content/table_1/fields/descricao/label"/></span></th></xsl:if><xsl:if test="rows/content/table_1/fields/codigo"><th td-name="codigo" align="left" show-label="true" class="text  gen-fields-holder" group-in=""><span><xsl:value-of select="rows/content/table_1/fields/codigo/label"/></span></th></xsl:if><xsl:if test="rows/content/table_1/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_1/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><input type="hidden" name="p_id_fk" value="{id}"/><input type="hidden" name="p_id_fk_desc" value="{id_desc}"/><xsl:if test="organica"><td align="left" data-order="{organica}" data-row="{position()}" data-title="{../../../fields/organica/label}" class="text" item-name="organica"><span class=""><xsl:value-of select="organica"/></span></td></xsl:if><xsl:if test="estado"><td align="center" data-row="{position()}" data-title="{../../label/estado}" class="bs-checkbox" item-name="estado" data-order="{estado_check=estado}"><xsl:if test="estado != '-0'"><label class="container-box "><input type="checkbox" name="p_estado_fk" value="{estado}" check-rel="estado" class="checkdcontrol"><xsl:if test="estado_check=estado"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/><span class="checkmark"/></label></xsl:if><input type="hidden" name="p_estado_check_fk" class="estado_check"><xsl:if test="estado_check=estado"><xsl:attribute name="value"><xsl:value-of select="estado_check"/></xsl:attribute></xsl:if></input><xsl:if test="estado_check!=estado"><input type="hidden" name="p_estado_fk" value="{estado}" class="estado"/></xsl:if></td></xsl:if><xsl:if test="descricao"><td align="left" data-order="{descricao}" data-row="{position()}" data-title="{../../../fields/descricao/label}" class="text" item-name="descricao"><span class=""><xsl:value-of select="descricao"/></span></td></xsl:if><xsl:if test="codigo"><td align="left" data-order="{codigo}" data-row="{position()}" data-title="{../../../fields/codigo/label}" class="text" item-name="codigo"><span class=""><xsl:value-of select="codigo"/></span></td></xsl:if><xsl:if test="//rows/content/table_1/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/><xsl:with-param name="type" select="'inl'"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if></div></div></div><div class="row " id="row-98c2d226"><div class="gen-column col-sm-12"><div class="gen-inner"><xsl:if test="rows/content/form_1"><div class="box igrp-forms gen-container-item sukundi" gen-class="sukundi" item-name="form_1"><div class="box-body"><div role="form"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/form_1/fields"/></div></div><xsl:apply-templates select="rows/content/form_1/tools-bar" mode="form-buttons"/></div></xsl:if></div></div></div></div></div></div></div><xsl:call-template name="IGRP-bottom"/></form><script type="text/javascript" src="{$path}/core/igrp/form/igrp.forms.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/jquery.dataTables.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/dataTables.bootstrap.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/igrp.table.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/bootstrap-contextmenu.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/table.contextmenu.js?v={$version}"/></body></html></xsl:template><xsl:include href="../../../xsl/tmpl/IGRP-functions.tmpl.xsl?v=21"/><xsl:include href="../../../xsl/tmpl/IGRP-variables.tmpl.xsl?v=21"/><xsl:include href="../../../xsl/tmpl/IGRP-home-include.tmpl.xsl?v=21"/><xsl:include href="../../../xsl/tmpl/IGRP-utils.tmpl.xsl?v=21"/><xsl:include href="../../../xsl/tmpl/IGRP-table-utils.tmpl.xsl?v=21"/><xsl:include href="../../../xsl/tmpl/IGRP-form-utils.tmpl.xsl?v=21"/></xsl:stylesheet>
