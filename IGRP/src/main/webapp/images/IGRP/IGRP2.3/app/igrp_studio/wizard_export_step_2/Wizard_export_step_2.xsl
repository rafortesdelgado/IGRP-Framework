<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8" indent="yes" doctype-system="about:legacy-compat"/><xsl:template match="/"><html><head><xsl:call-template name="IGRP-head"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/toolsbar/toolsbar.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/datatable/dataTables.bootstrap.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/core/igrp/table/igrp.tables.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/plugins/select2/select2.min.css?v={$version}"/><link rel="stylesheet" type="text/css" href="{$path}/plugins/select2/select2.style.css?v={$version}"/><style>.box #filtro{    box-shadow: 0 0px 0px rgba(0,0,0,0.1);}.isac{    box-shadow: 0 0px 0px rgba(0,0,0,0.1);}.igrp-box-holder.box&gt;.box-body {    padding: 10px 10px 0;}</style></head><body class="{$bodyClass} sidebar-off"><xsl:call-template name="IGRP-topmenu"/><form method="POST" class="IGRP-form" name="formular_default" enctype="multipart/form-data"><div class="container-fluid"><div class="row"><xsl:call-template name="IGRP-sidebar"/><div class="col-sm-9 col-md-10 col-md-offset-2 col-sm-offset-3 main" id="igrp-contents"><div class="content"><div class="row row-msg"><div class="gen-column col-md-12"><div class="gen-inner"><xsl:apply-templates mode="igrp-messages" select="rows/content/messages"/></div></div></div><div class="row " id="row-fd2d41db"><div class="gen-column col-sm-6"><div class="gen-inner"><xsl:if test="rows/content/sectionheader_1"><section class="content-header gen-container-item " gen-class="" item-name="sectionheader_1"><h2><xsl:value-of disable-output-escaping="yes" select="rows/content/sectionheader_1/fields/sectionheader_1_text/value"/></h2></section></xsl:if></div></div><div class="gen-column col-sm-6"><div class="gen-inner"><xsl:if test="rows/content/toolsbar_1"><div class="toolsbar-holder default gen-container-item " gen-structure="toolsbar" gen-fields=".btns-holder&gt;a.btn" gen-class="" item-name="toolsbar_1"><div class="btns-holder   pull-right" role="group"><xsl:apply-templates select="rows/content/toolsbar_1" mode="gen-buttons"><xsl:with-param name="vertical" select="'true'"/><xsl:with-param name="outline" select="'false'"/></xsl:apply-templates></div></div></xsl:if></div></div></div><div class="row " id="row-361a9b17"><div class="gen-column col-sm-6"><div class="gen-inner"><xsl:if test="rows/content/box_paginas"><div class="box igrp-box-holder gen-container-item " gen-class="" item-name="box_paginas"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/box_paginas/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'false'"/></xsl:call-template><div class="box-body" gen-preserve-content="true"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/box_paginas/fields"/><div><div class="row " id="row-a0bfa2e5"><div class="gen-column col-sm-12"><div class="gen-inner"><xsl:if test="rows/content/form_2"><div class="box igrp-forms gen-container-item " id="filtro" gen-class="" item-name="form_2" style="margin: -11px;"><div class="box-body"><div role="form"><xsl:apply-templates mode="form-hidden-fields" select="rows/content/form_2/fields"/><xsl:if test="rows/content/form_2/fields/modulo"><div class="col-sm-12 form-group  gen-fields-holder" item-name="modulo" item-type="select"><label for="{rows/content/form_2/fields/modulo/@name}"><xsl:value-of select="rows/content/form_2/fields/modulo/label"/></label><select class="form-control select2 IGRP_change" id="form_2_modulo" name="{rows/content/form_2/fields/modulo/@name}" multiple="multiple"><xsl:call-template name="setAttributes"><xsl:with-param name="field" select="rows/content/form_2/fields/modulo"/></xsl:call-template><xsl:for-each select="rows/content/form_2/fields/modulo/list/option"><option value="{value}" label="{text}"><xsl:if test="@selected='true'"><xsl:attribute name="selected">selected</xsl:attribute></xsl:if><span><xsl:value-of select="text"/></span></option></xsl:for-each></select></div></xsl:if></div></div><xsl:apply-templates select="rows/content/form_2/tools-bar" mode="form-buttons"/></div></xsl:if><xsl:if test="rows/content/table_pagina"><div class="box box-table-contents gen-container-item isac" gen-class="isac" item-name="table_pagina"><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_pagina" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_pagina/fields/pagina_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="pagina_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_pagina/fields/descricao_pagina"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_pagina/fields/descricao_pagina/label"/></span></th></xsl:if><xsl:if test="rows/content/table_pagina/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_pagina/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="pagina_ids"><td align="" data-row="{position()}" data-title="{../../label/pagina_ids}" class="bs-checkbox" item-name="pagina_ids" data-order="{pagina_ids_check=pagina_ids}"><xsl:if test="pagina_ids != '-0'"><label class=""><input type="checkbox" name="p_pagina_ids" value="{pagina_ids}" check-rel="pagina_ids"><xsl:if test="pagina_ids_check=pagina_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_pagina"><td align="left" data-order="{descricao_pagina}" data-row="{position()}" data-title="{../../../fields/descricao_pagina/label}" class="text" item-name="descricao_pagina"><span class=""><xsl:value-of select="descricao_pagina"/></span></td></xsl:if><xsl:if test="//rows/content/table_pagina/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if></div></div></div></div></div></div></xsl:if></div></div><div class="gen-column col-sm-6"><div class="gen-inner"><xsl:if test="rows/content/table_bpmn"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_bpmn"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_bpmn/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_bpmn" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_bpmn/fields/bpmn_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="bpmn_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_bpmn/fields/descricao_bpmn"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_bpmn/fields/descricao_bpmn/label"/></span></th></xsl:if><xsl:if test="rows/content/table_bpmn/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_bpmn/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="bpmn_ids"><td align="" data-row="{position()}" data-title="{../../label/bpmn_ids}" class="bs-checkbox" item-name="bpmn_ids" data-order="{bpmn_ids_check=bpmn_ids}"><xsl:if test="bpmn_ids != '-0'"><label class=""><input type="checkbox" name="p_bpmn_ids" value="{bpmn_ids}" check-rel="bpmn_ids"><xsl:if test="bpmn_ids_check=bpmn_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_bpmn"><td align="left" data-order="{descricao_bpmn}" data-row="{position()}" data-title="{../../../fields/descricao_bpmn/label}" class="text" item-name="descricao_bpmn"><span class=""><xsl:value-of select="descricao_bpmn"/></span></td></xsl:if><xsl:if test="//rows/content/table_bpmn/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_report"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_report"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_report/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_report" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_report/fields/report_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="report_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_report/fields/descricao_report"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_report/fields/descricao_report/label"/></span></th></xsl:if><xsl:if test="rows/content/table_report/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_report/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="report_ids"><td align="" data-row="{position()}" data-title="{../../label/report_ids}" class="bs-checkbox" item-name="report_ids" data-order="{report_ids_check=report_ids}"><xsl:if test="report_ids != '-0'"><label class=""><input type="checkbox" name="p_report_ids" value="{report_ids}" check-rel="report_ids"><xsl:if test="report_ids_check=report_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_report"><td align="left" data-order="{descricao_report}" data-row="{position()}" data-title="{../../../fields/descricao_report/label}" class="text" item-name="descricao_report"><span class=""><xsl:value-of select="descricao_report"/></span></td></xsl:if><xsl:if test="//rows/content/table_report/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_dao"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_dao"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_dao/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_dao" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_dao/fields/dao_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="dao_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_dao/fields/descricao_dao"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_dao/fields/descricao_dao/label"/></span></th></xsl:if><xsl:if test="rows/content/table_dao/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_dao/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="dao_ids"><td align="" data-row="{position()}" data-title="{../../label/dao_ids}" class="bs-checkbox" item-name="dao_ids" data-order="{dao_ids_check=dao_ids}"><xsl:if test="dao_ids != '-0'"><label class=""><input type="checkbox" name="p_dao_ids" value="{dao_ids}" check-rel="dao_ids"><xsl:if test="dao_ids_check=dao_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_dao"><td align="left" data-order="{descricao_dao}" data-row="{position()}" data-title="{../../../fields/descricao_dao/label}" class="text" item-name="descricao_dao"><span class=""><xsl:value-of select="descricao_dao"/></span></td></xsl:if><xsl:if test="//rows/content/table_dao/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_connections"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_connections"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_connections/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_connections" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_connections/fields/conexao_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="conexao_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_connections/fields/descricao_conexao"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_connections/fields/descricao_conexao/label"/></span></th></xsl:if><xsl:if test="rows/content/table_connections/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_connections/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="conexao_ids"><td align="" data-row="{position()}" data-title="{../../label/conexao_ids}" class="bs-checkbox" item-name="conexao_ids" data-order="{conexao_ids_check=conexao_ids}"><xsl:if test="conexao_ids != '-0'"><label class=""><input type="checkbox" name="p_conexao_ids" value="{conexao_ids}" check-rel="conexao_ids"><xsl:if test="conexao_ids_check=conexao_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_conexao"><td align="left" data-order="{descricao_conexao}" data-row="{position()}" data-title="{../../../fields/descricao_conexao/label}" class="text" item-name="descricao_conexao"><span class=""><xsl:value-of select="descricao_conexao"/></span></td></xsl:if><xsl:if test="//rows/content/table_connections/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_domain"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_domain"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_domain/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_domain" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_domain/fields/domain_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="domain_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_domain/fields/descricao_domain"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_domain/fields/descricao_domain/label"/></span></th></xsl:if><xsl:if test="rows/content/table_domain/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_domain/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="domain_ids"><td align="" data-row="{position()}" data-title="{../../label/domain_ids}" class="bs-checkbox" item-name="domain_ids" data-order="{domain_ids_check=domain_ids}"><xsl:if test="domain_ids != '-0'"><label class=""><input type="checkbox" name="p_domain_ids" value="{domain_ids}" check-rel="domain_ids"><xsl:if test="domain_ids_check=domain_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_domain"><td align="left" data-order="{descricao_domain}" data-row="{position()}" data-title="{../../../fields/descricao_domain/label}" class="text" item-name="descricao_domain"><span class=""><xsl:value-of select="descricao_domain"/></span></td></xsl:if><xsl:if test="//rows/content/table_domain/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_modulo"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_modulo"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_modulo/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_modulo" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_modulo/fields/modulo_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="modulo_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_modulo/fields/descricao_modulo"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_modulo/fields/descricao_modulo/label"/></span></th></xsl:if><xsl:if test="rows/content/table_modulo/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_modulo/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="modulo_ids"><td align="" data-row="{position()}" data-title="{../../label/modulo_ids}" class="bs-checkbox" item-name="modulo_ids" data-order="{modulo_ids_check=modulo_ids}"><xsl:if test="modulo_ids != '-0'"><label class=""><input type="checkbox" name="p_modulo_ids" value="{modulo_ids}" check-rel="modulo_ids"><xsl:if test="modulo_ids_check=modulo_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_modulo"><td align="left" data-order="{descricao_modulo}" data-row="{position()}" data-title="{../../../fields/descricao_modulo/label}" class="text" item-name="descricao_modulo"><span class=""><xsl:value-of select="descricao_modulo"/></span></td></xsl:if><xsl:if test="//rows/content/table_modulo/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if><xsl:if test="rows/content/table_menu"><div class="box box-table-contents gen-container-item " gen-class="" item-name="table_menu"><xsl:call-template name="box-header"><xsl:with-param name="title" select="rows/content/table_menu/@title"/><xsl:with-param name="collapsible" select="'true'"/><xsl:with-param name="collapsed" select="'true'"/></xsl:call-template><div class="box-body "><div class="table-contents-head"><div class="table-contents-inner"></div></div><div class="table-box"><div class="table-box-inner"><table id="table_menu" class="table table-striped  igrp-data-table  " exports=""><thead><tr><xsl:if test="rows/content/table_menu/fields/menu_ids"><th class="bs-checkbox gen-fields-holder" align="center"><span/><input type="checkbox" class="IGRP_checkall" check-rel="menu_ids" data-title="" data-toggle="tooltip"/></th></xsl:if><xsl:if test="rows/content/table_menu/fields/descricao_menu"><th align="left" class=" gen-fields-holder"><span><xsl:value-of select="rows/content/table_menu/fields/descricao_menu/label"/></span></th></xsl:if><xsl:if test="rows/content/table_menu/table/context-menu/item"><th class="igrp-table-ctx-th"/></xsl:if></tr></thead><tbody><xsl:for-each select="rows/content/table_menu/table/value/row[not(@total='yes')]"><tr><xsl:apply-templates mode="context-param" select="context-menu"/><xsl:if test="menu_ids"><td align="" data-row="{position()}" data-title="{../../label/menu_ids}" class="bs-checkbox" item-name="menu_ids" data-order="{menu_ids_check=menu_ids}"><xsl:if test="menu_ids != '-0'"><label class=""><input type="checkbox" name="p_menu_ids" value="{menu_ids}" check-rel="menu_ids"><xsl:if test="menu_ids_check=menu_ids"><xsl:attribute name="checked">checked</xsl:attribute></xsl:if></input><span class="slider round"/></label></xsl:if></td></xsl:if><xsl:if test="descricao_menu"><td align="left" data-order="{descricao_menu}" data-row="{position()}" data-title="{../../../fields/descricao_menu/label}" class="text" item-name="descricao_menu"><span class=""><xsl:value-of select="descricao_menu"/></span></td></xsl:if><xsl:if test="//rows/content/table_menu/table/context-menu/item"><td class="igrp-table-ctx-td"><xsl:apply-templates select="../../context-menu" mode="table-context-inline"><xsl:with-param name="row-params" select="context-menu"/></xsl:apply-templates></td></xsl:if></tr></xsl:for-each></tbody></table></div></div></div></div></xsl:if></div></div></div></div></div></div></div><xsl:call-template name="IGRP-bottom"/></form><script type="text/javascript" src="{$path}/core/igrp/form/igrp.forms.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/jquery.dataTables.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/datatable/dataTables.bootstrap.min.js?v={$version}"/><script type="text/javascript" src="{$path}/core/igrp/table/igrp.table.js?v={$version}"/><script type="text/javascript" src="{$path}/plugins/select2/select2.full.min.js?v={$version}"/><script type="text/javascript" src="{$path}/plugins/select2/select2.init.js?v={$version}"/></body></html></xsl:template><xsl:include href="../../../xsl/tmpl/IGRP-functions.tmpl.xsl?v=9"/><xsl:include href="../../../xsl/tmpl/IGRP-variables.tmpl.xsl?v=9"/><xsl:include href="../../../xsl/tmpl/IGRP-home-include.tmpl.xsl?v=9"/><xsl:include href="../../../xsl/tmpl/IGRP-utils.tmpl.xsl?v=9"/><xsl:include href="../../../xsl/tmpl/IGRP-form-utils.tmpl.xsl?v=9"/><xsl:include href="../../../xsl/tmpl/IGRP-table-utils.tmpl.xsl?v=9"/></xsl:stylesheet>
