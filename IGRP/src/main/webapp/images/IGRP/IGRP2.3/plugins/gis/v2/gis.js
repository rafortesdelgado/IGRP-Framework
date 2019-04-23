(function(){

	var modules = {};

	function MapController(dom){
		
		var app    = this,

			data   = $(dom).data().config || {},

			id     = $(dom).attr('id') || 'map-'+$.IGRP.utils.unique(),

			config = $.extend({

				id : id,

				dom : dom,

				groupLayers : [],

				widgets : []

			}, data ),
			
			urlMapSettings = GetURLMapSettings(id);
		
			
		if(urlMapSettings)
			
			config = $.extend( config, urlMapSettings );

		app.dom    = dom;
		
		app.id     = id;

		$(app.dom).attr('id' ,id);

		app.loading = function(v){
			
			var action = v ? 'addClass' : 'removeClass';

			$(dom)[action]('loading');

		};
		
		app.addWidgetButton = function(btn){
			
			$('.gis-widgets-controller', app.dom).append(btn);

		};

		if(modules.Map){

			app.map = new modules.Map( app, config );

			if(modules.BaseMaps)

				app.basemaps = new modules.BaseMaps( app, config.baseMaps );

			if(modules.Layers)

				app.layers = new modules.Layers( app, config.groupLayers );

			if(modules.Widgets)

				app.widgets = new modules.Widgets( app, config.widgets );

			if(modules.Panels)

				app.panels = new modules.Panels(dom);

		};

		return app;

	};
	
	function GetURLMapSettings(id){
		
		var params = document.IGRPParams || $.IGRP.utils.url.getParams(),
		
			mapsettings = params.gis_map_settings,
		
			settings = null;

		if(typeof mapsettings === 'string')
			
			mapsettings = JSON.parse(decodeURI(params.gis_map_settings ));

		if(mapsettings){
			
			try{
				
				var mapSettings    = mapsettings;
			
				if( mapSettings && mapSettings.id == id )
					
					settings = mapSettings;

			}catch(err){
				
				console.log(err);
				
			}

		}
		
		return settings;
		
	};

	window.GIS = $.IGRP.component('GIS',{
		
		path : $.IGRP.path+'/plugins/gis/v2',

		maps : [],

		module : function(name, options){

			if(options)

				modules[name] = options;

			return modules[name];

		},
		
		widgets : {
			
			items : {},
			
			register : function(name, options){
				
				if(!GIS.widgets.items[name])
					
					GIS.widgets.items[name] =  options;
				
			},
			
			get : function(name){
				
				return GIS.widgets.items[name];
				
			}
			
		},
		
		getMap : function(id){
			
			console.log(id);
			console.log(maps);
			
		},

		init : function(elements){
			
			elements = elements || $('.igrp-map-os-wrapper');
			
			elements.each(function(i, e){
				
				GIS.maps.push( new MapController(e) );

			});

		}

	});
	
	
	$(window).load(function(){
		
		setTimeout(function(){
			
			GIS.init();
			
		},2);
		
	});
	

})();