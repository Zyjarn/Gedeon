
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
}
export default ConfigAPI;