
import ICON_FAVORITES from '../../images/favorites.svg';
import ICON_FOLDER from '../../images/folder.svg';
import ICON_SEARCH from '../../images/search.svg';
import ICON_TREEVIEW from '../../images/treeview.svg';
import ICON_SETTINGS from '../../images/settings.svg';

class ConfigAPI{
	static getConfig(userId, callback){
		let config =
		{
			appViews : [
				{
					"name":"favorites",
					"icon":ICON_FAVORITES
				},
				{
					"name":"folder",
					"icon":ICON_FOLDER
				},
				{
					"name":"search",
					"icon":ICON_SEARCH
				},
				{
					"name":"treeview",
					"icon":ICON_TREEVIEW
				}			
			],
			
			appViewsConfig : {
				"favorites":{
					"name":"favorites",
					"icon":ICON_FAVORITES,
					"isOpenNewTab":false,
					"isClosable":false
				},
				"folder":{
					"name":"folder",
					"icon":ICON_FOLDER,
					"isOpenNewTab":false
				},
				"search":{
					"name":"search",
					"icon":ICON_SEARCH,
					"isOpenNewTab":true
				},
				"treeview":{
					"name":"treeview",
					"icon":ICON_TREEVIEW,
					"isOpenNewTab":true
				},
				"settings" : {
					"name":"settings",
					"icon":ICON_SETTINGS,
					"isOpenNewTab":false
				}
			},
		
			adminAppViews : [
				{
					"name":"settings",
					"icon":ICON_SETTINGS
				}
			]
		};
		
		callback(config);
	}

	static getAllSearchTemplates(){
		let defaultTemplate = {
			"label":"Default",
			"name":"__default__",
			"searchCriterias":[
				{
					"label":"default",
					"criteria":"label",
					"value":""
				}
			],
			"availableCriterias" : [
				{
					"label":"Dog",
					"criteria":"dog",
					"value":""
				},
				{
					"label":"Cat",
					"criteria":"cat",
					"value":""
				},
				{
					"label":"Duck",
					"criteria":"duck",
					"value":""
				},
				{
					"label":"Parrot",
					"criteria":"parrot",
					"value":""
				},
				{
					"label":"Hamster",
					"criteria":"hamster",
					"value":""
				},
				{
					"label":"Goldfish",
					"criteria":"goldfish",
					"value":""
				}
			],
			"columns" : [ 
				{"name":"id", "type":"string", "label":"Id", "isHidden":true, "isSortable":false},
				{"name":"mimetype", "type":"string", "label":"MimeType", "isStateIcon":true, "filter":"select"},
				{"name":"name", "type":"string", "label":"Name", "filter":"string"},
				{"name":"nature", "type":"string", "label":"Nature", "filter":"select"},
				{"name":"type", "type":"string", "label":"Type", "filter":"string"},
				{"name":"size", "type":"long", "label":"Content Size", "filter":"long"}
			],
			"availableColumns" : [
				{"name":"type1", "type":"string", "label":"Type 1", "filter":"string"},
				{"name":"type2", "type":"string", "label":"Type 2", "filter":"string"},
				{"name":"type3", "type":"string", "label":"Type 3", "filter":"string"},
				{"name":"type4", "type":"string", "label":"Type 4", "filter":"string"},
				{"name":"type5", "type":"string", "label":"Type 5", "filter":"string"},
				{"name":"type6", "type":"string", "label":"Type And Many others things", "filter":"string"},
				{"name":"type7", "type":"string", "label":"Type And Many others things like the time where my dog eat a potatoes.", "filter":"string"},
				{"name":"type8", "type":"string", "label":"Type 8", "filter":"string"}
			]
		};

		let template2 = {
			"label":"MyCustomSearch Avecun nom super long parrceque czaza",
			"name":"CustomSearch1",
			"searchCriterias":[
				{
					"label":"Dog",
					"criteria":"dog",
					"value":""
				},
				{
					"label":"Duck",
					"criteria":"duck",
					"value":"Donald"
				}
			],
			"availableCriterias" : [
				{
					"label":"Cat",
					"criteria":"cat",
					"value":""
				},
				{
					"label":"Parrot",
					"criteria":"parrot",
					"value":""
				},
				{
					"label":"Hamster",
					"criteria":"hamster",
					"value":""
				},
				{
					"label":"Goldfish",
					"criteria":"goldfish",
					"value":""
				}
			],
			"columns" : [ 
				{"name":"id", "type":"string", "label":"Id", "isHidden":true, "isSortable":false},
				{"name":"mimetype", "type":"string", "label":"MimeType", "isStateIcon":true, "filter":"select"},
				{"name":"name", "type":"string", "label":"Name", "filter":"string"},
				{"name":"nature", "type":"string", "label":"Nature", "filter":"select"},
				{"name":"type", "type":"string", "label":"Type", "filter":"string"},
				{"name":"size", "type":"long", "label":"Content Size", "filter":"long"},
				{"name":"type1", "type":"string", "label":"Type 1", "filter":"string"},
				{"name":"type2", "type":"string", "label":"Type 2", "filter":"string"},
				{"name":"type3", "type":"string", "label":"Type 3", "filter":"string"},
				{"name":"type4", "type":"string", "label":"Type 4", "filter":"string"},
				{"name":"type5", "type":"string", "label":"Type 5", "filter":"string"},
				{"name":"type6", "type":"string", "label":"Type 6", "filter":"string"},
				{"name":"type7", "type":"string", "label":"Type 7", "filter":"string"}				
			],
			"availableColumns" : [ 
				{"name":"type8", "type":"string", "label":"Type", "filter":"string"}
			]
		};
		return [defaultTemplate,template2];
	}


}
export default ConfigAPI;